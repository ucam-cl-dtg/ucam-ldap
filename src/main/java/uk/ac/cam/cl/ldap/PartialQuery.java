package uk.ac.cam.cl.ldap;


public class PartialQuery {
	
	/** Name of the session attribute containing the user PartialQuery object **/
	public static String ATTR_PARTIAL_QUERY = "UserPartialQuery";
	
	/** Tries holding cached matches **/
	private static LDAPTrie<LDAPUser> userCrsidMatches;
	private static LDAPTrie<LDAPUser> userNameMatches;
	private static LDAPTrie<LDAPGroup> groupNameMatches;
	
	private PartialQuery(){
		
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
