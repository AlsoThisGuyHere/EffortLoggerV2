package asuHelloWorldJavaFX;

public class User {

    private String firstName;
	
    private String lastName;

    private String role;

    private String username;

    private String password;

    private Boolean isLoggedIn;

    private String answer1;

    private String answer2;

    private String answer3;

    public User(String firstName, String lastName, String role, String username, String password, String answer1, String answer2, String answer3) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setRole(role);
        this.setUsername(username);
        this.setPassword(password);
        this.setAnswer1(answer1);
        this.setAnswer2(answer2);
        this.setAnswer3(answer3);
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

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }
}
