package com.elka.nn;

import java.util.Random;

public class Neuron {
	private float weights[]; // wagi wejscia neuronu
	private float weightsChange[]; // wektor na zmiane wagi w procesie uczenia	---> g
	private float weightsCopy[]; // kopie wag neuronu (do minimalizacji kierunkowej)
	private float weightsChangeCopy[];
	private float p[];
	private float p1[];
	private float x[]; // wektor wejsciowy (trzeba uwzglednic polaryzacje, wiec
						// z gory rozmiar o 1 wiekszy niz sam wektor)

	// private float diffYD; // zmienna przechowujaca roznice y-d

	private float u; // suma wagi * wektor_wejsciowy
	private float y; // wyjscie neuronu (juz po przetworzeniu)

	public final float BETA = 1.0f; // stala dla funkcji unipolarnej

	// public final float LEARN_CONST = 0.07; // stala uczenia sie

	public Neuron(final int inputSize) {
		this.y = 0;
		this.x = new float[inputSize];
		this.weightsChange = new float[x.length];
		this.weightsCopy = new float[x.length];
		this.p = new float[x.length];
		this.p1 = new float[x.length];
		this.weights = new float[x.length];
	}

	public void setRandomWeights() {
		Random generator = new Random();
		weights = new float[x.length];
		for (int i = 0; i < weights.length; i++) {
			float tmp = generator.nextFloat();
			//weights[i] = tmp*5;
			weights[i] = 0.2f*(-0.5f+tmp)*5f;
		}
	}

	public void setX(float[] input) {
		x[0] = 1.0f; // polaryzacja
		for (int i = 0; i < input.length; i++) {
			x[i + 1] = input[i];
		}
	}

	public float getX(int i) {
		return x[i];
	}

	public int getWeightsSize() {
		return weights.length;
	}

	public float getWeight(int i) {
		return weights[i];
	}

	public float getWeightChange(int i) {
		return weightsChange[i];
	}

	public void setWeightChange(int i, float in) {
		this.weightsChange[i] = this.weightsChange[i] + in;
	}

	public float getY() {
		return this.y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setYZero() {
		this.y = 0;
	}

	public float getU() {
		return u;
	}

	public void uniActiveFunction(float arg) {
		this.y = (float) (1f / (1f + Math.pow(Math.E, -BETA * arg)));
	}

	public float derivUniActiveFunction() {
		return BETA * y * (1 - y);
	}

	public void biActiveFunction(float arg) {
		this.y = (float) Math.tanh(BETA * arg);
	}

	public float derivBiActiveFunction() {
		return (float) (BETA * (1 - Math.pow(y, 2)));
	}

	public void setU() {
		this.u = 0;
		for (int i = 0; i < x.length; i++) {
			this.u = this.u + this.x[i] * this.weights[i]; // u = waga * wektor x
		}
	}

	public void setZeroToU() {
		this.u = 0;
	}
	
	public void setPAsG() {
		for (int i=0; i<p.length; i++) {
			p[i] = -weightsChange[i];
		}
	}
	
	public void makeCopyOFP() {
		for (int i=0; i<p.length; i++) {
			p1[i] = p[i];
		}
	}
	
	public void setPAsGWithGradSprzez(float paramGradSprzez) { 	// param = beta * p1[i]
		for (int i=0; i<p.length; i++) {
			p[i] = -weightsChange[i] + paramGradSprzez * p1[i];
		}
	}

//	public float getWeightsChange(int index) {
//		float[] tmp = new float[weightsChange.length];
//		for (int i=0; i<weightsChange.length; i++) {
//			tmp[i] = weightsChange[i];
//		}
//		return weightsChange[index];
//	}
	
	public float getPElement(int index) {
//		float[] tmp = new float[p.length];
//		for (int i=0; i<p.length; i++) {
//			tmp[i] = p[i];
//		}
//		return tmp;
		return p[index];
	}
	
	public void updateWeights(float learn_rate) {
		for (int i = 0; i < weights.length; i++) {
			weights[i] = weights[i] + learn_rate * p[i];
		}
	}

	public void setWeightsChangeToZero() {
		for (int i = 0; i < weightsChange.length; i++) {
			weightsChange[i] = 0;
		}
	}
	
	public void makeWeightsCopy() {
		for (int i=0; i<weights.length; i++) {
			this.weightsCopy[i] = this.weights[i];
		}
	}
	
	public void getWeightsCopyToWeights() {
		for (int i=0; i<weights.length; i++) {
			this.weights[i] = this.weightsCopy[i];
		}
	}
	
	public void makeWeightsChangeCopy() {
		for (int i=0; i<weightsChangeCopy.length; i++) {
			this.weightsChangeCopy[i] = this.weightsChange[i];
		}
	}
	
	public void getWeightsChangeCopyToWeightsChange() {
		for (int i=0; i<weightsChange.length; i++) {
			this.weightsChange[i] = this.weightsChangeCopy[i];
		}
	}
	
	public float getWeightsCopy(int i) {
		return this.weightsCopy[i];
	}
	
	public void updateWeightsOnCopy(float alfa) {	// robie to na w_i bo ono jest podpiete pod goForward
		for (int i=0; i<weights.length; i++) {
			this.weights[i] = this.weights[i]+alfa*(-this.weightsChange[i]); // @-weightsChange = p
		}
	}
	
	
	/*-------------------------------------------------------------------------------*/
	/*------------- USTALANIE WAG POCZATKOWYCH NA SZTYWNO DO TESTOW------------------*/
	
	public void setWeightsByParam(float[] newWeights) {
		if (this.weights.length != newWeights.length) {
			System.out.println("BLAD! Rozny rozmiar wag");
		}
		for (int i=0; i<weights.length; i++) {
			this.weights[i] = newWeights[i];
		}
	}
	
	@Override
	public String toString() {
		//String output = "w: \n";
		String output = "";
		for (int i = 0; i < weights.length; i++) {
			output += "W[" + i + "] " + weights[i] + " \n";
		}
		return output;
	}

	public String toStringWCH() {
		String output = "\n w_change: \n";
		for (int i = 0; i < weightsChange.length; i++) {
			output += weightsChange[i] + " ";
		}

		return output;
	}

	public String toStringGETX() {
		String output = "\n X: \n";
		for (int i = 0; i < this.x.length; i++) {
			output += "X" + i + ": ";
			output += this.x[i] + " ";
			output += "WEIGHT_CH: ";
			output += this.weightsChange[i] + " ";
		}
		output += "U= " + this.u + " ";
		output += "Y= " + this.y + " \n";
		return output;
	}
}
