package uk.ac.cam.cl.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class PartialQuery {

	/** Name of the session attribute containing the user PartialQuery object **/
	public static String ATTR_PARTIAL_QUERY = "UserPartialQuery";
	
	/** Trie holding cached matches **/
	private LDAPTrie<LDAPUser> userMatches;
	
	private PartialQuery(){
		userMatches = new LDAPTrie<LDAPUser>();
	}
	
	protected List<LDAPUser> userQuery(String x){
		return userMatches.getMatches(x);
	}
	
	protected static PartialQuery getPartialQueryInstance(HttpServletRequest req){
		PartialQuery sessionPQ = (PartialQuery)req.getSession().getAttribute(ATTR_PARTIAL_QUERY);
		if(sessionPQ!=null){
			return sessionPQ;
		} else {
			
			 req.getSession().setAttribute(ATTR_PARTIAL_QUERY, new PartialQuery());
			 return (PartialQuery)req.getSession().getAttribute(ATTR_PARTIAL_QUERY);
		}
	}
	
	/** Trie ADT class **/
	private class LDAPTrie<T> {
		
		private Map<Character, Node<T>> roots;
		
		private LDAPTrie() {
			roots = new HashMap<Character, Node<T>>();
		}
		
		private LDAPTrie(List<T> initMatches){
			roots = new HashMap<Character, Node<T>>();
			addMatches(initMatches);
		}
		
		private void addMatches(List<T> matches){
			for(T m : matches){
				addMatch(m);
			}
		}
		
		private void addMatch(T match){
			
			String key = ((LDAPUser)match).getCRSID();
			char[] chars = key.toCharArray();
						
			Node<T> currentNode = null;
			
			if(!roots.containsKey(chars[0])){
				roots.put(chars[0], new Node<T>(chars[0]));
			}
			
			currentNode = roots.get(chars[0]);
			
			for(int i=1; i< chars.length; i++){
				if(!currentNode.children.containsKey(chars[i])){
					currentNode.addChild(chars[i], new Node<T>(chars[i]));
				}
				
				currentNode = currentNode.getChild(chars[i]);
				
				// If its the last letter of the crisd put the user object in
				if(i==chars.length-1){ 
					currentNode.setData(match);
				}
			}
		}
		
		private List<T> getMatches(String x){
			
			char[] chars = x.toCharArray();
			
			Node<T> currentNode = null;
			
			if(!roots.containsKey(chars[0])){
				System.out.println("[LDAP Query] for " + x);
				addMatches((List<T>)LDAPProvider.multipleUserQuery("uid", x, true));
			}
			
			currentNode = roots.get(chars[0]);
			
			List<T> matches = new ArrayList<T>();
			
			for(int i=1; i<chars.length; i++){
				if(!currentNode.children.containsKey(chars[i])){ // no more stored matches, need to get more from LDAP
					System.out.println("[LDAP Query] for " + x.substring(0, i+1));
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
	
	/** Trie ADT Node class **/
	private class Node<T> {
		
		private final Character c;
		
		private T data;
		
		private Map<Character, Node<T>> children = new HashMap<Character, Node<T>>();
		
		private Node(char c){
			this.c = c;
			data = null;
		}
		
		private Node(char c, T data){
			this.c = c;
			this.data = data;
		}
		
		private char getChar(){
			return c;
		}
		
		private List<T> getPrefixMatches(List<T> matches){
			if(data!=null){ matches.add(data);}
			for(Node<T> child : children.values()){
				matches = child.getPrefixMatches(matches);
			}
			return matches;
		}
		
		private T getMatch(){
			return data;
		}
		
		private Node<T> getChild(char c){
			return children.get(c);
		}

		private void addChild(char c, Node<T> n){
			if(!children.containsKey(c)){
				children.put(c, n);
			}
		}
		
		private void setData(T data){
			this.data = data;
		}
		
	}
	
}
