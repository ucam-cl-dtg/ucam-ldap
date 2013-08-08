package uk.ac.cam.cl.dtg.ldap.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import uk.ac.cam.cl.dtg.ldap.LDAPObjectNotFoundException;
import uk.ac.cam.cl.dtg.ldap.LDAPQueryManager;
import uk.ac.cam.cl.dtg.ldap.LDAPUser;

public class ExistingUserQueryTest {

	// user that exists
	String crsid = "hp343";

	@Test
	public void getcName() {

		// get cName
		String cName = null;
		try {

			LDAPUser u = LDAPQueryManager.getUser(crsid);

			cName = u.getcName();
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(cName);
		System.out.println("Registered name: " + cName);

	}

	@Test
	public void getSurname() {

		// get surname
		String surname = null;
		try {

			LDAPUser u = LDAPQueryManager.getUser(crsid);

			surname = u.getSurname();
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(surname);
		System.out.println("Surname: " + surname);

	}

	@Test
	public void getEmail() {

		// get email
		String email = null;
		try {

			LDAPUser u = LDAPQueryManager.getUser(crsid);

			email = u.getEmail();
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(email);
		System.out.println("Email: " + email);

	}

	@Test
	public void getStatus() {

		// get status
		List<String> status = null;
		try {

			LDAPUser u = LDAPQueryManager.getUser(crsid);

			status = u.getStatus();
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(status);
		System.out.println("Status: " + status.get(0));

	}

	@Test
	public void getInstitution() {

		// get primary institution
		List<String> primaryInst = null;
		try {

			LDAPUser u = LDAPQueryManager.getUser(crsid);

			primaryInst = u.getInstitutions();
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(primaryInst);
		System.out.println("Primary Institution: " + primaryInst.get(0));

	}

	@Test
	public void getPhoto() {

		// get primary photo
		List<String> primaryPhoto = null;
		try {

			LDAPUser u = LDAPQueryManager.getUser(crsid);

			primaryPhoto = u.getPhotos();
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(primaryPhoto);

	}

	@Test
	public void getEssentials() {

		// get user essentials
		HashMap<String, String> essentials = null;
		try {

			LDAPUser u = LDAPQueryManager.getUser(crsid);

			essentials = u.getEssentials();
		} catch (LDAPObjectNotFoundException e) {
			fail(e.getMessage());
		}
		assertNotNull(essentials);

	}

}
