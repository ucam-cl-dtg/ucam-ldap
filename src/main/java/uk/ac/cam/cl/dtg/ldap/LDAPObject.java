package uk.ac.cam.cl.dtg.ldap;

import java.util.HashMap;

public abstract class LDAPObject {

	public static int counter;
	
	/**
	 * Set a default if a null value is returned from LDAP
	 */
	@SuppressWarnings("unchecked")
	static <T> T ifNull(Object v, T d) {
		return v == null ? d : (T) v;
	}

	protected static final void add(int flags, int constant, String key,
			Object field, HashMap<String, Object> map) {
				if ((flags & constant) != 0) {
					map.put(key, field);
				}
			}

	abstract String getID();

	abstract String getName();

	public abstract HashMap<String, Object> toMap(int flags);

	public LDAPObject() {
		counter++;
	}

}