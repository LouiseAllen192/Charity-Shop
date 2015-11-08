package inputOutput;

import java.util.Scanner;

/**
 * Class to input & validate data
 * @value scan Scanner for taking input
 * @author Louise Allen
 * @version 1.0
 *
 */
public class InputService {
	
	Scanner scan;
	
	/**
	 * No Arg constructor to create InputService object
	 */
	public InputService(){
		scan = new Scanner(System.in);
	}
	
	
	/**
	 * Takes input from user without validating
	 * @param prompt Prompt string to tell user what input to enter
	 * @return Input that user entered
	 */
	public String takeInput(String prompt){
		System.out.print(prompt);
		String x = scan.nextLine();
		return x;
	}
	
	/**
	 * Takes in user input and matches it to a regex to validate the format
	 * @param prompt Prompt string to tell user what input to enter
	 * @param error Error message to print when user enters wrong input format
	 * @param pattern regex pattern that user input must match
	 * @return Validated input that user entered
	 */
	public String takeInput(String prompt, String error, String pattern){
		scan = new Scanner(System.in);
		boolean valid = false;
		String x = null;
		while(!valid){
			System.out.print(prompt);
			x = scan.nextLine();
			if(x.matches(pattern)){
				valid = true;
			}
			else{
				System.out.print(error);
				valid=false;
			}
		}
		return x;
	}
	
	/**
	 * Closes scanner
	 */
	public void closeScanner(){
		scan.close();
	}
}
