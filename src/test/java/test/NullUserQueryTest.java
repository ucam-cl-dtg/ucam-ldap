package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import uk.ac.cam.cl.ldap.LDAPQueryManager;
import uk.ac.cam.cl.ldap.LDAPObjectNotFoundException;
import uk.ac.cam.cl.ldap.LDAPUser;

public class NullUserQueryTest {

	// user that doesn't exist
	String crsid = "xxxxxxxxxxx";
	
	@Test
	public void getcName() {
		
		// get cName
		try {
			LDAPQueryManager qm = LDAPQueryManager.getInstance();
			
			LDAPUser u = qm.getUser(crsid);
			
			u.getcName();
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));			
		}
	
	}
	
	@Test
	public void getSurname() {
		
		// get surname
		try {
			LDAPQueryManager qm = LDAPQueryManager.getInstance();
			
			LDAPUser u = qm.getUser(crsid);
			
			u.getSurname();
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
	
	}
	
	@Test
	public void getEmail() {
		
		// get email
		try {
			LDAPQueryManager qm = LDAPQueryManager.getInstance();
			
			LDAPUser u = qm.getUser(crsid);
			
			u.getEmail();
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
	
	} 
	
	@Test
	public void getStatus() {
		
		// get status
		try {
			LDAPQueryManager qm = LDAPQueryManager.getInstance();
			
			LDAPUser u = qm.getUser(crsid);
			
			u.getStatus();
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
	}
	
	@Test
	public void getInstitution() {
		
		// get primary institution
		try {
			LDAPQueryManager qm = LDAPQueryManager.getInstance();
			
			LDAPUser u = qm.getUser(crsid);
			
			u.getInstitutions();
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
	
	}
	
	@Test
	public void getPhoto() {
		
		// get primary photo
		try {
			LDAPQueryManager qm = LDAPQueryManager.getInstance();
			
			LDAPUser u = qm.getUser(crsid);
			
			u.getPhotos();
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
	}
	
	@Test
	public void getEssentials() {
		
		// get essentials
		try {
			LDAPQueryManager qm = LDAPQueryManager.getInstance();
			
			LDAPUser u = qm.getUser(crsid);
			
			u.getEssentials();
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
	}

}
