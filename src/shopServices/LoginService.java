package shopServices;


import java.io.IOException;
import systemPackage.FileNameService;
import employee.*;
import fileAccess.FileReaderService;
import inputOutput.*;

/**
 * Class to log an employee in to the system
 * @author Louise Allen
 * @version 1.0
 *
 */
public class LoginService {

	/**
	 * Takes in an username & password from the user. Checks if they are valid (i.e. already in the Employee file)<br>
	 * Determines which type of employee is logged in, creates an employee of that type  and returns the logged in employee
	 * @return loggedInEmployee Employee that is logged in to the system
	 * @throws IOException
	 */
	public Employee logIn() throws IOException {
		FileReaderService empfr = new FileReaderService(FileNameService.getEmployeeFile());
		OutputService output = new OutputService();
		InputService input = new InputService();
		String username, password, header = "Log in Page";
		output.outputHeader(header);
		username= inputUserName(output, empfr);
		password = inputPassword(username, output, input, empfr);
		Employee loggedInEmployee = determineEmployeeType(username, password, empfr);
		return loggedInEmployee; 
	}

	/**
	 * Checks through employee file to find employee details. 
	 * Creates Employee object that is either a Manager or a Sales Assistant
	 * @param username User name of employee trying to log in
	 * @param password Password of employee trying to log in
	 * @param empfr FileReader for Employee file.
	 * @return loggedInEmployee Employee that successfully logged in
	 * @throws IOException
	 */
	private Employee determineEmployeeType(String username, String password, FileReaderService empfr) throws IOException {
		String lineContainingUsername = empfr.returnRowContainingString(username);
		String[] parts = lineContainingUsername.split(",");
		Employee loggedInEmployee = null;
		if(parts[3].equals("m"))	loggedInEmployee = new Manager(username, password, parts[2]);
		if(parts[3].equals("s"))	loggedInEmployee = new SalesAssistant(username, password, parts[2]);
		return loggedInEmployee;
	}

	/**
	 * Checks if the password and user name entered match (i.e are on the same line of the file)
	 * @param password Employee password you are checking against correct username
	 * @param username Username that you have previously checked is valid
	 * @param empfr FileReader for Employee file.
	 * @return true if password & username match on file, otherwise return false
	 * @throws IOException
	 */
	private boolean checkIfValidPassword(String password, String username, FileReaderService empfr ) throws IOException {
		String lineContainingUsername = empfr.returnRowContainingString(username);
		String[] parts = lineContainingUsername.split(",");
		return (parts[1].equals(password)) ? true : false;
	} 

	/**
	 * Validates if username is already in the Employee file
	 * @param username usernameto be validated
	 * @param empfr FileReader for Employee file.
	 * @return true if username is on employee file in first column (username column)
	 * @throws IOException
	 */
	private boolean checkIfValidUserName(String username, FileReaderService empfr) throws IOException {
		return empfr.checkIfStringIsInColumnInFile(username, 0);
	}
	
	/**
	 * Takes in username from user and validates it
	 * @param output OuputService to output data to screen
	 * @param empfr FileReader for Employee file.
	 * @return username Valid username that is already on the employee file
	 * @throws IOException
	 */
	private String inputUserName(OutputService output, FileReaderService empfr) throws IOException{
		InputService input = new InputService();
		String error = "That is not a valid username\nUse your employee number as your username\nPlease try again\n";
		String username="";
		Boolean valid = false;
		
		while(!valid){
			username = input.takeInput("Enter username: ");
				if (checkIfValidUserName(username, empfr)){
					valid = true;
				}
				else{
					output.outputErrorMsg(error);
				}
			}
		return username;
	}
	
	/**
	 * Inputs username employee wants to log in with and validates it
	 * @param username valid username that is on the Employee file
	 * @param output OuputService to output data to screen
	 * @param input InputService to take input from user
	 * @param empfr FileReader for Employee file.
	 * @return password Valid password that matches with valid username
	 * @throws IOException
	 */
	private String inputPassword(String username, OutputService output, InputService input, FileReaderService empfr) throws IOException {
		String error = "Incorrect password\nNote:Password is case sensitive\nPlease try again\n";
		String password="";
		Boolean valid = false;
		
		while(!valid){
			password = input.takeInput("Enter password: ");
				if (checkIfValidPassword(password, username, empfr)){
					valid = true;
				}
				else{
					output.outputErrorMsg(error);
				}
			}
		return password;
	}


}
