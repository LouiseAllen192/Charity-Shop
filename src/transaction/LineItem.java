package transaction;

/**
 * Class to represent an line item on a receipt in the shop system
 * @author Louise Allen
 * @version 1.0
 *
 */
public class LineItem {

	private Item item;
	private int qty;
	private double price;
	private double totalPrice;
	
	/**
	 * No arg constructor to create LineItemObject
	 */
	public LineItem(){
	}
	
	/**
	 * Constructor that create line item
	 * @param item Item to be added to line item
	 * @param qty Quantity to be added to line item
	 */
	public LineItem(Item item, int qty){
		this.item =item;
		this.qty = qty;
		this.price = item.getPrice();
		this.totalPrice = calculateTotalPrice(price, qty);
	}

	/**
	 * Calculates total price for the line
	 * @param price price of item
	 * @param qty quantity of item
	 * @return total price for line
	 */
	private double calculateTotalPrice(double price, int qty) {
		return price * qty;
	}
	
	/**
	 * Getter method to return total price
	 * @return totalPrice
	 */
	public double getTotalPrice(){
		return totalPrice;
	}
	
	/**
	 * Getter method to return price
	 * @return price
	 */
	public double getPrice(){
		return price;
	}

	/**
	 * Getter method to return item
	 * @return item Item in line item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Getter method to return qty of item
	 * @return qty Quantity of item in line
	 */
	public int getQty() {
		return qty;
	}

	
}
