package uk.ac.cam.cl.ldap;

import java.util.List;

/**
 * @author      Holly Priest <hp343@cam.ac.uk>
 * @version     1                
 * Kind of similar to LDAPQueryManager but with more specific methods? Maybe get rid of
 * this class altogether? Just some middleman methods to make the LQM a bit tidier
 * 
 */
public class LDAPQueryHelper {
	
	/**
	 * Get user's registered (common) name
	 * @return String cName
	 */
	protected static String getcName(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		return u.getcName();
		
	}
	
	/**
	 * Get user's surname
	 * @return String surname
	 */
	protected static String getSurname(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		return u.getSurname();
		
	}
	
	/**
	 * Get user's email
	 * @return String email
	 */
	protected static String getEmail(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		return u.getEmail();
		
	}
	
	/**
	 * Get user's institution
	 * @return String institution
	 */
	protected static String getInstitution(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		List<String> instList = u.getInstitutions();
		
		return instList.get(0);
		
	}
	
	/**
	 * Get user's status
	 * @return String status
	 */
	protected static String getStatus(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		List<String> statusList = u.getStatus();
		
		if(statusList.contains("Staff")){
			return "staff";
		} 
		
		return "student";
	}
	
	/**
	 * Get user's primary photo
	 * @return String photo
	 */
	protected static String getPrimaryPhoto(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		List<String> photos = u.getPhotos();
		
		return photos.get(0);
	}
	
	/**
	 * Get all user's photos
	 * @return String photo
	 */
	protected static List<String> getAllPhotos(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		return u.getPhotos();
	}
}
