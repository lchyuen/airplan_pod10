package ubc.ece419.pod10;

import junit.framework.TestCase;
import ubc.ece419.pod10.action.user.UserSearchFlightsAction;
import ubc.ece419.pod10.domain.User;
import ubc.ece419.pod10.exceptions.UnableToCreateUserException;
import ubc.ece419.pod10.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class UserServiceTest extends TestCase {
	
	public static Long testUserID = (long) 0;
	
	public static Boolean testCreateUser = false;
	public static Boolean testLoginUser = false;
		
	String name = "tester";
	String password = "test1234";
	String wrongpass = "hahaha";
	String emailAddress  = "test@pod10.com";
	
	UserService userService = new UserService();
	
	public void testCreateUser() throws Exception
	{
		
		boolean result;
		boolean user_not_exist = true;
		
		try {
			testUserID = userService.createUser(name, password, emailAddress);
		} 
		catch(UnableToCreateUserException e){
			System.out.println(e.getMessage());
			user_not_exist = false;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			fail("==TEST ABORTED: Create User ID==");
			return;
		}
		
		if(user_not_exist){
			assertTrue("Create User: Cannot create test User", (testUserID > 0));
		}
		else
		{
			testUserID = userService.loginCheck(emailAddress, password);
		}
		
		
		// email address should be "blocked" since already used
		result = userService.isEmailAddressAlreadyUsed(emailAddress);
		assertTrue("Create User: Email address incorrectly shown as unused", result);
		
	}
	
	public void testLoginUser() throws Exception
	{
		if(!testCreateUser)
			testCreateUser();
		
		Long userID;
						
		// Check inputting wrong password
		userID = userService.loginCheck(emailAddress, wrongpass);
		assertNull("Login User: Wrong password detected as correct", userID);
		
		// Check inputting correct password obtains correct id
		userID = userService.loginCheck(emailAddress, password);
		assertEquals("Login User: Wrong user ID returned with correct password", testUserID, userID);
		
	}
	
	public void testUserByID() throws Exception
	{
		if(!testCreateUser)
			testCreateUser();
		
		User retUser;

		retUser = userService.getUserById(testUserID);
		
		//Check for parameters
		assertEquals(name, retUser.getName());
		assertEquals(emailAddress, retUser.getEmailAddress());
		//assertEquals(password, retUser.getPassword());
		
	}
	
	public void testUserIDByEmail() throws Exception
	{
		if(!testCreateUser)
			testCreateUser();
		
		Long retUserID;
		
		retUserID = userService.getUserIdByEmail(emailAddress);
		
		assertEquals("getUserIdByEmail: Email returned not equal", testUserID, retUserID);
	}
	
	public void testGetUserPoints() throws Exception
	{
		
		if(!testCreateUser)
			testCreateUser();
		
		User retUser;
		int userpoint;

		retUser = userService.getUserById(testUserID);
		
		userpoint = userService.getPoints(testUserID);	
		
		assertEquals("User point returned does not match table",retUser.getCurrentPointsBalance().intValue(), userpoint);
	}
	
}
