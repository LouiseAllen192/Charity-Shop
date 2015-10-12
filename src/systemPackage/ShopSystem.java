package systemPackage;

import java.io.IOException;

import shopServices.LoginService;
import employee.Employee;

public class ShopSystem {

	Employee loggedInEmployee;
	
	public void startSystem() throws IOException {
		
		FileNameService.setFileNameService();
		
		LoginService loginService = new LoginService();
		loggedInEmployee = loginService.logIn();
		
		
	}
	
	
	

}
