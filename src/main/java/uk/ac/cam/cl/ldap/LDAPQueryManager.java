package uk.ac.cam.cl.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author      Holly Priest <hp343@cam.ac.uk>
 * @version     1                
 * This class will provide all the public methods to get information from LDAP
 * This is the only class with public methods, all LDAP queries should be done through this class
 * 
 */
public class LDAPQueryManager {
	
	// User queries
	public static String getUsercName(String crsid) throws LDAPObjectNotFoundException {
		
		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPUser u = om.getUser(crsid);
		
		return u.getcName();
	}
	public static String getUserSurname(String crsid) throws LDAPObjectNotFoundException {
		
		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPUser u = om.getUser(crsid);
		
		return u.getSurname();
		
	}
	public static String getUserEmail(String crsid) throws LDAPObjectNotFoundException {

		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPUser u = om.getUser(crsid);
		
		return u.getEmail();
		
	}
	public static String getUserStatus(String crsid) throws LDAPObjectNotFoundException {

		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPUser u = om.getUser(crsid);
		
		List<String> statusList = u.getStatus();
		
		if(statusList.contains("Staff")){
			return "staff";
		} 
		
		return "student";
		
	}
	
	public static String getUserInstitution(String crsid) throws LDAPObjectNotFoundException {

		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPUser u = om.getUser(crsid);
		
		List<String> instList = u.getInstitutions();
		
		return instList.get(0);		
		
	}
	public static String getUserPhoto(String crsid) throws LDAPObjectNotFoundException {

		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPUser u = om.getUser(crsid);
		
		List<String> photos = u.getPhotos();
		
		return photos.get(0);
		
	}
	
	public static List<String> getAllUserPhotos(String crsid) throws LDAPObjectNotFoundException {
		
		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPUser u = om.getUser(crsid);
		
		return u.getPhotos();
	}
	
	public static HashMap<String, String> getUserEssentials(String crsid) throws LDAPObjectNotFoundException {

		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPUser u = om.getUser(crsid);
		
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put("crsid", crsid);
		data.put("cName", u.getcName());
		data.put("surname", u.getSurname());
		data.put("email", u.getEmail());
		
		return data;
		
	}	
	
	public static HashMap<String,?> getUserAll(String crsid) throws LDAPObjectNotFoundException {
		
		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPUser u = om.getUser(crsid);
		
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
	
	// Group queries
	public static String getGroupName(String groupID) throws LDAPObjectNotFoundException {
		
		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPGroup g = om.getGroup(groupID);
		
		return g.getName();
		
	}	
	public static String getGroupDescription(String groupID) throws LDAPObjectNotFoundException {
		
		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPGroup g = om.getGroup(groupID);
		
		return g.getDescription();
		
	}	
	public static List<String> getGroupUsers(String groupID) throws LDAPObjectNotFoundException {

		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPGroup g = om.getGroup(groupID);
		
		return g.getUsers();
		
	}	
	public static HashMap<String, ?> getGroupEssentials(String groupID) throws LDAPObjectNotFoundException {
		
		LDAPObjectManager om = LDAPObjectManager.getInstance();
		
		LDAPGroup g = om.getGroup(groupID);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("groupID", groupID);
		data.put("name", g.getName());
		data.put("description", g.getDescription());
		data.put("users", g.getUsers());
		
		return data;
		
	}	
	
	// Autocomplete queries
	public static List<HashMap<String, String>> partialUserByCrsid(String x) throws LDAPObjectNotFoundException {
		
		List<HashMap<String,String>> users = new ArrayList<HashMap<String,String>>();
		
		LDAPTrie<LDAPUser> userCrsidTrie = PartialQuery.getUserCrsidInstance();
		
		List<LDAPUser> matches = userCrsidTrie.getMatches(x);
		
		for(LDAPUser u : matches){
			System.out.println(u.getcName());
			users.add(u.getEssentials());
		}
		
		return users;
	}
	public static List<HashMap<String, String>> partialUserBySurname(String x) throws LDAPObjectNotFoundException {
		
		List<HashMap<String,String>> users = new ArrayList<HashMap<String,String>>();
		
		LDAPTrie<LDAPUser> userSurnameTrie = PartialQuery.getUserNameInstance();
		
		List<LDAPUser> matches = userSurnameTrie.getMatches(x);
		
		for(LDAPUser u : matches){
			System.out.println(u.getcName());
			users.add(u.getEssentials());
		}
		
		return users;
	}
	public static List<HashMap<String, String>> partialGroupByName(String x) throws LDAPObjectNotFoundException {
		
		List<HashMap<String,String>> users = new ArrayList<HashMap<String,String>>();
		
		LDAPTrie<LDAPGroup> groupNameTrie = PartialQuery.getGroupNameInstance();
		
		List<LDAPGroup> matches = groupNameTrie.getMatches(x);
		
		for(LDAPGroup u : matches){
			System.out.println(u.getName());
			//users.add(u.getName());
		}
		
		return users;
	}

}
