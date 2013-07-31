package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import uk.ac.cam.cl.ldap.LDAPObjectNotFoundException;
import uk.ac.cam.cl.ldap.LDAPQueryManager;

public class LDAPClassTest {
	
	@Test
	public void ExistingUserQuery () {
		
		// get a user that exists
		String crsid = "hp343";
		
		// get cName
		String cName = null;
		try {
			cName = LDAPQueryManager.getUsercName(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(cName);
		System.out.println("Registered name: " +cName);
		
		// get surname
		String surname = null;
		try {
			surname = LDAPQueryManager.getUserSurname(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(surname);
		System.out.println("Surname: " +surname);
		
		// get email
		String email = null;
		try {
			email = LDAPQueryManager.getUserEmail(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(email);
		System.out.println("Email: " +email);
		
		// get status
		String status = null;
		try {
			status = LDAPQueryManager.getUserStatus(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(status);
		System.out.println("Status: " +status);
		
		// get primary institution
		String primaryInst = null;
		try {
			primaryInst = LDAPQueryManager.getUserInstitution(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(primaryInst);
		System.out.println("Primary Institution: " +primaryInst);
		
		// get primary photo
		String primaryPhoto = null;
		try {
			primaryPhoto = LDAPQueryManager.getUserPhoto(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull("Primary Photo: " +primaryPhoto);
		
	}
	
	@Test
	public void nullUserQuery() {
		
		// get a user that doesn't exist
		String crsid = "xxxxx";
		
		// get cName
		try {
			LDAPQueryManager.getUsercName(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));			
		}
		
		// get surname
		try {
			LDAPQueryManager.getUserSurname(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
		// get email
		try {
			LDAPQueryManager.getUserEmail(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
		// get status
		try {
			LDAPQueryManager.getUserStatus(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
		// get primary institution
		try {
			LDAPQueryManager.getUserInstitution(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
		// get primary photo
		try {
			LDAPQueryManager.getUserPhoto(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
	}
	
	@Test
	public void ExistingGroupQuery () {
		
		// get a group that exists
		String groupID = "002278"; //members of computer lab
		
		String groupName = null;
		try {
			groupName = LDAPQueryManager.getGroupName(groupID);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(groupName);
		System.out.println(groupName);
		
		String description = null;
		try {
			description = LDAPQueryManager.getGroupDescription(groupID);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(description);
		System.out.println(description);
		
		List<String> users = null;
		try {
			users = LDAPQueryManager.getGroupUsers(groupID);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		
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
