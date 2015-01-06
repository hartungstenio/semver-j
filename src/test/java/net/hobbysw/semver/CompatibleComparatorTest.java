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

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CompatibleComparatorTest {

	@Test
	public void versionsShouldBeEquals() {
		final List<String> versions = Arrays.asList("1.0.0-alpha", "1.0.0-alpha.1",
				"1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11",
				"1.0.0-rc.1", "1.0.0");
		
		CompatibleComparator comp = new CompatibleComparator();
		
		for(int i = 0; i < versions.size() - 1; i++) {
			Version v1 = Version.valueOf(versions.get(i));
			Version v2 = Version.valueOf(versions.get(i + 1));
			Assert.assertEquals(comp.compare(v1, v2), 0);
		}
	}
	
	@Test
	public void versionShouldBeAscending() {
		final List<String> versions = Arrays.asList("0.0.1-alpha", "0.0.1-alpha.1", "0.1.0", "1.0.0");
		
		CompatibleComparator comp = new CompatibleComparator();
		
		for(int i = 0; i < versions.size() - 1; i++) {
			Version v1 = Version.valueOf(versions.get(i));
			Version v2 = Version.valueOf(versions.get(i + 1));
			Assert.assertTrue(comp.compare(v1, v2) < 0);
		}
	}
	
	@Test
	public void versionShouldBeDescending() {
		final List<String> versions = Arrays.asList("0.0.1-alpha", "0.0.1-alpha.1", "0.1.0", "1.0.0");
		
		CompatibleComparator comp = new CompatibleComparator();
		
		for(int i = versions.size() - 1; i > 0; i--) {
			Version v1 = Version.valueOf(versions.get(i));
			Version v2 = Version.valueOf(versions.get(i - 1));
			Assert.assertTrue(comp.compare(v1, v2) > 0);
		}
	}
}