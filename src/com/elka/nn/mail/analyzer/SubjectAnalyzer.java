package com.elka.nn.mail.analyzer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

public class SubjectAnalyzer {

	private MailReader mail;
	private List<String> listOfSubWords;
	
	//@TODO Tu trzeba bedzie wczytywac te liste s��w z pliku zeby to jakos ladnie miedzy wywolaniami przenosic
	
	public SubjectAnalyzer(MailReader mess) {
		this.mail = mess;
		this.listOfSubWords = new ArrayList<String>();
	}
	
	public double analyzeSubject() throws MessagingException {
		String s = mail.getMessage().getSubject();
		String[] arr = s.split("\\s+");
		for (String el : listOfSubWords) {
			for (int i=0; i<arr.length; i++) {
				if (arr[i].equalsIgnoreCase(el)) {
					return 1.0;
				}
			}
		}
		return 0.0;
	}
	
	public void addNewElementToList(String element) {
		this.listOfSubWords.add(element);
	}
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 * @throws MessagingException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, MessagingException {
		// TODO Auto-generated method stub
		String path = "D:\\SPAM_EML\\email2.eml";
		MailReader MR = new MailReader(path);
		SubjectAnalyzer sAnalyzer = new SubjectAnalyzer(MR);
		System.out.println(sAnalyzer.analyzeSubject());
		sAnalyzer.addNewElementToList("costam!");
		System.out.println(sAnalyzer.analyzeSubject());
	}

}
