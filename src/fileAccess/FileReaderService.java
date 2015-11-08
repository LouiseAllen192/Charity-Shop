package fileAccess;

import java.io.*;
import java.util.*;

/**
 * Class to create object that reads a file line by line into a String ArrayList<br>
 * Object can search through the file and return specific information about the file contents
 * @value fileContents String ArrayList containing contents of file
 * @author Louise Allen
 * @version 1.0
 *
 */
public class FileReaderService {
	
	
	ArrayList<String> fileContents = new ArrayList<String>();
	
	/**
	 * No arg constructor to crate a fileReader object
	 */
	public FileReaderService(){
	}
	
	/**
	 * Constructor that populates fileContents with contents of file
	 * @param filename name of file you want to read
	 * @throws IOException
	 */
	public FileReaderService(String filename) throws IOException{
		readFile(filename);
	}
	
	
	public ArrayList<String> getFileContents(){
		return fileContents;
	}
	
	
	/**
	 * Reads a file and put each line in the file into a subscript in a String ArrayList
	 * @param fileName Name of file you want to read
	 * @throws IOException
	 */
	public void readFile(String fileName)throws IOException{
		FileReader aFileReader = new FileReader(fileName);
		Scanner scan = new Scanner(aFileReader);
		ArrayList<String> fileContents = new ArrayList<String>();
		while (scan.hasNext()){
			fileContents.add(scan.nextLine());
		}
		scan.close();
		aFileReader.close();
		this.fileContents = fileContents;
	}
	
	
	
	
	/**
	 * Checks if a file contains a certain String
	 * @param checkString String you want to check if it's in file
	 * @return found Returns true if string is in file
	 * @throws IOException
	 */
	public boolean checkIfStringIsInFile(String checkString) throws IOException{
		boolean found=false;
		String[] arr;
		for(int i=0; i<fileContents.size() && !found; i++){
			arr = fileContents.get(i).split(",");
			for(int j=0; j<arr.length && !found; j++){
				if(arr[j].equals(checkString)){
					found=true;
				}
			}
		}
		return found;
	}

	/**
	 * Checks if a file contains a certain String in a certain position
	 * @param checkWord String you want to check if it's in certain column in file
	 * @param col Column number you want to check if checkString is in
	 * @return found Returns true if string is in the specified column in the file
	 * @throws IOException
	 */
	public boolean checkIfStringIsInColumnInFile(String checkWord, int col) throws IOException {
			boolean found=false;
			String[] arr;
			for(int i=0; i<fileContents.size() && !found; i++){
				arr = fileContents.get(i).split(",");
				if (arr[col].equals(checkWord)){
					found=true;
				}
			}
			return found;
	}
	

	/**
	 * Assuming we know the check word is in the file
	 * Method returns the entire line that contains the checkword
	 * @param checkWord String that is in the file
	 * @return lineFromFile Entire line that contains the checkWord
	 * @throws IOException
	 */
	public String returnRowContainingString(String checkWord)throws IOException{
		String lineFromFile="";
		boolean found=false;
		String[] arr;
		for(int i=0; i<fileContents.size() && !found;  i++){
			arr = fileContents.get(i).split(",");
			for(int j=0; j<arr.length && !found; j++){
				if(arr[j].equals(checkWord)){
					lineFromFile = fileContents.get(i);
					found=true;
				}
			}
		}
		
		return lineFromFile;
	}
	
	/**
	 * Assuming we know the check word is in the file
	 * Method returns the entire line that contains the checkword in the first position
	 * @param beginWith String that is in the file in first column
	 * @return lineFromFile Entire line that contains the checkWord in the first position
	 * @throws IOException
	 */
	public String returnRowBeginingWith(String beginWith)throws IOException{
		String lineFromFile="";
		boolean found=false;
		String[] arr;
		for(int i=0; i<fileContents.size() && !found;  i++){
			arr = fileContents.get(i).split(",");
			if(arr[0].equals(beginWith)){
				lineFromFile = fileContents.get(i);
				found=true;
			}
		}
		return lineFromFile;
	}
	
	/**
	 * Method that merges rows beginning with certain string together (separated with #) and returns that string
	 * @param beginWith String that is in the first column in the file
	 * @return linesFromFile Merged lines from file that start with beginWith
	 * @throws IOException
	 */
	public String returnMultipleRowsBeginingWith(String beginWith)throws IOException{
		String linesFromFile="";
		String[] arr;
		for(int i=0; i<fileContents.size();  i++){
			arr = fileContents.get(i).split(",");
			if(arr[0].equals(beginWith)){
				linesFromFile += fileContents.get(i) + "#";
			}
		}
		return linesFromFile;
	}
	
	/**
	 * Returns size of file contents
	 * @return number of lines in file
	 */
	public int numberOfLinesInFile(){
		return fileContents.size();
	}
	 
}


