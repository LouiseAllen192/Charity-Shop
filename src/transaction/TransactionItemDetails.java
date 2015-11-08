package transaction;

/**
 * Class to store the details relating to each item in a transaction of any kind in the system
 * @author Louise Allen
 * @version 1.0
 *
 */
public class TransactionItemDetails {
	
	private int itemCode;
	private String saleHireReturn;
	private int qty;
	
	/**
	 * No arg constructor
	 */
	public TransactionItemDetails(){
		
	}
	
	/**
	 * Constructor to create a TransactionItemDetails object
	 * @param itemCode Unique stock code to represent an item
	 * @param saleHireReturn A string that will either be s, h or r depending on whether it is a sale, hire or return
	 * @param qty the quantity of the item 
	 */
	public TransactionItemDetails(String itemCode, String saleHireReturn, int qty){
		this.itemCode= Integer.parseInt(itemCode);
		this.saleHireReturn = saleHireReturn;
		this.qty = qty;
	}
	
	/**
	 * Accessor method to get item code
	 * @return itemCode Unique stock code to represent an item
	 */
	public int getItemCode(){
		return itemCode;
	}
	
	/**
	 * Accessor method to get whether the item is for sale, hire or return
	 * @return saleHireReturn A string that will either be s, h or r depending on whether it is a sale, hire or return
	 */
	public String getSaleHireReturn(){
		return saleHireReturn;
	}
	
	/**
	 * Accessor method to get the quantity of the item
	 * @return qty the quantity of the item 
	 */
	public int getQuantity(){
		return qty;
	}
	
	/**
	 * Converts the details of the transaction to a / seperated string
	 * @return String with the details of the Transaction item
	 */
	public String detailsToString(){
		return "" + itemCode + "/" + saleHireReturn + "/" + qty;
	}
	
}
