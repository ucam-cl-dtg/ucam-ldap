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
							public LDAPGroup load(String groupID){
								return LDAPProvider.uniqueGroupQuery("groupID", groupID);
							}
						});
	}
	
	/**
	 * Gets LDAPGroup object from the map. Object will be added if it is not there
	 */
	protected LDAPGroup getGroupObject(String groupID){
		LDAPGroup group;
		try {
			group = groupMap.get(groupID);
		} catch (ExecutionException e) {
			return null;
		}
		
		if(group==null){
			// TODO: Throw group doesnt exist exception or something
		}
		
		return group;
	}

	/**
	 * Singleton method to get LDAPGroupManager 
	 */	
	protected static LDAPGroupManager getInstance(){
		if(gm==null){
			gm = new LDAPGroupManager();
		} 
		return gm;
	}
}
