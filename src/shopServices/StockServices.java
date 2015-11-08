package shopServices;

import inputOutput.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import systemPackage.FileNameService;
import transaction.Item;
import fileAccess.*;

/**
 * Class that completes functionality relating to Stock
 * {@value} input InputService to take input from user
 * {@value} output OuputService to output data to screen
 * {@value} fw File Writer to write to file
 * @author Louise Allen
 * @version 1.0
 */
public class StockServices {

	OutputService output;
	InputService input;
	FileWriterService fw;
	
	/**
	 * No arg constructor that creates a StockServices item
	 */
	public StockServices(){
		output = new OutputService();
		input = new InputService();
		fw = new FileWriterService();
	}
	
	/**
	 * Takes in input relating to new item to be added to stock<br>
	 * Adds new items to stock file
	 * @throws IOException
	 */
	public void addNewItemToStock() throws IOException {
		output.outputHeader("Add new stock item");
		Item newItem = new Item();
		DecimalFormat df = new DecimalFormat("#.00");
		String qty = inputQty();
		String itemS = newItem.getCode() + "," + newItem.getName() + "," + df.format(newItem.getPrice()) + "," + qty + "," + newItem.getDescription() + "," + newItem.getSaleOrHire();
		fw.appendToExistingFile(itemS, FileNameService.getStockFile());
		output.outputData("\n\nItem was sucessfully added to stock\n\n");
	}
	
	/**
	 * Asks user to input stock id number and a quantity.<br>
	 * Adds that quantity to the stock quantity
	 * @throws IOException
	 */
	public void addStock() throws IOException{
		FileReaderService stockfr = new FileReaderService(FileNameService.getStockFile());
		
		output.outputHeader("Update stock level");
		String stockItemCode= inputCode(stockfr);
		int qtyToBeAdded = Integer.parseInt(inputQty());
		
		ArrayList<String> updatedFileContents = stockfr.getFileContents();
		int i;
		FileWriterService fwriter = new FileWriterService();
		boolean found=false;
		String[] parts = null;
		
		for(i=0; i<updatedFileContents.size() && !found;  i++){
			String lineFromFile = updatedFileContents.get(i);
			String[] arr = lineFromFile.split(",");
			if(arr[0].equals(stockItemCode)){
				found=true;
				parts =  lineFromFile.split(",");
			}
		}
		
		parts[3] = "" + (Integer.parseInt(parts[3]) + qtyToBeAdded);				
		String changedLine ="";
		for(int j=0; j<parts.length; j++){
			changedLine+= parts[j] + ",";
		}
		updatedFileContents.set((i-1), changedLine);
		fwriter.writeToFile(FileNameService.getStockFile(), updatedFileContents);
		output.outputData("\n\nStock was successfully updated\n\n");
	}

	/**
	 * Input quantity to be added to stock
	 * @return quantity to be added to stock
	 */
	private String inputQty() {
		String prompt = "Input quantitiy you want to add to stock:\n";
		String error = "Incorrect format. Input must be a digit.\n\n";
		String pattern = "[\\d]+";
		return input.takeInput(prompt, error, pattern);
	}

	/**
	 * Inputs & validates item code to be re-stocked
	 * @param stockfr FileReader that can read the stock file contents
	 * @return in item code to be re-stocked
	 * @throws IOException
	 */
	private String inputCode(FileReaderService stockfr) throws IOException {
		String prompt = "Input Stock Item code you would like to restock:\n";
		String error = "Incorrect format. Input must be a digit.\n\n";
		String pattern = "[\\d]+", in="";
		boolean valid = false;
		while (!valid){
			in = input.takeInput(prompt, error, pattern);
			if(isValidCode(in, stockfr)){
				valid=true;
			}
			else{
				output.outputData("That item code is not on our system. PLease try again \n\n");
			}
		}
		return in;
	}

	/**
	 * Validates if stock code is in the stock file
	 * @param in Stock id to check
	 * @param stockfr FileReader that can read the stock file contents
	 * @return true if id is in stock file
	 * @throws IOException
	 */
	private boolean isValidCode(String in, FileReaderService stockfr) throws IOException {
		return (stockfr.checkIfStringIsInColumnInFile(in, 0)) ? true : false;
	}

}
