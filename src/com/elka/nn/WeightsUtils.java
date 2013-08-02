package com.elka.nn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeightsUtils {

	List<Double> weightsList;

	public WeightsUtils() {
		weightsList = new ArrayList<Double>();
	}

	public void writeWeightsToFile(File weightsFile) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(weightsFile);
			for (Double element : weightsList) {
				fw.write(element.toString() + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setWeightsList(List<Double> list) {
		this.weightsList = list;
	}

	public List<Double> getWeightsList() {
		return this.weightsList;
	}

	public void loadWeightsFromFile(File weightsFile) throws IOException {
		String str = null;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(weightsFile));
			while ((str = in.readLine()) != null) {
				Double tmp = new Double(str);
				weightsList.add(tmp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		WeightsUtils wu = new WeightsUtils();
		wu.loadWeightsFromFile(new File("/home/lukasz/Pulpit/wagi.txt"));
		for (Double el : wu.getWeightsList())
			System.out.println(el.toString());
	}

}
