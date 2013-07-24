package com.elka.nn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ChartDrawer {

	PrintStream chartFile;
	
	public ChartDrawer(String fileName) {
		// TODO Auto-generated constructor stub
		String os = System.getProperty("os.name");
		try {
			if (os.startsWith("Linux")) {
				chartFile = new PrintStream(new FileOutputStream(fileName));
			}
			else if (os.startsWith("Windows")) {
				String path = System.getProperty("user.home");
				File textfile = new File(path, fileName);
				chartFile = new PrintStream(new FileOutputStream(textfile));
			}
			else {
				System.out.println("ERROR: Not recognized OS");
				System.exit(1);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void writeToFile(String lineToWrite) {
		chartFile.println(lineToWrite);
	}
	
	public void closeWriting() {
		chartFile.close();
	}
	
	public void addPlotDefs() {
		String end = "figure(2); plot(krok, y,'--r'); ylabel('Blad sieci dla kolejnych wsp alfa');";
		chartFile.println(end);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "/home/lukasz/Pulpit/test.m";
		ChartDrawer cd = new ChartDrawer(path);
		String s = "Cos tam wrzucam";
		cd.writeToFile(s);
		cd.writeToFile(path);
		cd.addPlotDefs();

	}

}
