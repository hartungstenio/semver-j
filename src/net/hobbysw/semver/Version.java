package net.hobbysw.semver;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Semantic Versioning scheme. The format is MAJOR.MINOR.PATCH-pre.release+build
 * 
 * 
 * @author Christian Hartung
 *
 */
public class Version {
	private int major;
	private int minor;
	private int patch;
	private List<String> preReleaseIdentifiers = new ArrayList<String>();
	private List<String> buildMetadata = new ArrayList<String>();
	
	/**
	 * Constructs a new <code>Version</code> with 0.0.0 as value
	 */
	public Version() {
		this(0, 0, 0);
	}
	
	/**
	 * Constructs a new <code>Version</code> with the specified value
	 * 
	 * @param major the MAJOR version
	 * @param minor the MINOR version
	 * @param patch the PATCH version
	 * 
	 * @see	#setMajor
	 * @see #setMinor
	 * @see #setPatch
	 */
	public Version(int major, int minor, int patch) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	
	/**
	 * Gets the MAJOR version
	 * 
	 * @return the MAJOR version number, which should be greater than or equal 0
	 * @see	#setMajor
	 */
	public int getMajor() {
		return major;
	}
	
	/**
	 * Sets the MAJOR version, which should be changed when you make incompatible API changes.
	 *  
	 * @param major the MAJOR version number, which must be an integer greater than or equal 0
	 */
	public void setMajor(int major) {
		this.major = major;
	}
	
	/**
	 * Gets de MINOR version
	 * 
	 * @return the MINOR version number, which should be greater than or equal 0
	 * @see	#setMinor
	 */
	public int getMinor() {
		return minor;
	}
	
	/**
	 * Sets the MINOR version, which should be changed when you add functionality in a backwards-compatible manner.
	 *  
	 * @param minor the MINOR version number, which must be an integer greater than or equal 0
	 */
	public void setMinor(int minor) {
		this.minor = minor;
	}
	
	/**
	 * Gets de PATCH version
	 * 
	 * @return the PATCH version number, which should be greater than or equal 0
	 * @see	#setPatch
	 */
	public int getPatch() {
		return patch;
	}
	
	/**
	 * Sets the PATCH version, which should be changed when you make backwards-compatible bug fixes.
	 *  
	 * @param minor the PATCH version number, which must be an integer greater than or equal 0
	 */
	public void setPatch(int patch) {
		this.patch = patch;
	}
	
	/**
	 * Gets the pre-release identifiers list.
	 * 
	 * Pre-release identifiers are considered when comparing version numbers, and have lower precedence than
	 * the associated normal versions.
	 *  
	 * @return the list of pre-release identifiers.
	 */
	public List<String> getPreReleaseIdentifiers() {
		return preReleaseIdentifiers;
	}
	
	/**
	 * Gets the build metadata associated to this version.
	 * 
	 * Build metadata is ignored when determining version precedence.
	 * 
	 * @return the list of build metadata
	 */
	public List<String> getBuildMetadata() {
		return buildMetadata;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)return true;
		if (obj == null) return false;
		if (!(obj instanceof Version)) return false;
		
		Version v = (Version)obj;

		return     v.major == major
				&& v.minor == minor
				&& v.patch == patch
				&& v.preReleaseIdentifiers.equals(preReleaseIdentifiers)
				&& v.buildMetadata.equals(buildMetadata);
				
	}
	
	@Override
	public int hashCode() {
		final int multiplier = 37;
		
		int result = 17;
		result = multiplier * result + major;
		result = multiplier * result + minor;
		result = multiplier * result + patch;
		result = multiplier * result + preReleaseIdentifiers.hashCode();
		result = multiplier * result + buildMetadata.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(major).append('.').append(minor).append('.').append(patch);
		
		if(!preReleaseIdentifiers.isEmpty()) {
			sb.append('-');
			for(String s : preReleaseIdentifiers) {
				sb.append(s).append('.');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		
		if(!buildMetadata.isEmpty()) {
			sb.append('+');
			
			for(String s : buildMetadata) {
				sb.append(s).append('.');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		
		return sb.toString();
	}
	
	/**
	 * Converts a string in the format MAJOR.MINOR.PATCH-pre-release+build to a {@link Version}. Pre-release and build are both optional.
	 * 
	 * @param versionStr the version number as a string
	 * @return a new version object with information extracted from the string
	 * @throws VersionFormatException if the version does not conform to the MAJOR.MINOR.PATCH-pre-release+build format
	 */
	public static Version valueOf(String versionStr) throws VersionFormatException {
		if(versionStr == null) throw new VersionFormatException("null");
		if(versionStr.isEmpty()) throw new VersionFormatException("empty");
		
		final char[] versionChars = versionStr.toCharArray();
		int begin = 0;
		int current = 0;
		int len = versionChars.length;
		
		boolean build = false;
		boolean pr = false;
		
		Version result = new Version(-1, -1, -1);
		
		while(++current <= len) {
			if(current == len || versionChars[current] == '.' || versionChars[current]== '-' || versionChars[current] == '+') {
				String buf = String.valueOf(versionChars, begin, current - begin);
				begin = current + 1;
				
				if(pr) {
					result.preReleaseIdentifiers.add(buf);
				} else if(build) {
					result.buildMetadata.add(buf);
				} else {
					int val = Integer.parseInt(buf);
					if(result.major < 0) result.major = val;
					else if(result.minor < 0) result.minor = val;
					else if(result.patch < 0) result.patch = val;
					else throw VersionFormatException.forInputString(versionStr);
				}
				
				if(current < len && versionChars[current] != '.') {
					pr = (versionChars[current]== '-');
					build = (versionChars[current] == '+');
				}
			}
		}
		
		return result;
	}
}
