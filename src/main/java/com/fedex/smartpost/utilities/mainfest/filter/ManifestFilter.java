package com.fedex.smartpost.utilities.mainfest.filter;

import java.io.File;
import java.io.FilenameFilter;

public class ManifestFilter implements FilenameFilter {
	private final String pattern;

	public ManifestFilter(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public boolean accept(File dir, String name) {
		return (name.startsWith(pattern) && name.endsWith(".proc"));
	}
}
