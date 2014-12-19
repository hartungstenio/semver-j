package net.hobbysw.semver;

import org.junit.Assert;
import org.junit.Test;

public class VersionTest extends Version {
	private static final String VERSION_TO_TEST = "1.0.2-alpha.1+longhorn.internal";

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
}
