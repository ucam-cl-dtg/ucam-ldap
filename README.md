ucam-ldap
=========

# How to use:

* All queries for a specific user/group or a list of users/groups should be made through LDAPQueryManager
* All partial queries (eg. for autocomplete user search) should be made through LDAPPartialQuery
* All queries throw an LDAPObjectNotFoundException 
* An LDAPGroup object stores members as a list of crsids, to obtain any other data on the members separate queries must be made

### Examples
* Get a user's data

``` Java
LDAPUser u;
try {
	u = LDAPQueryManager.getUser(crsid);
} catch(LDAPObjectNotFoundException e){
//Error getting user - do something
}
        
String name = u.getcName(); // get users registered name
List<String> photos = u.getPhotos(); // get a list of users photos
```

* Get a group's data

``` Java
LDAPGroup g;
try {
	g = LDAPQueryManager.getGroup(id);
} catch(LDAPObjectNotFoundException e){
	//Error getting group - do something
}
      
String description = g.getDescription; // get group description
```

* Get group member data
``` Java
LDAPGroup g;
try {
	g = LDAPQueryManager.getGroup(id);
        List<String userCrsids = g.getUsers();
        List<LDAPUser> users;
        for(String u : userCrsids) {
        	users.add(LDAPQueryManager.getUser(u));
        }
} catch(LDAPObjectNotFoundException e){
	//do something
}
```

* Autocomplete search for users by CRSID
``` Java
@POST @Path("/queryCRSID")
public List<HashMap<String, String>> queryCRSId(@FormParam("q") String x) {  			
// Perform LDAP search
List<HashMap<String, String>> matches = null;
	try {
  		matches = LDAPPartialQuery.partialUserByCrsid(x);
  	} catch (LDAPObjectNotFoundException e){
		log.error("Error performing LDAPQuery: " + e.getMessage());
  		return new ArrayList<HashMap<String, String>>();
  	}		
	return matches;
}
```
* Autocomplete search for users by surname
	
``` Java
@POST @Path("/querySurname")
public List<HashMap<String, String>> querySurname(@FormParam("q") String x) {	
// Perform LDAP search
	List<HashMap<String, String>> matches = null;
	try {
		matches = LDAPPartialQuery.partialUserBySurname(x);
	} catch (LDAPObjectNotFoundException e){
		log.error("Error performing LDAPQuery: " + e.getMessage());
		return new ArrayList<HashMap<String, String>>();
	}
	return matches;
}
```
