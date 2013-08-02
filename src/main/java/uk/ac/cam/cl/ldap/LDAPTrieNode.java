package uk.ac.cam.cl.ldap;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/** Trie ADT Node class **/
class LDAPTrieNode<T> {
	
	private final Character c;
	
	private T data;
	
	Map<Character, LDAPTrieNode<T>> children = new WeakHashMap<Character, LDAPTrieNode<T>>();
	
	LDAPTrieNode(char c){
		this.c = c;
		data = null;
	}
	
	LDAPTrieNode(char c, T data){
		this.c = c;
		this.data = data;
	}
	
	char getChar(){
		return c;
	}
	
	List<T> getPrefixMatches(List<T> matches){
		if(data!=null){ matches.add(data);}
		for(LDAPTrieNode<T> child : children.values()){
			matches = child.getPrefixMatches(matches);
		}
		return matches;
	}
	
	T getMatch(){
		return data;
	}
	
	LDAPTrieNode<T> getChild(char c){
		return children.get(c);
	}

	void addChild(char c, LDAPTrieNode<T> n){
		if(!children.containsKey(c)){
			children.put(c, n);
		}
	}
	
	void setData(T data){
		this.data = data;
	}
	
}