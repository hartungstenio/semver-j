package io.github.hartungstenio.semver;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

public class SemVerTest {
    private final String[] ASCENDING_LIST_OF_VERSION_STRINGS = { 
            "1.0.0-alpha",
            "1.0.0-alpha+001",
            "1.0.0-alpha.1",
            "1.0.0-alpha.beta",
            "1.0.0-beta",
            "1.0.0-beta+exp.sha.5114f85",
            "1.0.0-beta.2",
            "1.0.0-beta.11",
            "1.0.0-rc.1",
            "1.0.0",
            "1.0.0+20130313144700"
    };
    
    private final SemVer[] ASCENDING_LIST_OF_VERSIONS = {
            SemVer.of(1, 0, 0, "alpha"),
            SemVer.of(1, 0, 0, Arrays.asList("alpha"), Arrays.asList("001")),
            SemVer.of(1, 0, 0, "alpha", 1),
            SemVer.of(1, 0, 0, "alpha", "beta"),
            SemVer.of(1, 0, 0, "beta"),
            SemVer.of(1, 0, 0, Arrays.asList("beta"), Arrays.asList("exp", "sha", "5114f85")),
            SemVer.of(1, 0, 0, "beta", 2),
            SemVer.of(1, 0, 0, "beta", 11),
            SemVer.of(1, 0, 0, "rc", 1),
            SemVer.of(1, 0, 0),
            SemVer.of(1, 0, 0, Collections.emptyList(), Arrays.asList("20130313144700"))
    };
    
    @Test(expected=SemVerException.class)
    public void invalidMajorVersionShouldThrowException() {
        SemVer.of(-1, 0, 0);
    }
    
    @Test(expected=SemVerException.class)
    public void invalidMinorVersionShouldThrowException() {
        SemVer.of(0, -1, 0);
    }
    
    @Test(expected=SemVerException.class)
    public void invalidPatchVersionShouldThrowException() {
        SemVer.of(0, 0, -1);
    }
    
    @Test
    public void versionsShouldBePreReleases() {
        Assert.assertTrue(SemVer.of(0, 0, 1).isPreRelease());
        Assert.assertTrue(SemVer.of(1, 0, 0, "alpha").isPreRelease());
    }
    
    @Test
    public void versionsShouldNotBePreReleases() {
        Assert.assertFalse(SemVer.of(1, 0, 0).isPreRelease());
    }
    
    @Test
    public void versionParsingShouldBeEqualToActualVersion() {
        IntStream.range(0, ASCENDING_LIST_OF_VERSION_STRINGS.length)
            .forEach(i -> {
                Assert.assertEquals(ASCENDING_LIST_OF_VERSION_STRINGS[i], ASCENDING_LIST_OF_VERSIONS[i], SemVer.parse(ASCENDING_LIST_OF_VERSION_STRINGS[i]));
            });
    }
    
    @Test
    public void precedenceCheck() {
        IntStream.range(0, ASCENDING_LIST_OF_VERSIONS.length - 1)
            .forEach(i -> {
                Assert.assertTrue(ASCENDING_LIST_OF_VERSION_STRINGS[i], ASCENDING_LIST_OF_VERSIONS[i].compareTo(ASCENDING_LIST_OF_VERSIONS[i + 1]) <= 0);
            });
    }
    
    @Test
    public void stringConversionCheck() {
        IntStream.range(0, ASCENDING_LIST_OF_VERSION_STRINGS.length - 1)
        .forEach(i -> {
            Assert.assertEquals(ASCENDING_LIST_OF_VERSION_STRINGS[i], SemVer.parse(ASCENDING_LIST_OF_VERSION_STRINGS[i]), ASCENDING_LIST_OF_VERSIONS[i]);
        });
    }
}
