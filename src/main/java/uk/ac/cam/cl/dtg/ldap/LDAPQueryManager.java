package uk.ac.cam.cl.dtg.ldap;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * A singleton class containing static methods to manage all (none partial) LDAP
 * queries. Caches LDAPUser and LDAPGroup objects using a LoadingCache with weak
 * keys and soft values Returns an LDAPUser or an LDAPGroup
 */
public class LDAPQueryManager {

	/**
	 * A placeholder object which we insert in the cache if we fail to find a user in the directory
	 */
	private static final LDAPUser USER_NOT_FOUND = new LDAPUser("not-found", "not-found", "not-found", "not-found", "not-found", null, null, null, null);

	/**
	 * A placeholder object which we insert in the cache if we fail to find a group in the directory
	 */
	private static final LDAPGroup GROUP_NOT_FOUND = new LDAPGroup("not-found", "not-found", "not-found", null);
	
	/** Singleton instance of LDAPGroupManager */
	private static LDAPQueryManager om = new LDAPQueryManager();

	/** Cache holding LDAPUser objects mapped by crsid */
	private LoadingCache<String, LDAPUser> userMap;
	/** Cache holding LDAPGroup objects mapped by groupID */
	private LoadingCache<String, LDAPGroup> groupMap;

	private static final Logger log = LoggerFactory.getLogger(LDAPQueryManager.class);
	
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
		try {
			LDAPUser user = qm.userMap.get(crsid);
			if (user == USER_NOT_FOUND) {
				throw new LDAPObjectNotFoundException("User "+crsid+" not found in directory");
			}
			return user;
		} catch (ExecutionException e) {
			log.error("Unexpected exception when looking up user in LDAP",e);
			throw new LDAPObjectNotFoundException("Unexpected exception querying directory",e);
		}
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
		try {
			LDAPGroup group = qm.groupMap.get(groupID);
			if (group == GROUP_NOT_FOUND) {
				throw new LDAPObjectNotFoundException("Group "+groupID+" not found in directory");
			}
			return group;
		} catch (ExecutionException e) {
			log.error("Unexpected exception when looking up user in LDAP",e);
			throw new LDAPObjectNotFoundException("Unexpected exception querying directory",e);
		}
	}

	private LDAPQueryManager() {
		// Create a user cache with soft keys
		userMap = CacheBuilder.newBuilder().maximumSize(500).build(new CacheLoader<String, LDAPUser>() {
					public LDAPUser load(String crsid) {
						try {
							log.info("Querying for "+crsid);
							return LDAPProvider.uniqueUserQuery("uid", crsid);
						} catch (LDAPObjectNotFoundException e) {
							return USER_NOT_FOUND;
						}
					}
				});
		// Create a group cache with soft keys
		groupMap = CacheBuilder.newBuilder().maximumSize(10).build(new CacheLoader<String, LDAPGroup>() {
					public LDAPGroup load(String groupID) {
						try {
							return LDAPProvider
									.uniqueGroupQuery("groupID", groupID);
						} catch (LDAPObjectNotFoundException e) {
							return GROUP_NOT_FOUND;							
						}
					}
				});
	}

	/**
	 * Get singleton instance of LDAPQueryManager
	 * 
	 * @return LDAPQueryManager
	 */
	public static LDAPQueryManager getInstance() {
		return om;
	}
}
