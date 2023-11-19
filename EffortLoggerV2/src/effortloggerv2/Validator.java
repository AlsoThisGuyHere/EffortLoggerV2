package effortloggerv2;

/*******
 * <p> Title: Validator_Main Class </p>
 * <p>This class is defines a validator to verify that text fields meet the input requirements.</p>
 * 
 * @author Cody Nijdl
 * 
 * */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	
	public static boolean validate(String data, String fieldType) { 

		final int MAX_TEXT_LENGTH = 200;
		final int MIN_UNAME_LENGTH = 6;
		final int MAX_UNAME_LENGTH = 32;
		final int MIN_PASS_LENGTH = 8;
		final int MAX_PASS_LENGTH = 32;
		
		//boolean result = true;
		
		//Pattern allowed_chars = Pattern.compile("[^\\p{Print}]");		// looks for any non-printable character (only supports ASCII)
		Pattern allowed_chars = Pattern.compile("[^\\p{Alnum}-._\\x20]");// looks for anything not a letter, number, period, underscore, or space
		Pattern chars = Pattern.compile("[\\p{Punct}]");
		Pattern nums = Pattern.compile("[\\p{Digit}]");
		Pattern alpha = Pattern.compile("[\\p{Alpha}]");
		Matcher matcher = allowed_chars.matcher(data);
		
		switch (fieldType) {
			
			// if the field should contain text
			case "Text":
				if (data.length() > MAX_TEXT_LENGTH) {return false;}									// check length
				else if (matcher.find()) {return false;}												// check for restricted characters
				break;
			// if the field is for some sort of name
			case "Name":	
				allowed_chars = Pattern.compile("[^\\p{Alpha}.\'-]");									// letters and .-'
				if (data.length() > MAX_UNAME_LENGTH) {return false;}									// check length
				else if (allowed_chars.matcher(data).find()) {return false;}							// check for restricted characters
				break;
			// if the field is for a user name
			case "Username":
				allowed_chars = Pattern.compile("[^\\p{Alnum}.-_]");									// letters, numbers, and .-_
				if (data.length() < MIN_UNAME_LENGTH || data.length() > MAX_UNAME_LENGTH) {return false;}	// check length
				else if (allowed_chars.matcher(data).find()) {return false;}							// check for restricted characters
				break;
			// if the field is for a password
			case "Password":
				allowed_chars = Pattern.compile("[^\\p{Graph}]"); 										// visible characters
				if (data.length() < MIN_PASS_LENGTH || data.length() > MAX_PASS_LENGTH) {return false;} // check length
				else if (allowed_chars.matcher(data).find()) {return false;}							// check for restricted characters
				else if (!chars.matcher(data).find()) {return false;}									// check for special characters
				else if (!nums.matcher(data).find()) {return false;}									// check for numbers
				else if (!alpha.matcher(data).find()) {return false;}									// check for letters
				break;
			default:
				return true;
				
		}
		
		
		return true;
	}
}
