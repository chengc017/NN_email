package com.elka.nn;

import java.util.List;
import java.util.ArrayList;

public class MinKierunkowa {

	private double firstParam; 	// a
	private double secParam;	// b
	private NeuralNet Network;
	private ArrayList<Double>[] dataList;	// tu zeby mi ladnie iterowa³ w petli po kolejnych próbkach ;) <-- @TODO
	
	public MinKierunkowa(NeuralNet NetCopy, double alfa) {
		this.firstParam = 0.0;
		this.secParam = 1E-5;
		this.Network = NetCopy;
	}
	
	private double goForwardMinKierunkowa(double[] data, double param) {
		//Network.makeOriginalWeightsCopy();			// zeby mi oryginalne wagi nie zniknely
		Network.getOriginalWeights();
		Network.updateCopyWeightsInLayers(param);
		return Network.goThroughLearning(data);
	}
	
	private double getParamOfMinKierunkowa(double[] data) {
		Network.makeOriginalWeightsCopy();			// zeby mi oryginalne wagi nie zniknely
		double ff1 = goForwardMinKierunkowa(data, this.firstParam);
		double ff2 = goForwardMinKierunkowa(data, this.secParam);
		while (ff1 > ff2) {
			firstParam = secParam;
			secParam = 2*secParam;
			ff1 = goForwardMinKierunkowa(data, this.firstParam);
			ff2 = goForwardMinKierunkowa(data, this.secParam);
		}
		
		double al1 = secParam - 0.613*(secParam-firstParam);
		double al2 = 0.613*(secParam-firstParam)+firstParam;
		Network.getOriginalWeights();
		for (int i=0; i<15; i++) {
			ff1 = goForwardMinKierunkowa(data, al2);
			ff2 = goForwardMinKierunkowa(data, al1);
			if (ff1 > ff2) {
				secParam = al2;
				al2 = al1;
				al1 = 0.613*firstParam+(1-0.613)*secParam;
			} 
			else {
				firstParam = al1;
				al1 = al2;
				al2 = 0.613*secParam+(1-0.613)*firstParam;
			}
			Network.getOriginalWeights();
		}
		return secParam;
	}
	
	
	

	

	
	
	
	
	
	
	
	
	// Trzeba stworzyc w NeuraltNet procedure, ktora uaktualnia te WeightsCopy:
	// dla ka¿dej warstwy: dla ka¿dego neuronu w warstwie trzeba zachowaæ w_i, potem pod w_i
	// wgraæ wcopy_i i uaktualnic te w_i poprzez ten alfa * -weightsChange. (bo weightsChange = -p_i)
	// Mamy wtedy ladne podstawione_tymczasowe w_i, liczymy sobie spokojnie te bledy w funkcji ff
	// (tylko tez tam w goForward trzeba by zwracac bledy zeby moc porownac) i liczymy dalej ten wspolczynnik.
	
	// gradienty sprzezone to juz chyba bedize troche latwiej :)
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
