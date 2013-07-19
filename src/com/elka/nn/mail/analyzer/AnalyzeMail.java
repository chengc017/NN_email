package com.elka.nn.mail.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.MessagingException;

public class AnalyzeMail {

	private double[] binVector;
	private MailReader MR;
	private HashMapUtils hsu;
	
	public AnalyzeMail(String mailPath) throws FileNotFoundException, MessagingException {
		MR = new MailReader(mailPath);
		hsu = new HashMapUtils();
		binVector = new double[50];
	}
	
	public void getDoubleVector(File wordsFile) throws MessagingException {
		try {
			String tmpMail = MR.getText(MR.getMessage());
			String[] tmpArray = tmpMail.split("\\s+");	// narazie niech to beda whitespace'y
			
			hsu.readHashMapFromFile(wordsFile);			// wczytanie listy slow z pliku
			String[] words = hsu.getWordsArray();		
			
			// tu moze trzeba by jeszcze z rozmiarem tych tablic pokombinowac zeby sie nie psuly
			
			for (int i=0; i<words.length; i++) {
				for (int k=0; k<tmpArray.length; k++)
					if (words[i].equalsIgnoreCase(tmpArray[k])) {
						binVector[i] = 1.0;
						break;
					}
					else {
						binVector[i] = 0.0;
					}
						
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double[] getbinVector() {
		return binVector;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
