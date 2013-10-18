package uk.ac.cam.cl.dtg.ldap;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
	public static final String KEY_COLLEGENAME = "collegename";
	
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
	 * Will return true if user is an undergrad or postgrad.
	 * 
	 * @return
	 */
	public boolean isStudent() {
		return this.status.contains("student");
	}

	/**
	 * Will return true if user is a staff member. However, some students also
	 * have this flag set
	 */
	public boolean isStaff() {
		return this.status.contains("staff");
	}

	private static ConcurrentHashMap<String, String> collegeMap;
	static {
		collegeMap = new ConcurrentHashMap<String, String>();

		collegeMap.put("CHRSTUG", "Christ's");
		collegeMap.put("CHURUG", "Churchill");
		collegeMap.put("CLAREUG", "Clare");
		collegeMap.put("CORPUG", "Corpus Christi");
		collegeMap.put("DOWNUG", "Downing");
		collegeMap.put("EMMUG", "Emmanuel");
		collegeMap.put("FITZUG", "Fitzwilliam");
		collegeMap.put("GIRTUG", "Girton");
		collegeMap.put("CAIUSUG", "Gonville & Caius");
		collegeMap.put("HOMUG", "Homerton");
		collegeMap.put("HUGHUG", "Hughes Hall");
		collegeMap.put("JESUSUG", "Jesus");
		collegeMap.put("KINGSUG", "King's");
		collegeMap.put("LCCUG", "Lucy Cavendish");
		collegeMap.put("MAGDUG", "Magdalene");
		collegeMap.put("NEWHUG", "Murray Edwards");
		collegeMap.put("NEWNUG", "Newnham");
		collegeMap.put("PEMBUG", "Pembroke");
		collegeMap.put("PETUG", "Peterhouse");
		collegeMap.put("QUENUG", "Queens'");
		collegeMap.put("ROBINUG", "Robinson");
		collegeMap.put("SELUG", "Selwyn");
		collegeMap.put("SIDUG", "Sidney Sussex");
		collegeMap.put("CATHUG", "St Catharine's");
		collegeMap.put("EDMUG", "St Edmund's");
		collegeMap.put("JOHNSUG", "St John's");
		collegeMap.put("TRINUG", "Trinity");
		collegeMap.put("TRINHUG", "Trinity Hall");
		collegeMap.put("WOLFCUG", "Wolfson");
	}

	/**
	 * Returns the name of the student's college. This doesn't produce the right
	 * results for graduate students (at least). Returns the string "Unknown" if
	 * no college is found.
	 * 
	 * @return
	 */
	public String getCollegeName() {
		for (String inst : instID) {
			String name = collegeMap.get(inst);
			if (name != null)
				return name;
		}
		return "Unknown";
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
	 * Flag to include the display name. This is either their personalised
	 * choice of name if they have one or their LDAP registered name if they
	 * dont
	 */
	public static final int INCLUDE_DISPLAYNAME = 1 << 2;

	/**
	 * Flag to include email address
	 */
	public static final int INCLUDE_EMAIL = 1 << 3;

	/**
	 * Flag to include instid. This is a list of groupIDs for LDAP groups
	 */
	public static final int INCLUDE_INSTID = 1 << 4;

	/**
	 * Flag to include a list of institution names that the user is associated
	 * with. This is a list of names corresponding with instIDs
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

	/**
	 * Flag to inlcude the user's college if we could work it out.
	 */
	public static final int INCLUDE_COLLEGENAME = 1<<10;
	
	public static final int INCLUDE_ALL = INCLUDE_CRSID | INCLUDE_DISPLAYNAME
			| INCLUDE_EMAIL | INCLUDE_INSTID | INCLUDE_INSTITUTIONS
			| INCLUDE_PHOTO | INCLUDE_NAME | INCLUDE_STATUS | INCLUDE_SURNAME | INCLUDE_COLLEGENAME;

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
		add(flags,INCLUDE_COLLEGENAME, KEY_COLLEGENAME,getCollegeName(),data);
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
