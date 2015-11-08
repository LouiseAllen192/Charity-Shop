package systemPackage;

import java.io.IOException;
import java.text.ParseException;

/**
 * This class runs our main begins running the shop system
 * @author Louise Allen
 * @version 1.0
 *
 */
public class SystemBoot {

	/**
	 * Our main method creates a ShopSystem object and then runs the startSystem method
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {
		ShopSystem shopSystem = new ShopSystem();
		shopSystem.startSystem();

	}

}
