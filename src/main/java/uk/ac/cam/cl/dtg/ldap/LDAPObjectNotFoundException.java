package uk.ac.cam.cl.dtg.ldap;

public class LDAPObjectNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LDAPObjectNotFoundException(String message) {
		super(message);
	}

	public LDAPObjectNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LDAPObjectNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public LDAPObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LDAPObjectNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
