package com.elka.nn;

import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class MinKierunkowa {

	private float firstParam; 	// a
	private float secParam;	// b
	private NeuralNet Network;
	private ArrayList<Float>[] dataList;	// tu zeby mi ladnie iterowa� w petli po kolejnych pr�bkach ;) <-- @TODO
	
	public MinKierunkowa(NeuralNet NetCopy) {
		this.firstParam = 0.0f;
		this.secParam = 1E-5f;
		this.Network = NetCopy;
	}
	
	private float goForwardMinKierunkowa(float[] data, float param) {
		//Network.makeOriginalWeightsCopy();			// zeby mi oryginalne wagi nie zniknely
		Network.getOriginalWeights();
		Network.updateCopyWeightsInLayers(param);
		return Network.goThroughLearning(data);
	}
	
	public float getParamOfMinKierunkowa(float[] data) {
		Network.makeOriginalWeightsCopy();			// zeby mi oryginalne wagi nie zniknely
		firstParam = 0.0f;
		secParam = 1E-5f;
		float ff1 = goForwardMinKierunkowa(data, this.firstParam);
		float ff2 = goForwardMinKierunkowa(data, this.secParam);
		while (ff1 > ff2) {
			firstParam = secParam;
			secParam = 2*secParam;
			ff1 = goForwardMinKierunkowa(data, this.firstParam);
			ff2 = goForwardMinKierunkowa(data, this.secParam);
		}
		
		float al1 = secParam - 0.613f*(secParam-firstParam);
		float al2 = 0.613f*(secParam-firstParam)+firstParam;
		Network.getOriginalWeights();
		for (int i=0; i<15; i++) {
			ff1 = goForwardMinKierunkowa(data, al2);
			ff2 = goForwardMinKierunkowa(data, al1);
			if (ff1 > ff2) {
				secParam = al2;
				al2 = al1;
				al1 = 0.613f*firstParam+(1f-0.613f)*secParam;
			} 
			else {
				firstParam = al1;
				al1 = al2;
				al2 = 0.613f*secParam+(1f-0.613f)*firstParam;
			}
			Network.getOriginalWeights();
		}
		Network.getOriginalWeights();
		return secParam;
	}
	
	public float getParamOfMinKierunkowa(Vector<float[]> dVec, float[] data) {
		Network.makeOriginalWeightsCopy();			// zeby mi oryginalne wagi nie zniknely
		firstParam = 0.0f;
		secParam = 1E-9f;//0.001;
		float ff1 = goForwardMinKierunkowa(dVec, data, this.firstParam);
		float ff2 = goForwardMinKierunkowa(dVec, data, this.secParam);
		while (ff1 > ff2) {
			firstParam = secParam;
			secParam = 2*secParam;
			ff1 = goForwardMinKierunkowa(dVec, data, this.firstParam);
			ff2 = goForwardMinKierunkowa(dVec, data, this.secParam);
		}
		
		float al1 = secParam - 0.613f*(secParam-firstParam);
		float al2 = 0.613f*(secParam-firstParam)+firstParam;
		Network.getOriginalWeights();
		for (int i=0; i<15; i++) {
			ff1 = goForwardMinKierunkowa(dVec, data, al2);
			ff2 = goForwardMinKierunkowa(dVec, data, al1);
			if (ff1 > ff2) {
				secParam = al2;
				al2 = al1;
				al1 = 0.613f*firstParam+(1f-0.613f)*secParam;
			} 
			else {
				firstParam = al1;
				al1 = al2;
				al2 = 0.613f*secParam+(1f-0.613f)*firstParam;
			}
			Network.getOriginalWeights();
		}
		return secParam;
	}
	
	private float goForwardMinKierunkowa(Vector<float[]> dVec, float[] data, float param) {
		//Network.makeOriginalWeightsCopy();			// zeby mi oryginalne wagi nie zniknely
		Network.getOriginalWeights();
		Network.updateCopyWeightsInLayers(param);
		return Network.goThroughLearning(dVec, data);
	}

	

	
	
	
	
	
	
	
	
	// Trzeba stworzyc w NeuraltNet procedure, ktora uaktualnia te WeightsCopy:
	// dla ka�dej warstwy: dla ka�dego neuronu w warstwie trzeba zachowa� w_i, potem pod w_i
	// wgra� wcopy_i i uaktualnic te w_i poprzez ten alfa * -weightsChange. (bo weightsChange = -p_i)
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
