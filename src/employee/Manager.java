package employee;

import java.io.IOException;
import java.text.ParseException;

import shopServices.*;
import inputOutput.*;
/**
 * Class to represent an employee of Manager type
 * @author Louise Allen
 * @version 1.0
 */
public class Manager extends Employee {

	
	/**
	 * No arg constructor to create a Manager Object
	 */
	public Manager(){
		super();
	}
	
	/**
	 * Constructor that takes creates a Manager and sets its username, password and name by calling its super constructer 
	 * @param username 
	 * @param password
	 * @param name
	 */
	public Manager(String username, String password, String name){
		super(username, password, name);
	}

	/**
	 * Abstract method that overrides the showMenu method in it's super class and displays the menu options a Manager should have.
	 */
	@Override
	public void showMenu() {
		OutputService output = new OutputService();
		String choices = 	"1. Initiate Transaction\n" +
							"2. Return Item\n" +
							"3. Display summary of all items currently in stock\n" +
							"4. Display details related to specific item\n" +
							"5. Display summary of all sales/hires\n" +
							"6. Add new item to stock\n" +
							"7. Update stock level\n" + 
							"8. Remove employee from records\n" +
							"9. Add new employee\n" +
							"0.Exit\n";
		output.outputData(choices);
		
	}

	/**
	 *  Executes a specific function depending on which choice the user made.
	 *  Creates TillServices, StockService and Employee Service objects to execute functionality.
	 *  @param functionNumber Function number represents the choice the user made in the main menu
	 *  @param name First name of logged in staff member
	 */
	@Override
	public boolean executeFunction(int functionNumber, String name) throws IOException, ParseException {
		TillServices ts = new TillServices();
		StockServices ss = new StockServices();
		EmployeeServices es = new EmployeeServices();
		switch(functionNumber){
			case(0): return false;
			case (1): ts.initiateNewTransaction(name);
			break;
			case (2): ts.returnItem(name);
			break;
			case (3): ts.displayInStock();
			break;
			case (4): ts.specificItemDetailsDisplay();
			break;
			case (5): ts.summaryOfAllSalesHires();
			break;
			case (6): ss.addNewItemToStock();
			break;
			case (7): ss.addStock();
			break;
			case (8): es.removeEmployee();
			break;
			case (9): es.addEmployee();
			break;
		}
		return true;
	}
	


}
