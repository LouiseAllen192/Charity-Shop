package transaction;

import java.text.*;
import java.util.Date;

/**
 * Class that stores the details of an item which has been hired out
 * @author Louise Allen
 * @version 1.0
 *
 */
public class HiredOutItem {

		private int transactionNumber;
		private Date transactionDate;
		private String itemCode;
		private int qty;
		private Date returnDate;
		private String itemName;
		
		/**
		 * no arg constructor to create a HiredOutItem object
		 */
		public  HiredOutItem(){
			
		} 
		
		/**
		 * Constructor that takes in all String arguments and populates the fileds
		 * @param transactionNumber Unique transaction number for transaction 
		 * @param transactionDate Date of transaction
		 * @param itemCode Unique stock code for item
		 * @param qty quantity of the item hired out
		 * @param returnDate Date the customer must bring the item back by
		 * @param itemName name of the item
		 * @throws ParseException
		 */
		public  HiredOutItem(String transactionNumber, String transactionDate, String itemCode, String qty, String returnDate, String itemName) throws ParseException{
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			this.transactionNumber = Integer.parseInt(transactionNumber);
			this.transactionDate = df.parse(transactionDate);
			this.itemCode = itemCode;
			this.qty = Integer.parseInt(qty);
			this.returnDate = df.parse(returnDate);
			this.itemName = itemName;
		}
		
		/**
		 * Constructor that takes in the values for the hired out items and creates the object
		 * @param transactionNumber Unique transaction number for transaction 
		 * @param transactionDate Date of transaction
		 * @param itemCode Unique stock code for item
		 * @param qty quantity of the item hired out
		 * @param returnDate Date the customer must bring the item back by
		 * @param itemName name of the item
		 * @throws ParseException
		 */
		public  HiredOutItem(int transactionNumber, Date transactionDate, String itemCode, int qty, Date returnDate, String itemName) throws ParseException{
			this.transactionNumber = transactionNumber;
			this.transactionDate = transactionDate;
			this.itemCode = itemCode;
			this.qty = qty;
			this.returnDate = returnDate;
			this.itemName = itemName;
		}
		
		/**
		 * Accessor method to get the transaction number
		 * @return transactionNumber Unique transaction number for transaction 
		 */
		public int getTransactionNumber(){
			return transactionNumber;
		}
		
		/**
		 * Accessor method to get the transaction date
		 * @return transactionDate Date of transaction
		 */
		public Date getTransactionDate(){
			return transactionDate;
		}
		
		/**
		 * Accessor method to get the item code
		 * @return itemCode Unique stock code for item
		 */
		public String getItemCode(){
			return itemCode;
		}
		
		/**
		 * Accessor method to get the item quantity
		 * @return qty quantity of the item hired out
		 */
		public int getQty(){
			return qty;
		}
		
		/**
		 * Accessor method to get the item return date
		 * @return returnDate Date the customer must bring the item back by
		 */
		public Date getReturnDate(){
			return returnDate;
		}
		
		/**
		 * Accessor method to get the item name
		 * @return itemName Item name
		 */
		public String getItemName(){
			return itemName;
		}
		
		
		
}
