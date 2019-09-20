package com.fedex.smartpost.utilities.mainfest.utils;

import com.fedex.smartpost.utilities.mainfest.filter.ManifestFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;

public class FileUtilities {
	private static final Log log = LogFactory.getLog(FileUtilities.class);

	public static File[] getFileList(String pattern) {
//		File dir = new File("\\\\prodisinas\\fxsp-postal1prd\\PostageManifest\\archive");
		File dir = new File("V:\\PostageManifest\\archive");
		if (!dir.isDirectory()) {
			log.error("Invalid directory provided.");
			return null;
		}
		return dir.listFiles(new ManifestFilter(pattern));
	}
}
