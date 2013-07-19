package com.elka.nn.mail.analyzer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

	
	public FileReader() {
	}	
	
	public List<String> getWordsFromFile(String path){
		String strLine;
		List<String> bannedWords = new ArrayList<String>();
		try {
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
			while ((strLine=br.readLine())!= null) {
				//System.out.println(strLine);
				bannedWords.add(strLine);
			}
			in.close();
//			System.out.println(bannedWords);
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		return bannedWords;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileReader FR = new FileReader();
		List<String> list = new ArrayList<String>();
		String path = "/home/lukasz/Pulpit/text.txt";
		list = FR.getWordsFromFile(path);
		System.out.println(list);
	}
	
}
