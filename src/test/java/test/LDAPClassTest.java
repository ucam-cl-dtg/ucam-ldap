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
	public void userQuery () {
		
		// get a user
		String crsid = "hp343";
		
		String cName = LDAPQueryManager.getUsercName(crsid);
		assertNotNull(cName);
		System.out.println(cName);
		
		String surname = LDAPQueryManager.getUserSurname(crsid);
		assertNotNull(surname);
		System.out.println(surname);
		
		String email = LDAPQueryManager.getUserEmail(crsid);
		assertNotNull(email);
		System.out.println(email);
		
		String primaryInst = LDAPQueryManager.getUserInstitution(crsid);
		assertNotNull(primaryInst);
		System.out.println(primaryInst);
		
		String primaryPhoto = LDAPQueryManager.getUserPhoto(crsid);
		assertNotNull(primaryPhoto);

		// get a group
		
	}

}
