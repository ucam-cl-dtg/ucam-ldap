package uk.ac.cam.cl.ldap;

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
		return LDAPQueryHelper.getcName(crsid);
	}
	public static String getUserSurname(String crsid){
		return LDAPQueryHelper.getSurname(crsid);
	}
	public static String getUserEmail(String crsid){
		return LDAPQueryHelper.getEmail(crsid);
	}
	public static String getUserStatus(String crsid){
		return LDAPQueryHelper.getStatus(crsid);
	}
	public static String getUserInstitution(String crsid){
		return LDAPQueryHelper.getInstitution(crsid);
	}
	public static String getUserPhoto(String crsid){
		return LDAPQueryHelper.getPrimaryPhoto(crsid);
	}
	public static HashMap<String, String> getUserEssentials(String crsid){
		return LDAPQueryHelper.getUserEssentials(crsid);
	}	
	public static HashMap<String,?> getUserAll(String crsid){
		return LDAPQueryHelper.getUserAll(crsid);
	}	
	
	// Get group data
	public static String getGroupName(String groupID){
		return LDAPQueryHelper.getGroupName(groupID);
	}	
	public static String getGroupDescription(String groupID){
		return LDAPQueryHelper.getGroupDescription(groupID);
	}	
	public static List<String> getGroupUsers(String groupID){
		return LDAPQueryHelper.getGroupUsers(groupID);
	}	
	public static HashMap<String, ?> getGroupEssentials(String groupID){
		return LDAPQueryHelper.getGroupAll(groupID);
	}	
	
	//Specific methods for autocomplete - for now cruedly passing in the session and iterating user objects here
	public static void partialQuery(HttpServletRequest r, String x){
		PartialQuery pq = (PartialQuery.getPartialQueryInstance(r));
		List<LDAPUser> matches = pq.userQuery(x);
		System.out.println("Partial query for: " + x);
		for(LDAPUser u : matches) {
			System.out.print(u.getCRSID() + ", ");
		}
		System.out.println();
	}

}
