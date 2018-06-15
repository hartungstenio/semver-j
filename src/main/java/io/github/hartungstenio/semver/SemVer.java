/*
 * This file is part of the SemVer-J project.
 * Copyright (c) 2018 Christian Hartung
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License
 * which accompanies this distribution.
 *
 * Contributors:
 *     Christian Hartung - initial API and implementation
 */
package io.github.hartungstenio.semver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A version in the Semantic Versioning format, such as 1.0.0-alpha+001.
 * <p>
 * {@code SemVer} is an immutable objects that represents a version of a software,
 * viewed as MAJOR.MINOR.PATCH numbers. Other information, such as pre-release
 * names and build identifiers can also be accessed. For example, the value
 * "1.0.0 alpha 1" can be stored using a {@code SemVer}.
 * <p>
 * The <a href="https://semver.org/">Semantic Versioning 2.0.0</a> is a widely
 * adopted versioning scheme, and uses three digits (MAJOR.MINOR.PATCH), optional
 * pre-release tags and optional build metadata tags. Breaking changes are indicated
 * by increasing the MAJOR version, new non-breaking features increment the MINOR
 * version, and all other non-breaking changes (like bug fixes) increment the PATCH
 * version. The presence of pre-release tags (-alpha, -rc.3) indicates that the version
 * is unstable and might not satisfy the intended compatibility requirements.
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class
 * 
 * @implSpec
 * This class is immutable and thread-safe.
 * 
 * @author Christian Hartung
 *
 */
public class SemVer implements Comparable<SemVer> {
    
    /**
     * A initial version of a new software, '1.0.0-SNAPSHOT'.
     * This could be used by an application as a default version for new projects
     */
    public static SemVer INITIAL = SemVer.of(0, 0, 1, "SNAPSHOT");
    
    /**
     * The MAJOR version.
     */
    private final int major;
    
    /**
     * The MINOR version.
     */
    private final int minor;
    
    /**
     * The PATCH version.
     */
    private final int patch;
    
    /**
     * The pre-release tags
     */
    private final Collection<?> preRelease;
    
    /**
     * The build metadata
     */
    private final Collection<?> build;
    
    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code SemVer} from a MAJOR, MINOR and PATCH versions.
     * 
     * @param major the MAJOR version, non-negative
     * @param minor the MINOR version, non-negative
     * @param patch the PATCH version, non-negative
     * @return the version, not null
     * @throws SemVerException if the value of any field is out of range
     */
    public static SemVer of(final int major, final int minor, final int patch) {
        return of(major, minor, patch, Collections.emptySet());
    }
    
    /**
     * Obtains an instance of {@code SemVer} from a MAJOR, MINOR and PATCH versions,
     * and a set of pre-release tags
     * 
     * @param major the MAJOR version, non-negative
     * @param minor the MINOR version, non-negative
     * @param patch the PATCH version, non-negative
     * @param preRelease the pre-release tags, may be empty
     * @return the version, not null
     * @throws SemVerException if the value of any field is out of range
     */
    public static SemVer of(final int major, final int minor, final int patch, final Object... preRelease) {
        return of(major, minor, patch, Arrays.asList(preRelease));
    }
    
    /**
     * Obtains an instance of {@code SemVer} from a MAJOR, MINOR and PATCH versions,
     * and a set of pre-release tags
     * 
     * @param major the MAJOR version, non-negative
     * @param minor the MINOR version, non-negative
     * @param patch the PATCH version, non-negative
     * @param preRelease the pre-release tags, may be empty
     * @return the version, not null
     * @throws SemVerException if the value of any field is out of range
     */
    public static SemVer of(final int major, final int minor, final int patch, final Collection<?> preRelease) {
        return create(major, minor, patch, preRelease, Collections.emptyList());
    }
    
    /**
     * Obtains an instance of {@code SemVer} from a MAJOR, MINOR and PATCH versions,
     * a set of pre-release tags, and a set of build metadata.
     * 
     * @param major the MAJOR version, non-negative
     * @param minor the MINOR version, non-negative
     * @param patch the PATCH version, non-negative
     * @param preRelease the pre-release tags, may be empty
     * @param build the build metadata, may be empty
     * @return the version, not null
     * @throws SemVerException if the value of any field is out of range
     */
    public static SemVer of(final int major, final int minor, final int patch,
            final Collection<?> preRelease, final Collection<?> build) {
        return create(major, minor, patch, preRelease, build);
    }
    
    /**
     * Creates a new version from the individual fields.
     * @param major the MAJOR version, validated as non-negative
     * @param minor the MINOR version, validated as non-negative
     * @param patch the PATCH version, validated as non-negative
     * @param preRelease the pre-release tags
     * @param build the build metadata
     * @return the version, not null
     * @throws SemVerException if the value of any field is out of range
     */
    private static SemVer create(final int major, final int minor, final int patch,
            final Collection<?> preRelease, final Collection<?> build) {
        if(major < 0) throw new SemVerException("Invalid MAJOR version");
        if(minor < 0) throw new SemVerException("Invalid MINOR version");
        if(patch < 0) throw new SemVerException("Invalid PATCH version");
        
        return new SemVer(
                major,
                minor,
                patch,
                Objects.requireNonNull(preRelease, "Invalid pre-release tags"),
                Objects.requireNonNull(build, "Invalid build metadata")
               );
    }
    
    public static SemVer parse(final String versionStr) {
        if(versionStr == null) throw new SemVerException("null");
        if(versionStr.isEmpty()) throw new SemVerException("empty");
        
        final char[] versionChars = versionStr.toCharArray();
        int begin = 0;
        int current = 0;
        int len = versionChars.length;
        
        boolean build = false;
        boolean pr = false;
        
        Integer major = null;
        Integer minor = null;
        Integer patch = null;
        List<Object> buildMetadata = new ArrayList<>();
        List<Object> preReleaseIdentifiers = new ArrayList<>();
        
        while(++current <= len) {
            if(current == len || versionChars[current] == '.' || versionChars[current]== '-' || versionChars[current] == '+') {
                String buf = String.valueOf(versionChars, begin, current - begin);
                begin = current + 1;
                
                if(pr) {
                    preReleaseIdentifiers.add(buf);
                } else if(build) {
                    buildMetadata.add(buf);
                } else {
                    int val = Integer.parseInt(buf);
                    if(major == null) major = val;
                    else if(minor == null) minor = val;
                    else if(patch == null) patch = val;
                    else throw new SemVerException(versionStr);
                }
                
                if(current < len && versionChars[current] != '.') {
                    pr = (versionChars[current]== '-');
                    build = (versionChars[current] == '+');
                }
            }
        }

        return create(major, minor, patch, preReleaseIdentifiers, buildMetadata);
    }

    /**
     * Constructor, previously validated.
     * 
     * @param major the MAJOR number
     * @param minor the MINOR number
     * @param patch the PATCH number
     * @param preRelease the pre-release tags
     * @param build the build metadata
     */
    private SemVer(final int major, final int minor, final int patch, final Collection<?> preRelease, final Collection<?> build) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.preRelease = Collections.unmodifiableCollection(preRelease);
        this.build = Collections.unmodifiableCollection(build);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Gets the MAJOR version.
     * 
     * @return the MAJOR version, non-negative
     */
    public int getMajor() {
        return this.major;
    }
    
    /**
     * Gets the MINOR version.
     * 
     * @return the MINOR version, non-negative
     */
    public int getMinor() {
        return this.minor;
    }
    
    /**
     * Gets the PATCH version.
     * 
     * @return the PATCH version, non-negative
     */
    public int getPatch() {
        return this.patch;
    }
    
    /**
     * Gets the pre-release tags
     * @return the pre-release tags, not null
     */
    public Collection<?> getPreRelease() {
        return this.preRelease;
    }
    
    /**
     * Gets the build metadata
     * @return the build metadata, not null
     */
    public Collection<?> getBuild() {
        return this.build;
    }
    
    //-----------------------------------------------------------------------
    /**
     * Checks if this is pre-released software. 
     * <p>
     * Pre-released software indicates that the version is unstable and might
     * not satisfy the intended compatibility requirements as denoted by its associated normal version
     * 
     * @return true if the version is a pre-release, false otherwise
     */
    public boolean isPreRelease() {
        return this.major == 0 || !this.preRelease.isEmpty();
    }
    
    //-----------------------------------------------------------------------
    /**
     * Compares this version to another version
     * <p>
     * The comparison is primarily based on the date, from earliest to latest.
     * It is "consistent with equals", as defined by {@link Comparable}.
     * 
     * @param other  the other version to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    public int compareTo(SemVer otherVersion) {
        int cmp = this.major - otherVersion.major;
        if(cmp == 0) {
            cmp = this.minor - otherVersion.minor;
            if(cmp == 0) {
                cmp = this.patch - otherVersion.patch;
            }
            
            if(cmp == 0) {
                cmp = Boolean.compare(otherVersion.isPreRelease(), this.isPreRelease());
                
                if(cmp == 0) {
                    Iterator<?> thisIterator = this.preRelease.iterator();
                    Iterator<?> otherIterator = otherVersion.preRelease.iterator();
                    
                    while(cmp == 0 && thisIterator.hasNext() && otherIterator.hasNext()) {
                        String thisPR = thisIterator.next().toString();
                        String otherPR = otherIterator.next().toString();
                        cmp = compareTags(thisPR, otherPR);
                    }
                    
                    if(cmp == 0) {
                        if(thisIterator.hasNext()) {
                            cmp = 1;
                        } else if(otherIterator.hasNext()) {
                            cmp = -1;
                        }
                    }
                }
            }
        }
        
        return cmp;
    }
    
    /**
     * Compares two tag identifiers
     * <p>
     * Identifiers consisting of only digits are compared numerically and identifiers
     * with letters or hyphens are compared lexically in ASCII sort order. Numeric
     * identifiers always have lower precedence than non-numeric identifiers
     * @param thisTag the tag from this object
     * @param otherTag the tag from the other object
     * @return the comparator value, negative if less, positive if greater
     */
    private int compareTags(String thisTag, String otherTag) {
        Integer thisNum = null;
        Integer otherNum = null;
        int cmp = 0;
        
        try {
            thisNum = Integer.parseUnsignedInt(thisTag);
        } catch(NumberFormatException e) { }
        
        try {
            otherNum = Integer.parseUnsignedInt(otherTag);
        } catch(NumberFormatException e) { }
        
        if(thisNum != null && otherNum != null) {
            cmp = thisNum.intValue() - otherNum.intValue();
        } else {
            if(thisNum != null) {
                cmp = -thisNum.intValue();
            } else if(otherNum != null) {
                cmp = -otherNum.intValue();
            } else {
                cmp = thisTag.compareTo(otherTag);
            }
        }
        
        return cmp;
    }
    
    /**
     * Checks if this version is newer than another version.
     * <p>
     * This checks to see if this version represents software version released
     * after another version of the software.
     * <pre>
     *   SemVer a = SemVer.parse("1.0.0-alpha.1");
     *   SemVer b = SemVer.parse("1.0.0-beta");
     *   a.isNewerThan(b); // false
     *   a.isNewerThan(a); // false
     *   b.isNewerThan(a); // true
     * </pre>
     * 
     * @param otherVersion the other version to compare to, not null
     * @return true if this version was released after other, false otherwise
     */
    public boolean isNewerThan(SemVer otherVersion) {
        return compareTo(otherVersion) > 0;
    }
    
    /**
     * Checks if this version is older than another version.
     * <p>
     * This checks to see if this version represents software version released
     * before another version of the software.
     * <pre>
     *   SemVer a = SemVer.parse("1.0.0-alpha.1");
     *   SemVer b = SemVer.parse("1.0.0-beta");
     *   a.isOlderThan(b); // true
     *   a.isOlderThan(a); // false
     *   b.isOlderThan(a); // false
     * </pre>
     * 
     * @param otherVersion the other version to compare to, not null
     * @return true if this version was released before other, false otherwise
     */
    public boolean isOlderThan(SemVer otherVersion) {
        return compareTo(otherVersion) > 0;
    }
    
    /**
     * Checks if this version is compatible with another version.
     * <p>
     * This checks to see if this version is API-compatible to another version
     * of the software.
     * <pre>
     *   SemVer a = SemVer.parse("1.0.1");
     *   SemVer b = SemVer.parse("1.1.0");
     *   SemVer c = SemVer.parse("2.0.0");
     *   a.isCompatibleWith(a); // true
     *   a.isCompatibleWith(b); // false
     *   b.isCompatibleWith(a); // false
     *   a.isCompatibleWith(c); // false
     *   b.isCompatibleWith(c); // false
     * </pre>
     * 
     * @param otherVersion the other version to compare to, not null
     * @return true if this version is compatible with the other version, false otherwise
     */
    public boolean isCompatibleWith(SemVer otherVersion) {
        // initial development. Anything may change at any time
        if(major == 0 || otherVersion.major == 0) return compareTo(otherVersion) == 0;
        
        return major == otherVersion.major && compareTo(otherVersion) >= 0;
    }
    
    //-----------------------------------------------------------------------
    /**
     * Checks if this version is equal to another version.
     * <p>
     * Compares this {@code SemVer} with another ensuring that the version is the same.
     * <p>
     * Only objects of type {@code SemVer} are compared, other types return false.
     * 
     * @implSpec
     * This takes build tags into account.
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other version
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        
        if(obj instanceof SemVer) {
            return compareTo((SemVer)obj) == 0;
        }
        
        return false;
    }
    
    /**
     * A hash code for this version.
     *
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.major;
        result = prime * result + this.minor;
        result = prime * result + this.patch;
        result = prime * result + this.preRelease.hashCode();
        result = prime * result + this.build.hashCode();
        return result;
    }
    
    //-----------------------------------------------------------------------
    /**
     * Outputs this versuib as a {@code String}, such as {@code 1.0.0-alpha.1}.
     * <p>
     * The output will be in the Semantic Versioning format {@code MAJOR.MINOR.PATCH-pre-release+build}.
     *
     * @return a string representation of this version, not null
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        
        buf.append(this.major).append('.').append(this.minor).append('.').append(this.patch);
        
        if(!this.preRelease.isEmpty()) {
            buf.append('-');
            for(Object pr : this.preRelease) {
                buf.append(pr).append('.');
            }
            
            buf.deleteCharAt(buf.length() - 1);
        }
        
        if(!this.build.isEmpty()) {
            buf.append('+');
            for(Object bm : this.build) {
                buf.append(bm).append('.');
            }
            
            buf.deleteCharAt(buf.length() - 1);
        }
        
        return buf.toString();
    }
}
