package fileAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class FileWriterService {
	
	//adds a String passed in onto the end of a file
	public void writeToFile(String fileName, ArrayList<String> fileContents) throws FileNotFoundException{
		PrintWriter outfile = new PrintWriter(fileName);
		for(int i=0; i<fileContents.size(); i++){
			outfile.println(fileContents.get(i));
		}
		outfile.close();
	}
	
	//appends to an existing file
	public void appendToExistingFile(String toBeAdded, String fileName) throws IOException{
		FileWriter aFileWriter = new FileWriter(fileName, true);
		PrintWriter out = new PrintWriter(aFileWriter);
		out.println(toBeAdded);
		out.close();
		aFileWriter.close();
	}
	
	//deletes an entire line from a file if that line contains a certain String
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
}
