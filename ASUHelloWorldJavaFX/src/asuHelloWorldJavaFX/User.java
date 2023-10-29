package asuHelloWorldJavaFX;

public class User {
	
	private String firstName;
	
	private String lastName;
	
	private String role;
	
	private String username;
	
	private String password;
	
	private Boolean isLoggedIn;
	
	public User(String firstName, String lastName, String role, String username, String password) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setRole(role);
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}
