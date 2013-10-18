package uk.ac.cam.cl.dtg.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchResult;

final class EmptyNamingEnumeration implements
		NamingEnumeration<SearchResult> {
	@Override
	public boolean hasMoreElements() {
		return false;
	}

	@Override
	public SearchResult nextElement() {
		return null;
	}

	@Override
	public SearchResult next() throws NamingException {
		return null;
	}

	@Override
	public boolean hasMore() throws NamingException {
		return false;
	}

	@Override
	public void close() throws NamingException {
	}
}