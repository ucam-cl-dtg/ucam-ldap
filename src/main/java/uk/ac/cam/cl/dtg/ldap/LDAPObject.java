package uk.ac.cam.cl.dtg.ldap;

public abstract class LDAPObject {

	public static int counter;
	
	/**
	 * Set a default if a null value is returned from LDAP
	 */
	@SuppressWarnings("unchecked")
	static <T> T ifNull(Object v, T d) {
		return v == null ? d : (T) v;
	}

	abstract String getID();

	abstract String getName();

	public LDAPObject() {
		counter++;
	}

}