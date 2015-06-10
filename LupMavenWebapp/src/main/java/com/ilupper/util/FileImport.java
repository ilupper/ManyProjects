package com.ilupper.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileImport {

	public static void main(String[] args) {
		FileImport fileImport = new FileImport();
		String message = null;
		message = fileImport.importFile("importFile.txt");
		System.out.println(message);
		message = fileImport.importFileByLine("importFile.txt");
		System.out.println(message);
	}
	
	public String importFileByLine(String filename) {
		File file = new File(filename);
		FileReader fr = null; 
		
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String message = "";
			
			String line = null;
			do {
				line = br.readLine();
				if (line != null)
					message = message.concat(line + "\n");
			} while (line != null);
			
			return message + "|";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "blank";
			
	}
	
	public String importFile(String filename) {
		File file = new File(filename);
		int filesize = (int)file.length();
		byte[] byteArray = new byte[filesize];
		
		FileInputStream fis = null; 
		try {
			fis = new FileInputStream(file);
			fis.read(byteArray);
			char[] charArray = new char[byteArray.length];
			for (int i=0; i < byteArray.length; i++) {
				charArray[i] = (char)byteArray[i];	
			}
			CharArrayWriter caw = new CharArrayWriter();
			caw.write(charArray, 0, charArray.length);
			return caw.toString() + "|";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "blank";
	}

}
