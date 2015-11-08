package employee;

import java.io.IOException;
import java.text.ParseException;
import shopServices.TillServices;
import inputOutput.OutputService;


/**
 * Class to represent an employee of Sales Assistant type
 * @author Louise Allen
 * @version 1.0
 *
 */
public class SalesAssistant extends Employee {

	/**
	 * No arg constructor to create a SalesAssistant Object
	 */
	public SalesAssistant(){
		super();
	}
	
	/**
	 * Constructor that takes creates a Sales Assistant and sets its username, password and name by calling its super constructer 
	 * @param username 
	 * @param password
	 * @param name
	 */
	public SalesAssistant(String username, String password, String name) {
		super(username, password, name);
	}

	/**
	 * Abstract method that overrides the showMenu method in it's super class and displays the menu options a Sales Assistant should have.
	 */
	@Override
	public void showMenu() {
		OutputService output = new OutputService();
		String choices = 	"1. Initiate Transaction\n" +
							"2. Return Item\n" +
							"0.Exit\n";
		output.outputData(choices);
		
	}

	/**
	 *  Executes a specific function depending on which choice the user made.
	 *  Creates a TillServices object to execute functionality.
	 *  @param functionNumber Function number represents the choice the user made in the main menu
	 *  @param name First name of logged in staff member
	 */
	@Override
	public boolean executeFunction(int functionNumber, String name) throws IOException, ParseException {
		TillServices ts = new TillServices();
		switch(functionNumber){
			case(0): return false;
			case (1): ts.initiateNewTransaction(name);
			break;
			case (2): ts.returnItem(name);
			break;
		}
		return true;
	}

}
