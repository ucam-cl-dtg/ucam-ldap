package uk.ac.cam.cl.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author      Holly Priest <hp343@cam.ac.uk>
 * @version     1                
 * This class will provide all the public methods to get information from LDAP
 * This is the only class with public methods, all LDAP queries should be done through this class
 * 
 */
public class LDAPQueryManager {
	
	// Get user data
	public static String getUsercName(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		return u.getcName();
	}
	public static String getUserSurname(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		return u.getSurname();
		
	}
	public static String getUserEmail(String crsid){

		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		return u.getEmail();
		
	}
	public static String getUserStatus(String crsid){

		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		List<String> statusList = u.getStatus();
		
		if(statusList.contains("Staff")){
			return "staff";
		} 
		
		return "student";
		
	}
	
	public static String getUserInstitution(String crsid){

		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		List<String> instList = u.getInstitutions();
		
		return instList.get(0);		
		
	}
	public static String getUserPhoto(String crsid){

		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		List<String> photos = u.getPhotos();
		
		return photos.get(0);
		
	}
	
	public static List<String> getAllUserPhotos(String crsid){
		
		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		return u.getPhotos();
	}
	
	public static HashMap<String, String> getUserEssentials(String crsid){

		LDAPUserManager um = LDAPUserManager.getInstance();
		
		LDAPUser u = um.getUserObject(crsid);
		
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put("crsid", crsid);
		data.put("cName", u.getcName());
		data.put("surname", u.getSurname());
		data.put("email", u.getEmail());
		
		return data;
		
	}	
	
	public static HashMap<String,?> getUserAll(String crsid){
		
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
	
	// Get group data
	public static String getGroupName(String groupID){
		
		LDAPGroupManager gm = LDAPGroupManager.getInstance();
		
		LDAPGroup g = gm.getGroupObject(groupID);
		
		return g.getName();
		
	}	
	public static String getGroupDescription(String groupID){
		
		LDAPGroupManager gm = LDAPGroupManager.getInstance();
		
		LDAPGroup g = gm.getGroupObject(groupID);
		
		return g.getDescription();
		
	}	
	public static List<String> getGroupUsers(String groupID){

		LDAPGroupManager gm = LDAPGroupManager.getInstance();
		
		LDAPGroup g = gm.getGroupObject(groupID);
		
		return g.getUsers();
		
	}	
	public static HashMap<String, ?> getGroupEssentials(String groupID){
		
		LDAPGroupManager gm = LDAPGroupManager.getInstance();
		
		LDAPGroup g = gm.getGroupObject(groupID);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("groupID", groupID);
		data.put("name", g.getName());
		data.put("description", g.getDescription());
		data.put("users", g.getUsers());
		
		return data;
		
	}	
	
	//Specific methods for autocomplete - for now cruedly passing in the session
	public static List<HashMap<String, String>> partialUserQuery(HttpServletRequest r, String x){
		
		PartialQuery pq = (PartialQuery.getPartialQueryInstance(r));
		List<LDAPUser> matches = pq.userQuery(x);
		
		List<HashMap<String, String>> users = new ArrayList<HashMap<String, String>>();
		
		for(LDAPUser u : matches){
			users.add(u.getEssentials());
		}
		
		return users;
	}

}
