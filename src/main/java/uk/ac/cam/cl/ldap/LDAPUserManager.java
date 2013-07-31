package uk.ac.cam.cl.ldap;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * A singleton class containing a map from CRSid to a softly referenced user
 * object. Caches the user object with all their data. Supports concurrent
 * accesses. 
 */
class LDAPUserManager {
	
	/** Singleton instance of LDAPUserManager */
	private static LDAPUserManager um;
	
//	/** Concurrent hash map with weak keys, using google-collections MapMaker */
//	private ConcurrentMap<String, LDAPUser> userMap;
	
	private LoadingCache<String, LDAPUser> userMap;
	
	private LDAPUserManager(){
		// Create a cache with soft keys
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
	}
	
	/**
	 * Gets LDAPUser object from the map. Object will be added if it is not there
	 */
	LDAPUser getUserObject(String crsid) throws LDAPObjectNotFoundException {
		LDAPUser user;
		try {
			user = userMap.get(crsid);
		} catch (ExecutionException e) {
			throw new LDAPObjectNotFoundException("Error getting user: " + e.getMessage());
		}
		
		if(user==null){
			throw new LDAPObjectNotFoundException("Error getting user");
		}
		
		return user;
	}

	/**
	 * Singleton method to get LDAPUserManager 
	 */	
	static LDAPUserManager getInstance(){
		if(um==null){
			um = new LDAPUserManager();
		} 
		return um;
	}
}
