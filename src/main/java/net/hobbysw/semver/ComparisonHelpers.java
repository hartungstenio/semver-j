/*
 * This file is part of the SemVer-J project.
 * Copyright (c) 2015 Christian Hartung
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License
 * which accompanies this distribution.
 *
 * Contributors:
 *     Christian Hartung - initial API and implementation
 */
package net.hobbysw.semver;

import java.util.List;

/**
 * Helper methods to auxiliate version comparisons
 * 
 * @author Christian Hartung
 *
 */
public final class ComparisonHelpers {
	
	/**
	 * Compare two numbers
	 * 
	 * @param v1 the first number, may be the major, minor, patch or any pre-release identifier
	 * @param v2 the second number, may be the major, minor, patch or any pre-release identifier
	 * @return an integer which indicates if v1 has higher precedence than (&gt; 0), lower precedence than (&lt; 0) or the same precedence as (= 0) v2 
	 */
	public static int compareNumbers(int v1, int v2) {
		return v1 - v2;
	}
	
	/**
	 * Compare two strings, following the rules:
	 * <ol>
	 *   <li>identifiers consisting of only digits are compared numerically</li>
	 *   <li>identifiers with letters or hyphens are compared lexically in ASCII sort order</li>
	 *   <li>numeric identifiers always have lower precedence than non-numeric identifiers</li>
	 * </ol>
	 * 
	 * @param v1 the first string, which should one of the pre-release identifiers
	 * @param v2 the second string, which should one of the pre-release identifiers
	 * @return an integer which indicates if v1 has higher precedence than (&gt; 0), lower precedence than (&lt; 0) or the same precedence as (= 0) v2
	 */
	public static int compareStrings(String v1, String v2) {
		int n1;
		int n2;
				
		try {
			n1 = Integer.parseInt(v1);
		} catch(NumberFormatException ex) {
			n1 = -1;
		}
		
		try {
			n2 = Integer.parseInt(v2);
		} catch(NumberFormatException ex) {
			n2 = -1;
		}
		
		if(n1 >= 0 && n2 >= 0) return compareNumbers(n1, n2);
		if(n1 >= 0) return -1;
		if(n2 >= 0) return 1;
		return v1.compareTo(v2);
	}
	
	/**
	 * Compare two sets, considering that a larger set has higher precedence than a smaller set.
	 * An empty set has higher precedence than a non-empty set.
	 * 
	 * @param v1 the first set, which must be a set of pre-release identifiers
	 * @param v2 the second set, which must be a set of pre-release identifiers
	 * @return an integer which indicates if v1 has higher precedence than (&gt; 0), lower precedence than (&lt; 0) or the same precedence as (= 0) v2
	 */
	public static int compareList(List<String> v1, List<String> v2) {
		if(v1.size() != v2.size()) {
			if(v1.isEmpty()) return 1;
			if(v2.isEmpty()) return -1;
		}
		
		return v1.size() - v2.size();
	}
	
	/**
	 * Compares two version numbers, considering only MAJOR.MINOR.PATCH
	 * @param v1 the first version
	 * @param v2 the second version
	 * @return an integer which indicates if v1 has higher precedence than (&gt; 0), lower precedence than (&lt; 0) or the same precedence as (= 0) v2
	 */
	public static int compareVersionNumbers(Version v1, Version v2) {
		int i = compareNumbers(v1.getMajor(), v2.getMajor());
		if(i != 0) return i;
		
		i = compareNumbers(v1.getMinor(), v2.getMinor());
		if(i != 0) return i;
		
		i = compareNumbers(v1.getPatch(), v2.getPatch());
		return i;
	}
	
	/**
	 * Compare two sets of pre-release identifiers
	 * @param v1 the first set
	 * @param v2 the second set
	 * @return an integer which indicates if v1 has higher precedence than (&gt; 0), lower precedence than (&lt; 0) or the same precedence as (= 0) v2
	 */
	public static int comparePreReleaseIdentifiers(List<String> v1, List<String> v2) {
		int len = Math.min(v1.size(), v2.size());
		
		for(int i = 0; i < len; i++) {
			int r = compareStrings(v1.get(i), v2.get(i));
			if(r != 0) return r;
		}
		
		return compareList(v1, v2);
	}
}
