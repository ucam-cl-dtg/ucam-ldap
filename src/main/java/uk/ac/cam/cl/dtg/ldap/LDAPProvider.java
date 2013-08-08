package uk.ac.cam.cl.dtg.ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Holly Priest <hp343@cam.ac.uk>
 * @version 1 All the main functionality for the LDAP queries
 * 
 */
public class LDAPProvider {

	/** Connection constants **/
	private static final String CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private static final String PROVIDER_URL = "ldap://ldap.lookup.cam.ac.uk:389";
	private static final String CONTEXT_FILTER = "o=University of Cambridge,dc=cam,dc=ac,dc=uk";

	/**
	 * Initialises context and sets up basic query
	 * 
	 * @return NamingEnumeration<SearchResult>
	 */
	private static NamingEnumeration<SearchResult> initialiseContext(
			String type, String parameter, String subtree, boolean partial) {

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, PROVIDER_URL);

		String searchContext = "ou=" + subtree + "," + CONTEXT_FILTER;
		String searchParameters;
		if (partial) {
			searchParameters = "(" + type + "=" + parameter + "*)";
		} else {
			searchParameters = "(" + type + "=" + parameter + ")";
		}

		DirContext ctx;
		NamingEnumeration<SearchResult> searchResults;
		try {

			ctx = new InitialDirContext(env);

			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchResults = ctx.search(searchContext, searchParameters,
					controls);

		} catch (NamingException e) {
			return null;
		}

		return searchResults;

	}

	/**
	 * Sets up unique result query
	 * 
	 * @return NamingEnumeration<? extends Attribute>
	 * @throws LDAPObjectNotFoundException
	 */
	private static Attributes setupUniqueQuery(String type, String parameter,
			String subtree) throws LDAPObjectNotFoundException {
		SearchResult searchResult;

		try {
			searchResult = initialiseContext(type, parameter, subtree, false)
					.next();

			if (searchResult == null) {
				return null;
			}

		} catch (NamingException e) {
			return null;
		} catch (NullPointerException e) {
			throw new LDAPObjectNotFoundException("User not found");
		}

		return searchResult.getAttributes();
	}

	/**
	 * Unique user query Returns an LDAPUser object Takes 2 arguments: attribute
	 * to search (eg. uid) and r to search with
	 * 
	 * @return LDAPUser
	 */
	static LDAPUser uniqueUserQuery(String type, String parameter)
			throws LDAPObjectNotFoundException {

		Attributes userResult = setupUniqueQuery(type, parameter, "people");

		if (userResult == null) {
			throw new LDAPObjectNotFoundException("User not found");
		}

		return initLDAPUser(userResult);
	}

	/**
	 * List of users Query Returns a list of LDAPUser objects
	 */
	static ArrayList<LDAPUser> multipleUserQuery(String type, String parameter,
			boolean partial) throws LDAPObjectNotFoundException {

		NamingEnumeration<SearchResult> searchResults = initialiseContext(type,
				parameter, "people", partial);

		ArrayList<LDAPUser> users = new ArrayList<LDAPUser>();

		try {
			while (searchResults.hasMore()) {
				users.add(initLDAPUser(searchResults.next().getAttributes()));
			}
		} catch (NamingException e) {
			return null;
		}

		return users;

	}

	/**
	 * Unique group query Returns an LDAPGroup object Takes 2 arguments:
	 * attribute to search (eg. uid) and parameter to search with
	 * 
	 * @return LDAPUser
	 */
	static LDAPGroup uniqueGroupQuery(String type, String parameter)
			throws LDAPObjectNotFoundException {

		Attributes groupResult = setupUniqueQuery(type, parameter, "groups");

		return initLDAPGroup(groupResult);
	}

	/**
	 * Multiple group query Returns a list of LDAPGroup objects Takes 3
	 * arguments: attribute to search (eg. uid), parameter to search with and
	 * whether partial
	 * 
	 * @return LDAPUser
	 */
	static List<LDAPGroup> multipleGroupQuery(String type, String parameter,
			boolean partial) throws LDAPObjectNotFoundException {

		NamingEnumeration<SearchResult> searchResults = initialiseContext(type,
				parameter, "groups", partial);

		ArrayList<LDAPGroup> groups = new ArrayList<LDAPGroup>();

		try {
			while (searchResults.hasMore()) {
				System.out.println("more results");
				try {
					LDAPGroup g = initLDAPGroup(searchResults.next()
							.getAttributes());
					groups.add(g);
				} catch (LDAPObjectNotFoundException e) {
					// don't add the group to the list
					// log.debug(e.getMessage);
				}
			}
		} catch (NamingException e) {
			return null;
		}

		return groups;

	}

	/**
	 * Creates an LDAPUser object from attributes
	 * 
	 * @return LDAPUser
	 */
	private static LDAPUser initLDAPUser(Attributes userResult)
			throws LDAPObjectNotFoundException {

		String crsid;
		String cn;
		String sn;
		String mail;
		List<String> misAff;
		List<String> institutions;
		List<String> photos;

		try {
			// Get crisd
			if (userResult.get("uid") != null) {
				crsid = userResult.get("uid").get().toString();
			} else {
				crsid = null;
			}

			if (userResult.get("cn") != null) {
				// Get registered name
				cn = userResult.get("cn").get().toString();
			} else {
				cn = null;
			}

			if (userResult.get("sn") != null) {
				// Get surname
				sn = userResult.get("sn").get().toString();
			} else {
				sn = null;
			}

			if (userResult.get("mail") != null) {
				// Get email
				mail = userResult.get("mail").get().toString();
			} else {
				mail = null;
			}

			// Get misAffiliation
			NamingEnumeration<?> misAffEnum;
			misAff = new ArrayList<String>();

			if (userResult.get("misAffiliation") != null) {
				misAffEnum = userResult.get("misAffiliation").getAll();

				while (misAffEnum.hasMore()) {
					misAff.add(misAffEnum.next().toString());
				}
			} else {
				misAff = null;
			}

			// Get institutions
			NamingEnumeration<?> instEnum;
			institutions = new ArrayList<String>();

			if (userResult.get("ou") != null) {
				instEnum = userResult.get("ou").getAll();

				while (instEnum.hasMore()) {
					institutions.add(instEnum.next().toString());
				}
			} else {
				institutions = null;
			}

			// Get photos
			NamingEnumeration<?> photoEnum;
			photos = new ArrayList<String>();

			if (userResult.get("jpegPhoto") != null) {
				photoEnum = userResult.get("jpegPhoto").getAll();

				while (photoEnum.hasMore()) {
					byte[] p = (byte[]) photoEnum.next();
					photos.add(new String(Base64.encodeBase64(p)));
				}
			} else {
				photos = null;
			}

		} catch (NamingException e) {
			return null;
		}

		if (crsid == null) { // If uid is null the user does not exist
			throw new LDAPObjectNotFoundException("User does not exist");
		}

		return new LDAPUser(crsid, cn, sn, mail, misAff, institutions, photos);

	}

	/**
	 * Creates an LDAPGroup object from attributes
	 * 
	 * @return LDAPGroup
	 */
	private static LDAPGroup initLDAPGroup(Attributes groupResult)
			throws LDAPObjectNotFoundException {

		String groupID;
		String groupTitle;
		String description;
		List<String> users;

		try {

			if (!groupResult.get("visibility").get().toString().equals("cam")) {
				throw new LDAPObjectNotFoundException(
						"Group not publicly visible");
			}

			if (groupResult.get("groupID") != null) {
				// Get groupID
				groupID = groupResult.get("groupID").get().toString();
			} else {
				groupID = null;
			}

			if (groupResult.get("groupTitle") != null) {
				// Get group name
				groupTitle = groupResult.get("groupTitle").get().toString();
				System.out.println(groupTitle);
			} else {
				groupTitle = null;
			}

			if (groupResult.get("description") != null) {
				// Get description
				description = groupResult.get("description").get().toString();
			} else {
				description = null;
			}

			// Get users
			NamingEnumeration<?> usersEnum;
			users = new ArrayList<String>();

			if (groupResult.get("uid") != null) {
				usersEnum = groupResult.get("uid").getAll();
				while (usersEnum.hasMore()) {
					users.add(usersEnum.next().toString());
				}
			}

			if (groupID == null) {
				throw new LDAPObjectNotFoundException("Group does not exist");
			}

		} catch (NamingException e) {
			return null;
		}

		return new LDAPGroup(groupID, groupTitle, description, users);

	}

}
