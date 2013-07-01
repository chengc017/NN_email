package com.elka.nn;

import java.util.Random;

public class Neuron {
	private double weights[]; // wagi wejscia neuronu
	private double weightsChange[]; // wektor na zmiane wagi w procesie uczenia
	private double x[]; // wektor wejsciowy (trzeba uwzglednic polaryzacje, wiec
						// z gory rozmiar o 1 wiekszy niz sam wektor)

	// private double diffYD; // zmienna przechowujaca roznice y-d

	private double u; // suma wagi * wektor_wejsciowy
	private double y; // wyjscie neuronu (juz po przetworzeniu)

	public final double BETA = 1.0; // stala dla funkcji unipolarnej

	// public final double LEARN_CONST = 0.07; // stala uczenia sie

	public Neuron(final int inputSize) {
		this.y = 0;
		this.x = new double[inputSize];
		this.weightsChange = new double[x.length];
	}

	public void setRandomWeights() {
		Random generator = new Random();
		weights = new double[x.length];
		for (int i = 0; i < weights.length; i++) {
			double tmp = generator.nextDouble();
			weights[i] = tmp;
		}
	}

	public void setX(double[] input) {
		x[0] = 1.0; // polaryzacja
		for (int i = 0; i < input.length; i++) {
			x[i + 1] = input[i];
		}
	}

	public double getX(int i) {
		return x[i];
	}

	public int getWeightsSize() {
		return weights.length;
	}

	public double getWeight(int i) {
		return weights[i];
	}

	public double getWeightChange(int i) {
		return weightsChange[i];
	}

	public void setWeightChange(int i, double in) {
		this.weightsChange[i] = this.weightsChange[i] + in;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setYZero() {
		this.y = 0;
	}

	public double getU() {
		return u;
	}

	public void uniActiveFunction(double arg) {
		this.y = 1 / (1 + Math.pow(Math.E, -BETA * arg));
	}

	public double derivUniActiveFunction() {
		return BETA * y * (1 - y);
	}

	public void biActiveFunction(double arg) {
		this.y = Math.tanh(BETA * arg);
	}

	public double derivBiActiveFunction() {
		return BETA * (1 - Math.pow(y, 2));
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

	public void updateWeights(double learn_rate) {
		for (int i = 0; i < weights.length; i++) {
			weights[i] = weights[i] + (-learn_rate * weightsChange[i]);
		}
	}

	public void setWeightsChangeToZero() {
		for (int i = 0; i < weightsChange.length; i++) {
			weightsChange[i] = 0;
		}
	}

	@Override
	public String toString() {
		String output = "w: \n";
		for (int i = 0; i < weights.length; i++) {
			output += weights[i] + " ";
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
