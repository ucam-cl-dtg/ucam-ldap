package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;

import uk.ac.cam.cl.ldap.LDAPObjectNotFoundException;
import uk.ac.cam.cl.ldap.LDAPQueryManager;

public class ExistingUserQueryTest {
	
	// user that exists
	String crsid = "hp343";
	
	@Test
	public void getcName () {
		
		// get cName
		String cName = null;
		try {
			cName = LDAPQueryManager.getUsercName(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(cName);
		System.out.println("Registered name: " +cName);
	
	} 
	
	@Test
	public void getSurname () {
		
		// get surname
		String surname = null;
		try {
			surname = LDAPQueryManager.getUserSurname(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(surname);
		System.out.println("Surname: " +surname);
		
	}
	
	@Test
	public void getEmail () {
		
		// get email
		String email = null;
		try {
			email = LDAPQueryManager.getUserEmail(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(email);
		System.out.println("Email: " +email);
		
	}
	
	@Test
	public void getStatus () {
		
		// get status
		String status = null;
		try {
			status = LDAPQueryManager.getUserStatus(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(status);
		System.out.println("Status: " +status);
		
	}
	
	@Test
	public void getInstitution () {
		
		// get primary institution
		String primaryInst = null;
		try {
			primaryInst = LDAPQueryManager.getUserInstitution(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(primaryInst);
		System.out.println("Primary Institution: " +primaryInst);
		
	}
	
	@Test
	public void getPhoto () {
		
		// get primary photo
		String primaryPhoto = null;
		try {
			primaryPhoto = LDAPQueryManager.getUserPhoto(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(primaryPhoto);
		
	}
	
	@Test
	public void getEssentials () {
		
		// get user essentials
		HashMap<String,String> essentials = null;
		try {
			 essentials = LDAPQueryManager.getUserEssentials(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(essentials);
		
	}
	
	@Test
	public void getAll () {
		
		// get user all
		HashMap<String,String> all = null;
		try {
			 all = LDAPQueryManager.getUserEssentials(crsid);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(all);
		
	}
}
