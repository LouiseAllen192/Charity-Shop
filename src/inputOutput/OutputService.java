package inputOutput;

/**
 * Class to output data to screen
 * @author Louise Allen
 * @version 1.0
 *
 */
public class OutputService {
	
	/**
	 * No Arg constructor to create OutputService object
	 */
	public OutputService(){
		
	}
	
	
	/**
	 * Outputs data to screen
	 * @param data Data you want outputted 
	 */
	public void outputData(String data){
		System.out.print(data);
	}
	
	/**
	 * Outputs data to screen as an error message
	 * @param data Data you want outputted 
	 */
	public void outputErrorMsg(String data){
		System.out.print("Error - " + data);
	}
	
	/**
	 * Outputs data to screen as header on top of page
	 * @param header Data you want outputted to header 
	 */
	public void outputHeader(String header){
		System.out.print("\n\n**********************    " + header + "    **********************\n\n");
	}
	
	/**
	 * Outputs data in two column format
	 * @param v1 String argument to be put in first column
	 * @param v2 String argument to be put in second column
	 */
	public void outputTwoColumns(String v1, String v2){
		System.out.printf("%-30.30s  %-30.30s%n", v1, v2);
	}
	
	/**
	 * Outputs data in three column format
	 * @param v1 String argument to be put in first column
	 * @param v2 String argument to be put in second column
	 * @param v3 String argument to be put in third column
	 */
	public void outputThreeColumns(String v1, String v2, String v3){
		System.out.printf("%-20s  %-20s  %-20s%n", v1, v2, v3);
	}
	
	/**
	 * Outputs data in four column format
	 * @param v1 String argument to be put in first column
	 * @param v2 String argument to be put in second column
	 * @param v3 String argument to be put in third column
	 * @param v4 String argument to be put in fourth column
	 */
	public void outputFourColumns(String v1, String v2, String v3, String v4){
		System.out.printf("%-20s  %-15s  %-15s | %-20s%n", v1, v2, v3, v4);
	}

	/**
	 * Outputs data in six column format (wide format)
	 * @param v1 String argument to be put in first column
	 * @param v2 String argument to be put in second column
	 * @param v3 String argument to be put in third column
	 * @param v4 String argument to be put in fourth column
	 * @param v5 String argument to be put in fifth column
	 * @param v6 String argument to be put in sixth column
	 */
	public void outputSixColumns(String v1, String v2, String v3, String v4, String v5, String v6) {
		System.out.printf("%-20s  %-20s  %-20s  %-20s  %-20s  %-20s%n", v1, v2, v3, v4, v5, v6);
	}
	
	/**
	 * Outputs data in six column format that is close together
	 * @param v1 String argument to be put in first column
	 * @param v2 String argument to be put in second column
	 * @param v3 String argument to be put in third column
	 * @param v4 String argument to be put in fourth column
	 * @param v5 String argument to be put in fifth column
	 * @param v6 String argument to be put in sixth column
	 */
	public void outputSixCloseColumns(String v1, String v2, String v3, String v4, String v5, String v6) {
		System.out.printf("%-10s  %-10s  %-10s  %-10s  %-10s  %-10s%n", v1, v2, v3, v4, v5, v6);
	}
	
}
