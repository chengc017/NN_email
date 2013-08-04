package com.elka.nn.mail.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.MessagingException;

import com.elka.nn.FlowVariables;

public class AnalyzeMail {

	private double[] binVector;
	private MailReader MR;
	private HashMapUtils hsu;
	private FlowVariables fv;
	
	public AnalyzeMail(HashMapUtils hsu, FlowVariables fv) throws FileNotFoundException, MessagingException {
		this.hsu = hsu;
		this.fv = fv;
	}
	
	public void makeDoubleVector(String mailPath) throws MessagingException {
		try {
			binVector = new double[fv.WORDSIZE];
			MR = new MailReader(mailPath);
			String tmpMail = MR.getText(MR.getMessage());
			String[] tmpArray = tmpMail.split("\\s+");	// narazie niech to beda whitespace'y
			
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
