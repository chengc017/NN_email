package com.elka.nn.mail.analyzer;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.elka.nn.FlowVariables;

public class HashMapUtils {
	
	public final int SIZEOFWORDS;

	private HashMap<String, Integer> wordcount;
	private HashMap<String, Integer> wordcountSPAM;
	private HashMap<String, Integer> wordcountGOOD;
//	private File inFile;
	private BufferedReader in;
	private FileWriter fw;
	private String[] wordsArray;
	private String[] wordsSPAMArray;
	private String[] wordsGOODArray;
	private FlowVariables fv;

	
	public HashMapUtils(FlowVariables fv) {
//		this.inFile = new File(path);
		this.fv = fv;
		SIZEOFWORDS = fv.WORDSIZE;
		wordcount = new HashMap<String, Integer>();
		wordcountSPAM = new HashMap<String, Integer>();
		wordcountGOOD = new HashMap<String, Integer>();
		wordsArray = new String[fv.WORDSIZE];
		wordsSPAMArray = new String[fv.WORDSIZE];
		wordsGOODArray = new String[fv.WORDSIZE];
		this.in = null;
		this.fw = null;
	}

	public void getCountedList(String textToCount, boolean forSPAM) throws IOException {

		try {
			String[] strA = textToCount.split(" ");
			wordcount.clear();
			// reading line by line from file
			for (String strElement : strA) {
				String str = strElement.toLowerCase(); // convert to lower case

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
			if (forSPAM == true) {
				wordcountSPAM.putAll(wordcount) ;
			} else {
				wordcountGOOD.putAll(wordcount);
			}
		} catch (Exception e) {
			// If something unexpected happened
			// print exception information and quit
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void sortHashMapByValuesToFile(File inFile, boolean forSPAM) {
	    // This code sorts outputs HashMap sorting it by values 
	    // First we're getting values array  
		HashMap<String, Integer> wordcountTMP = new HashMap<String, Integer>();
		
		if (forSPAM == true) {
			wordcountTMP.putAll(wordcountSPAM) ;
		} else {
			wordcountTMP.putAll(wordcountGOOD);
		}
		
	    ArrayList<Integer> values = new ArrayList<Integer>();
	    values.addAll(wordcountTMP.values());
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
	            for (String s : wordcountTMP.keySet()) {
	                if (wordcountTMP.get(s) == i) {
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
	
	public void sortHashMapByValuesInNN(boolean forSPAM) {
	    // This code sorts outputs HashMap sorting it by values 
	    // First we're getting values array  
		
		HashMap<String, Integer> wordcountTMP = new HashMap<String, Integer>();
		
		if (forSPAM == true) {
			wordcountTMP.putAll(wordcountSPAM) ;
		} else {
			wordcountTMP.putAll(wordcountGOOD);
		}
		
	    ArrayList<Integer> values = new ArrayList<Integer>();
	    values.addAll(wordcountTMP.values());
	    // and sorting it (in reverse order) 
	    Collections.sort(values, Collections.reverseOrder());
	    
	    wordsArray = null;
	    wordsArray = new String[fv.WORDSIZE];
	    
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
	            for (String s : wordcountTMP.keySet()) {
	                if (wordcountTMP.get(s) == i) {
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
	        if (forSPAM == true) {
	        	wordsSPAMArray = Arrays.copyOf(wordsArray, wordsArray.length);
	        } else {
	        	wordsGOODArray = Arrays.copyOf(wordsArray, wordsArray.length);
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void readHashMapFromFile(File inputFile, Component parent, boolean forSPAM) throws IOException {
		String[] tmpArray = new String[2];
		int i = 0;
		wordsArray = null;
	    wordsArray = new String[fv.WORDSIZE];
		try {
			in = new BufferedReader(new FileReader(inputFile));
			String str;
			while ((str = in.readLine()) != null) {
				if (i == SIZEOFWORDS)
					break;
				str = str.toLowerCase();
				tmpArray = str.split(":");			// do tymczasowej tablicy wyrzucam przeczytany wiersz
				wordsArray[i] = tmpArray[0];		// i biore pierwszy element (klucz)
				i++;
			}
			if (forSPAM == true) {
				wordsSPAMArray = Arrays.copyOf(wordsArray, wordsArray.length);
			} else {
				wordsGOODArray = Arrays.copyOf(wordsArray, wordsArray.length);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Wystąpił błąd - niepoprawne wczytanie słów", "Błąd wczytywania słów", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String[] getWordsSPAMArray() {
		return wordsSPAMArray;
	}
	
	public String[] getWordsGOODArray() {
		return wordsGOODArray;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
/*		String dirPath = "C:\\Users\\Lukasz\\Desktop\\test_hash.txt";
		String outPath = "C:\\Users\\Lukasz\\Desktop\\out2.txt";
//		HashMapUtils HMU = new HashMapUtils();
//		HMU.getCountedList(dirPath);
		File inFile = new File(outPath); 
//		HMU.sortHashMapByValues();
//		HMU.readHashMapFromFile(inFile);
//		String[] tmp = HMU.getWordsArray();
		for (String el : tmp) {
			System.out.println(el);*/
//		}
	}

}
