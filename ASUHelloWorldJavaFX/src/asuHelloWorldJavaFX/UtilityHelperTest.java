package asuHelloWorldJavaFX;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UtilityHelperTest {
	
	UtilityHelper utilityHelper;
	
	@BeforeEach
	void setup() {
		utilityHelper = new UtilityHelper();
	}

	@Test
	void testUsernameExists() {
		Assertions.assertEquals(true, utilityHelper.findUser("AdamTheAdmin2"));
	}
	
	@Test
	void testUsernameDoesNotExists() {
		Assertions.assertEquals(false, utilityHelper.findUser("Test"));
	}
	
	@Test
	void testUsersFoundFromFile() {
		Assertions.assertEquals(2, UtilityHelper.getUsersFromFile("src\\sample.txt").size());
	}
	
	@Test
	void testUsersNotFoundFromFile() {
		Assertions.assertEquals(0, UtilityHelper.getUsersFromFile("src\\sample2.txt").size());
	}
	
	@Test
	void testPasswordAndConfirmPassword() {
		Assertions.assertTrue(utilityHelper.validatePasswordAndConfirmPassword("password", "password"));
	}
	
	@Test
	void testPasswordAndConfirmPasswordMismatch() {
		Assertions.assertFalse(utilityHelper.validatePasswordAndConfirmPassword("password", "something"));
	}
	
	@Test
	void testFields1() {
		Assertions.assertTrue(utilityHelper.validateForgotPassword(null, "", ""));
	}
	
	@Test
	void testFields2() {
		Assertions.assertTrue(utilityHelper.validateForgotPassword("Sample", "", ""));
	}
	
	@Test
	void testFields3() {
		Assertions.assertTrue(utilityHelper.validateForgotPassword("Sample", "Password", ""));
	}
	
	@Test
	void testFields4() {
		Assertions.assertFalse(utilityHelper.validateForgotPassword("Sample", "Password", "Password"));
	}
}
