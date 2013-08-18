package com.elka.nn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;


public class LearningVector {

	private double[] goodWords;
	private double[] badWords;
	private double[] otherElements;
	private double[] tmpArray;
	
	private HashMap<double[], Double> hash;
	
	private FlowVariables fv;
	
	public LearningVector(FlowVariables fv){
		this.goodWords = new double[fv.WORDSIZE];
		this.badWords = new double[fv.WORDSIZE];
		this.otherElements = new double[fv.OTHERSIZE];
		
		this.tmpArray = new double[fv.NUM_INPUT-1];
		
		this.hash = new HashMap<double[], Double>();
		this.fv = fv;
	}
	
	public HashMap<double[], Double> getHash(){
		return this.hash;
	}
	
	
	public void setArraysWithSamples() throws Exception {
		double value = 0;
		for (int i = 0; i<250; i++) {
			fullFillArrays();
			value = makeComparison();
			double[] array = new double[tmpArray.length];
			array = Arrays.copyOf(tmpArray, tmpArray.length);
			getAllArraysInOne();
			hash.put(array, value);
		}
	}
	
	private void fullFillArrays() {
		for (int i = 0; i<badWords.length; i++)
			badWords[i] = makeRandomInsert();
		for (int i = 0; i<goodWords.length; i++)
			goodWords[i] = makeRandomInsert();
		for (int i = 0; i<otherElements.length; i++) {
			otherElements[i] = makeRandomInsert();
		}
	}
	
	private double makeRandomInsert() {
		Random generator = new Random();
		return generator.nextInt(2);
	}
	
	private double makeComparison() {
		double[] bad = this.badWords;
		double[] good = this.goodWords;
		double[] other = this.otherElements;
		double outValue = 0.0;
		
		int badCount = 0;
		int goodCount = 0;
		int otherCount = 0;
		
		for (int i = 0; i<bad.length; i++)
			badCount = bad[i] == 1.0 ? badCount+1 : badCount;
		for (int i = 0; i<good.length; i++)
			goodCount = good[i] == 1.0 ? goodCount+1 : goodCount;
		for (int i = 0; i<other.length; i++)
			otherCount = other[i] == 1.0 ? otherCount+1 : otherCount;
		
		if (badCount > goodCount && badCount-goodCount >= 3) {
			if (otherCount == 0) {
				outValue = 0.7;
			}
			else if (otherCount == 1) {
				outValue = other[1] == 1 ? 0.8 : 0.95;
			}
			else {
				outValue = 1.0;
			}
		} 
		else if (badCount > goodCount && badCount-goodCount < 3) {
			if (otherCount == 0) {
				outValue = 0.7;
			}
			else if (otherCount == 1) {
				outValue = other[1] == 1 ? 0.6 : 0.8;
			}
			else {
				outValue = 0.8;
			}
		}
		else if (goodCount > badCount && goodCount-badCount >= 3) {
			if (otherCount == 0) {
				outValue = 0.15;
			}
			else if (otherCount == 1) {
				outValue = other[1] == 1 ? 0.35 : 0.45;
			}
			else {
				outValue = 0.5;
			}
		}
		else if (goodCount > badCount && goodCount-badCount < 3) {
			if (otherCount == 0) {
				outValue = 0.15;
			}
			else if (otherCount == 1) {
				outValue = other[1] == 1 ? 0.35 : 0.6;
			}
			else {
				outValue = 0.65;
			}
		}
		else if (goodCount == badCount) {
			if (otherCount == 0) {
				outValue = 0.5;
			}
			else if (otherCount == 1) {
				outValue = other[1] == 1 ? 0.45 : 0.65;
			}
			else {
				outValue = 0.8;
			}
		}
		
		return outValue;
	}
	
	private void getAllArraysInOne() throws Exception {
		if (this.tmpArray.length != (this.goodWords.length + this.badWords.length + this.otherElements.length)) {
			throw new Exception();
		}
		int index = 0;
		int tmpIndex = 0;
		for (int i=0; i<badWords.length; i++) {
			this.tmpArray[i] = this.badWords[i];
			index = i;
		}
		index++;
		for (int i=0; i<goodWords.length; i++) {
			tmpArray[index+i] = goodWords[i];
			tmpIndex = index + i;
		}
		tmpIndex++;
		for (int i=0; i<otherElements.length; i++) {
			tmpArray[tmpIndex+i] = otherElements[i];
		}
	}
	
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		Hashtable<double[], Double> tmp = new Hashtable<double[], Double>(); 
//		double[] tmp1 = new double[] {1.0, 0.0};
//		double tmp1Value = 1.0;
//		double[] tmp2 = new double[] {1.0, 1.0};
//		double tmp2Value = 1.0;
//		tmp.put(tmp1, tmp1Value);
//		tmp.put(tmp2, tmp2Value);
////		
////		java.util.Iterator<double[]> it = tmp.keySet().iterator();
////		
////		while(it.hasNext()) {
////			double[] doubleX = it.next();
////			for (double el : doubleX) {
////					System.out.println(el);
////			}
////			double val = tmp.get(doubleX);
////			System.out.println(val);
////		}
//
////		System.out.println(tmp.containsKey(new double[]{1.0, 0.0}));
//		for (double[] el : x) {
//			for (double elem : el)
//				System.out.println(elem);
//		}
		FlowVariables fv = new FlowVariables();
		LearningVector lv = new LearningVector(fv);
		
		lv.setArraysWithSamples();
		HashMap<double[], Double> hash = lv.getHash();
		java.util.Iterator<double[]> it = hash.keySet().iterator();
		double val = 0.0;
		
		System.out.println("SIZE: " + hash.size());
		
		while(it.hasNext()) {
			double[] doubleX = it.next();
			for (double el : doubleX) {
					System.out.print(el + " | ");
			}
			val = hash.get(doubleX);
			System.out.println("VALUE: " + val );
			System.out.println("");
		}
	}

}
