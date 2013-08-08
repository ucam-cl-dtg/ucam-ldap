package uk.ac.cam.cl.ldap;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * A singleton class containing static methods to manage all (none partial) LDAP
 * queries. Caches LDAPUser and LDAPGroup objects using a LoadingCache with weak
 * keys and soft values Returns an LDAPUser or an LDAPGroup
 */
public class LDAPQueryManager {

	/** Singleton instance of LDAPGroupManager */
	private static LDAPQueryManager om;

	/** Cache holding LDAPUser objects mapped by crsid */
	private LoadingCache<String, LDAPUser> userMap;
	/** Cache holding LDAPGroup objects mapped by groupID */
	private LoadingCache<String, LDAPGroup> groupMap;

	/**
	 * Gets an LDAPUser from the cache if it is there, or queries LDAP and
	 * stores user in the cache if not.
	 * 
	 * @param crsid
	 *            String crsid of the user to look up
	 * @return LDAPUser holding LDAP data of user
	 * @throws LDAPObjectNotFoundException
	 *             if user with specified crsid was not found in LDAP
	 */
	public static LDAPUser getUser(String crsid)
			throws LDAPObjectNotFoundException {

		LDAPQueryManager qm = LDAPQueryManager.getInstance();

		LDAPUser user;
		try {
			user = qm.userMap.get(crsid);
		} catch (ExecutionException e) {
			throw new LDAPObjectNotFoundException("Error getting user: "
					+ e.getMessage());
		}

		if (user == null) {
			throw new LDAPObjectNotFoundException(
					"Unable to retrieve user from cache or otherwise");
		}

		return user;
	}

	/**
	 * Gets an LDAPGroup from the cache if it is there, or queries LDAP and
	 * stores group in the cache if not.
	 * 
	 * @param groupID
	 *            String id of the group to look up
	 * @return LDAPGroup holding LDAP data of group
	 * @throws LDAPObjectNotFoundException
	 *             if group with specified id was not found in LDAP
	 */
	public static LDAPGroup getGroup(String groupID)
			throws LDAPObjectNotFoundException {

		LDAPQueryManager qm = LDAPQueryManager.getInstance();

		LDAPGroup group;
		try {
			group = qm.groupMap.get(groupID);
		} catch (ExecutionException e) {
			throw new LDAPObjectNotFoundException("Error getting group: "
					+ e.getMessage());
		}

		if (group == null) {
			throw new LDAPObjectNotFoundException(
					"Unable to retrieve group from cache or otherwise");
		}

		return group;
	}

	private LDAPQueryManager() {
		// Create a user cache with soft keys
		userMap = CacheBuilder.newBuilder().maximumSize(500).weakKeys()
				.softValues().build(new CacheLoader<String, LDAPUser>() {
					public LDAPUser load(String crsid)
							throws LDAPObjectNotFoundException {
						return LDAPProvider.uniqueUserQuery("uid", crsid);
					}
				});
		// Create a group cache with soft keys
		groupMap = CacheBuilder.newBuilder().maximumSize(500).weakKeys()
				.softValues().build(new CacheLoader<String, LDAPGroup>() {
					public LDAPGroup load(String groupID)
							throws LDAPObjectNotFoundException {
						return LDAPProvider
								.uniqueGroupQuery("groupID", groupID);
					}
				});
	}

	/**
	 * Get singleton instance of LDAPQueryManager
	 * 
	 * @return LDAPQueryManager
	 */
	public static LDAPQueryManager getInstance() {
		if (om == null) {
			om = new LDAPQueryManager();
		}
		return om;
	}
}
