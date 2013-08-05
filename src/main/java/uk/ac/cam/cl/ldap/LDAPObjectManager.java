package uk.ac.cam.cl.ldap;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * A singleton class containing a map of crsid to LDAPUser and of groupID to LDAPGroup
 * object. Caches the object with it's data. Supports concurrent access.
 * 
 */
class LDAPObjectManager {
	
	/** Singleton instance of LDAPGroupManager */
	private static LDAPObjectManager om;
	
	private LoadingCache<String, LDAPUser> userMap;
	private LoadingCache<String, LDAPGroup> groupMap;
	
	private LDAPObjectManager(){
		// Create a user cache with soft keys
		userMap = CacheBuilder.newBuilder()
				.maximumSize(500)
				.weakKeys()
				.softValues()
				.build(
						new CacheLoader<String, LDAPUser>(){
							public LDAPUser load(String crsid)  throws LDAPObjectNotFoundException {
								return LDAPProvider.uniqueUserQuery("uid", crsid);
							}
						});
		// Create a group cache with soft keys
		groupMap = CacheBuilder.newBuilder()
				.maximumSize(500)
				.weakKeys()
				.softValues()
				.build(
						new CacheLoader<String, LDAPGroup>() {
							public LDAPGroup load(String groupID)  throws LDAPObjectNotFoundException {
								return LDAPProvider.uniqueGroupQuery("groupID", groupID);
							}
						});
	}
	
	/**
	 * Gets LDAPUser object from the map. Object will be added if it is not there
	 */
	LDAPUser getUser(String crsid) throws LDAPObjectNotFoundException {
		LDAPUser user;
		try {
			user = userMap.get(crsid);
		} catch (ExecutionException e) {
			throw new LDAPObjectNotFoundException("Error getting user: " + e.getMessage());
		}
		
		if(user==null){
			throw new LDAPObjectNotFoundException("Unable to retrieve user from cache or otherwise");
		}
		
		return user;
	}
	
	/**
	 * Gets LDAPGroup object from the map. Object will be added if it is not there
	 */
	LDAPGroup getGroup(String groupID) throws LDAPObjectNotFoundException {
		LDAPGroup group;
		try {
			group = groupMap.get(groupID);
		} catch (ExecutionException e) {
			throw new LDAPObjectNotFoundException("Error getting group: " + e.getMessage());
		}
		
		if(group==null){
			throw new LDAPObjectNotFoundException("Unable to retrieve group from cache or otherwise");
		}
		
		return group;
	}

	/**
	 * Singleton method to get LDAPGroupManager 
	 */	
	static LDAPObjectManager getInstance(){
		if(om==null){
			om = new LDAPObjectManager();
		} 
		return om;
	}
}
