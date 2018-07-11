package com.jiajie.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;

public class AliasToJSONResultTransformer implements ResultTransformer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2403665288665821957L;

	@SuppressWarnings("unchecked")
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map result = new HashMap(tuple.length);
		for ( int i=0; i<tuple.length; i++ ) {
			String alias = aliases[i];
			if ( alias!=null ) {
				result.put( alias.toLowerCase(), tuple[i] );
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List transformList(List collection) {
		System.out.println(collection);
		return collection;
	}

}
