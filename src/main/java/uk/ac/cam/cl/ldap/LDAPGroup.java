package uk.ac.cam.cl.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**         
 * A class containing all data for a particular LDAP queried group
 */
public class LDAPGroup extends LDAPObject {
	
	/**
	 * Fields to cache user data once looked up
	 */
	private String groupID;
	private String groupTitle;
	private String description;
	private List<String> users;

	/** Class constructor **/
	LDAPGroup(String groupID, String groupTitle, String description, List<String> users){
		
		super();
		
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
	@Override
	public String getID(){
			return groupID;
	}
	
	/**
	 * Get group name
	 * @return String groupTitle
	 */
	@Override
	public String getName(){
			return groupTitle;
	}
	
	/**
	 * Get group description
	 * @return String description
	 */
	public String getDescription(){
			return description;
	}
	
	/**
	 * Get group users 
	 * @return List<String> users
	 */
	public List<String> getUsers(){
		return users;
	}
	
	/**
	 * Gets id, group name, description
	 * @return HashMap
	 */
	public HashMap<String, String> getEssentials(){
		
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put("id", groupID);
		data.put("name", groupTitle);
		data.put("description", description);
		
		return data;
	}	
}
