package uk.ac.cam.cl.ldap;

public abstract class LDAPObject {

	/**
	 * Set a default if a null value is returned from LDAP
	 */
	@SuppressWarnings("unchecked")
	protected static <T> T ifNull(Object v, T d) {
		return v == null ? d : (T) v;
	}

	protected abstract String getID();

	public LDAPObject() {
		
	}

}