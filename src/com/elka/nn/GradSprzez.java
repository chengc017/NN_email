package com.elka.nn;

import java.util.ArrayList;
import java.util.List;

public class GradSprzez {

	private double licz;
	private double mian;
	private double beta;
	private int licznikgs;
	private List<Double> copyG;
	private List<Double> copyP;
	
	
	public GradSprzez() {
		this.licz = 0.0;
		this.mian = 0.0;
		this.licznikgs = 0;
		this.beta = 0.0;
		List<Double> copyG = new ArrayList<Double>();
		List<Double> copyP = new ArrayList<Double>();
	}
	
	public void makeGradSprzez(NeuralNet Network) {
		if (licznikgs == 0) {
			Network.updateWeightsInLayers();
			licznikgs = 5;
		}
		else {
			getLicznik(Network);
			getMian(Network);
			setBeta();
			Network.updateWeightsInLayersGradSprzez(getBeta());
		}
		makeCopyG(Network);
		licznikgs--;
	}
	
	private void setBeta() {
		beta = licz/mian;
	}
	
	private double getBeta() {
		return beta;
	}
	
	private void getLicznik(NeuralNet Network) {
		List<Double> tmpLicz = new ArrayList<Double>();
		tmpLicz = Network.getWeightsChangeTable();
		licz = 0;
		for (int i=0; i<tmpLicz.size(); i++) {
			licz = licz + (tmpLicz.get(i)*tmpLicz.get(i));
		}
	}
	
	private void getMian(NeuralNet Network) {
		mian = 0;
		for (int i=0; i<copyG.size(); i++) {
			mian = mian + (copyG.get(i)*copyG.get(i));
		}
	}
	
	private void makeCopyG(NeuralNet Network) {
		copyG = Network.getWeightsChangeTable();
	}
	
	private void makeCopyP(NeuralNet Network) {
		copyP = Network.getPElementTable();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
