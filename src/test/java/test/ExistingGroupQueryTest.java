package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import uk.ac.cam.cl.ldap.LDAPObjectNotFoundException;
import uk.ac.cam.cl.ldap.LDAPQueryManager;

public class ExistingGroupQueryTest {
	
	// group that exists
	String id = "002278";
	
	@Test
	public void getTitle () {
		
		// get name
		String name = null;
		try {
			name = LDAPQueryManager.getGroupName(id);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(name);
		System.out.println("Group name: " +name);
	
	} 
	
	@Test
	public void getDescription () {
		
		// get surname
		String description = null;
		try {
			description = LDAPQueryManager.getGroupDescription(id);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(description);
		System.out.println("Description: " +description);
		
	}
	
	@Test
	public void getUsers () {
		
		// get users
		List<String> users = null;
		try {
			users = LDAPQueryManager.getGroupUsers(id);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(users);
		System.out.print("Users: ");
		for(String u : users){
			System.out.print(u+",");
		}
		System.out.println();
		
	}
	
	@Test
	public void getEssentials () {
		
		// get group essentials
		HashMap<String,?> essentials = null;
		try {
			 essentials = LDAPQueryManager.getGroupEssentials(id);
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(essentials);
		
	}

}
