package com.elka.nn.mail.analyzer;

import java.io.File;

import javax.swing.text.StyledDocument;

public class AnaylzeWords {

	private MailReader MR;
	private HTMLmessage html_mess;
	private boolean isHTML = false;
	private HashMapUtils hsu;
	private File toSave;

	public AnaylzeWords(HashMapUtils hsu) {
		// toSave = new File(fileToSavePath);
		this.hsu = hsu;
	}

	public void readFiles(final File dir, StyledDocument doc) throws Exception {
		for (final File fileEntry : dir.listFiles()) {
			if (fileEntry.isDirectory()) {
				System.out.println("Jest tu folder: "
						+ fileEntry.getAbsolutePath());
				readFiles(fileEntry, doc);
			} else if (fileEntry.isFile()) {
				System.out.println("A tu mamy pliczek: "
						+ fileEntry.getAbsolutePath());
				String tmp_path = fileEntry.getAbsolutePath();
				System.out.println("A to jest jego nazwa: "
						+ fileEntry.getName());

				if (tmp_path.endsWith(".eml")) {
					MR = new MailReader(tmp_path);
					html_mess = new HTMLmessage(MR);
					isHTML = html_mess.analyzeParts(html_mess.getMail().getMessage());
					if (!isHTML) {
						hsu.getCountedList(MR.getText(MR.getMessage()));
					}
				}
				else {
					doc.insertString(doc.getLength(), "Pominięto plik: " + tmp_path + "(niewłaściwy typ)" + "\n", null);
				}
			}
		}
		// hsu.sortHashMapByValuesToFile(toSave);
		// System.out.println("Tu jestem po SORT_HASH");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * String fileToSavePath = "C:\\Users\\Lukasz\\Desktop\\out2.txt";
		 * String dirOfFiles = "/home/lukasz/Pulpit/cos_tam"; File dir = new
		 * File(dirOfFiles); AnaylzeWords AW = new AnaylzeWords(); try {
		 * AW.readFiles(dir); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); System.exit(1); }
		 */

	}
}
