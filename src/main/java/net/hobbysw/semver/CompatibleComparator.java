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

import java.util.Comparator;

/**
 * Compare {@link Version} for compatibility, and not exact precedence.
 * 
 * <p>Two versions are backward-compatible if they have the same MAJOR version.</p>
 * 
 * <p>One exception is for MAJOR version zero. It means the version is in initial development
 * and anything may change.</p>  
 * 
 * @author Christian Hartung
 *
 */
public class CompatibleComparator implements Comparator<Version> {

	public int compare(Version v1, Version v2) {
		// initial development. Anything may change at any time
		if(v1.getMajor() == 0 || v2.getMajor() == 0) return v1.compareTo(v2);
		
		return v1.getMajor() - v2.getMajor();
	}
}
