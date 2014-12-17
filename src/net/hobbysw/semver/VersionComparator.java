package net.hobbysw.semver;

import java.util.Comparator;
import java.util.List;

public class VersionComparator implements Comparator<Version> {

	@Override
	public int compare(Version o1, Version o2) {
		if(o1 == null) {
			return o2 == null ? 0 : -1;
		}
		
		int i = o1.getMajor() - o2.getMajor();
		if(i != 0) {
			return i;
		}
		
		i = o1.getMinor() - o2.getMinor();
		if(i != 0) {
			return i;
		}
		
		i = o1.getPatch() - o2.getPatch();
		if(i != 0) {
			return i;
		}
		
		return comparePreReleaseIdentifiers(o1.getPreReleaseIdentifiers(), o2.getPreReleaseIdentifiers());
	}
	
	private int comparePreReleaseIdentifiers(List<String> v1, List<String> v2) {
		if(v1.isEmpty()) {
			return v2.isEmpty() ? 0 : 1;
		}
		
		if(v2.isEmpty()) {
			return v1.isEmpty() ? 0 : -1;
		}
		
		int len1 = v1.size();
		int len2 = v2.size();
		
		for(int i = 0; i < Math.max(len1, len2); i++) {
			try {
				int n1 = Integer.parseInt(v1.get(i));
				int n2 = Integer.parseInt(v2.get(i));
				
				if(n1 != n2) {
					return n1 - n2;
				}
			} catch(NumberFormatException ex) {
				
			}
		}
		
		return len2 - len1;
	}
}
