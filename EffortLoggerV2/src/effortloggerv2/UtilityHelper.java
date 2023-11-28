package effortloggerv2;

import java.util.ArrayList;
import java.util.List;

public class UtilityHelper {
	
	private ReadWrite readWrite = new ReadWrite("src\\sample.txt");

	public static List<User> getUsersFromFile(String filename) {
    	List<User> users = new ArrayList<User>();
    	ReadWrite readWrite = new ReadWrite(filename);
    	String readData = readWrite.read();
    	if (readData == null) return new ArrayList<User>();
    	String[] stringUsers = readData.split("\n");
    	for (int i = 0; i< stringUsers.length; i++) {
    		System.out.println(stringUsers[i]);
    		String[] splitUserDetails = stringUsers[i].split(" ");
    		if (splitUserDetails.length != 8) continue;
    		users.add(new User(splitUserDetails[0], splitUserDetails[1], splitUserDetails[2], splitUserDetails[3], splitUserDetails[4], splitUserDetails[5], splitUserDetails[6], splitUserDetails[7]));
    	}
    	return users;
    }
	
	public static List<PlanningPoker> getTasksFromFile(String filename) {
    	List<PlanningPoker> tasks = new ArrayList<PlanningPoker>();
    	ReadWrite readWrite = new ReadWrite(filename);
    	String readData = readWrite.read();
    	if (readData == null) return new ArrayList<PlanningPoker>();
    	String[] stringTasks = readData.split("\n");
    	for (int i = 0; i< stringTasks.length; i++) {
    		System.out.println(stringTasks[i]);
    		String[] splitTaskDetails = stringTasks[i].split(" ");
    		if (splitTaskDetails.length != 3) continue;
    		tasks.add(new PlanningPoker(splitTaskDetails[0], splitTaskDetails[1], splitTaskDetails[2]));
    	}
    	return tasks;
    }
	
	// Method to check whether the user exists
	public boolean findUser(String username) {
		return getUsersFromFile("src\\sample.txt").stream().anyMatch(user -> user.getUsername().equals(username));
	}
	
	// Validate all the fields are passed
	public boolean validateForgotPassword(String username, String password, String confirmPassword) {
		return checkTextInput(username) || checkTextInput(password) || checkTextInput(confirmPassword);
	}
	
	// Validate password and confirm password are same
	public boolean validatePasswordAndConfirmPassword(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}
	
	// Method to update the user password
	public List<User> updateUser(String username, String password) {
		List<User> users = getUsersFromFile("src\\sample.txt");
		users.forEach(user -> {
				if (user.getUsername().equals(username)) {
					System.out.println("Print");
					user.setPassword(password);
				}
			 });
		return users;
	}
	
	// Method to save the users in the encrypted format
	public void saveUsers(List<User> users) {
		readWrite.write(users.get(0).getFirstName() + " " + users.get(0).getLastName() + " " + users.get(0).getRole() + " " + users.get(0).getUsername() + " " + users.get(0).getPassword() + " " + users.get(0).getAnswer1() + " " + users.get(0).getAnswer2() + " " + users.get(0).getAnswer3());
		for (int i = 1; i < users.size(); i++) {
			readWrite.append(users.get(i).getFirstName() + " " + users.get(i).getLastName() + " " + users.get(i).getRole() + " " + users.get(i).getUsername() + " " + users.get(i).getPassword() + " " + users.get(i).getAnswer1() + " " + users.get(i).getAnswer2() + " " + users.get(i).getAnswer3(), true);
		}
	}
	
	// Validate input text are not null, blank or empty
	private boolean checkTextInput(String value) {
    	return value == null || value.isBlank() || value.isEmpty();
    }
}
