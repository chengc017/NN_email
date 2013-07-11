package com.elka.nn;

import java.util.ArrayList;
import java.util.List;

public class GradSprzez {

	private float licz;
	private float mian;
	private float beta;
	private int licznikgs;
	private List<Float> copyG;
	private List<Float> copyP;
	
	
	public GradSprzez() {
		this.licz = 0.0f;
		this.mian = 0.0f;
		this.licznikgs = 0;
		this.beta = 0.0f;
		List<Float> copyG = new ArrayList<Float>();
		List<Float> copyP = new ArrayList<Float>();
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
	
	private float getBeta() {
		return beta;
	}
	
	private void getLicznik(NeuralNet Network) {
		List<Float> tmpLicz = new ArrayList<Float>();
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
