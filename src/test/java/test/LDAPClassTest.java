package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import uk.ac.cam.cl.ldap.LDAPQueryManager;

public class LDAPClassTest {
	
	@Test
	public void ExistingUserQuery () {
		
		// get a user that exists
		String crsid = "hp343";
		
		String cName = LDAPQueryManager.getUsercName(crsid);
		assertNotNull(cName);
		System.out.println("Registered name: " +cName);
		
		String surname = LDAPQueryManager.getUserSurname(crsid);
		assertNotNull(surname);
		System.out.println("Surname: " +surname);
		
		String email = LDAPQueryManager.getUserEmail(crsid);
		assertNotNull(email);
		System.out.println("Email: " +email);
		
		String status = LDAPQueryManager.getUserStatus(crsid);
		assertNotNull(status);
		System.out.println("Status: " +status);
		
		String primaryInst = LDAPQueryManager.getUserInstitution(crsid);
		assertNotNull(primaryInst);
		System.out.println("Primary Institution: " +primaryInst);
		
		String primaryPhoto = LDAPQueryManager.getUserPhoto(crsid);
		assertNotNull("Primary Photo: " +primaryPhoto);
		
	}
	
	@Test
	public void nullUserQuery() {
		
	}
	
	@Test
	public void ExistingGroupQuery () {
		
		// get a group that exists
		String groupID = "002278"; //members of computer lab
		
		String groupName = LDAPQueryManager.getGroupName(groupID);
		assertNotNull(groupName);
		System.out.println(groupName);
		
		String description = LDAPQueryManager.getGroupDescription(groupID);
		assertNotNull(description);
		System.out.println(description);
		
		List<String> users = LDAPQueryManager.getGroupUsers(groupID);
		assertNotNull(users);
		System.out.println("Users: ");
		for(String u : users){
			System.out.print(u + ",");	
		}

	}
	
	@Test
	public void nullGroupQuery() {
		
	}
	
	@Test
	public void invisibleGroupQuery() {
		
	}

	@Test
	public void noUsersGroupQuery () {

	}
	
}
