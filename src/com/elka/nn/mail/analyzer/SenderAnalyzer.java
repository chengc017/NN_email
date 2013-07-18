package com.elka.nn.mail.analyzer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.Address;
import javax.mail.MessagingException;

public class SenderAnalyzer {


	private MailReader mail;
	private List<String> listOfSubWords;
	
	//@TODO Tu trzeba bedzie wczytywac te liste s³ów z pliku zeby to jakos ladnie miedzy wywolaniami przenosic
	
	public SenderAnalyzer(MailReader mess) {
		this.mail = mess;
	}
	
	public int[] analyzeSender() throws MessagingException {
		Address s = mail.getMessage().getFrom()[0];
		String addr_str = s.toString();
		String[] arr = addr_str.split("[\\s@<>]");
		int[] intTab = new int[arr.length];
		for (int i=0; i<arr.length; i++) {
			//System.out.println(has2Digits(arr[i]));
			intTab[i] = has2Digits(arr[i]);
		}
		return intTab;
	}
	
	public void addNewElementToList(String element) {
		this.listOfSubWords.add(element);
	}
	
	public int has2Digits(String str){
		int count = 0;
		if (str!=null) {
			for (char c : str.toCharArray()) {
				if (Character.isDigit(c))
					count++;
				if (count >= 2)
					return 1;
			}
		}
		return 0;
	}
	
	public int returnTheBiggestVal(int[] tab) {
		for (int el : tab)
			if (el == 1)
				return 1;
		return 0;
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
		SenderAnalyzer SA = new SenderAnalyzer(MR);
		int[] tmp = SA.analyzeSender();
		int ret = SA.returnTheBiggestVal(tmp);
		System.out.println(ret);
	}

}
