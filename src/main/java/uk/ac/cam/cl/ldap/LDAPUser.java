package uk.ac.cam.cl.ldap;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author      Holly Priest <hp343@cam.ac.uk>
 * @version     1                
 * This class will provide all the information on a user, pulled (lazily?) from LDAP
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

	/**
	 * Class constructor taking a crsid of the user to lookup
	 */
	protected LDAPUser(String crsid){
		
		this.crsid = crsid;
			
		// set default values
		this.cName = "Unknown user";
		this.surname = "Unknown user";
		this.email = "No email";
		this.institutions = Arrays.asList("No institutions");
		this.status = Arrays.asList("Student");
		this.photos = Arrays.asList("none");
		
		// get and store user info
		getUserInfo();
		
	}
	
	/**
	 * Get users display name 
	 * @return String displayName
	 */
	protected String getcName(){
		if(cName==null){
			getUserInfo();
		}
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
	 * Gets all information related to user and caches it
	 */
	protected void getUserInfo(){
		HashMap<String, ?> userData = LDAPProvider.fullUserQuery(crsid);
		
		this.cName = ifNull(userData.get("cName"),"Unknown");
		this.surname = ifNull(userData.get("surname"),"Unknown");
		this.email =  ifNull(userData.get("email"),"No email");
		this.institutions = Collections.unmodifiableList(ifNull(userData.get("institutions"),Arrays.asList("No Institution")));
		this.status = Collections.unmodifiableList(ifNull(userData.get("status"),Arrays.asList("Student")));
		this.photos = Collections.unmodifiableList(ifNull(userData.get("photos"),Arrays.asList("none")));;		

	}	

	/**
	 * Set a default if a null value is returned from LDAP
	 */
	@SuppressWarnings("unchecked")
	private static <T> T ifNull(Object v,T d) {
		return v == null ? d : (T) v;
	}
}
