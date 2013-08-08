package uk.ac.cam.cl.ldap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author      Holly Priest <hp343@cam.ac.uk>
 * @version     1                
 * This class will provide all the information on a user, cached from LDAP
 * 
 */
public class LDAPUser extends LDAPObject {
	
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
	LDAPUser(String crsid, String cName, String surname, String email, 
				List<String> status, List<String> institutions,  List<String> photos){
		
		super();
		
		this.crsid = crsid;
			
		// set default values
		this.cName = ifNull(cName,"Unknown user");
		this.surname = ifNull(surname,"Unknown user");
		this.email = ifNull(email,"No email");
		this.institutions = ifNull(institutions,Arrays.asList("No institution"));
		this.status = ifNull(status,Arrays.asList("Student"));
		this.photos = ifNull(photos,Arrays.asList("none"));
		
		
	}
	
	/**
	 * Get users crsid 
	 * @return String crsid
	 */
	@Override
	public String getID(){
			return crsid;
	}
	
	/**
	 * Get surname for trie matching
	 * @return String surname
	 */
	@Override
	String getName(){
			return surname;
	}
	
	/**
	 * Get users display name 
	 * @return String displayName
	 */
	public String getcName(){
			return cName;
	}
	
	/**
	 * Get users surname
	 * @return String surname
	 */
	public String getSurname(){
			return surname;
	}
	
	/**
	 * Get users email
	 * @return String email
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * Gets a list of institutions associated with user
	 * @return String status
	 */
	public List<String> getInstitutions(){
		return institutions;
	}

	/**
	 * Gets a list of misAffiliations associated with user
	 * If 'staff' misAffiliations user is present sets status as staff, otherwise student
	 * @return String status
	 */
	public List<String> getStatus(){
		return status;
	}
	
	/**
	 * Gets photo as an encoded base 64 jpeg
	 * To display in soy template, use  <img src="data:image/jpeg;base64,{$user.photo}" /> or similar
	 * @return String photo
	 */
	public List<String> getPhotos(){
		return photos;
	}	
	
	/**
	 * Gets cName, surname, email
	 * @return HashMap
	 */
	public HashMap<String, String> getEssentials(){
		
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put("crsid", crsid);
		data.put("name", cName);
		data.put("surname", surname);
		data.put("email", email);
		
		return data;
	}	
	
	/**
	 * Gets cName, surname, email
	 * @return HashMap
	 */
	public HashMap<String, String> getAll(){
		
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put("crsid", crsid);
		data.put("name", cName);
		data.put("surname", surname);
		data.put("email", email);
		data.put("institution", institutions.get(0));
		data.put("status", status.get(0));
		data.put("photo", photos.get(0));
		
		return data;
	}
	
	
}
