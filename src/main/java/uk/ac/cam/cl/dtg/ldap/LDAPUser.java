package uk.ac.cam.cl.dtg.ldap;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * A class containing all data for a particular LDAP queried user
 */
public class LDAPUser extends LDAPObject {

	private static final String KEY_SURNAME = "surname";
	private static final String KEY_STATUS = "status";
	private static final String KEY_NAME = "name";
	private static final String KEY_PHOTO = "photo";
	private static final String KEY_INSTITUTIONS = "institution";
	private static final String KEY_INSTID = "instID";
	private static final String KEY_EMAIL = "email";
	public static final String KEY_DISPLAYNAME = "username";
	public static final String KEY_CRSID = "crsid";
	/**
	 * Fields to cache user data once looked up
	 */
	private String crsid;
	private String regName;
	private String displayName;
	private String surname;
	private String email;
	private List<String> instID;
	private List<String> institutions;
	private List<String> status;
	private List<String> photos;

	/** Class constructor taking a crsid of the user to lookup **/
	LDAPUser(String crsid, String regName, String displayName, String surname,
			String email, List<String> instID, List<String> status,
			List<String> institutions, List<String> photos) {

		super();

		this.crsid = crsid;

		// set default values
		this.regName = ifNull(regName, "undefined");
		this.displayName = ifNull(displayName, regName);
		this.surname = ifNull(surname, "undefined");
		this.email = ifNull(email, "undefined");
		this.instID = ifNull(instID, Arrays.asList("undefined"));
		this.institutions = ifNull(institutions, Arrays.asList("undefined"));
		this.status = ifNull(status, Arrays.asList("undefined"));
		this.photos = ifNull(photos, Arrays.asList("undefined"));

		Collections.sort(this.institutions);
		Collections.sort(this.instID);
	}

	/**
	 * Get users crsid
	 * 
	 * @return String crsid
	 */
	@Override
	public String getID() {
		return crsid;
	}

	/**
	 * Get surname for trie matching
	 * 
	 * @return String surname
	 */
	@Override
	String getName() {
		return surname;
	}

	/**
	 * Get users display name, defaults to registered name if not set
	 * 
	 * @return String registered name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Old method to get display name
	 * 
	 * @deprecated user {@link getDisplayName()} instead.
	 */
	@Deprecated
	public String getcName() {
		return displayName;
	}

	/**
	 * Get users registered name
	 * 
	 * @return String registered name
	 */
	public String getRegName() {
		return regName;
	}

	/**
	 * Get users surname
	 * 
	 * @return String surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Get users email
	 * 
	 * @return String email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Get institution id
	 * 
	 * @return String instID
	 */
	public List<String> getInstID() {
		return instID;
	}

	/**
	 * Gets a list of institutions associated with user
	 * 
	 * @return String status
	 */
	public List<String> getInstitutions() {
		return institutions;
	}

	/**
	 * Gets a list of misAffiliations associated with user If 'staff'
	 * misAffiliations user is present sets status as staff, otherwise student
	 * 
	 * @return String status
	 */
	public List<String> getStatus() {
		return status;
	}

	/**
	 * Gets photo as an encoded base 64 jpeg To display in soy template, use
	 * <img src="data:image/jpeg;base64,{$user.photo}" /> or similar
	 * 
	 * @return String photo
	 */
	public List<String> getPhotos() {
		return photos;
	}

	/**
	 * Flag to include the user's CRSID in results
	 */
	public static final int INCLUDE_CRSID = 1 << 0;
	
	/**
	 * Flag to include the display name.  This is either their personalised choice of name if they have one or their LDAP registered name if they dont
	 */
	public static final int INCLUDE_DISPLAYNAME = 1 << 2;
	
	/**
	 * Flag to include email address
	 */
	public static final int INCLUDE_EMAIL = 1 << 3;
	
	/**
	 * Flag to include instid. TODO: What is this?
	 */
	public static final int INCLUDE_INSTID = 1 << 4;
	
	/**
	 * Flag to include a list of institution names that the user is associated with
	 */
	public static final int INCLUDE_INSTITUTIONS = 1 << 5;
	
	/**
	 * Flag to include the user's photo - this is a base64 encoded jpeg 
	 */
	public static final int INCLUDE_PHOTO = 1 << 6;
	
	/**
	 * Flag to include the user's registered name from LDAP
	 */
	public static final int INCLUDE_NAME = 1 << 7;
	
	/**
	 * Flag to include the user's status. TODO: what values can this have
	 */
	public static final int INCLUDE_STATUS = 1 << 8;
	
	/**
	 * Flag to include the user's surname
	 */
	public static final int INCLUDE_SURNAME = 1 << 9;

	public static final int INCLUDE_ALL = INCLUDE_CRSID | INCLUDE_DISPLAYNAME
			| INCLUDE_EMAIL | INCLUDE_INSTID | INCLUDE_INSTITUTIONS
			| INCLUDE_PHOTO | INCLUDE_NAME | INCLUDE_STATUS | INCLUDE_SURNAME;

	@Override
	public HashMap<String, Object> toMap(int flags) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		add(flags, INCLUDE_CRSID, KEY_CRSID, crsid, data);
		add(flags, INCLUDE_DISPLAYNAME, KEY_DISPLAYNAME, displayName, data);
		add(flags, INCLUDE_EMAIL, KEY_EMAIL, email, data);
		add(flags, INCLUDE_INSTID, KEY_INSTID, instID, data);
		add(flags, INCLUDE_INSTITUTIONS, KEY_INSTITUTIONS, institutions, data);
		add(flags, INCLUDE_PHOTO, KEY_PHOTO, photos.get(0), data);
		add(flags, INCLUDE_NAME, KEY_NAME, regName, data);
		add(flags, INCLUDE_STATUS, KEY_STATUS, status.get(0), data);
		add(flags, INCLUDE_SURNAME, KEY_SURNAME, surname, data);
		return data;
	}

	/**
	 * Gets cName, surname, email
	 * 
	 * @return HashMap
	 */
	public HashMap<String, Object> getEssentials() {
		return toMap(INCLUDE_CRSID | INCLUDE_NAME | INCLUDE_DISPLAYNAME
				| INCLUDE_SURNAME | INCLUDE_EMAIL);
	}

	/**
	 * Gets cName, surname, email
	 * 
	 * @return HashMap
	 */
	public HashMap<String, Object> getAll() {
		return toMap(INCLUDE_ALL);
	}

}
