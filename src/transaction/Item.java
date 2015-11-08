package transaction;

import inputOutput.InputService;
import java.io.IOException;
import fileAccess.FileReaderService;
import systemPackage.FileNameService;

/**
 * Class to represent an item in the shop system
 * @author Louise Allen
 * @version 1.0
 *
 */
public class Item {
	
	private String code;
	private String name;
	private double price;
	private String description;
	private String saleOrHire;
	
	
	/**
	 * Constructor for creating Item object using existing entry in Stock file
	 * @param code String to represent item code
	 * @param name String to represent item name
	 * @param price String to represent item price
	 * @param description String to represent item description
	 * @param saleOrHire String to represent whether item is for sale or hire
	 */
	public Item(String code, String name, String price, String description, String saleOrHire){
		this.code = code;
		this.name = name;
		this.price = Double.parseDouble(price);
		this.description = description;
		this.saleOrHire = saleOrHire;
	}
	
	/**
	 * Constructor for populating a new item
	 * @throws IOException
	 */
	public Item() throws IOException{
		InputService input = new InputService();
		FileReaderService stockfr = new FileReaderService(FileNameService.getStockFile());
		this.code = "" + (stockfr.numberOfLinesInFile() + 1);
		this.name = inputItemName(input);
		this.price = inputItemPrice(input);
		this.description = inputItemDescription(input);
		this.saleOrHire = inputSaleOrHire(input);
	}
	
	/**
	 * Getter method to return item price
	 * @return price Value representing price of item
	 */
	public double getPrice(){
		return price;
	}

	/**
	 * Getter method to return item name
	 * @return name Value representing name of item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter method to return item code
	 * @return code Value representing item code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Getter method to return item description
	 * @return description String value representing item description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Getter method to return if item is for sale or hire
	 * @return saleOrHire String value representing if item is for sale or hire
	 */
	public String getSaleOrHire() {
		return saleOrHire;
	}

	/**
	 * Takes in if item is for sale or hire
	 * @param input InputService to take data in from user
	 * @return validated user input
	 */
	private String inputSaleOrHire(InputService input) {
		String prompt = "Please enter whether this item is for sale or hire:\ns = Sale\nh = Hire\nEnter s or h\n";
		String error = "Incorrect input format.Must be either s or h\nPlease try again\n\n";
		String pattern = "s|h";
		return input.takeInput(prompt, error, pattern);
	}

	/**
	 * Takes in item description
	 * @param input InputService to take data in from user
	 * @return validated user input
	 */
	private String inputItemDescription(InputService input) {
		String prompt = "Please enter item description:\n";
		String error = "Incorrect input format.Must be contain at least 1 letters\nPlease try again\n\n";
		String pattern = ".*[a-zA-Z]+.*";
		return input.takeInput(prompt, error, pattern);
	}

	/**
	 * Takes in item price
	 * @param input InputService to take data in from user
	 * @return validated user input
	 */
	private double inputItemPrice(InputService input) {
		String prompt = "Please enter item price:\n";
		String error = "Incorrect input format.Must be contain a digit\nPlease try again\n\n";
		String pattern = "^\\d+\\.\\d+$|\\d+";
		return Double.parseDouble(input.takeInput(prompt, error, pattern));
	}

	/**
	 * Takes in item name
	 * @param input InputService to take data in from user
	 * @return validated user input
	 */
	private String inputItemName(InputService input) {
		String prompt = "Please enter item name:\n";
		String error = "Incorrect input format.\nInput can not contain spaces or numbers and must contain at leaast one character\nPlease try again\n\n";
		String pattern = "^[^\\d\\s]+$";
		return input.takeInput(prompt, error, pattern);
	}
	
	
}
