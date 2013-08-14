package uk.ac.cam.cl.dtg.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/** Trie ADT Node class **/
class LDAPTrieNode<T> {

	private final Character c;

	private List<T> data;

	Map<Character, LDAPTrieNode<T>> children = new WeakHashMap<Character, LDAPTrieNode<T>>();

	LDAPTrieNode(char c) {
		this.c = c;
		this.data = new ArrayList<T>();
	}

	LDAPTrieNode(char c, T data) {
		this.c = c;
		this.data = new ArrayList<T>();
		this.data.add(data);
	}

	char getChar() {
		return c;
	}

	List<T> getPrefixMatches(List<T> matches) {
		if (data != null) {
			for(T m : data) {
				matches.add(m);
			}
		} else {
			return new ArrayList<T>();
		}
		for (LDAPTrieNode<T> child : children.values()) {
			matches = child.getPrefixMatches(matches);
		}
		return matches;
	}

	List<T> getData() {
		return data;
	}

	LDAPTrieNode<T> getChild(char c) {
		return children.get(c);
	}

	void addChild(char c, LDAPTrieNode<T> n) {
		if (!children.containsKey(c)) {
			children.put(c, n);
		}
	}

	void setData(T data) {
		this.data.add(data);
	}

}