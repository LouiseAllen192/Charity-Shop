package systemPackage;

import inputOutput.*;
import java.io.IOException;
import java.text.ParseException;
import shopServices.LoginService;
import employee.*;

/**
 * This class starts our system and displays the main menu.
 * @value Employee Stores a logged in employee
 * @value OutputService output service object used to output data to screen
 * @author Louise Allen
 * @version 1.0
 *
 */
public class ShopSystem {

	private Employee loggedInEmployee;
	private OutputService output;
	
	/**
	 * Constructor that initializes data fields & file name service.
	 * Also displays welcome message to logged in employee.
	 * Then starts the main menu.
	 * @throws IOException
	 * @throws ParseException
	 */
	public void startSystem() throws IOException, ParseException {
		output = new OutputService();
		FileNameService.initialiseFileNameService();
		LoginService loginService = new LoginService();
		loggedInEmployee = loginService.logIn();
		displayWelcome();
		mainMenu();
		
	}
	
	/**
	 * Runs a loop that will only end when user selects 0 (exit) from main menu.
	 * Shows a different menu depending on which type employee type is logged in. <br>
	 * Inputs number user selects from menu. 
	 * Runs executeFunction method passing user choice to it.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void mainMenu() throws IOException, ParseException {
		InputService input = new InputService();
		boolean repeat = true;
		while(repeat){
			output.outputHeader("Main Menu");
			loggedInEmployee.showMenu();
			int inputNumber=0;
			String inputString;
			boolean valid = false;
			while(!valid){
				inputString = input.takeInput("\nPlease choose a number:");
				String pattern = "";
				if (loggedInEmployee instanceof Manager)	pattern = "[0-9]{1}";
				else										pattern = "[0-2]{1}";
				if(inputString.matches(pattern)){
					valid=true;
					inputNumber = Integer.parseInt(inputString);
				}
				else{
					output.outputErrorMsg("Incorrect format of input.\nPlease try again\n");
				}
			}
			repeat = loggedInEmployee.executeFunction(inputNumber, loggedInEmployee.getName());
		}
		
		output.outputData("\nGoodbye");
		System.exit(0);
	}

	/**
	 * Displays welcome message to logged in employee
	 */
	private void displayWelcome(){
		String header = "Welcome", welcomeMsg = "\nHello " + loggedInEmployee.getName() + "\nWelcome back :)\n\n";
		output.outputHeader(header);
		output.outputData(welcomeMsg);
	}
	

}
