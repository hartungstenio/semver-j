package net.hobbysw.semver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implements the Semantic Versioning scheme.
 * 
 * @author Christian Hartung <chrishartung+semver-j at outlook.com>
 *
 */
public class Version {
	private int major;
	private int minor;
	private int patch;
	private List<String> preReleaseIdentifiers = new ArrayList<String>();
	private List<String> buildMetadata = new ArrayList<String>();
	
	public int getMajor() {
		return major;
	}
	
	public void setMajor(int major) {
		this.major = major;
	}
	
	public int getMinor() {
		return minor;
	}
	
	public void setMinor(int minor) {
		this.minor = minor;
	}
	
	public int getPatch() {
		return patch;
	}
	
	public void setPatch(int patch) {
		this.patch = patch;
	}
	
	public List<String> getPreReleaseIdentifiers() {
		return preReleaseIdentifiers;
	}
	
	public List<String> getBuildMetadata() {
		return buildMetadata;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof Version)) {
		     return false;
		}
		
		Version v = (Version)obj;

		return     v.getMajor() == getMajor()
				&& v.getMinor() == getMinor()
				&& v.getPatch() == getPatch()
				&& v.getPreReleaseIdentifiers().equals(getPreReleaseIdentifiers())
				&& v.getBuildMetadata().equals(getBuildMetadata());
				
	}
	
	@Override
	public int hashCode() {
		return (((17 * 37) + getMajor()) * 37 + getMinor()) * 37 + getPatch();
	}
	
	public static Version valueOf(String str) {
		String build = null;
		String preRelease = null;
		int major, minor, patch;
		
		String[] parts = str.split("+");
		if(parts.length > 1) {
			build = parts[1];
		}
		
		parts = parts[0].split("-");
		if(parts.length > 1) {
			preRelease = parts[1];
		}
		
		parts = parts[0].split("\\.");
		if(parts.length != 3) {
			throw new IllegalArgumentException("Invalid version number: " + str);
		}
		
		major = Integer.parseInt(parts[0]);
		minor = Integer.parseInt(parts[1]);
		patch = Integer.parseInt(parts[2]);
		
		Version ver = new Version();
		ver.setMajor(major);
		ver.setMinor(minor);
		ver.setPatch(patch);
		
		if(preRelease != null) {
			ver.getPreReleaseIdentifiers().addAll(Arrays.asList(preRelease.split(".")));
		}
		
		if(build != null) {
			ver.getBuildMetadata().addAll(Arrays.asList(build.split(".")));
		}
		
		return ver;
	}
}
