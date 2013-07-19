package com.elka.nn.mail.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HashMapUtils {

	private HashMap<String, Integer> wordcount;
	private File inFile;
	private BufferedReader in;
	private FileWriter fw;
	
	public HashMapUtils(String path) {
		this.inFile = new File(path);
		this.in = null;
		this.fw = null;
		this.wordcount = new HashMap<String, Integer>();
	}
	
	private void getCountedList(String dirPath) throws IOException {
		
		try { 
    	
    	
        // Opening file 
        // change "/Users/anyexample/input.txt" to path to your test file  
        in = new BufferedReader(new FileReader(dirPath));
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
                if ((!Character.isLetter(str.charAt(i))) || (i + 1 == str.length())) { 
                    // do nothing if previous character was also non-letter 
                    if (i - idx1 > 1) { 
                        // copy word from input string buffer to new variable  
                        // from previous non-letter symbol  
                        // to current symbol which is also non-letter 

                        // if this is a letter(than it is last character in the line  
                        // and we should copy it to word)   
                        if (Character.isLetter(str.charAt(i))) 
                            i++;

                        // copying...  
                        String word = str.substring(idx1 + 1, i);

                        // Check if word is in HashMap 
                        if (wordcount.containsKey(word)) { 
                            // get number of occurrences for this word 
                            // increment it  
                            // and put back again  
                            wordcount.put(word, wordcount.get(word) + 1);
                        } else { 
                            // this is first time we see this word, set value '1' 
                            wordcount.put(word, 1);
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

    // This code sorts outputs HashMap sorting it by values 

    // First we're getting values array  
    ArrayList<Integer> values = new ArrayList<Integer>();
    values.addAll(wordcount.values());
    // and sorting it (in reverse order) 
    Collections.sort(values, Collections.reverseOrder());
    
    try {
//    	File newFile = new File(outPath);
    	//BufferedWriter out = new BufferedWriter(new FileWriter(outPath));
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
	
	
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String dirPath = "C:\\Users\\Lukasz\\Desktop\\test_hash.txt";
		String outPath = "C:\\Users\\Lukasz\\Desktop\\out1.txt";
		HashMapUtils HMU = new HashMapUtils(outPath);
		HMU.getCountedList(dirPath);
	}

}
