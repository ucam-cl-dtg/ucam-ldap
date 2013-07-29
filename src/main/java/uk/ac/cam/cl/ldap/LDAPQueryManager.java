package uk.ac.cam.cl.ldap;

import java.util.ArrayList;

import com.google.common.collect.ImmutableMap;

/**
 * 
 * @author      Holly Priest <hp343@cam.ac.uk>
 * @version     1                
 * This class will provide all the public methods to get information about a user. 
 * (I think) This will be the only class that is public, the rest will be accessed by
 * the package only.
 * 
 */
public class LDAPQueryManager {
	
	//example methods
	
	//Get details of user
	public static String getUsercName(String crsid){
		return LDAPQueryHelper.getcName(crsid);
	}
	public static String getUserSurname(String crsid){
		return LDAPQueryHelper.getSurname(crsid);
	}
	public static String getUserEmail(String crsid){
		return LDAPQueryHelper.getEmail(crsid);
	}
	public static String getUserInstitution(String crsid){
		return LDAPQueryHelper.getInstitution(crsid);
	}
	public static String getUserPhoto(String crsid){
		return LDAPQueryHelper.getPrimaryPhoto(crsid);
	}
	public static ImmutableMap<String,?> getUserEssentials(String crsid){
		return ImmutableMap.of();
	}	
	public static ImmutableMap<String,?> getUserAll(String crsid){
		return ImmutableMap.of();
	}	
	
	//Less important methods for users
	public static ImmutableMap<String,?> getUserInstitutions(String crsid){
		return ImmutableMap.of();
	}	
	public static ImmutableMap<String,?> getUserGroups(String crsid){
		return ImmutableMap.of();
	}	
	//Methods for other things like phone number, role webpage?
	
	//Methods for groups and institutions - these dont use the usermanager(since they wont be used much)
	public static ImmutableMap<String,?> getGroupName(String groupID){
		return ImmutableMap.of();
	}	
	public static ImmutableMap<String,?> getGroupMembers(String groupID){
		return ImmutableMap.of();
	}	
	public static ImmutableMap<String,?> getGroupEssentials(String groupID){
		return ImmutableMap.of();
	}	
	
	//Specific methods for autocomplete - do these use the user manager?
	public static ArrayList<ImmutableMap<String, ?>> tokenQueryUserByCRSID(String x){
		return new ArrayList<ImmutableMap<String,?>>();
	}
	public static ArrayList<ImmutableMap<String, ?>> tokenQueryUserBySurname(String x){
		return new ArrayList<ImmutableMap<String,?>>();		
	}
	public static ArrayList<ImmutableMap<String, ?>> tokenQueryGroupByName(String x){
		return new ArrayList<ImmutableMap<String,?>>();		
	}
}
