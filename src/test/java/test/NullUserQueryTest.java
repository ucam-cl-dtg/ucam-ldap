package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import uk.ac.cam.cl.ldap.LDAPObjectNotFoundException;
import uk.ac.cam.cl.ldap.LDAPQueryManager;

public class NullUserQueryTest {

	// user that doesn't exist
	String crsid = "xxxxxxxxxxx";
	
	@Test
	public void getcName() {
		
		// get cName
		try {
			LDAPQueryManager.getUsercName(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));			
		}
	
	}
	
	@Test
	public void getSurname() {
		
		// get surname
		try {
			LDAPQueryManager.getUserSurname(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
	
	}
	
	@Test
	public void getEmail() {
		
		// get email
		try {
			LDAPQueryManager.getUserEmail(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
	
	} 
	
	@Test
	public void getStatus() {
		
		// get status
		try {
			LDAPQueryManager.getUserStatus(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
	}
	
	@Test
	public void getInstitution() {
		
		// get primary institution
		try {
			LDAPQueryManager.getUserInstitution(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
	
	}
	
	@Test
	public void getPhoto() {
		
		// get primary photo
		try {
			LDAPQueryManager.getUserPhoto(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
	}
	
	@Test
	public void getEssentials() {
		
		// get essentials
		try {
			LDAPQueryManager.getUserEssentials(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
	}
	
	@Test
	public void getAll() {
		
		// get all
		try {
			LDAPQueryManager.getUserAll(crsid);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
	}
}
