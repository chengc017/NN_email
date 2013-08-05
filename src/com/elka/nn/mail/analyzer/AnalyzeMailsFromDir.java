package com.elka.nn.mail.analyzer;

import java.io.File;
import java.io.FileNotFoundException;

import javax.mail.MessagingException;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import com.elka.nn.FlowVariables;
import com.elka.nn.NeuralNet;

public class AnalyzeMailsFromDir {

	private HashMapUtils hsu;
	private FlowVariables fv;
	private AnalyzeMail am;
	private NeuralNet NN;
	private int allCount;
	private int goodCount;
	private int badCount;
	
	
	public AnalyzeMailsFromDir(HashMapUtils hsu, FlowVariables fv, NeuralNet NN) {
		this.hsu = hsu;
		this.fv = fv;
		this.NN = NN;
		this.allCount = 0;
		this.goodCount = 0;
		this.badCount = 0;
	}
	
	public void getScoreFromCurrentDir(File dir, StyledDocument doc) {
		double out;
		for (final File fileEntry : dir.listFiles()) {
			if (fileEntry.isDirectory()) {
				System.out.println("Jest tu folder: "
						+ fileEntry.getAbsolutePath());
				getScoreFromCurrentDir(fileEntry, doc);
			} else if (fileEntry.isFile()) {
				// Czesc komentarzowa do weryfikacji czy dobrze skacze po pliczkach
				System.out.println("A tu mamy pliczek: "
						+ fileEntry.getAbsolutePath());
				String tmp_path = fileEntry.getAbsolutePath();
				System.out.println("A to jest jego nazwa: "
						+ fileEntry.getName());

				if (tmp_path.endsWith(".eml")) {
					try {
						am = new AnalyzeMail(hsu, fv);
						am.makeDoubleVector(tmp_path);
						out = NN.goForward(am.getbinVector());
						this.countingFunc(out);
						allCount++;
						//doc.insertString(doc.getLength(), "Wynik to: " + out + "\n" , null);
					} catch (FileNotFoundException | MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						doc.insertString(doc.getLength(), "Pominięto plik: " + tmp_path + "(niewłaściwy typ)" + "\n", null);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	public int getCount() {
		return this.allCount;
	}
	
	public void countingFunc(double arg) {
		if(arg >= 0.51)
			badCount++;
		else
			goodCount++;
	}
	
	public void infoDoc(StyledDocument doc) throws BadLocationException {
		doc.insertString(doc.getLength(), "Przeanalizowano " +
											allCount + " wiadomości.\n", null);
		doc.insertString(doc.getLength(), badCount + " wiadomości sklasyfikowano jako SPAM oraz " +
				goodCount + " wiadomości jako pożądane.\n", null);
	}
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
