package uk.ac.cam.cl.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class containing static methods to manage all partial LDAP queries.
 * Caches LDAPUser prefix queries using Trie structures and only queries LDAP if data
 * is not already stored
 */
public class LDAPPartialQuery {
	
	/** Name of the session attribute containing the user PartialQuery object **/
	public static String ATTR_PARTIAL_QUERY = "UserPartialQuery";
	
	/** Tries holding cached matches **/
	private static LDAPTrie<LDAPUser> userCrsidMatches;
	private static LDAPTrie<LDAPUser> userNameMatches;
	private static LDAPTrie<LDAPGroup> groupNameMatches;
	
	private LDAPPartialQuery(){
		
	}
	
	/**
	 * Prefix search by CRSID
	 * Makes a prefix query to LDAP for the specified string and returns a list of users
	 * that match the query along with their basic data (name, surname, email)
	 * @param x prefix string to search
	 * @return List of maps of user data
	 * @throws LDAPObjectNotFoundException
	 */
	public static List<HashMap<String, String>> partialUserByCrsid(String x) throws LDAPObjectNotFoundException {
		
		List<HashMap<String,String>> users = new ArrayList<HashMap<String,String>>();
		
		LDAPTrie<LDAPUser> userCrsidTrie = LDAPPartialQuery.getUserCrsidInstance();
		
		List<LDAPUser> matches = userCrsidTrie.getMatches(x);
		
		for(LDAPUser u : matches){
			users.add(u.getEssentials());
		}
		
		return users;
	}
	
	/**
	 * Prefix search by surname
	 * Makes a prefix query to LDAP for the specified string and returns a list of users
	 * that match the query along with their basic data (name, surname, email)
	 * @param x prefix string to search
	 * @return List of maps of user data
	 * @throws LDAPObjectNotFoundException
	 */
	public static List<HashMap<String, String>> partialUserBySurname(String x) throws LDAPObjectNotFoundException {
		
		List<HashMap<String,String>> users = new ArrayList<HashMap<String,String>>();
		
		LDAPTrie<LDAPUser> userSurnameTrie = LDAPPartialQuery.getUserNameInstance();
		
		List<LDAPUser> matches = userSurnameTrie.getMatches(x);
		
		for(LDAPUser u : matches){
			System.out.println(u.getcName());
			users.add(u.getEssentials());
		}
		
		return users;
	}
	
	/**
	 * Makes a prefix query to LDAP for the specified string and returns a list of groups
	 * that match the query along with their basic data (id, name, description)
	 * Note: does NOT return group users: must be retrieved separately through LDAPQueryManager
	 * @param x prefix string to search
	 * @return List of maps of group data
	 * @throws LDAPObjectNotFoundException
	 */
	public static List<HashMap<String, String>> partialGroupByName(String x) throws LDAPObjectNotFoundException {
		
		List<HashMap<String,String>> groups = new ArrayList<HashMap<String,String>>();
		
		LDAPTrie<LDAPGroup> groupNameTrie = LDAPPartialQuery.getGroupNameInstance();
		
		List<LDAPGroup> matches = groupNameTrie.getMatches(x);
		
		for(LDAPGroup g : matches){
			groups.add(g.getEssentials());
		}
		
		return groups;
	}
	
	static LDAPTrie<LDAPUser> getUserCrsidInstance(){
		
		if(userCrsidMatches==null){
			userCrsidMatches = new LDAPTrie<LDAPUser>("user", "uid");
		}
		return userCrsidMatches;
	}
	
	static LDAPTrie<LDAPUser> getUserNameInstance(){
		
		if(userNameMatches==null){
			userNameMatches = new LDAPTrie<LDAPUser>("user", "sn");
		}
		return userNameMatches;
	}
	
	static LDAPTrie<LDAPGroup> getGroupNameInstance(){
		
		if(groupNameMatches==null){
			groupNameMatches = new LDAPTrie<LDAPGroup>("group", "groupTitle");
		}
		return groupNameMatches;
	}
	
}
