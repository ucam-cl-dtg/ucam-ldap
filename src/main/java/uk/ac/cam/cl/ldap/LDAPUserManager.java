package uk.ac.cam.cl.ldap;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;

/**
 * @version     1                
 * A singleton class containing a (weak, concurrent) hashmap of crsid to a user object
 * Caches the user object with all their data. 
 */
class LDAPUserManager {
	
	/** Singleton instance of LDAPUserManager */
	private static LDAPUserManager um;
	
	/** Concurrent hash map with weak keys, using google-collections MapMaker */
	private ConcurrentMap<String, LDAPUser> userMap;
	
	private LDAPUserManager(){
		// Create a concurrent map with weak keys, default capacity 16
		userMap = new MapMaker().weakKeys().makeMap();
	}
	
	/**
	 * Gets LDAPUser object from the map. Object will be added if it is not there
	 */
	protected LDAPUser getUserObject(String crsid){
		LDAPUser user = userMap.get(crsid);
		if(user==null){
			LDAPUser newUser = new LDAPUser(crsid);
			user = userMap.putIfAbsent(crsid, newUser); //returns existing userobject if there is one
			if(user==null){ 
				user = newUser; // successful put, use newly created object
			}
		}
		return user;
	}

	/**
	 * Singleton method to get LDAPUserManager 
	 */	
	protected static LDAPUserManager getInstance(){
		if(um==null){
			um = new LDAPUserManager();
		} 
		return um;
	}
}
