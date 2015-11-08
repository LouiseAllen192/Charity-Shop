package transaction;

import inputOutput.*;

import java.io.IOException;
import java.text.*;
import java.util.*;

import systemPackage.FileNameService;
import fileAccess.*;

/**
 * Class to represent any type of transaction in the system
 * @author Louise Allen
 * @version 1.0
 *
 */
public class Transaction {
	
	
	private int transactionNumber;
	private Date dateOfTransaction;
	private String servedBy;
	private double totalTransactionPrice;
	private ArrayList<TransactionItemDetails> itemDetails;
	
	private ArrayList<LineItem> lineItems;
	private ArrayList<HiredOutItem> hiredDetails;
	private Receipt receipt;
	
	private boolean validCodeQty;
	private boolean finishedInput;
	
	InputService input = new InputService();
	OutputService output = new OutputService();
	FileReaderService stockfr;
	
	
	/**
	 * No arg constructor to create a transaction object
	 */
	public Transaction (){
	}
	
	
	/**
	 * Constructor that creates a new sale or hire transaction with new data fields
	 * @param nameServer Name of employee serving customer
	 * @throws IOException
	 * @throws ParseException
	 */
	public Transaction (String nameServer) throws IOException, ParseException{
		FileReaderService transactionFile = new FileReaderService(FileNameService.getTransactionFile());
		hiredDetails = new ArrayList<HiredOutItem>();
		lineItems = new ArrayList<LineItem>();
		validCodeQty = false;
		servedBy = nameServer;
		dateOfTransaction = new Date();
		transactionNumber = transactionFile.numberOfLinesInFile() + 1;
		itemDetails = inputItems();
		
		receipt = new Receipt(dateOfTransaction, servedBy, lineItems, transactionNumber, hiredDetails);
		totalTransactionPrice = receipt.getTotalSalePrice();
	}

	
	/**
	 * Constructor that creates a transaction object from a transaction listing on the transaction file
	 * @param tranNum Unique number assigned to the transaction 
	 * @param dateofT Date that transaction took place
	 * @param nameServer Name of staff member logged in
	 * @param details Contains the specific details relating to each item in the transaction
	 * @throws ParseException
	 */
	public Transaction (String tranNum, String dateofT, String nameServer, String details) throws ParseException{
		transactionNumber = Integer.parseInt(tranNum);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dateOfTransaction = df.parse(dateofT);
		servedBy = nameServer;
		itemDetails = new ArrayList<TransactionItemDetails>();
		
		String[] parts = details.split(":");
		for(int i=0; i< parts.length; i++){
			String[] parts2 = parts[i].split("/");
			itemDetails.add(new TransactionItemDetails(parts2[0], parts2[1], Integer.parseInt(parts2[2])));
		}
	}
	
	/**
	 * Constructor to create a transaction of return type
	 * @param lateFee represents the amount of a late fee due
	 * @param servedBy Name of staff member logged in to the system
	 * @param hiredDetails List of the details relating to each specific item that is being hired out
	 * @throws IOException
	 */
	public Transaction (double lateFee, String servedBy, ArrayList<HiredOutItem> hiredDetails) throws IOException{
		stockfr = new FileReaderService(FileNameService.getStockFile());
		FileReaderService transactionFile = new FileReaderService(FileNameService.getTransactionFile());
		transactionNumber = transactionFile.numberOfLinesInFile() + 1;
		dateOfTransaction = new Date();
		this.servedBy = servedBy;
		totalTransactionPrice = lateFee;
		itemDetails = new ArrayList<TransactionItemDetails>();
		for(int i=0; i<hiredDetails.size();i++){
			itemDetails.add(new TransactionItemDetails(hiredDetails.get(i).getItemCode(), "r", hiredDetails.get(i).getQty()));
		}
	}
	
	/**
	 * Accessor method to get transaction number
	 * @return transactionNumber Unique number assigned to the transaction 
	 */
	public int getTransactionNumber(){
		return transactionNumber;
	}
	
	/**
	 * Accessor method to get transaction  date
	 * @return dateOfTransaction Date that transaction took place
	 */
	public Date getDateOfTransaction(){
		return dateOfTransaction;
	}
	
	/**
	 * Accessor method to get name of employee serving customer
	 * @return servedBy Name of staff member logged in to the system
	 */
	public String getServedBy(){
		return servedBy;
	}
	
	/**
	 * Accessor method to get transaction item details
	 * @return itemDetails List of details relating to each item in transaction
	 */
	public ArrayList<TransactionItemDetails> getItemDetails(){
		return itemDetails;
	}
	
	/**
	 * Accessor method to get transaction lineItems
	 * @return lineItems List of lines that will print on receipt
	 */
	public ArrayList<LineItem> getLineItems(){
		return lineItems;
	}
	
	
	/**
	 * Inputs the details of the transaction. Validates input and populates fields
	 * @return itemDetails List of details relating to each item in transaction
	 * @throws IOException
	 * @throws ParseException
	 */
	private ArrayList<TransactionItemDetails> inputItems() throws IOException, ParseException {
		
		stockfr = new FileReaderService(FileNameService.getStockFile());
		itemDetails = new ArrayList<TransactionItemDetails>();
		
		int qty=0, stockNumber=0;
		String promptForCode = "\nEnter item code:  ", codeFormatError = "Invalid item code format.\nPlease enter a number.\n\n";
		String invalidCodeError = "The item code you have entered is not on our system.\nPlease try again.\n";
		String promptForQty = "Enter qty of item required:", qtyFormatError = "Invalid input format\nPlease enter a number\n\n";
		String noStockError = "This item is not in stock\n", ynError = "Invalid entry. Please enter Y or N\n"; 
		String removeProduct = "This product has been removed from transaction", ynPattern = "(y|Y|n|N)", saleOrHire="", code = null;
		
		// loops while full transaction not finished
		while(!finishedInput){
			
			//loops until valid code & qty entered
			while(!validCodeQty){
				code = input.takeInput(promptForCode, codeFormatError, "[\\d]+");
				if(!validCode(code)){
					output.outputErrorMsg(invalidCodeError);
				}
				else{
					qty = Integer.parseInt(input.takeInput(promptForQty, qtyFormatError , "[\\d]+"));
					stockNumber = numberInStock(code);
					
					//if no stock left
					if(stockNumber==0){
						output.outputData(noStockError);
					}
					
					// if number qty required is more than what we have in stock
					// give option of buying all the ones we have in stock or remove item
					else if(qty>stockNumber){
						String prompt = "We do not have " + qty + " of this item in stock. We only have " + stockNumber +
								"\nWould the customer like to buy/hire " + stockNumber + " of these items?: Y/N\n";
						String yOrN = input.takeInput(prompt, ynError,ynPattern);
						if(yOrN.equalsIgnoreCase("n")){
							output.outputData(removeProduct + "\n");
						}
						else{
							qty = stockNumber;
							saleOrHire = determineSaleOrHire(code);
							validCodeQty = true;
						}
					}
					
					// valid code & qty
					else{
						saleOrHire = determineSaleOrHire(code);
						validCodeQty = true;
					}	
				}
			}
			if(validCodeQty){
				if(saleOrHire.equals("h"))		itemIsHireItem(saleOrHire, code, qty, ynError, removeProduct, ynPattern);
				else							itemIsSaleItem(saleOrHire, code, qty, ynError, ynPattern);
			}
		}
		return itemDetails;
	}

	/**
	 * Checks stock amount for a specific item
	 * @param code unique item code
	 * @return number of a specific item that is in stock
	 * @throws IOException
	 */
	private int numberInStock(String code) throws IOException {
		String[] parts = stockfr.returnRowBeginingWith(code).split(",");
		return Integer.parseInt(parts[3]);
	}
	
	/**
	 * Checks if code is a valid code from systems Stock file
	 * @param code Unique code relating to specific stock item
	 * @return true if valid, false if invalid
	 * @throws IOException
	 */
	private boolean validCode(String code) throws IOException{
		if(stockfr.checkIfStringIsInColumnInFile(code, 0)){
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if an item is for sale or for hire 
	 * @param code Unique code relating to specific stock item
	 * @return String that will be either "s" for Sale or "h" for Hire
	 * @throws IOException
	 */
	private String determineSaleOrHire(String code) throws IOException {
		FileReaderService stockfr = new FileReaderService(FileNameService.getStockFile());
		String[] parts = stockfr.returnRowBeginingWith(code).split(",");
		return parts[5];
	}
	
	/**
	 * Informs user item is for hire & asks them if they want to hire item<br>
	 * If no: Removes item
	 * If yes: Processes the hire
	 * @param saleOrHire "s" for Sale or "h" for hire
	 * @param code Unique code relating to specific stock item
	 * @param qty Quantity of item
	 * @param ynError Error message that will print if user enters something other than y or n
	 * @param remove Product Message that will print if item was successfully added for hire
	 * @param ynPattern Regex pattern for y or n input
	 * @throws IOException
	 * @throws ParseException
	 */
	private void itemIsHireItem(String saleOrHire, String code, int qty, String ynError, String removeProduct, String ynPattern) throws IOException, ParseException {
		String promptHireItem = "This item is for hire and not for sale.\nWould the customer like to hire this item? Y/N\n";
		String hireItemYN = "";
		hireItemYN = input.takeInput(promptHireItem, ynError, ynPattern);
		if(hireItemYN.equalsIgnoreCase("n")){
			output.outputData(removeProduct + "\n");
			addAnotherItem(ynError, ynPattern);
		}
		else{
			processHire(saleOrHire, code, qty, ynError, ynPattern);
		}
	}
	
	/**
	 *<h4> Process a hire</h4>
	 * Gets number of days user would like to hire for. Calculates & displays return date.<br>
	 * Adds details of hire to itemDetailss list, line Items list & hired out items list.<br>
	 * Adds details to hiredOutItems file & updates stock
	 * @param saleOrHire "s" for Sale or "h" for hire
	 * @param code Unique code relating to specific stock item
	 * @param qty Quantity of item
	 * @param ynError Error message that will print if user enters something other than y or n
	 * @param ynPattern Regex pattern for y or n input
	 * @throws IOException
	 * @throws ParseException
	 */
	private void processHire(String saleOrHire, String code, int qty, String ynError, String nyPattern) throws IOException, ParseException {
		String promptNumDays = "Enter number of days you would like to hire items for: (1-5)\n";
		String errorNumDays = "Incorrect format for input. Must be number between 1 and 5";
		int numDays=1;
		TransactionItemDetails itemDets = new TransactionItemDetails(code, saleOrHire, qty);
		itemDetails.add(itemDets);
		numDays = Integer.parseInt(input.takeInput(promptNumDays, errorNumDays, "[1-5]{1}"));
		Date returnDate = calculateReturnDate(numDays);
		output.outputData("This item has been successfully added for hire.\n");
		String out = "\nThe customer must return this item by " + returnDate +
				"\nIf the item is not returned by this date there will be a €10 late charge for every day it is late.\n\n";
		output.outputData(out);
		Item item1 = createItem(code);
		lineItems.add(new LineItem(item1, qty));
		HiredOutItem hire = new HiredOutItem(transactionNumber, dateOfTransaction, code, qty, returnDate, item1.getName());
		hiredDetails.add(hire);
		addHireToHiredOutFile(hire, qty);
		updateStock(itemDets);
		addAnotherItem(ynError, nyPattern);
	}
	
	/**
	 * Calculates the return date based on the date of the transaction plus the duration of hire
	 * @param numDays number of days customer would like to hire for
	 * @return returnDate Date item must be returned by
	 */
	private Date calculateReturnDate(int numDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOfTransaction);            
		calendar.add(Calendar.DAY_OF_YEAR, numDays);
		Date returnDate = calendar.getTime();
		return returnDate;
	}
	
	/**
	 * Adds the hire item details to the HiredOutItems file 
	 * @param hire The hire details contained in a hiredOutItem object
	 * @param qty Quantity of item being hired
	 * @throws IOException
	 */
	private void addHireToHiredOutFile(HiredOutItem hire, int qty) throws IOException {
		FileReaderService hiredOutFR = new FileReaderService(FileNameService.getForHireFile());
		FileWriterService hiredOutFWriter = new FileWriterService();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String hireString = transactionNumber + "," + df.format(dateOfTransaction) + "," + hire.getItemCode() + "," + qty + "," +  df.format(hire.getReturnDate()) ; 
		if(hiredOutFR.numberOfLinesInFile() == 0){
			ArrayList<String> arr = new ArrayList<String>();
			arr.add(hireString);
			hiredOutFWriter.writeToFile(FileNameService.getForHireFile(), arr);
		}
		else{
			hiredOutFWriter.appendToExistingFile(hireString, FileNameService.getForHireFile());
		}
		
	}

	
	/**
	 * <h3>Process Sale</h3>
	 * Adds item details to item details list & line items lists.
	 * @param saleOrHire "s" for Sale or "h" for hire
	 * @param code Unique code relating to specific stock item
	 * @param qty Quantity of item
	 * @param ynError Error message that will print if user enters something other than y or n
	 * @param ynPattern Regex pattern for y or n input
	 * @throws IOException
	 */
	private void itemIsSaleItem(String saleOrHire, String code, int qty, String ynError, String ynPattern) throws IOException {
		TransactionItemDetails itemDets = new TransactionItemDetails(code, saleOrHire, qty);
		itemDetails.add(itemDets);
		output.outputData("This item has been successfully added for purchase.\n\n");
		lineItems.add(new LineItem(createItem(code), qty));
		updateStock(itemDets);
		addAnotherItem(ynError, ynPattern);
	}

	/**
	 * Asks user if they want to add another item<br>
	 * If yes: resets validCodeQty to false<br>
	 * If no: Sets finishedInput flag to true
	 * @param ynError Error message that will print if user enters something other than y or n
	 * @param ynPattern Regex pattern for y or n input
	 */
	private void addAnotherItem(String ynError, String ynPattern){
		String prompt = "Add another item? Y/N\n";
		String addAnotherYorN = input.takeInput(prompt,ynError,ynPattern);
		if(addAnotherYorN.equalsIgnoreCase("n"))	finishedInput = true;
		else	validCodeQty = false;
	}

	/**
	 * Gets details of item from stock file and creates item object
	 * @param code Unique code relating to specific stock item
	 * @return an item object holding all the deatils of the specific item
	 * @throws IOException
	 */
	private Item createItem(String code) throws IOException {
		String lineFromFile = stockfr.returnRowBeginingWith(code);
		String[] x = lineFromFile.split(",");
		Item item = new Item(x[0], x[1], x[2], x[4], x[5]);
		return item;
	}

	/**
	 * Converts transaction details to a String
	 * @return result String with transaction details merged together in a comma seperated way
	 */
	public String transToString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String result = transactionNumber + "," + df.format(dateOfTransaction) + "," + servedBy + "," + totalTransactionPrice + ",";
		for(int i=0; i<itemDetails.size();i++){
			result+=itemDetails.get(i).detailsToString() + ":";
		}
		return result;		
	}
	
	/**
	 * Updates stock quantities according to what was taken from the transaction
	 * @param itemDetail List of the details relating to each item in the transaction
	 * @throws IOException
	 */
	public void updateStock(TransactionItemDetails itemDetail) throws IOException {
		ArrayList<String> updatedFileContents = stockfr.getFileContents();
		int i;
		FileWriterService fwriter = new FileWriterService();
		boolean found=false;
		String[] parts = null;
		
		for(i=0; i<updatedFileContents.size() && !found;  i++){
			String lineFromFile = updatedFileContents.get(i);
			String[] arr = lineFromFile.split(",");
			if(arr[0].equals("" + itemDetail.getItemCode())){
				found=true;
				parts =  lineFromFile.split(",");
			}
		}
		
		if(itemDetail.getSaleHireReturn().equals("r"))	parts[3] = "" + (Integer.parseInt(parts[3]) + itemDetail.getQuantity());
		else							parts[3] = "" + (Integer.parseInt(parts[3]) - itemDetail.getQuantity());
		String changedLine ="";
		for(int j=0; j<parts.length; j++){
			changedLine+= parts[j] + ",";
		}
		updatedFileContents.set((i-1), changedLine);
		fwriter.writeToFile(FileNameService.getStockFile(), updatedFileContents);
		stockfr.readFile(FileNameService.getStockFile());
	}

	/**
	 * diplays receipt relating to transcation
	 */
	public void printReceipt() {
		receipt.displayReceipt();
	}

	
}
