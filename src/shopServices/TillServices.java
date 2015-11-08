package shopServices;

import inputOutput.*;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import fileAccess.FileReaderService;
import fileAccess.FileWriterService;
import systemPackage.FileNameService;
import transaction.HiredOutItem;
import transaction.Transaction;
import transaction.TransactionItemDetails;

/**
 * Class that completes functionality relating to all Till Services
 * @author Louise Allen
 * @version 1.0
 *
 */
public class TillServices {
	
	OutputService output = new OutputService();
	InputService input = new InputService();
	FileWriterService fr = new FileWriterService();
	
	/**
	 * No arg constructor to create tillServices object
	 */
	public TillServices(){
	}
	
	/**
	 * Creates an new transaction & prints receipt
	 * @param empName Name of logged in employee
	 * @throws IOException
	 * @throws ParseException
	 */
	public void initiateNewTransaction(String empName) throws IOException, ParseException{
		output.outputHeader("Transaction");
		Transaction newTran = new Transaction(empName);
		fr.appendToExistingFile(newTran.transToString(), FileNameService.getTransactionFile());
		newTran.printReceipt();
	}
	
	/**
	 * <h3>Returns an item</h3>
	 * Takes in a valid transaction number<br>
	 * Creates & displays list of hired out item details<br>
	 * Calculates late fee<br>
	 * Initiates a return transaction
	 * Adds return transaction details to transaction file
	 * Removes hired item details from HiredOutItems file
	 * @param staffName Name of logged in employee
	 * @throws IOException
	 * @throws ParseException
	 */
	public void returnItem(String staffName) throws IOException, ParseException{
		
		FileReaderService hiredOutFR = new FileReaderService(FileNameService.getForHireFile());
		ArrayList<HiredOutItem> hiredDetails;
		output.outputHeader("Return");
		String tranNum = retrieveValidTranNumber(hiredOutFR);
		hiredDetails = createHiredItemsList(hiredOutFR, tranNum);
		displayHireDetails(hiredDetails, tranNum);
		double lateFee = lateFeeApplication(hiredDetails);
		Transaction returnTransaction = new Transaction(lateFee,staffName,hiredDetails);
		fr.appendToExistingFile(returnTransaction.transToString(), FileNameService.getTransactionFile());
		removeHiredItemsFromFile(hiredDetails);
		
		for(int i=0; i<hiredDetails.size(); i++){
			returnTransaction.updateStock(new TransactionItemDetails(hiredDetails.get(i).getItemCode() ,"r" , hiredDetails.get(i).getQty() ) );
		}
		
		
	}

	/**
	 * Displays all items in stock
	 * @throws IOException
	 */
	public void displayInStock() throws IOException{
		output.outputHeader("Items in Stock");
		FileReaderService stockfr = new FileReaderService(FileNameService.getStockFile());
		ArrayList<String> itemsInStock = new ArrayList<String>();
		itemsInStock = stockfr.getFileContents();
		output.outputData("ITEM NAME:\t\t\tQTY\n\n");
		for(int i=0; i<itemsInStock.size(); i++){
			String[] parts = itemsInStock.get(i).split(",");
			if(!parts[3].equals("0")){
				output.outputTwoColumns(parts[1], parts[3]);
			}
		}
	}
	
	/**
	 * Takes in an item code and displays the specific details relating to that item
	 * @throws IOException
	 */
	public void specificItemDetailsDisplay() throws IOException{
		String itemCode="";
		output.outputHeader("Item Details");
		FileReaderService stockfr = new FileReaderService(FileNameService.getStockFile());
		boolean valid = false;
		while(!valid){
			itemCode = input.takeInput("Enter the item code to see item details:");
			if(stockfr.checkIfStringIsInFile(itemCode)){
				valid =true;
			}
			else{
				output.outputErrorMsg("The item code you entered is not on our system.\nPlease try again:");
			}
		}
		String stockDetails = stockfr.returnRowContainingString(itemCode);
		String[] parts = stockDetails.split(",");
		output.outputData("\n");
		output.outputSixColumns("CODE","NAME","PRICE","QTY","SALE/HIRE", "DESCRIPTION");
		output.outputSixColumns(parts[0], parts[1], parts[2], parts[3], parts[5], parts[4]);

		
	}
	
	/**
	 * Takes in two dates and displays details from all transactions between those dates
	 * @throws IOException
	 * @throws ParseException
	 */
	public void summaryOfAllSalesHires() throws IOException, ParseException{
		FileReaderService tranfr = new FileReaderService(FileNameService.getTransactionFile());
		output.outputHeader("Summary of transactions");
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String prompt = "Please enter the dates you wish to view transactions between (seperated with a single space)\nFormat:dd/mm/yyyy dd/mm/yyyy\n";
		String pattern = "[\\d]{2}/[\\d]{2}/[\\d]{4} [\\d]{2}/[\\d]{2}/[\\d]{4}", error = "Incorrect format input. \nPlease try again\n\n";
		String inputString = input.takeInput(prompt, error, pattern);
		String []parts = inputString.split(" ");
		Date startDate = df.parse(parts[0]);
		Date endDate = df.parse(parts[1]);
		
		ArrayList<String> tranFileContents = tranfr.getFileContents();
		boolean found=false, firstFind=true;
		for(int i=0; i<tranFileContents.size(); i++){
			String []col = tranFileContents.get(i).split(",");
			Date checkDate = df.parse(col[1]);
			if(startDate.compareTo(checkDate) <= 0 && checkDate.compareTo(endDate) <= 0){
				found=true;
				if(firstFind){
					output.outputData("\n\n\nItem Details format: Item code / sale hire return / qty \n\n");
					output.outputSixCloseColumns("Trans. Num","Date","Time","Staff","Total","Item Details");
					output.outputData("_______________________________________________________________________________________________\n\n");
					firstFind=false;
				}
				String det = "";
				String[] details = col[4].split(":");
				for(int j=0; j<details.length; j++){
					det += details[j] + "  ";
				}
				String[] y = col[1].split(" ");
				
				output.outputSixCloseColumns(col[0], y[0], y[1], col[2], "€" + col[3], det); 
			}
		}
		
		if(!found){
			output.outputData("\n\nThere were no transactions between the dates you chose.\n\n");
		}
		
	}
	
	/**
	 * Validates if transaction number is in hiredOutItems file
	 * @param tranNum Unique transaction code
	 * @param hiredOutFR file reader with the contents of the HiredOutItems file on it
	 * @return true if code is on the file, otherwise false
	 * @throws IOException
	 */
	private boolean validTransactionNumber(String tranNum, FileReaderService hiredOutFR) throws IOException {
		return hiredOutFR.checkIfStringIsInColumnInFile(tranNum, 0);
	}

	/**
	 * Takes in an item code & returns it's name
	 * @param itemCode Unique stock item code
	 * @return item name
	 * @throws IOException
	 */
	private String determineItemName(String itemCode) throws IOException {
		FileReaderService stockfr = new FileReaderService(FileNameService.getStockFile());
		String [] parts = stockfr.returnRowBeginingWith(itemCode).split(",");
		return parts[1];
	}
	
	/**
	 * Removes all of the hired details inputed from the hiredOutItems file
	 * @param hiredDetails List with the details of the hiredOutItems being returned
	 * @throws FileNotFoundException
	 */
	private void removeHiredItemsFromFile(ArrayList<HiredOutItem> hiredDetails) throws FileNotFoundException {
		for(int i=0; i<hiredDetails.size(); i++){
			fr.deleteLineFromFileFirstColString("" + hiredDetails.get(i).getTransactionNumber(), FileNameService.getForHireFile());
		}
	}

	/**
	 * Prompts for a transaction number, checks if it is on the hiredOutITems file
	 * @param hiredOutFR file reader with the contents of the HiredOutItems file on it
	 * @return tranNum Unique transaction code customer is trying to return item from
	 * @throws IOException
	 */
	private String retrieveValidTranNumber(FileReaderService hiredOutFR)throws IOException {
		String prompt = "Please enter the customers transaction number from when they hired the item \nThis will be found on the customer receipt\n"; 
		String error = "\nIncorrect format of input, must be a digit\nPlease try again\n\n", pattern="[\\d]+", tranNum = null;
		boolean valid= false;
		while (!valid){
			tranNum = input.takeInput(prompt, error, pattern);
			if(validTransactionNumber(tranNum, hiredOutFR)){
				valid = true;
			}
			else{
				output.outputData("The transaction number you entered is not on our system.\nPlease try again\n\n");
			}
		}
		return tranNum;
	}

	/**
	 * Displays details of hire
	 * @param hiredDetails list of details of hired out items
	 * @param tranNum unique transaction number
	 */
	private void displayHireDetails(ArrayList<HiredOutItem> hiredDetails,String tranNum) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String out = "\n\nTransaction number: " + tranNum + "\nDate of Hire: " + df.format(hiredDetails.get(0).getTransactionDate()) 
					+ "\n\n\nDetails of item/items hired:\n\n";
		output.outputData(out);
		output.outputThreeColumns("Item Name", "Qty", "Return Date");
		for(int i=0; i<hiredDetails.size(); i++){
			output.outputThreeColumns(hiredDetails.get(i).getItemName(), "" + hiredDetails.get(i).getQty(), df.format(hiredDetails.get(i).getReturnDate()));
		}
	}

	/**
	 * Checks if item is late & applies fees if it is
	 * @param hiredDetails list of details of hired out items
	 * @return amount representing late fee due (will be 0.0 if no late fees due)
	 * @throws ParseException
	 */
	private double lateFeeApplication(ArrayList<HiredOutItem> hiredDetails) throws ParseException {
		boolean lateFeesApply=false;
		int fee = 10;
		double lateFee = 0.0;
		DecimalFormat decimalf = new DecimalFormat("#.00");
		
		for(int i=0; i<hiredDetails.size(); i++){
			if(countDaysLate(hiredDetails.get(i).getReturnDate())>0){
				lateFeesApply=true;
			}
		}
		if(lateFeesApply){
			output.outputData("\n____________________________________________________________\n\nCharge per late day (per item): €10\n\n");
			output.outputFourColumns("Item","Qty","Days Late","Charge");
    		for(int i=0; i<hiredDetails.size(); i++){
    			int daysLate = countDaysLate(hiredDetails.get(i).getReturnDate());
    			double charge = daysLate * fee * hiredDetails.get(i).getQty();
    			lateFee+= charge;
    			output.outputFourColumns(hiredDetails.get(i).getItemName(), "" + hiredDetails.get(i).getQty(), "" + daysLate, "€" + decimalf.format(charge));
    		}
		}
		output.outputData("\nTotal late fee: €" + decimalf.format(lateFee) + "\n\n");
		return lateFee;
	}

	/**
	 * Counts how many days it is late by
	 * @param returnD Date customer is supposed to return items on
	 * @return daysLate number of late days
	 */
	private int countDaysLate(Date returnD) {
		Date current = new Date();
		int diff = (int) (current.getTime() - returnD.getTime());
		int daysLate = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		if(daysLate<0)	daysLate=0;
		return (daysLate);
	}

	/**
	 * Creates the list of HiredOutItems
	 * @param hiredOutFR File reader containing contents of HiredOutItems text file
	 * @param tranNum Unique transaction number
	 * @return list of details relating to items hired out
	 * @throws IOException
	 * @throws ParseException
	 */
	private ArrayList<HiredOutItem> createHiredItemsList(FileReaderService hiredOutFR, String tranNum) throws IOException, ParseException {
		ArrayList<HiredOutItem> hiredDetails = new ArrayList<HiredOutItem>();
		String[] hiredItemStrings = hiredOutFR.returnMultipleRowsBeginingWith(tranNum).split("#");
		hiredDetails = new ArrayList<HiredOutItem>();
		for(int i=0; i < hiredItemStrings.length; i++){
			String[] parts = hiredItemStrings[i].split(",");
			String itemN = determineItemName(parts[2]);
			hiredDetails.add(new HiredOutItem(parts[0], parts[1], parts[2], parts[3], parts[4], itemN));
		}
		return hiredDetails;
	}
	
	
	
}
