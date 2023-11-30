package effortloggerv2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// @author Frans Emil Malapo
// Class to control reading and writing files for data storage
// Automatically writes files using encryption from Encryptor
// Assumes files read are encrypted by Encryptor
public final class ReadWrite {
	
	private String path;  // Path of the text file to read and write to
	
	// Creates new ReadWrite object using specified path
	// Suggested that the path given should already exist as a text file. Otherwise, errors may occur.
	//  (Particularly the read() function)
	// @param path - file path of the text document to write to
	//                  Make sure to include the .txt extension within the string
	//  Example: "src\\filename.txt" <- filename.txt is stored in the same folder as these classes
	//                                    "\\" serves as an escape character to represent "\"
	public ReadWrite(String path)
	{
		this.path = path;
	}
	
	// Writes to the file given from the string path
	// If a file already exists in the path, the contents will be replaced with the input
	// Prints error if fails
	// @param input - String input to be written to file at path
	public void write(String input)
	{
		try
		{
			FileWriter writer = new FileWriter(path);
		    writer.write(Encryptor.encrypt(input));
		    writer.close();
		    System.out.println("Success");
		} catch (IOException e) {
			System.out.println("ERROR IN WRITING TO FILE");
			e.printStackTrace();
		}
	}

	// Appends to the file given from the string path.
	// Second argument determines whether or not to append to a new line
	// Prints error if fails
	// @param input - String input to be appended to file at path
	// @param newLine - whether or not to append input to a new line. True if yes, false if no.
	public void append(String input, boolean newLine)
	{
		try
		{
			FileWriter writer = new FileWriter(path, true);
			if(newLine) { input = "\n" + input; }
		    writer.write(Encryptor.encrypt(input));
		    writer.close();
		} catch (IOException e) {
			System.out.println("ERROR IN WRITING TO FILE");
			e.printStackTrace();
		}
	}

	// Returns the contents of the text file at path as a String
	// Prints error if fails
	// @return - String contents of file
	public String read()
	{
		try {
			Scanner scan = new Scanner(new File(path));
			String output = "";
			
			while(scan.hasNextLine())
			{
				output += scan.nextLine() + "\n";
			}
			
			scan.close();
			
			output = Encryptor.decrypt(output);
			
			return output.substring(0, output.length() - 1);
			
		} catch (Exception e) {
			System.out.println("ERROR IN READING FILE");
			return null;
		}
	}
}
