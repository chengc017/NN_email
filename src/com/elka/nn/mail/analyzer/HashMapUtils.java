package com.elka.nn.mail.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HashMapUtils {
	
	public final int SIZEOFWORDS = 50;

	private HashMap<String, Integer> wordcount;
//	private File inFile;
	private BufferedReader in;
	private FileWriter fw;
	private String[] wordsArray;

	
	public HashMapUtils() {
//		this.inFile = new File(path);
		wordcount = new HashMap<String, Integer>();
		wordsArray = new String[SIZEOFWORDS];
		this.in = null;
		this.fw = null;
	}

	public void getCountedList(File filePath) throws IOException {

		try {

			// Opening file
			// change "/Users/anyexample/input.txt" to path to your test file
			in = new BufferedReader(new FileReader(filePath));
			// string buffer for file reading
			String str;

			// reading line by line from file
			while ((str = in.readLine()) != null) {
				str = str.toLowerCase(); // convert to lower case

				// starting index, we'll use this to copy words from string
				int idx1 = -1;
				// process each characters
				for (int i = 0; i < str.length(); i++) {
					// trigger condition if current character is not letter
					// or it is the end of line
					if ((!Character.isLetter(str.charAt(i)))
							|| (i + 1 == str.length())) {
						// do nothing if previous character was also non-letter
						if (i - idx1 > 1) {
							// copy word from input string buffer to new
							// variable
							// from previous non-letter symbol
							// to current symbol which is also non-letter

							// if this is a letter(than it is last character in
							// the line
							// and we should copy it to word)
							if (Character.isLetter(str.charAt(i)))
								i++;

							// copying...
							String word = str.substring(idx1 + 1, i);
							word = word.toLowerCase();
							
							// zabezpiecznie przed przedimkami typu a, in, do itp...
							if (word.length() > 2) {					
								// Check if word is in HashMap
								if (wordcount.containsKey(word)) {
									// get number of occurrences for this word
									// increment it
									// and put back again
									wordcount.put(word, wordcount.get(word) + 1);
								} else {
									// this is first time we see this word, set
									// value '1'
									wordcount.put(word, 1);
								}
							}
						}

						// remember current position as last non-letter symbol
						idx1 = i;
					}
				}
			}
			// Close buffered reader
			in.close();
		} catch (Exception e) {
			// If something unexpected happened
			// print exception information and quit
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void sortHashMapByValuesToFile(File inFile) {
	    // This code sorts outputs HashMap sorting it by values 
	    // First we're getting values array  
	    ArrayList<Integer> values = new ArrayList<Integer>();
	    values.addAll(wordcount.values());
	    // and sorting it (in reverse order) 
	    Collections.sort(values, Collections.reverseOrder());
	    
	    try {
//	    	File newFile = new File(outPath);
//	    	BufferedWriter out = new BufferedWriter(new FileWriter(outPath));
	        fw = new FileWriter(inFile);
	    	int last_i = -1;
	        // Now, for each value  
	        for (Integer i : values) { 
	            if (last_i == i) // without dublicates  
	                continue;
	            last_i = i;
	            // we print all hash keys  
	            for (String s : wordcount.keySet()) {
	                if (wordcount.get(s) == i) {
	                	// which have this value
	                	fw.write(s + ":" + i + "\n");
	                	System.out.println(s + ":" + i);
	                }
	            }
	            // pretty inefficient, but works  
	        } 
	        fw.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public void sortHashMapByValuesInNN() {
	    // This code sorts outputs HashMap sorting it by values 
	    // First we're getting values array  
	    ArrayList<Integer> values = new ArrayList<Integer>();
	    values.addAll(wordcount.values());
	    // and sorting it (in reverse order) 
	    Collections.sort(values, Collections.reverseOrder());
	    
	    try {
//	    	File newFile = new File(outPath);
//	    	BufferedWriter out = new BufferedWriter(new FileWriter(outPath));
	    	int last_i = -1;
	        // Now, for each value  
	    	int arrayCounter = 0;
	        for (Integer i : values) {
	        	if (arrayCounter == SIZEOFWORDS)
                	break;
	            if (last_i == i) // without dublicates  
	                continue;
	            last_i = i;
	            // we print all hash keys  
	            for (String s : wordcount.keySet()) {
	                if (wordcount.get(s) == i) {
	                	// which have this value
	                	//System.out.println(s + ":" + i);
	                	wordsArray[arrayCounter] = s;
	                	arrayCounter++;
	                }
	                if (arrayCounter == SIZEOFWORDS)
	                	break;
	            }
	            // pretty inefficient, but works  
	        } 
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void readHashMapFromFile(File inputFile) throws IOException {
		String[] tmpArray = new String[2];
		int i = 0;
		try {
			in = new BufferedReader(new FileReader(inputFile));
			String str;
			while ((str = in.readLine()) != null) {
				str = str.toLowerCase();
				tmpArray = str.split(":");			// do tymczasowej tablicy wyrzucam przeczytany wiersz
				wordsArray[i] = tmpArray[0];		// i biore pierwszy element (klucz)
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getWordsFromHashMapToArray() {
		
	}
	
	public String[] getWordsArray() {
		return wordsArray;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String dirPath = "C:\\Users\\Lukasz\\Desktop\\test_hash.txt";
		String outPath = "C:\\Users\\Lukasz\\Desktop\\out2.txt";
		HashMapUtils HMU = new HashMapUtils();
//		HMU.getCountedList(dirPath);
		File inFile = new File(outPath); 
//		HMU.sortHashMapByValues();
		HMU.readHashMapFromFile(inFile);
		String[] tmp = HMU.getWordsArray();
		for (String el : tmp) {
			System.out.println(el);
		}
	}

}
