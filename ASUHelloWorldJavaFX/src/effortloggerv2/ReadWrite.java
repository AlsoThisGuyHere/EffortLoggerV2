package effortloggerv2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class ReadWrite {
	
	private String path;
	public ReadWrite(String path)
	{
		this.path = path;
	}

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
