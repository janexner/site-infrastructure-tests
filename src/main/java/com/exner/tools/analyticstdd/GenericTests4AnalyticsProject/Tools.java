package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

public class Tools {
	
	public static final String DTM = "DTM";
	public static final String AA = "Adobe Analytics";
	public static final String AT = "Adobe Target";
	public static final String AAM = "Adobe Audience Manager";
	public static final String MCVID = "Marketing Cloud Visitor ID Service";

	public static boolean testVersionNotOlderThanBaseVersion(String version, String versionBase) {
		String[] testVersionElements = version.split("\\.");
		String[] baseVersionElements = versionBase.split("\\.");

		int loops = Math.min(testVersionElements.length, baseVersionElements.length);
		
		// one special case: loops = 0
		if (null == version || version.isEmpty()) {
			return false;
		}

		for (int i = 0; i < loops; i++) {
			long tvm = Long.parseLong(testVersionElements[i]);
			long bvm = Long.parseLong(baseVersionElements[i]);
			if (tvm > bvm) {
				return true;
			} else if (bvm > tvm) {
				return false;
			}
		}
		
		// hm... they are the same up to the length of the shorter one. what now?
		if (testVersionElements.length > baseVersionElements.length) {
			return true;
		} else if (testVersionElements.length < baseVersionElements.length) {
			return false;
		}

		return true;
	}

	public static boolean testVersionNotNewerThanBaseVersion(String version, String versionBase) {
		String[] testVersionElements = version.split("\\.");
		String[] baseVersionElements = versionBase.split("\\.");

		int loops = Math.min(testVersionElements.length, baseVersionElements.length);
		
		// one special case: loops = 0
		if (null == version || version.isEmpty()) {
			return false;
		}

		for (int i = 0; i < loops; i++) {
			long tvm = Long.parseLong(testVersionElements[i]);
			long bvm = Long.parseLong(baseVersionElements[i]);
			if (tvm > bvm) {
				return false;
			} else if (bvm > tvm) {
				return true;
			}
		}
		
		// hm... they are the same up to the length of the shorter one. what now?
		if (testVersionElements.length > baseVersionElements.length) {
			return false;
		} else if (testVersionElements.length < baseVersionElements.length) {
			return true;
		}

		return true;
	}
}
