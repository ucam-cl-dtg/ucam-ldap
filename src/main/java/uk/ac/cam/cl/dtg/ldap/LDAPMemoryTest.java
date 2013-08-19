package uk.ac.cam.cl.dtg.ldap;

import java.util.HashMap;
import java.util.List;

public class LDAPMemoryTest {

	public static void main(String[] args) throws LDAPObjectNotFoundException{
		System.out.println("test");
		
		List<HashMap<String, String>> users = LDAPPartialQuery.partialUserBySurname("sm");
		System.out.println(users.size());
		for(HashMap<String, String> m : users){
			System.out.println("id:" + m.get("crsid"));
			LDAPUser u = LDAPQueryManager.getUser(m.get("crsid"));
			System.out.println(u.getcName());
		}
		
		List<HashMap<String, String>> users2 = LDAPPartialQuery.partialUserBySurname("pa");
		System.out.println(users.size());
		for(HashMap<String, String> m : users2){
			System.out.println("id:" + m.get("crsid"));
			LDAPUser u = LDAPQueryManager.getUser(m.get("crsid"));
			System.out.println(u.getcName());
		}
		
		List<HashMap<String, String>> users3 = LDAPPartialQuery.partialUserBySurname("pr");
		System.out.println(users.size());
		for(HashMap<String, String> m : users3){
			System.out.println("id:" + m.get("crsid"));
			LDAPUser u = LDAPQueryManager.getUser(m.get("crsid"));
			System.out.println(u.getcName());
		}
		
		List<HashMap<String, String>> users4 = LDAPPartialQuery.partialUserBySurname("pr");
		System.out.println(users.size());
		for(HashMap<String, String> m : users3){
			System.out.println("id:" + m.get("crsid"));
			LDAPUser u = LDAPQueryManager.getUser(m.get("crsid"));
			System.out.println(u.getcName());
		}
		
		List<HashMap<String, String>> users7 = LDAPPartialQuery.partialUserBySurname("po");
		System.out.println(users.size());
		for(HashMap<String, String> m : users7){
			System.out.println("id:" + m.get("crsid"));
			LDAPUser u = LDAPQueryManager.getUser(m.get("crsid"));
			System.out.println(u.getcName());
		}
		
		List<HashMap<String, String>> users5 = LDAPPartialQuery.partialUserBySurname("ab");
		System.out.println(users.size());
		for(HashMap<String, String> m : users5){
			System.out.println("id:" + m.get("crsid"));
			LDAPUser u = LDAPQueryManager.getUser(m.get("crsid"));
			System.out.println(u.getcName());
		}
		
		List<HashMap<String, String>> users6= LDAPPartialQuery.partialUserBySurname("ri");
		System.out.println(users.size());
		for(HashMap<String, String> m : users6){
			System.out.println("id:" + m.get("crsid"));
			LDAPUser u = LDAPQueryManager.getUser(m.get("crsid"));
			System.out.println(u.getcName());
		}
		
	}
}
