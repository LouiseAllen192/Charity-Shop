package employee;

import java.io.IOException;
import java.text.ParseException;

/**
 * Abstract class that represents an employee using the system
 * @value userId Employee userID/staff number
 * @value password Employee password for logging in to system
 * @value name Employee first name
 * @author Louise Allen
 * @version 1.0
 *
 */
public abstract class Employee {

	//userId & password not used. Included in employee for extensibility purposes only
	private String userId;
	private String password;
	private String name;
	
	
	/**
	 * No argument constructor to create an Employee object
	 */
	public Employee(){
	}
	
	/**
	 * Constructor that takes in a value for username, password & name and populates the employee details
	 * @param username
	 * @param password
	 * @param name
	 */
	public Employee(String username, String password, String name){
		userId = username;
		this.password = password;
		this.name = name;
	}
	
	/**
	 * Getter method to return the employee name
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Abstract method to display the options a certain employee member will have in the system
	 */
	public abstract void showMenu();
	
	/**
	 * Abstract function to execute the option the user chose to do
	 * @param functionNumber represents option the user chose in the menu
	 * @param name represents name of employee logged in and serving the customer
	 * @return boolean to represent whether the user wants to exit the system or not
	 * @throws IOException
	 * @throws ParseException
	 */
	public abstract boolean executeFunction(int functionNumber, String name) throws IOException, ParseException;
	
		
}
