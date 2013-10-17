package uk.ac.cam.cl.dtg.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class containing all data for a particular LDAP queried group
 */
public class LDAPGroup extends LDAPObject {

	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_GROUPTITLE = "name";
	private static final String KEY_GROUPID = "id";
	/**
	 * Fields to cache user data once looked up
	 */
	private String groupID;
	private String groupTitle;
	private String description;
	private List<String> users;

	/** Class constructor **/
	LDAPGroup(String groupID, String groupTitle, String description,
			List<String> users) {

		super();

		this.groupID = groupID;

		// set default values
		this.groupTitle = ifNull(groupTitle, "Unknown group");
		this.description = ifNull(description, "No description");
		this.users = ifNull(users, new ArrayList<String>());
	}

	/**
	 * Get groupID
	 * 
	 * @return String groupID
	 */
	@Override
	public String getID() {
		return groupID;
	}

	/**
	 * Get group name
	 * 
	 * @return String groupTitle
	 */
	@Override
	public String getName() {
		return groupTitle;
	}

	/**
	 * Get group description
	 * 
	 * @return String description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get group users
	 * 
	 * @return List<String> users
	 */
	public List<String> getUsers() {
		return users;
	}

	/**
	 * Gets id, group name, description
	 * 
	 * @return HashMap
	 */
	public HashMap<String, Object> getEssentials() {
		return toMap(INCLUDE_DESCRIPTION | INCLUDE_GROUPID | INCLUDE_GROUPTITLE);
	}

	public static final int INCLUDE_DESCRIPTION = 1 << 0;
	public static final int INCLUDE_GROUPID = 1 << 1;
	public static final int INCLUDE_GROUPTITLE = 1 << 2;
	public static final int INCLUDE_ALL = INCLUDE_DESCRIPTION | INCLUDE_GROUPID
			| INCLUDE_GROUPTITLE;

	public HashMap<String, Object> toMap(int flags) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		add(flags, INCLUDE_DESCRIPTION, KEY_DESCRIPTION, description, data);
		add(flags, INCLUDE_GROUPID, KEY_GROUPID, groupID, data);
		add(flags, INCLUDE_GROUPTITLE, KEY_GROUPTITLE, groupTitle, data);
		return data;
	}
}
