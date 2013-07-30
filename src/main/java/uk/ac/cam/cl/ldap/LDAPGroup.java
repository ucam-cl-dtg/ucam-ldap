package uk.ac.cam.cl.ldap;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author      Holly Priest <hp343@cam.ac.uk>
 * @version     1                
 * This class will provide all the information on a group, cached from LDAP
 * 
 */
public class LDAPGroup {
	
	/**
	 * Fields to cache user data once looked up
	 */
	private String groupID;
	private String groupTitle;
	private String description;
	private List<String> users;

	/** Class constructor **/
	protected LDAPGroup(String groupID, String groupTitle, String description, List<String> users){
		
		this.groupID = groupID;
			
		// set default values
		this.groupTitle = ifNull(groupTitle,"Unknown group");
		this.description = ifNull(description,"No description");
		this.users = ifNull(users,new ArrayList<String>());	
		
	}
	
	/**
	 * Get groupID
	 * @return String groupID
	 */
	protected String getID(){
			return groupID;
	}
	
	/**
	 * Get group name
	 * @return String groupTitle
	 */
	protected String getName(){
			return groupTitle;
	}
	
	/**
	 * Get group description
	 * @return String description
	 */
	protected String getDescription(){
			return description;
	}
	
	/**
	 * Get group users
	 * @return List<String> users
	 */
	protected List<String> getUsers(){
		return users;
	}	

	/**
	 * Set a default if a null value is returned from LDAP
	 */
	@SuppressWarnings("unchecked")
	private static <T> T ifNull(Object v,T d) {
		return v == null ? d : (T) v;
	}
}
