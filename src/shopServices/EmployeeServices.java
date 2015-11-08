package shopServices;

import java.io.IOException;
import systemPackage.FileNameService;
import fileAccess.*;
import inputOutput.*;

/**
 * Class that completes functionality relating to employees
 * {@value} input InputService to take input from user
 * {@value} output OuputService to output data to screen
 * @author Louise Allen
 * @version 1.0
 *
 */
public class EmployeeServices {

	InputService input;
	OutputService output;
	
	/**
	 * No args constructor that creates an employee and initializes it's input & output providers
	 */
	public EmployeeServices(){
		input = new InputService();
		output = new OutputService();
	}
	
	/**
	 * Inputs employee file details to be added and
	 * adds a new Employee to the employee file
	 * @throws IOException
	 */
	public void addEmployee() throws IOException {
		output.outputHeader("Add new Employee");
		FileWriterService fr = new FileWriterService();
		String id, password, ename, etype, lineToAdd;
		
		id = inputID("Add", "The id number you entered is already in use.\nPlease choose a different employee id number\n\n");
		password = inputPassword();
		ename = inputEname();
		etype = inputEType();
		lineToAdd=id + "," + password + "," + ename + "," + etype;
		fr.appendToExistingFile(lineToAdd, FileNameService.getEmployeeFile());
		
	}
	
	/**
	 * Inputs type of employee to be added
	 * @return etype Type of employee
	 */
	private String inputEType() {
		String etypePrompt = "What type of employee are you adding?\n1.Manager\n2.Sales Assistant:\n\nEnter 1 or 2\n\n";
		String etypeError = "\nIncorrect format. Input can either be 1 or 2\nPlease try again\n\n"; 
		String etypePattern =  "1|2";
		String etype = input.takeInput(etypePrompt, etypeError, etypePattern);
		if(etype.equals("1"))	etype = "m";
		else					etype = "s";
		return etype;
	}

	/**
	 * Inputs employee first name to be added to new employee
	 * @return ename Employee first name
	 */
	private String inputEname() {
		String enamePrompt = "Enter employee first name:\n";
		String enameError = "Incorrect format. Input can not contain spaces or numbers and must contain at least one character.\nPlease try again\n\n"; 
		String enamePattern =  "^[^\\d\\s]+$";
		String ename = input.takeInput(enamePrompt, enameError, enamePattern);
		return ename;
	}

	/**
	 * Inputs employee password to be added to new employee
	 * @return password
	 */
	private String inputPassword() {
		String promptPassword = "Enter employee password:\n";
		String patternPass = "(?=^.{5,20}$)(?=(.*\\d){1})(?=(.*[A-Za-z]){2})(?!.*[\\s])^.*";
		String errorPassword = "Incorrect password format. \nPassowrd must: 5-20 length\nnot contain spaces\ncontain at least 1 digits\n" +
								"contain at least 2 letters (case insensitive)\n\nPlease try again\n\n"; 
		String password = input.takeInput(promptPassword, errorPassword, patternPass);
		return password;
	}

	/**
	 * Inputs id of employee to either be added as new employee of be deleted from file
	 * @param RemOrAdd String representing if you are getting an id of an employee to add or remove
	 * @param alreadyThere String to output when the id entered is already in file
	 * @return id validated id number of new employee
	 * @throws IOException
	 */
	private String inputID(String RemOrAdd,  String alreadyThere) throws IOException {
		String promptID = "Enter employee id number:\n";
		String errorID = "Incorrect input format. Must be 5 digits\nPlease try again\n\n";
		String patternID = "[\\d]{5}", id="";
		boolean validID = false;
		if (RemOrAdd.equals("Rem")){
			while(!validID){
				id = input.takeInput(promptID, errorID, patternID);
				if(idInFileAlready(id)){
					validID = true;
				}
				else{
					output.outputData(alreadyThere);
				}
			}
		}
		else{
			id = input.takeInput(promptID, errorID, patternID);
			while(!validID){
				if(!idInFileAlready(id)){
					validID = true;
				}
				else{
					output.outputData(alreadyThere);
				}
			}
		}
				return id;
	}

	/**
	 * Removes employee from employee file
	 * @throws IOException
	 */
	public void removeEmployee() throws IOException {
		output.outputHeader("Remove Employee");
		String empToBeRemoved = inputID("Rem" , "That ID number is not on our file.Please try again\n\n");
		boolean sure = checkIfSure(empToBeRemoved);
		if(sure){
			FileWriterService fr = new FileWriterService();
			fr.deleteLineFromFileFirstColString(empToBeRemoved, FileNameService.getEmployeeFile());
			output.outputData("Employee has been sucessfully removed\n");
		}
		else{
			output.outputData("Employee has not been removed from system\n");
		}
	}

	/**
	 * Asks user if they are sure they want to delete the employee
	 * @param empNo employee number of employee to be removed
	 * @return boolean representing true if user is sure with their choice
	 */
	private boolean checkIfSure(String empNo) {
		String prompt = "Are you sure you want to erase employee number " + empNo + " from the system? Y/N\n";
		String error = "\nIncorrect format. Input needs to be Y or N. \n\n";
		String pattern = "y|Y|n|N";
		String sure = input.takeInput(prompt, error, pattern);
		if (sure.equalsIgnoreCase("y"))	return true;
		else 							return false;
	}

	/**
	 * Checks if id number is already in the employee file
	 * @param id id number to be checked
	 * @return true if id is already in the employee file
	 * @throws IOException
	 */
	private boolean idInFileAlready(String id) throws IOException {
		FileReaderService empfr = new FileReaderService(FileNameService.getEmployeeFile());
		return empfr.checkIfStringIsInColumnInFile(id, 0);
	}

}
