package asuHelloWorldJavaFX;

public class Encryptor {
	
	private static int OFFSET = 15;

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
