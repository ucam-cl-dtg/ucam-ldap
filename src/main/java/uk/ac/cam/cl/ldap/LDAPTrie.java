package uk.ac.cam.cl.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Trie ADT class **/
class LDAPTrie<T extends LDAPObject> {
	
	private Map<Character, LDAPTrieNode<T>> roots;
	
	LDAPTrie() {
		roots = new HashMap<Character, LDAPTrieNode<T>>();
	}
	
	LDAPTrie(List<T> initMatches){
		roots = new HashMap<Character, LDAPTrieNode<T>>();
		addMatches(initMatches);
	}
	
	void addMatches(List<T> matches){
		for(T m : matches){
			addMatch(m);
		}
	}
	
	void addMatch(T match){
		
		String key = match.getID();
		char[] chars = key.toCharArray();
					
		LDAPTrieNode<T> currentNode = null;
		
		if(!roots.containsKey(chars[0])){
			roots.put(chars[0], new LDAPTrieNode<T>(chars[0]));
		}
		
		currentNode = roots.get(chars[0]);
		
		for(int i=1; i< chars.length; i++){
			if(!currentNode.children.containsKey(chars[i])){
				currentNode.addChild(chars[i], new LDAPTrieNode<T>(chars[i]));
			}
			
			currentNode = currentNode.getChild(chars[i]);
			
			// If its the last letter of the crisd put the user object in
			if(i==chars.length-1){ 
				currentNode.setData(match);
			}
		}
	}
	
	List<T> getMatches(String x){
		
		char[] chars = x.toCharArray();
		
		LDAPTrieNode<T> currentNode = null;
		
		if(!roots.containsKey(chars[0])){
			addMatches((List<T>)LDAPProvider.multipleUserQuery("uid", x, true));
		}
		
		currentNode = roots.get(chars[0]);
		
		List<T> matches = new ArrayList<T>();
		
		for(int i=1; i<chars.length; i++){
			if(!currentNode.children.containsKey(chars[i])){ // no more stored matches, need to get more from LDAP
				List<T> newMatches = (List<T>) LDAPProvider.multipleUserQuery("uid", x.substring(0, i+1), true);
				addMatches(newMatches);
			} else {
				currentNode = currentNode.getChild(chars[i]);
			}
			
			if(i==chars.length-1){ // If this is the end of the search string get all results
				matches = currentNode.getPrefixMatches(matches);
			}
		}
		
		return matches;
	}
	
}