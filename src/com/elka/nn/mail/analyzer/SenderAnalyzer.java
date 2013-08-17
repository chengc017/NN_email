package com.elka.nn.mail.analyzer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.Address;
import javax.mail.MessagingException;

public class SenderAnalyzer {


	private MailReader mail;
	
	public SenderAnalyzer(MailReader mess) {
		this.mail = mess;
	}
	
	public int[] analyzeSender() throws MessagingException {
		Address s = mail.getMessage().getFrom()[0];
		String addr_str = s.toString();
		String[] arr = addr_str.split("[\\s@<>.]");
		int[] intTab = new int[arr.length];
		for (int i=0; i<arr.length; i++) {
			System.out.println(arr[i]);
			intTab[i] = hasXDigits(arr[i], 3);
		}
		return intTab;
	}
	
	
	/**
	 * 
	 * @param str		badany string
	 * @param howMany	ilosc cyfr zawartych w warunku sprawdzenia
	 * @return			1 on true, 0 on false
	 */
	
	public int hasXDigits(String str, int howMany){
		int count = 0;
		if (str!=null) {
			for (char c : str.toCharArray()) {
				if (Character.isDigit(c))
					count++;
				if (count >= howMany)
					return 1;
			}
		}
		return 0;
	}
	
	/**
	 * @param str 		string badany (sender mail address)
	 * @param domain	wprowadzona badana domena
	 * @return			1 on true, 0 on false
	 * @throws MessagingException 
	 */
	
	public int hasDomain(String domain) throws MessagingException {
		String str = mail.getMessage().getFrom()[0].toString();
		if (str!=null) {
			return str.contains(domain) ? 1 : 0;
		}
		return 0;
	}
	
	public int returnTheBiggestVal(int[] tab) {
		for (int el : tab)
			if (el == 1)
				return 1;
		return 0;
	}
	
	public int getAnalyze() throws MessagingException {
		int[] ret = analyzeSender();
		if (returnTheBiggestVal(ret) == 1 || hasDomain(".ru") == 1 || hasDomain(".cn") == 1)
			return 1;
		else
			return 0;
	}
	
	
	
	/**
	 * @param args
	 * @throws MessagingException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, MessagingException {
		// TODO Auto-generated method stub
		//String path = "D:\\SPAM_EML\\email2.eml";
		String path = "C:\\Users\\Lukasz\\Desktop\\email16.eml";
		MailReader MR = new MailReader(path);
		SenderAnalyzer SA = new SenderAnalyzer(MR);
		int[] tmp = SA.analyzeSender();
		int ret = SA.returnTheBiggestVal(tmp);
		System.out.println("has domain: " + SA.hasDomain(".net"));
		System.out.println(ret);
	}

}
