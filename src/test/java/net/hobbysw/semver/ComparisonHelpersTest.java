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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ComparisonHelpersTest extends Version {
	private static List<String> EMPTY_LIST = Collections.emptyList();
	
	@Test
	public void numberShouldBeLesser() {
		Assert.assertTrue(ComparisonHelpers.compareNumbers(1, 2) < 0);
		Assert.assertTrue(ComparisonHelpers.compareNumbers(0, 1) < 0);
	}
	
	@Test
	public void numberShouldBeGreater() {
		Assert.assertTrue(ComparisonHelpers.compareNumbers(2, 1) > 0);
		Assert.assertTrue(ComparisonHelpers.compareNumbers(1, 0) > 0);
	}
	
	@Test
	public void numbersShouldBeEquals() {
		Assert.assertEquals(ComparisonHelpers.compareNumbers(1, 1), 0);
		Assert.assertEquals(ComparisonHelpers.compareNumbers(0, 0), 0);
	}
	
	@Test
	public void stringShouldBeGreater() {
		Assert.assertTrue(ComparisonHelpers.compareStrings("a", "b") < 0);
		Assert.assertTrue(ComparisonHelpers.compareStrings("1", "a") < 0);
		Assert.assertTrue(ComparisonHelpers.compareStrings("1", "10") < 0);
		Assert.assertTrue(ComparisonHelpers.compareStrings("alpha", "alphas") < 0);
	}
	
	@Test
	public void stringShouldBeLesser() {
		Assert.assertTrue(ComparisonHelpers.compareStrings("b", "a") > 0);
		Assert.assertTrue(ComparisonHelpers.compareStrings("a", "1") > 0);
		Assert.assertTrue(ComparisonHelpers.compareStrings("10", "1") > 0);
		Assert.assertTrue(ComparisonHelpers.compareStrings("alphas", "alpha") > 0);
	}
	
	@Test
	public void stringsShouldBeEquals() {
		Assert.assertEquals(ComparisonHelpers.compareStrings("a", "a"), 0);
		Assert.assertEquals(ComparisonHelpers.compareStrings("b", "b"), 0);
		Assert.assertEquals(ComparisonHelpers.compareStrings("10", "10"), 0);
	}
	
	@Test
	public void setShouldBeGreater() {
		Assert.assertTrue(ComparisonHelpers.compareList(Arrays.asList("alpha", "1"), Arrays.asList("alpha")) > 0);
		Assert.assertTrue(ComparisonHelpers.compareList(Arrays.asList("beta", "1"), Arrays.asList("beta")) > 0);
		Assert.assertTrue(ComparisonHelpers.compareList(EMPTY_LIST, Arrays.asList("alpha")) > 0);
	}
	
	@Test
	public void setShouldBeLesser() {
		Assert.assertTrue(ComparisonHelpers.compareList(Arrays.asList("alpha"), Arrays.asList("alpha", "1")) < 0);
		Assert.assertTrue(ComparisonHelpers.compareList(Arrays.asList("beta"), Arrays.asList("beta", "1")) < 0);
		Assert.assertTrue(ComparisonHelpers.compareList(Arrays.asList("alpha"), EMPTY_LIST) < 0);
	}
	
	@Test
	public void setShouldBeEquals() {
		Assert.assertEquals(ComparisonHelpers.compareList(Arrays.asList("alpha"), Arrays.asList("alpha")), 0);
		Assert.assertEquals(ComparisonHelpers.compareList(Arrays.asList("beta", "1"), Arrays.asList("beta", "1")), 0);
		Assert.assertEquals(ComparisonHelpers.compareList(EMPTY_LIST, EMPTY_LIST), 0);
	}
	
	@Test
	public void versionShouldBeGreater() {
		Assert.assertTrue(ComparisonHelpers.compareVersionNumbers(new Version(1, 0, 0), new Version(0, 0, 1)) > 0);
		Assert.assertTrue(ComparisonHelpers.compareVersionNumbers(new Version(1, 1, 0), new Version(0, 1, 0)) > 0);
		Assert.assertTrue(ComparisonHelpers.compareVersionNumbers(new Version(1, 0, 2), new Version(1, 0, 1)) > 0);
	}
	
	@Test
	public void versionShouldBeLesser() {
		Assert.assertTrue(ComparisonHelpers.compareVersionNumbers(new Version(0, 0, 1), new Version(1, 0, 0)) < 0);
		Assert.assertTrue(ComparisonHelpers.compareVersionNumbers(new Version(0, 1, 0), new Version(1, 1, 0)) < 0);
		Assert.assertTrue(ComparisonHelpers.compareVersionNumbers(new Version(1, 0, 1), new Version(1, 0, 2)) < 0);
	}
	
	@Test
	public void versionsShouldBeEquals() {
		Assert.assertEquals(ComparisonHelpers.compareVersionNumbers(new Version(1, 0, 0), new Version(1, 0, 0)), 0);
		Assert.assertEquals(ComparisonHelpers.compareVersionNumbers(new Version(1, 1, 0), new Version(1, 1, 0)), 0);
		Assert.assertEquals(ComparisonHelpers.compareVersionNumbers(new Version(1, 1, 1), new Version(1, 1, 1)), 0);
	}
	
	@Test
	public void identifiersShouldBeGreater() {
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("alpha", "1"), Arrays.asList("alpha")) > 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("alpha", "beta"), Arrays.asList("alpha", "1")) > 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("beta"), Arrays.asList("alpha", "beta")) > 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("beta", "2"), Arrays.asList("beta")) > 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("beta", "11"), Arrays.asList("beta")) > 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("rc"), Arrays.asList("beta", "11")) > 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(EMPTY_LIST, Arrays.asList("rc")) > 0);
	}
	
	@Test
	public void identifiersShouldBeLesser() {
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("alpha"), Arrays.asList("alpha", "1")) < 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("alpha", "1"), Arrays.asList("alpha", "beta")) < 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("alpha", "beta"), Arrays.asList("beta")) < 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("beta"), Arrays.asList("beta", "2")) < 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("beta"), Arrays.asList("beta", "11")) < 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("beta", "11"), Arrays.asList("rc")) < 0);
		Assert.assertTrue(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("rc"), EMPTY_LIST) < 0);
	}
	
	@Test
	public void identifiersShouldBeEquals() {
		Assert.assertEquals(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("alpha"), Arrays.asList("alpha")), 0);
		Assert.assertEquals(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("alpha", "1"), Arrays.asList("alpha", "1")), 0);
		Assert.assertEquals(ComparisonHelpers.comparePreReleaseIdentifiers(Arrays.asList("alpha", "beta"), Arrays.asList("alpha", "beta")), 0);
		Assert.assertEquals(ComparisonHelpers.comparePreReleaseIdentifiers(EMPTY_LIST, EMPTY_LIST), 0);
	}
}
