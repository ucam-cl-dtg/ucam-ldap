package uk.ac.cam.cl.dtg.ldap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A class containing static methods to manage all partial LDAP queries. Caches
 * LDAPUser prefix queries using Trie structures and only queries LDAP if data
 * is not already stored
 */
public class LDAPPartialQuery {

	/** Name of the session attribute containing the user PartialQuery object **/
	public static String ATTR_PARTIAL_QUERY = "UserPartialQuery";

	/** Tries holding cached matches **/
	private static LDAPTrie<LDAPUser> userCrsidMatches = new LDAPTrie<LDAPUser>(
			"user", "uid");
	private static LDAPTrie<LDAPUser> userNameMatches = new LDAPTrie<LDAPUser>(
			"user", "sn");
	private static LDAPTrie<LDAPGroup> groupNameMatches = new LDAPTrie<LDAPGroup>(
			"group", "groupTitle");

	private LDAPPartialQuery() {

	}

	private static <T extends LDAPObject> List<HashMap<String, Object>> partialToMap(
			String partialQuery, LDAPTrie<T> trie, int fieldsToMap)
			throws LDAPObjectNotFoundException {
		if (partialQuery == null)
			throw new LDAPObjectNotFoundException(
					"No records found. Query was null");
		List<T> matches = trie.getMatches(partialQuery);
		List<HashMap<String, Object>> result = new LinkedList<HashMap<String, Object>>();
		for (T t : matches) {
			result.add(t.toMap(fieldsToMap));
		}
		return result;
	}

	/**
	 * Prefix search by CRSID Makes a prefix query to LDAP for the specified
	 * string and returns a list of users that match the query along with their
	 * basic data (name, surname, email)
	 * 
	 * @param x
	 *            prefix string to search
	 * @return List of maps of user data
	 * @throws LDAPObjectNotFoundException
	 */
	public static List<HashMap<String, Object>> partialUserByCrsid(String x)
			throws LDAPObjectNotFoundException {
		return partialToMap(x, userCrsidMatches, LDAPUser.INCLUDE_CRSID
				| LDAPUser.INCLUDE_NAME | LDAPUser.INCLUDE_DISPLAYNAME
				| LDAPUser.INCLUDE_SURNAME | LDAPUser.INCLUDE_EMAIL);
	}

	/**
	 * Prefix search by surname Makes a prefix query to LDAP for the specified
	 * string and returns a list of users that match the query along with their
	 * basic data (name, surname, email)
	 * 
	 * @param x
	 *            prefix string to search
	 * @return List of maps of user data
	 * @throws LDAPObjectNotFoundException
	 */
	public static List<HashMap<String, Object>> partialUserBySurname(String x)
			throws LDAPObjectNotFoundException {
		return partialToMap(x, userNameMatches, LDAPUser.INCLUDE_CRSID
				| LDAPUser.INCLUDE_NAME | LDAPUser.INCLUDE_DISPLAYNAME
				| LDAPUser.INCLUDE_SURNAME | LDAPUser.INCLUDE_EMAIL);
	}

	/**
	 * Makes a prefix query to LDAP for the specified string and returns a list
	 * of groups that match the query along with their basic data (id, name,
	 * description) Note: does NOT return group users: must be retrieved
	 * separately through LDAPQueryManager
	 * 
	 * @param x
	 *            prefix string to search
	 * @return List of maps of group data
	 * @throws LDAPObjectNotFoundException
	 */
	public static List<HashMap<String, Object>> partialGroupByName(String x)
			throws LDAPObjectNotFoundException {
		return partialToMap(x, groupNameMatches, LDAPGroup.INCLUDE_DESCRIPTION
				| LDAPGroup.INCLUDE_GROUPID | LDAPGroup.INCLUDE_GROUPTITLE);
	}

	/**
	 * Prefix search by CRSID Makes a prefix query to LDAP for the specified
	 * string and returns a list of users that match the query along with their
	 * basic data (name, surname, email) but only from the institution specified
	 * 
	 * @param x
	 *            prefix string to search
	 * @return List of maps of user data
	 * @throws LDAPObjectNotFoundException
	 */
	public static List<HashMap<String, Object>> partialUserByCrsidInInst(
			String x, String i) throws LDAPObjectNotFoundException {

		LDAPTrie<LDAPUser> instUsers = new LDAPTrie<>(
				LDAPProvider.multipleUserQuery("instID", i, false));

		return partialToMap(x, instUsers, LDAPUser.INCLUDE_CRSID
				| LDAPUser.INCLUDE_NAME | LDAPUser.INCLUDE_DISPLAYNAME
				| LDAPUser.INCLUDE_SURNAME | LDAPUser.INCLUDE_EMAIL);
	}

}
