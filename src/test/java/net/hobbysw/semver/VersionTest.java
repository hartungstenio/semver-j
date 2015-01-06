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

public class VersionTest extends Version {
	private static final String VERSION_TO_TEST = "1.0.2-alpha.1+longhorn.internal";
	private static final List<String> LIST_OF_VERSIONS = Arrays.asList("1.0.0-alpha", "1.0.0-alpha.1", "1.0.0-alpha.beta", "1.0.0-beta", "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0");

	@Test(expected = VersionFormatException.class)
	public void versionParsingShouldFailOnNull() {
		Version.valueOf(null);
	}
	
	@Test(expected = VersionFormatException.class)
	public void versionParsingShouldFailOnEmptyString() {
		Version.valueOf("");
	}
	
	@Test(expected = VersionFormatException.class)
	public void versionParsingShouldFailOnInvalidVersion() {
		Version.valueOf("1.0.0.1");
	}
	
	@Test
	public void versionParsingShouldWork() {
		Version.valueOf("1.0.0");
		Version.valueOf("1.0.0+release.1");
		Version.valueOf("1.0.0-alpha");
		Version.valueOf("1.0.0-alpha.1");
		Version.valueOf(VERSION_TO_TEST);
	}
	
	@Test
	public void versionParsingAnalysis() {
		Version version = Version.valueOf(VERSION_TO_TEST);
		
		Assert.assertEquals(version.getMajor(), 1);
		Assert.assertEquals(version.getMinor(), 0);
		Assert.assertEquals(version.getPatch(), 2);
		
		Assert.assertEquals(version.getPreReleaseIdentifiers().size(), 2);
		Assert.assertEquals(version.getPreReleaseIdentifiers().get(0), "alpha");
		Assert.assertEquals(version.getPreReleaseIdentifiers().get(1), "1");
		
		Assert.assertEquals(version.getBuildMetadata().size(), 2);
		Assert.assertEquals(version.getBuildMetadata().get(0), "longhorn");
		Assert.assertEquals(version.getBuildMetadata().get(1), "internal");
	}
	
	@Test
	public void versionShouldConvertToString() {
		Assert.assertEquals(Version.valueOf(VERSION_TO_TEST).toString(), VERSION_TO_TEST);
	}
	
	@Test
	public void versionsShouldBeAscending() {
		for(int i = 0; i < LIST_OF_VERSIONS.size() - 1; i++) {
			Version v1 = Version.valueOf(LIST_OF_VERSIONS.get(i));
			Version v2 = Version.valueOf(LIST_OF_VERSIONS.get(i + 1));
			Assert.assertTrue(v1.compareTo(v2) < 0);
		}
	}
	
	@Test
	public void versionsShouldBeDescending() {
		for(int i = LIST_OF_VERSIONS.size() - 1; i > 0; i--) {
			Version v1 = Version.valueOf(LIST_OF_VERSIONS.get(i));
			Version v2 = Version.valueOf(LIST_OF_VERSIONS.get(i - 1));
			Assert.assertTrue(v1.compareTo(v2) > 0);
		}
	}
	
	@Test
	public void versionsShouldBeEquals() {
		final List<String> versions = Arrays.asList("1.0.0-alpha", "1.0.0-alpha+1", "1.0.0-alpha+2", "1.0.0-alpha+build.1");
		
		for(int i = 0; i < versions.size() - 1; i++) {
			Version v1 = Version.valueOf(versions.get(i));
			Version v2 = Version.valueOf(versions.get(i + 1));
			Assert.assertEquals(v1.compareTo(v2), 0);
		}
	}
}
