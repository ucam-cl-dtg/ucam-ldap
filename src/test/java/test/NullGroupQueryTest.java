package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import uk.ac.cam.cl.ldap.LDAPObjectNotFoundException;
import uk.ac.cam.cl.ldap.LDAPQueryManager;

public class NullGroupQueryTest {

	// group that doesn't exist
	String id = "xxxxxxxxxxx";
	
	@Test
	public void getTitle() {
		
		// get groupname
		try {
			LDAPQueryManager.getGroupName(id);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));			
		}
	
	}
	
	@Test
	public void getDescription() {
		
		// get surname
		try {
			LDAPQueryManager.getGroupDescription(id);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
	
	}
	
	@Test
	public void getUsers() {
		
		// get email
		try {
			LDAPQueryManager.getGroupUsers(id);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
	
	} 
	
	@Test
	public void getEssentials() {
		
		// get essentials
		try {
			LDAPQueryManager.getGroupEssentials(id);
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
		
	}

}
