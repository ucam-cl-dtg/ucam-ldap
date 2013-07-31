package uk.ac.cam.cl.ldap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PartialQuery {
	
	/** Name of the session attribute containing the user PartialQuery object **/
	public static String ATTR_PARTIAL_QUERY = "UserPartialQuery";
	
	/** Trie holding cached matches **/
	private LDAPTrie<LDAPUser> userMatches;
	
	private PartialQuery(){
		userMatches = new LDAPTrie<LDAPUser>();
	}
	
	List<LDAPUser> userQuery(String x) throws LDAPObjectNotFoundException {
		return userMatches.getMatches(x);
	}
	
	static PartialQuery getPartialQueryInstance(HttpServletRequest req){
		PartialQuery sessionPQ = (PartialQuery)req.getSession().getAttribute(ATTR_PARTIAL_QUERY);
		if(sessionPQ!=null){
			return sessionPQ;
		} else {

			req.getSession().setAttribute(ATTR_PARTIAL_QUERY, new PartialQuery());
			return (PartialQuery)req.getSession().getAttribute(ATTR_PARTIAL_QUERY);
		}
	}
	
}
