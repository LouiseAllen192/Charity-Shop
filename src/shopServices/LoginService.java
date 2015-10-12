package shopServices;


import java.io.IOException;

import systemPackage.FileNameService;
import employee.*;
import fileAccess.FileReaderService;
import inputOutput.*;


public class LoginService {

	public Employee logIn() throws IOException {
		
		OutputService output = new OutputService();
		String username, password;
		
		output.outputData("\n\n**********************  Log in Page   **********************\n\n");
		
		username= getUserName();
		password = getPassword(username);
		
		
		Employee loggedInEmployee = new Employee(username, password);
		
		return loggedInEmployee; 
	}


	private boolean checkIfValidPassword(String password, String username) throws IOException {
		FileReaderService fr = new FileReaderService();
		String lineContainingUsername = fr.returnRowContainingString(username, FileNameService.getEmployeeFile());
		String[] parts = lineContainingUsername.split(",");
		return (parts[1].equals(password)) ? true : false;
	} 

	private boolean checkIfValidUserName(String username) throws IOException {
		FileReaderService fr = new FileReaderService();
		return fr.checkIfStringIsInColumnInFile(username, 0, FileNameService.getEmployeeFile());
	}
	
	private String getUserName() throws IOException{
		InputService input = new InputService();
		OutputService output = new OutputService();
		String error = "That is not a valid username\nUse your employee number as your username\nPlease try again\n";
		String username="";
		Boolean valid = false;
		
		while(!valid){
			username = input.takeInput("Enter username: ");
				if (checkIfValidUserName(username)){
					valid = true;
				}
				else{
					output.outputErrorMsg(error);
				}
			}
		return username;
	}
	
	private String getPassword(String username) throws IOException {
		InputService input = new InputService();
		OutputService output = new OutputService();
		String error = "Incorrect password\nNote:Password is case sensitive\nPlease try again\n";
		String password="";
		Boolean valid = false;
		
		while(!valid){
			password = input.takeInput("Enter password: ");
				if (checkIfValidPassword(password, username)){
					valid = true;
				}
				else{
					output.outputErrorMsg(error);
				}
			}
		return password;
	}


}
