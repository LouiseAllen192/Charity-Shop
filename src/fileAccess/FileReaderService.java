package fileAccess;

import java.io.*;
import java.util.*;

public class FileReaderService {
	
	
	//reads a file and put each line in the file into a subscript in a String ArrayList
	public ArrayList<String> readFile(String fileName)throws IOException{
		
		FileReader aFileReader = new FileReader(fileName);
		Scanner scan = new Scanner(aFileReader);
		ArrayList<String> fileContents = new ArrayList<String>();
		while (scan.hasNext()){
			fileContents.add(scan.nextLine());
		}
		scan.close();
		aFileReader.close();
		return fileContents;
	}
	
	//Checks if a file contains a certain String
	public boolean checkIfStringIsInFile(String checkString, String fileName) throws IOException{
		ArrayList<String> fileContents = new ArrayList<String>();
		fileContents = readFile(fileName);
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

	public boolean checkIfStringIsInColumnInFile(String checkWord, int col, String fileName) throws IOException {

			ArrayList<String> fileContents = new ArrayList<String>();
			fileContents = readFile(fileName);
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
	
	//assuming we know the check word is in the file
	public String returnRowContainingString(String checkWord, String filename)throws IOException{
		String lineFromFile="";
		ArrayList<String> fileContents = new ArrayList<String>();
		fileContents = readFile(filename);
		boolean found=false;
		String[] arr;
		for(int i=0; i<fileContents.size() && !found;  i++){
			arr = fileContents.get(i).split(",");
			for(int j=0; j<arr.length && !found; j++){
				if(arr[j].equals(checkWord)){
					lineFromFile = fileContents.get(i);
				}
			}
		}
		
		return lineFromFile;
	}
	 
}


