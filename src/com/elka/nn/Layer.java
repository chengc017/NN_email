package com.elka.nn;

public class Layer {

	protected Neuron[] neurons;

	/**
	 * Konstruktor warstwy (tablica neuronow)
	 * 
	 * @param size
	 *            okresla ile neuronow jest w danej warstwie
	 * @param inputSize
	 *            okresla ile wejsc jest do kazdego neuronu
	 */
	public Layer(final int size, final int inputSize) {
		neurons = new Neuron[size];
		for (int i = 0; i < neurons.length; i++) {
			neurons[i] = new Neuron(inputSize);
			neurons[i].setRandomWeights();
		}
	}

	public void startLayer(double[] inputX, boolean tryb) {
		for (Neuron n : neurons) {
			n.setX(inputX);
			n.setU();
			n.setYZero();
			if (tryb == true) {
				n.biActiveFunction(n.getU());
				// n.uniActiveFunction(n.getU());
			} else {
				n.setY(n.getU());
			}

			// n.biActiveFunction(n.getU());
		}
	}

	public double getLastSolution() {
		return neurons[0].getY();
	}

	public double[] getLayerOutput() {
		double[] out = new double[neurons.length]; //
		for (int i = 0; i < out.length; i++) {
			out[i] = neurons[i].getY();
		}
		return out;
	}

	/**
	 * Uzyskanie bledu ostatniej (wyjsciowej) warstwy
	 * 
	 * @param target
	 *            oczekiwane wyjscie
	 */
	public void setNeuronsWeightsToZero() {
		for (Neuron n : neurons) {
			n.setWeightsChangeToZero();
		}
	}

	public void setLastLayersError(double target) {
		for (Neuron n : neurons) {
			for (int i = 0; i < n.getWeightsSize(); i++) {
				if (i == 0) {
					n.setWeightChange(i, (n.getY() - target) * 1.0); // usuniete
																		// 2*
																		// przed
																		// nawaisem,
																		// tak
																		// samo
																		// u
																		// dolu
				} else {
					n.setWeightChange(i, (n.getY() - target) * n.getX(i)); // to
																			// X
																			// to
																			// po
																			// prostu
																			// juz
																			// obliczone
																			// wejscie
																			// tego
																			// neuronu
																			// (bo
																			// to
																			// jest
				} // tak naprawde wyjscie neuronu poprzedniej warstwy (czyli
					// jakby y z poprzedniej
					// warstwy (y = funkcja(u) gdzie u = suma (x_j * w_ij)
			}
		}
	}

	/**
	 * @TODO trzeba zmienic wzor na blad warstwy ukrytej
	 */

	public void setLayersError(Layer outputLayer, double target) { // usuniete
																	// 2* w
																	// zmiennej
																	// in
		for (Neuron neuOut : outputLayer.neurons) {
			for (int i = 0; i < this.neurons.length; i++) {
				for (int j = 0; j < this.neurons[i].getWeightsSize(); j++) {
					// double in =
					// 2*(neuOut.getY()-target)*(neuOut.getWeight(i+1)*neurons[i].derivUniActiveFunction()*neurons[i].getX(j));
					// // i+1 bo zerowa waga dla polaryzacji, a my mamy jakby
					// zerowy neuron jako pierwszy
					double in = (neuOut.getY() - target)
							* (neuOut.getWeight(i + 1)
									* neurons[i].derivBiActiveFunction() * neurons[i]
										.getX(j));
					this.neurons[i].setWeightChange(j, in);
				}
			}
		}
	}

	public void updateWeightsInNeuronsLayer(double learn_rate) { // tu przez
																	// argument
																	// przekazywac
																	// wartosc
		for (Neuron n : neurons) { // wsp uczenia
			n.updateWeights(learn_rate);
		}
	}

	public String toString(String layersName) {
		String output = layersName + "\n";
		for (Neuron neuron : neurons) {
			output += neuron + "\n";
		}
		return output;
	}

	public String toStringWCH(String layersName) {
		String output = layersName + "\n";
		for (Neuron neuron : neurons) {
			output += neuron.toStringWCH();
		}
		return output;
	}

	public String toStringX(String layersName) {
		String output = layersName + "\n";
		for (Neuron neuron : neurons) {
			output += neuron.toStringGETX();
		}
		return output;
	}

	public static void main(String[] args) {
		Layer hidden = new Layer(2, 6);
		Layer output = new Layer(1, 3);
		double[] input = new double[] { 1.0, 1.0, 1.0, 0.0, 0.0 };

		// hidden.startLayer(input);
		double[] outVec = hidden.getLayerOutput();
		for (int i = 0; i < outVec.length; i++) {
			System.out.println(outVec[i]);
		}
		// output.startLayer(outVec);
		System.out.println(output.toString("wyjsciowa"));
	}
}
