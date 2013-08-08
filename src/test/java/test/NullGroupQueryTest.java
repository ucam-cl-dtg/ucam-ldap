package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import uk.ac.cam.cl.ldap.LDAPGroup;
import uk.ac.cam.cl.ldap.LDAPQueryManager;
import uk.ac.cam.cl.ldap.LDAPObjectNotFoundException;

public class NullGroupQueryTest {

	// group that doesn't exist
	String id = "xxxxxxxxxxx";
	
	@Test
	public void getTitle() {
		
		// get groupname
		try {

			LDAPGroup g = LDAPQueryManager.getGroup(id);
			
			g.getName();
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));			
		}
	
	}
	
	@Test
	public void getDescription() {
		
		// get surname
		try {

			LDAPGroup g = LDAPQueryManager.getGroup(id);
			
			g.getDescription();
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
	
	}
	
	@Test
	public void getUsers() {
		
		// get email
		try {
			
			LDAPGroup g = LDAPQueryManager.getGroup(id);
			
			g.getUsers();
			fail("Did not throw exception");
		} catch (LDAPObjectNotFoundException e) {
			assertTrue((e instanceof LDAPObjectNotFoundException));
		}
	
	} 
	


}
