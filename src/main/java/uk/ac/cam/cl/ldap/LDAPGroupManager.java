package uk.ac.cam.cl.ldap;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * A singleton class containing a map from groupID to a softly referenced LDAPGroup
 * object. Caches the group object with it's data. Supports concurrent
 * accesses. 
 */
class LDAPGroupManager {
	
	/** Singleton instance of LDAPGroupManager */
	private static LDAPGroupManager gm;
	
	private LoadingCache<String, LDAPGroup> groupMap;
	
	private LDAPGroupManager(){
		// Create a cache with soft keys
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
	 * Gets LDAPGroup object from the map. Object will be added if it is not there
	 */
	LDAPGroup getGroupObject(String groupID) throws LDAPObjectNotFoundException {
		LDAPGroup group;
		try {
			group = groupMap.get(groupID);
		} catch (ExecutionException e) {
			throw new LDAPObjectNotFoundException("Error getting group: " + e.getMessage());
		}
		
		if(group==null){
			throw new LDAPObjectNotFoundException("Error getting group");
		}
		
		return group;
	}

	/**
	 * Singleton method to get LDAPGroupManager 
	 */	
	static LDAPGroupManager getInstance(){
		if(gm==null){
			gm = new LDAPGroupManager();
		} 
		return gm;
	}
}
