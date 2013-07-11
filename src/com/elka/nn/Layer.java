package com.elka.nn;

import java.util.ArrayList;
import java.util.List;

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
			//neurons[i].setRandomWeights();
		}
	}

	public void startLayer(float[] inputX, boolean tryb) {
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

	public float getLastSolution() {
		return neurons[0].getY();
	}

	public float[] getLayerOutput() {
		float[] out = new float[neurons.length]; //
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

	
	// tu jest brane n.getY bo wyj�ciowy neuron jest tylko jeden, dlatego brane jest jego Y.
	public void setLastLayersError(float target) {
		for (Neuron n : neurons) {
			for (int i = 0; i < n.getWeightsSize(); i++) {
				if (i == 0) {
					n.setWeightChange(i, (n.getY() - target) * 1.0f); 
					// usuniete 2* przed nawaisem, tak samo u dolu
				} else {
					n.setWeightChange(i, (n.getY() - target) * n.getX(i)); 
				} 	// to X to po prostu juz obliczone wejscie tego neuronu (bo to jest
					// tak naprawde wyjscie neuronu poprzedniej warstwy (czyli
					// jakby y z poprzedniej
					// warstwy (y = funkcja(u) gdzie u = suma (x_j * w_ij a funkcja = tanh)
			}
		}
	}
	
/*	public void setLastLayersError(float target, float paramGradSprzez) {		// paraGradSprzezony = beta*p1[i]
		for (Neuron n : neurons) {
			for (int i = 0; i < n.getWeightsSize(); i++) {
				if (i == 0) {
					n.setWeightChange(i, (n.getY() - target) * 1.0 + paramGradSprzez); 
					// usuniete 2* przed nawaisem, tak samo u dolu
				} else {
					n.setWeightChange(i, (n.getY() - target) * n.getX(i) + paramGradSprzez); 
				} 	// to X to po prostu juz obliczone wejscie tego neuronu (bo to jest
					// tak naprawde wyjscie neuronu poprzedniej warstwy (czyli
					// jakby y z poprzedniej
					// warstwy (y = funkcja(u) gdzie u = suma (x_j * w_ij)
			}
		}
	}*/

	/**
	 * @TODO trzeba zmienic wzor na blad warstwy ukrytej
	 */

	public void setLayersError(Layer outputLayer, float target) { // usuniete 2* w zmiennej in
		for (Neuron neuOut : outputLayer.neurons) {
			for (int i = 0; i < this.neurons.length; i++) {
				for (int j = 0; j < this.neurons[i].getWeightsSize(); j++) {
					// float in =
					// 2*(neuOut.getY()-target)*(neuOut.getWeight(i+1)*neurons[i].derivUniActiveFunction()*neurons[i].getX(j));
					// // i+1 bo zerowa waga dla polaryzacji, a my mamy jakby
					// zerowy neuron jako pierwszy
					float in = (neuOut.getY() - target) * (neuOut.getWeight(i + 1)
							* neurons[i].derivBiActiveFunction() * neurons[i].getX(j));
					this.neurons[i].setWeightChange(j, in);
				}
			}
		}
	}
	
/*	public void setLayersError(Layer outputLayer, float target, float paramGradSprzez) { // usuniete 2* w zmiennej in
		for (Neuron neuOut : outputLayer.neurons) {
			for (int i = 0; i < this.neurons.length; i++) {
				for (int j = 0; j < this.neurons[i].getWeightsSize(); j++) {
					// float in =
					// 2*(neuOut.getY()-target)*(neuOut.getWeight(i+1)*neurons[i].derivUniActiveFunction()*neurons[i].getX(j));
					// // i+1 bo zerowa waga dla polaryzacji, a my mamy jakby
					// zerowy neuron jako pierwszy
					float in = (neuOut.getY() - target) * (neuOut.getWeight(i + 1)
							* neurons[i].derivBiActiveFunction() * neurons[i].getX(j));
					this.neurons[i].setWeightChange(j, in + paramGradSprzez);
				}
			}
		}
	}*/

	public void updateWeightsInNeuronsLayer(float learn_rate) { // tu przez argument przekazywac wartosc
		for (Neuron n : neurons) { 								 // wsp uczenia
			n.makeCopyOFP();
			n.setPAsG();
			n.updateWeights(learn_rate);
		}
	}
	
	public void updateWeightsInNeuronsLayerGradSprzez(float learn_rate, float paramGradSprzez) { // tu przez argument przekazywac wartosc
		for (Neuron n : neurons) { 								 // wsp uczenia
			n.makeCopyOFP();
			n.setPAsGWithGradSprzez(paramGradSprzez);
			n.updateWeights(learn_rate);
		}
	}
	
	public void makeNeuronCopy() {
		for (Neuron n : neurons) {
			n.makeWeightsCopy();
		}
	}
	
	public void makeWeightsChangeNeuronCopy() {
		for (Neuron n : neurons) {
			n.makeWeightsChangeCopy();
		}
	}
	
	public void updateCopyWeightsInNeuronsLayer(float alfa) {
		for (Neuron n : neurons) {
			n.updateWeightsOnCopy(alfa);
		}
	}
	
	public void getWeightsCopyToWeightsInNeuronsLayer() {
		for (Neuron n : neurons) {
			n.getWeightsCopyToWeights();
		}
	}
	
	
	public List<Float> getWeightsChangeLayer() {
//		float[] tmp = new float[neurons.length*neurons[0].getWeightsSize()];   // robie miejsce na tablice wag dla kazdego neuronu
		List<Float> tmp = new ArrayList<Float>();
//		for (int i=0; i<neurons.length*neurons[0].getWeightsSize(); i++) {
//			for (Neuron n : neurons) {
//				for (int k=0; k<neurons[0].getWeightsSize(); k++) {
//					tmp[i] = n.getWeightChange(k);
//				}
//			}
//		}
		for (Neuron n : neurons) {
			for (int i=0; i<n.getWeightsSize(); i++) {
				tmp.add(n.getWeightChange(i));
			}
		}
		return tmp;
	}
	
	public List<Float> getPElementLayer() {
//		float[] tmp = new float[neurons.length*neurons[0].getWeightsSize()];   // robie miejsce na tablice wag dla kazdego neuronu
//		for (int i=0; i<neurons.length*neurons[0].getWeightsSize(); i++) {
		List<Float> tmp = new ArrayList<Float>();
		for (Neuron n : neurons) {
			for (int k=0; k<n.getWeightsSize(); k++) {
				tmp.add(n.getPElement(k));
			}
		}
//		}
		return tmp;
	}
	
	/*-------------------------------------------------------------------------------*/
	/*------------- USTALANIE WAG POCZATKOWYCH NA SZTYWNO DO TESTOW------------------*/
	
	public void setWeightsByParamInNeurons(float[] newWeights) {
		for (Neuron n : this.neurons) {
			n.setWeightsByParam(newWeights);
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

	/*public static void main(String[] args) {
		Layer hidden = new Layer(2, 6);
		Layer output = new Layer(1, 3);
		float[] input = new float[] { 1.0, 1.0, 1.0, 0.0, 0.0 };

		// hidden.startLayer(input);
		float[] outVec = hidden.getLayerOutput();
		for (int i = 0; i < outVec.length; i++) {
			System.out.println(outVec[i]);
		}
		// output.startLayer(outVec);
		System.out.println(output.toString("wyjsciowa"));
	}*/
}
