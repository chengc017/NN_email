package com.elka.nn.mail.analyzer;

import java.io.File;

public class FilesInDirs {
	
	static File folder = new File("/home/lukasz/Pulpit/fold/");
	
	public static void readFiles(final File dir) throws Exception {
		for (final File fileEntry : dir.listFiles()) {
			if (fileEntry.isDirectory()) {
				System.out.println("Jest tu folder: " + fileEntry.getAbsolutePath());
				readFiles(fileEntry);
			}
			else if (fileEntry.isFile()) {
				System.out.println("A tu mamy pliczek: " + fileEntry.getAbsolutePath());
				System.out.println("A to jest jego nazwa: " + fileEntry.getName());
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
