package effortloggerv2;

// @author Frans Emil Malapo
// Class to encrypt and decrypt Strings using an offset key
// All functions are static and meant for use without instantiation
public class Encryptor {
	
	private static int OFFSET = 15; // Key for encryption

	// Encrypts a given unencrypted string
	// @param input - String to encrypt
	// @return - encrypted String
	public static String encrypt(String input)
	{
		String output = "";
		for(int i = 0; i < input.length(); i++)
		{
			if(input.charAt(i) == '\n') { output += '\n'; }
			else
			{
				if(i % 2 == 0)
				{
					output += (char) (input.charAt(i) + OFFSET);
				}
				else
				{
					output += (char) (input.charAt(i) + OFFSET);
				}
			}
		}
		return output;
	}

	// Decrpyts a given encrypted string and returns decrypted String
	// @param input - encrypted String to decrypt
	// @return - decrypted String
	public static String decrypt(String input)
	{
		String output = "";
		for(int i = 0; i < input.length(); i++)
		{
			if(input.charAt(i) == '\n') { output += '\n'; }
			else
			{
				if(i % 2 == 0)
				{
					output += (char) (input.charAt(i) - OFFSET);
				}
				else
				{
					output += (char) (input.charAt(i) - OFFSET);
				}
			}
		}
		return output;
	}
}
