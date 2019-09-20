package com.fedex.smartpost.utilities.mainfest;

import com.fedex.smartpost.utilities.mainfest.enums.D1RecordEnum;
import com.fedex.smartpost.utilities.mainfest.enums.HeaderRecordEnum;
import com.fedex.smartpost.utilities.mainfest.utils.FileUtilities;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManifestReader {
	private static final Log log = LogFactory.getLog(ManifestReader.class);
	private static BufferedWriter bw;

	public static void main(String[] args) throws IOException {
		ManifestReader reader = new ManifestReader();
		bw = new BufferedWriter(new FileWriter("/Support/2019-04-14.txt"));
		bw.write("MailDt\tEFN\tPIC\n");
		reader.process();
		bw.close();
	}

	private void process() {
		int totalRecordsWritten = 0;
		log.info("Starting process...");
		File[] files = FileUtilities.getFileList("EVS_V2_201904140001");
		if (files == null) {
			log.info("No file found...");
			return;
		}
		log.info("Found " + files.length + " files to process.");
		for (File file : files) {
			log.info("Processing file: " + file.getName());
			try {
				totalRecordsWritten += processFile(file);
			}
			catch (IOException e) {
				log.error("Unable to read file: " + file.getName());
			}
		}
		log.info("Total records written: " + totalRecordsWritten);
	}

	private int processFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		int recordsWritten = 0;
		String efn = null;
		String mailDate = null;
		while (br.ready()) {
			String line = br.readLine().trim();
			String[] data = line.split("\\|");
//			String masterEfn = file.getName().substring(20);
//			masterEfn = masterEfn.substring(masterEfn.indexOf("_"));
			switch (data[0]) {
				case "H1":
					efn = data[HeaderRecordEnum.EFN.ordinal()];
					mailDate = data[HeaderRecordEnum.DateOfMailing.ordinal()];
					break;
				case "D1":
					bw.write(mailDate + "\t" + efn + "\t" + data[D1RecordEnum.UspsBarcodeNumber.ordinal()] + "\n");
					recordsWritten++;
			}
		}
		br.close();
		log.info("Records written: " + recordsWritten);
		return recordsWritten;
	}
}
