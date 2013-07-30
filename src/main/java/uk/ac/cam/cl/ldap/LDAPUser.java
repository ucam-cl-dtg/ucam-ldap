package uk.ac.cam.cl.ldap;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author      Holly Priest <hp343@cam.ac.uk>
 * @version     1                
 * This class will provide all the information on a user, cached from LDAP
 * 
 */
public class LDAPUser {
	
	/**
	 * Fields to cache user data once looked up
	 */
	private String crsid;
	private String cName;
	private String surname;
	private String email;
	private List<String> institutions;
	private List<String> status;
	private List<String> photos;

	/** Class constructor taking a crsid of the user to lookup **/
	protected LDAPUser(String crsid, String cName, String surname, String email, 
				List<String> status, List<String> institutions,  List<String> photos){
		
		this.crsid = crsid;
			
		// set default values
		this.cName = ifNull(cName,"Unknown user");
		this.surname = ifNull(surname,"Unknown user");
		this.email = ifNull(email,"No email");
		this.institutions = ifNull(institutions,Arrays.asList("No institutions"));
		this.status = ifNull(status,Arrays.asList("Student"));
		this.photos = ifNull(photos,Arrays.asList("none"));
		
		
	}
	
	/**
	 * Get users display name 
	 * @return String displayName
	 */
	protected String getcName(){
			return cName;
	}
	
	/**
	 * Get users surname
	 * @return String surname
	 */
	protected String getSurname(){
			return surname;
	}
	
	/**
	 * Get users email
	 * @return String email
	 */
	protected String getEmail(){
		return email;
	}
	
	/**
	 * Gets a list of institutions associated with user
	 * @return String status
	 */
	protected List<String> getInstitutions(){
		return institutions;
	}

	/**
	 * Gets a list of misAffiliations associated with user
	 * If 'staff' misAffiliations user is present sets status as staff, otherwise student
	 * @return String status
	 */
	protected List<String> getStatus(){
		return status;
	}
	
	/**
	 * Gets photo as an encoded base 64 jpeg
	 * To display in soy template, use  <img src="data:image/jpeg;base64,{$user.photo}" /> or similar
	 * @return String photo
	 */
	protected List<String> getPhotos(){
		return photos;
	}	

	/**
	 * Set a default if a null value is returned from LDAP
	 */
	@SuppressWarnings("unchecked")
	private static <T> T ifNull(Object v,T d) {
		return v == null ? d : (T) v;
	}
}
