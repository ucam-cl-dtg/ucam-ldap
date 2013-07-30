package uk.ac.cam.cl.ldap;

import java.util.HashMap;
import java.util.List;

/**
 * @author      Holly Priest <hp343@cam.ac.uk>
 * @version     1                
 * Kind of similar to LDAPQueryManager but with more specific methods? Maybe get rid of
 * this class altogether? Just some middleman methods to make the LQM a bit tidier
 * 
 */
public class LDAPQueryHelper {
	
	// users
	
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
	
	/**
	 * Get user's basic information: registered name, surname, email
	 * @return String photo
	 */
	protected static HashMap<String, String> getUserEssentials(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put("crsid", crsid);
		data.put("cName", u.getcName());
		data.put("surname", u.getSurname());
		data.put("email", u.getEmail());
		
		return data;
	}
	
	/**
	 * Get all user's information
	 * @return HashMap
	 */
	protected static HashMap<String, ?> getUserAll(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("crsid", crsid);
		data.put("cName", u.getcName());
		data.put("surname", u.getSurname());
		data.put("email", u.getEmail());
		data.put("institutions", u.getInstitutions());
		data.put("photos", u.getPhotos());
		data.put("status", u.getStatus());
		
		return data;
	}
	
	// groups
	
	/**
	 * Get group  name
	 * @return String groupName
	 */
	protected static String getGroupName(String groupID){
		
		LDAPGroupManager gm = LDAPGroupManager.getInstance();
		
		LDAPGroup g = gm.getGroupObject(groupID);
		
		return g.getName();
		
	}
	
	/**
	 * Get group  description
	 * @return String groupName
	 */
	protected static String getGroupDescription(String groupID){
		
		LDAPGroupManager gm = LDAPGroupManager.getInstance();
		
		LDAPGroup g = gm.getGroupObject(groupID);
		
		return g.getDescription();
		
	}
	
	/**
	 * Get group  users
	 * @return String groupName
	 */
	protected static List<String> getGroupUsers(String groupID){
		
		LDAPGroupManager gm = LDAPGroupManager.getInstance();
		
		LDAPGroup g = gm.getGroupObject(groupID);
		
		return g.getUsers();
		
	}
	
	/**
	 * Get all groups's information
	 * @return HashMap
	 */
	protected static HashMap<String, ?> getGroupAll(String groupID){
		
		LDAPGroupManager gm = LDAPGroupManager.getInstance();
		
		LDAPGroup g = gm.getGroupObject(groupID);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("groupID", groupID);
		data.put("name", g.getName());
		data.put("description", g.getDescription());
		data.put("users", g.getUsers());
		
		return data;
	}
}
