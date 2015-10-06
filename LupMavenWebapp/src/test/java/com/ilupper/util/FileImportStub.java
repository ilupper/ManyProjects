package com.ilupper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileImportStub {

	Logger log;
	public FileImportStub() {
		log = LoggerFactory.getLogger(this.getClass());
	}
	
	public static void main(String[] args) {
		FileImport fileImport = new FileImport();
		String message = null;
		message = fileImport.importFile("importFile.txt");
		message = fileImport.importFileByReadLine("importFile.txt");
		
		new FileImportStub().log.info("message = " + message);
	}
}
