package uk.ac.cam.cl.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.codec.binary.Base64;

import com.google.common.collect.ImmutableMap;


/**
 * @author      Holly Priest <hp343@cam.ac.uk>
 * @version     1                
 * All the main functionality for the LDAP queries
 * 
 */
public class LDAPProvider {

	/** Connection constants **/
	private static final String CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private static final String PROVIDER_URL = "ldap://ldap.lookup.cam.ac.uk:389";
	private static final String CONTEXT_FILTER = "o=University of Cambridge,dc=cam,dc=ac,dc=uk";
	
	/**
	 * Initialises context and sets up basic query
	 * @return NamingEnumeration<SearchResult>
	 */
	private static NamingEnumeration<SearchResult> initialiseContext(String type, String parameter, String subtree) {
		
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, PROVIDER_URL);
		
		String searchContext = "ou=" + subtree + "," + CONTEXT_FILTER;
		String searchParameters = "(" + type + "=" + parameter + ")";
		
		DirContext ctx;
		NamingEnumeration<SearchResult> searchResults;
		try {
			
			ctx = new InitialDirContext(env);
		 
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchResults = ctx.search(searchContext, searchParameters, controls);
			
		} catch (NamingException e) {
			return null;
		}
		
		return searchResults;
		
	}
	
	/**
	 * Sets up unique result query
	 * @return NamingEnumeration<? extends Attribute>
	 */	
	private static Attributes setupUniqueQuery(String type, String parameter, String subtree){
		SearchResult searchResult;
		
		try {
			searchResult = initialiseContext(type, parameter, subtree).next();
			
			if(searchResult==null){ return null;}
			
		} catch (NamingException e) {
			return null;
		}
		
		return searchResult.getAttributes();
	}
	
	/**
	 * Unique user query
	 * Returns an LDAPUser object
	 * Takes 2 arguments: attribute to search (eg. uid) and r to search with
	 * @return LDAPUser
	 */
	protected static LDAPUser uniqueUserQuery(String type, String parameter) {
		
		Attributes userResult = setupUniqueQuery(type, parameter, "people");

		return initLDAPUser(userResult);
	}
	
	/**
	 * List of users Query
	 * Returns a list of LDAPUser objects
	 */
	protected static ArrayList<LDAPUser> multipleUserQuery(String type, String parameter) {
		
		NamingEnumeration<SearchResult> searchResults = initialiseContext(type, parameter, "people");
		
		ArrayList<LDAPUser> users = new ArrayList<LDAPUser>();
		
		try {
			while(searchResults.hasMore()){
				users.add(initLDAPUser(searchResults.next().getAttributes()));
			}
		} catch (NamingException e) {
			return null;
		}
		
		return users;
		
	}
	
	/**
	 * Unique group query
	 * Returns an LDAPGroup object
	 * Takes 2 arguments: attribute to search (eg. uid) and r to search with
	 * @return LDAPUser
	 */
	protected static LDAPGroup uniqueGroupQuery(String type, String parameter) {
		
		Attributes groupResult = setupUniqueQuery(type, parameter, "groups");

		return initLDAPGroup(groupResult);
	}
	
	/**
	 * Creates an LDAPUser object from attributes
	 * @return LDAPUser
	 */
	private static LDAPUser initLDAPUser(Attributes userResult) {

		String crsid;
		String cn;
		String sn;
		String mail;
		List<String> misAff;
		List<String> institutions;
		List<String> photos;

		try {
			// Get crisd
			crsid = userResult.get("uid").get().toString();	
		
			// Get registered name
			cn = userResult.get("cn").get().toString();
		
			// Get surname
			sn = userResult.get("sn").get().toString();
		
			// Get email
			mail = userResult.get("mail").get().toString();
		
			// Get misAffiliation
			NamingEnumeration<?> misAffEnum;
				 misAffEnum = userResult.get("misAffiliation").getAll();
				 misAff = new ArrayList<String>();
				 
				 while(misAffEnum.hasMore()){
					 misAff.add(misAffEnum.next().toString());
				 }	 
			
			// Get institutions
			NamingEnumeration<?> instEnum;
				 instEnum = userResult.get("ou").getAll();
				 institutions = new ArrayList<String>();
				 
				 while(instEnum.hasMore()){
					 institutions.add(instEnum.next().toString());
				 }	 
	
			// Get photos
			NamingEnumeration<?> photoEnum;
	
				 photoEnum = userResult.get("jpegPhoto").getAll();
				 photos = new ArrayList<String>();
				 
				 while(photoEnum.hasMore()){
						byte[] p = (byte[])photoEnum.next();
						photos.add(new String(Base64.encodeBase64(p)));	
				 }	 
			 
		} catch(NamingException e){
			return null;
		}
		
		if(crsid==null){ // If uid is null the user does not exist
			return null;
		}
		
		return new LDAPUser(crsid, cn, sn, mail, misAff, institutions, photos);

	}
	
	/**
	 * Creates an LDAPGroup object from attributes
	 * @return LDAPGroup
	 */
	private static LDAPGroup initLDAPGroup(Attributes groupResult) {

		String groupID;
		String groupTitle;
		String description;
		List<String> users;

		try {
			
			if(!groupResult.get("visibility").get().toString().equals("cam")){
				//Group not visible, return null
			}
			
			// Get groupID
			groupID = groupResult.get("groupID").get().toString();	
			
		
			// Get group name
			groupTitle = groupResult.get("groupTitle").get().toString();
			
		
			// Get description
			description = groupResult.get("description").get().toString();
			
		
			// Get users
			NamingEnumeration<?> usersEnum;
				 usersEnum = groupResult.get("uid").getAll();
				 users = new ArrayList<String>();
				 
				 while(usersEnum.hasMore()){
					 users.add(usersEnum.next().toString());
				 }	  
				 
				 System.out.println("Problem not with users");
			 
		} catch(NamingException e){
			return null;
		}
		
		if(groupID==null){ // If groupID is null the group does not exist
			return null;
		}
		
		return new LDAPGroup(groupID, groupTitle, description, users);

	}
	
//	/**
//	 * Partial User Query
//	 * Constructs and calls final query, returning immutable map of crsid, displayname, surname
//	 * Includes partial matches in search
//	 * Takes 2 arguments: attribute to search (eg. uid) and, string x to match results with
//	 * Possible subtrees to search: people, groups, institutions
//	 * @return List<ImmutableMap<String,?>>
//	 */
//	public static List partialUserQuery(String x, String type){
//		
//		Hashtable env = setupQuery();
//		NamingEnumeration<SearchResult> enumResults;
//		
//		Attributes a = null;
//		try {
//			DirContext ctx = new InitialDirContext(env);
//			SearchControls controls = new SearchControls();
//			controls.setReturningAttributes(new String[]{"uid", "displayName", "sn"});
//			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//			enumResults = ctx.search(
//					"ou=people,o=University of Cambridge,dc=cam,dc=ac,dc=uk",
//					"("+type+"=" + x + "*)", controls);
//		} catch (Exception e) {
//			return null;
//		}
//		
//		try {
//			ArrayList<ImmutableMap<String,?>> userMatches = new ArrayList<ImmutableMap<String,?>>();			
//			
//			// Convert enumeration type results to string
//				while(enumResults.hasMore()){
//					Attributes result = enumResults.next().getAttributes();
//					userMatches.add(ImmutableMap.of("crsid", result.get("uid").get().toString(), "name", result.get("displayName").get().toString(), "surname", result.get("sn").get().toString()));
//				}
//				
//			return userMatches;
//					
//        } catch (NamingException e) {
//			return null;
//		} 
//		
//	}
//	
//	/**
//	 * Partial Group Query
//	 * Constructs and calls final query, returning immutable map of crsid, displayname, surname
//	 * Includes partial matches in search
//	 * Takes 2 arguments: attribute to search (eg. uid) and, string x to match results with
//	 * Possible subtrees to search: people, groups, institutions
//	 * @return List<ImmutableMap<String,?>>
//	 */
//	public static List partialGroupQuery(String x, String type){
//		
//		System.out.println("Searching " + type + " for " + x);
//		
//		Hashtable env = setupQuery();
//		NamingEnumeration<SearchResult> enumResults;
//		
//		Attributes a = null;
//		try {
//			DirContext ctx = new InitialDirContext(env);
//			SearchControls controls = new SearchControls();
//			controls.setReturningAttributes(new String[]{"groupID", "groupTitle", "description", "visibility"});
//			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//			enumResults = ctx.search(
//					"ou=groups,o=University of Cambridge,dc=cam,dc=ac,dc=uk",
//					"("+type+"=*" + x + "*)", controls);
//		} catch (Exception e) {
//			return null;
//		}
//		
//		try {
//			ArrayList<ImmutableMap<String,?>> groupMatches = new ArrayList<ImmutableMap<String,?>>();			
//			
//			// Convert enumeration type results to string
//				while(enumResults.hasMore()){
//					Attributes result = enumResults.next().getAttributes();
//					// only add if members are public
//					if(result.get("visibility").get().toString().equals("cam")){
//						//dont add if the group is larger than 50 people
//						groupMatches.add(ImmutableMap.of("id", result.get("groupID").get().toString(), "name", result.get("groupTitle").get().toString(), "description", result.get("description").get().toString()));
//					} 
//				}
//				
//			return groupMatches;
//					
//        } catch (NamingException e) {
//			return null;
//		} 
//		
//	}

}
