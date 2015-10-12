package inputOutput;

import java.util.Scanner;

public class InputService {
	
	public String takeInput(String prompt){
		
		Scanner scan = new Scanner(System.in);
		System.out.print(prompt);
		String x = scan.nextLine();
		return x;
	}
}
