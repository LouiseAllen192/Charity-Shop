package fileAccess;

import java.io.*;
import java.util.*;


/**
 * Class to create an object that can write to & change file contents
 * @author Louise Allen
 * @version 1.0
 *
 */
public class FileWriterService {
	
	static Scanner scan;
	/**
	 * No arg constructor to crate a fileWriter object
	 */
	public FileWriterService(){
	}
	
	
	/**
	 * Takes in an arrayList and writes it to the file specified in fileName
	 * @param fileName Name of file you want to write to
	 * @param fileContents String arraylist you want to write to the file
	 * @throws FileNotFoundException
	 */
	public void writeToFile(String fileName, ArrayList<String> fileContents) throws FileNotFoundException{
		PrintWriter outfile = new PrintWriter(fileName);
		for(int i=0; i<fileContents.size(); i++){
			outfile.println(fileContents.get(i));
		}
		outfile.close();
	}
	
	/**
	 * Appends to an existing file
	 * @param toBeAdded String you want to add to the end of an existing file
	 * @param fileName Name of file you want to append to
	 * @throws IOException
	 */
	public void appendToExistingFile(String toBeAdded, String fileName) throws IOException{
		FileWriter aFileWriter = new FileWriter(fileName, true);
		PrintWriter out = new PrintWriter(aFileWriter);
		out.println(toBeAdded);
		out.close();
		aFileWriter.close();
	}
	
	
	/**
	 * Deletes an entire line from a file if that line contains a certain String
	 * @param x String you want to check for in file
	 * @param fileName Name of file you want the line deleted from
	 * @throws FileNotFoundException
	 */
	public void deleteLineFromFileContainingString(String x, String fileName) throws FileNotFoundException{
		String aLineFromFile;
		ArrayList<String> newFileContents = new ArrayList<String>();
		File aFile = new File(fileName);
		Scanner scan = new Scanner(aFile);
		while (scan.hasNext()){
			aLineFromFile = scan.nextLine();
			if (!aLineFromFile.contains(x)){
				newFileContents.add(aLineFromFile);
			}
			scan.close();
			writeToFile(fileName, newFileContents );
		}
	 
	}
	
	/**
	 * Delete line from file where first column is certain String
	 * @param x String that will be in first column of line you are deleting
	 * @param fileName Name of file you want the line to be deleted from
	 * @throws FileNotFoundException
	 */
	public void deleteLineFromFileFirstColString(String x, String fileName) throws FileNotFoundException{
		String aLineFromFile;
		ArrayList<String> newFileContents = new ArrayList<String>();
		File aFile = new File(fileName);
		scan = new Scanner(aFile);
		while (scan.hasNext()){
			aLineFromFile = scan.nextLine();
			String[] parts = aLineFromFile.split(",");
			if (!parts[0].equals(x)){
				newFileContents.add(aLineFromFile);
			}
			writeToFile(fileName, newFileContents );
		}
	 
	}
	
	public static void closeScanner(){
		scan.close();
	}


}
