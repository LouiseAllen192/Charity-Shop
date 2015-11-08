package systemPackage;

import java.io.File;

import inputOutput.*;

/**
 * Class to get and store file names used in system.
 * @author Louise Allen
 * @version 1.0
 *
 */
public class FileNameService {
	
	private static String employeeFile, stockFile, transactionFile, forHireFile;
	
	/**
	 * Gets user choice as to whether they want to use default filenames or enter their own.<br>
	 * If they want to use default it assigns the default filenames.<br>
	 * If they want to enter their own it enters them and makes sure they are valid file names that are on file
	 */
	public static void initialiseFileNameService(){
		boolean valid = false;
		InputService input = new InputService();
		OutputService output = new OutputService();
		String header = "File Name Input";
		String prompt = "Would you like to use default filenames? Y/N\n";
		output.outputHeader(header);
		while(!valid){
			String choise = input.takeInput(prompt);
			if(choise.equals("Y") || choise.equals("y")){
					valid = true;
					employeeFile = "Employee.txt";
					stockFile = "StockDetails.txt";
					transactionFile= "Transactions.txt";
					forHireFile = "HiredOutItems.txt";
			}
			else if(choise.equals("N") || choise.equals("n")){
					valid=true;
					employeeFile = getFilenameInput("Enter employee filename: ", input, output);
					stockFile = getFilenameInput("Enter stock filename: ", input, output);
					transactionFile = getFilenameInput("Enter transaction filename: ", input, output);
					forHireFile = getFilenameInput("Enter for-Hire filename: ", input, output);
			}
			else{
					valid = false;
					output.outputErrorMsg("Incorrect entry. Please enter Y or N\n");
			}
		}
		
		
	}
	
	/**
	 * Returns the name of the file that contains all stock details
	 * @return stockFile Name of file with stock contents
	 */
	public static String getStockFile(){
		return stockFile;
	}
	
	/**
	 * Returns the name of the file that contains all transaction details
	 * @return transactionFile Name of file with stock contents
	 */
	public static String getTransactionFile(){
		return transactionFile;
	}
	
	/**
	 * Returns the name of the file that contains all employee details
	 * @return  employeeFile Name of file with transaction contents
	 */
	public static String getEmployeeFile() {
		return employeeFile;
	}
	
	/**
	 * Returns the name of the file that contains details of hired out items
	 * @return forHireFile Name of file with details of hired out items
	 */
	public static String getForHireFile() {
		return forHireFile;
	}
	
	/**
	 * Checks if filename exists on your system
	 * @param filename Name of file you want to check
	 * @return exists boolean representing whether valid or not
	 */
	private static boolean checkIfFileExists(String filename){
		boolean exists=false;
		File aFile = new File(filename);
		if (aFile.exists()){
			exists=true;
		}
		return exists;
	}
	
	/**
	 * Prompts user to enter a valid filename. Will return the filename if it exists on the system
	 * @param prompt String to represent the input prompt for each file
	 * @param input InputService to take input from user
	 * @param output OnputService to output data to screen
	 * @return filename valid file name that is on the system
	 */
	private static String getFilenameInput(String prompt, InputService input, OutputService output){
		String filename="";
		String error = "That file does not exist.\nPlease enter the name of a file that is on your system\n";
		Boolean valid = false;
		while(!valid){
			filename = input.takeInput(prompt);
			if (checkIfFileExists(filename)){
				valid = true;
			}
			else{
				output.outputErrorMsg(error);
			}
		}
		return filename;
	}

}
