package systemPackage;

import java.io.File;

import inputOutput.*;

public class FileNameService {
	
	public static String employeeFile, stockFile, transactionFile, forHireFile;
	
	
	public static void setFileNameService(){
		employeeFile = getFilenameInput(employeeFile, "Enter employee filename: ");
		stockFile = getFilenameInput(stockFile, "Enter stock filename: ");
		transactionFile = getFilenameInput(transactionFile, "Enter transaction filename: ");
		forHireFile = getFilenameInput(forHireFile, "Enter for-Hire filename: ");
	}
	
	public static String getStockFile(){
		return stockFile;
	}
	
	public static String getTransactionFile(){
		return transactionFile;
	}
	
	public static String getEmployeeFile() {
		return employeeFile;
	}
	
	public static String getForHireFile() {
		return forHireFile;
	}
	
	private static boolean checkIfFileExists(String filename){
		boolean exists=false;
		File aFile = new File(filename);
		if (aFile.exists()){
			exists=true;
		}
		return exists;
	}
	
	private static String getFilenameInput(String filename, String prompt){
		InputService input = new InputService();
		OutputService output = new OutputService();
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
