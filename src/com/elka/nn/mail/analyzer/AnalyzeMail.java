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
	private HTMLmessage htmlmess;
	private SenderAnalyzer sa;
	
	public AnalyzeMail(HashMapUtils hsu, FlowVariables fv) throws FileNotFoundException, MessagingException {
		this.hsu = hsu;
		this.fv = fv;
	}
	
	public void makeDoubleVector(String mailPath) throws MessagingException {
		try {
			binVector = new double[fv.WORDSIZE*2 + fv.OTHERSIZE];
			MR = new MailReader(mailPath);
			htmlmess = new HTMLmessage(MR);
			String tmpMail = MR.getText(MR.getMessage());
//			System.out.println(tmpMail);
//			System.out.println(mailPath);
			String[] tmpArray = tmpMail.split("\\s+");	
			
			String[] SPAMwords = hsu.getWordsSPAMArray();		
			String[] GOODwords = hsu.getWordsGOODArray();		
			int nextI = 0;	
			
			for (int i=0; i<SPAMwords.length; i++) {
				for (int k=0; k<tmpArray.length; k++) {
					if (SPAMwords[i].equalsIgnoreCase(tmpArray[k])) {
						binVector[i] = 1.0;
						break;
					}
					else {
						binVector[i] = 0.0;
					}
				}
				nextI = i;
			}
			System.out.println("nextI: " + nextI);
			nextI = nextI + 1;
			for (int i=0; i<GOODwords.length; i++) {
				for (int k=0; k<tmpArray.length; k++) {
					if (GOODwords[i].equalsIgnoreCase(tmpArray[k])) {
						binVector[nextI] = 1.0;
						break;
					}
					else {
						binVector[nextI] = 0.0;
					}
				}
				nextI++;
			}
			htmlmess.analyzeParts(MR.getMessage());
			binVector[nextI] = htmlmess.getIntOfBoolean();	// czy ma naglowki HTML?
			nextI++;
			if (MR.isLinkIn(tmpMail) == 1 || MR.isImageIn(tmpMail) == 1 || tmpMail.contains("http")) {	// czy zawiera obrazek lub link
				binVector[nextI] = 1;
			}
			else {
				binVector[nextI] = 0;
			}
			nextI++;
			sa = new SenderAnalyzer(MR);
			binVector[nextI] = sa.getAnalyze();
					
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
