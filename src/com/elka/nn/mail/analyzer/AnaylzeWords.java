package com.elka.nn.mail.analyzer;

import java.io.File;

public class AnaylzeWords {
	
	private MailReader MR;
	private HTMLmessage html_mess;
	private boolean isHTML = false;
	private HashMapUtils hsu;
	private File toSave;
	
	public AnaylzeWords(String fileToSavePath) {
		toSave = new File(fileToSavePath);
		hsu = new HashMapUtils();
	}
	
	
	public void readFiles(final File dir) throws Exception {
		for (final File fileEntry : dir.listFiles()) {
			if (fileEntry.isDirectory()) {
				System.out.println("Jest tu folder: " + fileEntry.getAbsolutePath());
				readFiles(fileEntry);
			}
			else if (fileEntry.isFile()) {
				System.out.println("A tu mamy pliczek: " + fileEntry.getAbsolutePath());
				String tmp_path = fileEntry.getAbsolutePath();
				System.out.println("A to jest jego nazwa: " + fileEntry.getName());
				
				MR = new MailReader(tmp_path);
				html_mess = new HTMLmessage(MR);
				isHTML = html_mess.analyzeParts(html_mess.getMail().getMessage());
				if (!isHTML) {
					hsu.getCountedList(fileEntry);
				}
			}
		}
		hsu.sortHashMapByValuesToFile(toSave);
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileToSavePath = "C:\\Users\\Lukasz\\Desktop\\out2.txt";
		String dirOfFiles = "C:\\Users\\Lukasz\\Desktop\\cos_tam";
		File dir = new File(dirOfFiles);
		AnaylzeWords AW = new AnaylzeWords(fileToSavePath);
		try {
			AW.readFiles(dir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}

}
