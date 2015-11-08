
package transaction;

import inputOutput.OutputService;
import java.text.*;
import java.util.*;

/**
 * 
 * Receipt class stores data relating to a receipt and also has functionality to print a receipt
 * @version 1.0
 * @author Louise Allen
 *
 */
public class Receipt {
	private Date date;
	private String servedBy;
	private ArrayList<LineItem> lineItems;
	private double totalSalePrice;
	private int transactionNumber;
	private ArrayList<HiredOutItem> hiredDetails;
	
	/**
	 * No arg constructor to create a Receipt object
	 */
	public Receipt(){
		
	}
	
	/**
	 * Constructor to create a new receipt item for a transaction
	 * @param date Date of receipt creation
	 * @param servedBy First name of emplyee who is logged in
	 * @param lineItems List of line items depending on what was purchased/hired
	 * @param transactionNumber Unique transaction id number
	 * @param hiredDetails Details of the items that are for hire
	 */
	public Receipt(Date date, String servedBy, ArrayList<LineItem> lineItems, int transactionNumber, ArrayList<HiredOutItem> hiredDetails){
		this.date =date;
		this.servedBy = servedBy;
		this.lineItems = lineItems;
		this.totalSalePrice = calculateTotalSalePrice(lineItems);
		this.transactionNumber = transactionNumber;
		this.hiredDetails = hiredDetails;
	}

	/**
	 * Getter method to return total sale price
	 * @return totalSalePrice total price transaction cost
	 */
	public double getTotalSalePrice() {
		return totalSalePrice;
	}

	
	/**
	 * Displays receipt
	 */
	public void displayReceipt() {
		OutputService output = new OutputService();
		DecimalFormat df = new DecimalFormat("€#.00"); 
		DateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat timef = new SimpleDateFormat("HH:mm:ss");
		
		String r = "\n\n-----------------------------------------------------------------\n\n\n";
		r+="                 Give a Little Charity Shop\n";
		r+="                25,Maryville Row,LalaLand,Earth\n";
		r+="                   Tel:55-0123  Fax:81945362\n\n\n\n";
		r+="Date: " + datef.format(date) + "\n";
		r+="Time: " + timef.format(date) + "\n";
		r+="Transaction Number: " + transactionNumber + "\n";
		r+="Today you were served by:" + servedBy + "\n\n";
		r+= "------------------------------------------------------------------\n\n\n";
		output.outputData(r);
		output.outputFourColumns("Item Name:","Price","Qty","Total");
		output.outputData("__________________________________________________________________\n\n");
		for(int i=0; i<lineItems.size(); i++){
			LineItem litem = lineItems.get(i);
			output.outputFourColumns(litem.getItem().getName(), df.format(litem.getItem().getPrice()), "" + litem.getQty(), df.format(litem.getTotalPrice()));
		}
		output.outputData("\n\nTotal sale price : " + df.format(totalSalePrice));
		
		if(hiredDetails.size() > 0){
			output.outputData("\n\n\nThe following are the details for your hire items:\n\n");
			output.outputThreeColumns("Item Name:", "Qty", "Return Date");
			output.outputData("__________________________________________________________________\n\n");
			for(int i=0; i<hiredDetails.size(); i++){
				HiredOutItem hireItem = hiredDetails.get(i);
				output.outputThreeColumns(hireItem.getItemName(), "" + hireItem.getQty(), datef.format(hireItem.getReturnDate()));
			}
			output.outputData("\n\nPlease note: If items are not returned by the return date stated above\nthere will be a €10 late fee for every day it is late");
		}
		
		output.outputData("\n\n\nThank you for your business\nPlease come again\n\n\n");
	}
	
	

	/**
	 * Calculates total sale price
	 * @param lineItem List of line items from transaction
	 * @return totalSalePrice Sum of the total price of all lineItems
	 */
	private double calculateTotalSalePrice(ArrayList<LineItem> lineItems) {
		double total = 0;
		for(int i=0; i<lineItems.size(); i++){
			total+= lineItems.get(i).getTotalPrice();
		}
		return total;
	}
	
	
}
