package com.elka.nn;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class NeuralNet {

	// public final int NUM_LAYERS = 2;

	// TODO Zrobic wywylywanie sieci z parametrami okreslajacymi ilosc warstw, i
	// ile input itp
	/*
	 * private final int NUM_NEUR_HID = 2; private final int NUM_NEUR_OUT = 1;
	 * private final int NODES_NUM = 6; // wektor 5 danych + 1 polaryzacja
	 * private final int SINX_NODES = 2; // jedna dana + 1 polaryzacja
	 */
	private float error;
	private float prevError;
	private float learnRate;

	private final float KW = 1.01f;
	private final float PD = 0.7f;
	private final float PI = 1.01f;
	
	private static final int TO = 10;
	public static final float DX = 15.0f/(TO-1);

	protected Layer[] layers;

	/**
	 * 
	 * @param numOfLayers
	 *            - ilosc warstw
	 * @param numNeurons1
	 *            - ilosc neuronow warstwy 1 (ukrytej)
	 * @param numNeuron1Size
	 *            - rozmiar wektora wejsciowego (zawsze o 1 wiekszy bo
	 *            polaryzacja - czyli dla wektora 5 elem. trzeba tu ustawic na
	 *            6)
	 * @param numNeurons2
	 *            - ilosc neuronow warstwy 2 (wyjsciowej) Rozmiar wektora
	 *            warstwy wyjsciowej = ilosc neuronow warstw 1 (numNeurons1 +
	 *            1), +1 bo polaryzacja
	 */

	public NeuralNet(float learnRate, final int numOfLayers,
			final int numNeurons1, final int numNeuron1Size,
			final int numNeurons2) {
		this.error = 0;
		this.prevError = 0;
		this.learnRate = learnRate;
		layers = new Layer[numOfLayers];
		layers[0] = new Layer(numNeurons1, numNeuron1Size); // warstwa ukryta
		layers[1] = new Layer(numNeurons2, numNeurons1 + 1); // warstwa wyjsciowa (tyle ile w ukrytej + 1 (polaryzacja)
	}

	public void learnNet(float[] inputX, float target) {
		layers[0].startLayer(inputX, true);
		layers[1].startLayer(layers[0].getLayerOutput(), false); // przekazuje wyjscie warstwy ukrytej
																 // na wejscie warstwy wyjsciowej
		layers[1].setLastLayersError(target); // ustawiam sobie odpowiednie
												// zmiany wag w warstwie
												// wyjsciowej (ale NIE ZMIENIAM)
		layers[0].setLayersError(layers[1], target); // ustawiam sobie
														// odpowiednie zmiany
														// wag w warstwie
														// ukrytej (ale NIE
														// ZMIENIAM)
	}
	
	public float goForward(float[] inputX) {
		layers[0].startLayer(inputX, true);
		layers[1].startLayer(layers[0].getLayerOutput(), false);
		return layers[1].getLastSolution();
	}

	public float goThroughLearning(float[] data) {
		// System.out.println(NN.toString());
		/*
		if (x.length != data.length) {
			System.out.println("Rozmiar wektora wejsciowego != wektora z danymi");
			return -1;
		}*/
		float[] x = new float[1];
		setErrorZero();
		for (int i = 0; i < data.length; i++) {
			x = new float[] { 0.1f+DX*i };
			//learnNet(x, data[i]);
			// System.out.println(NN.toStringX());
			setError(goForward(x), data[i]);
			/*System.out.println("Iteracja zew: " + k + " Iteracja wew: "
					+ i + " " + " Wart. otrzymana: "
					+ getLayerLastSolution() + " Wart. ocze: "
					+ data[i]);
			*/
		}
		return getError();
	}

	public float goThroughLearning(Vector<float[]> dVec, float[] data) {
		// System.out.println(NN.toString());
		/*
		if (x.length != data.length) {
			System.out.println("Rozmiar wektora wejsciowego != wektora z danymi");
			return -1;
		}*/
		float[] x = new float[5];
		setErrorZero();
		for (int i = 0; i < data.length; i++) {
			x = dVec.get(i);
			//learnNet(x, data[i]);
			// System.out.println(NN.toStringX());
			setError(goForward(x), data[i]);
			/*System.out.println("Iteracja zew: " + k + " Iteracja wew: "
					+ i + " " + " Wart. otrzymana: "
					+ getLayerLastSolution() + " Wart. ocze: "
					+ data[i]);
			*/
		}
		return getError();
	}
	
	/*
	public float goForwardMinKierunkowa(float[] inputX, float alfa) {
		layers[0].startLayer(inputX, true);
		layers[1].startLayer(layers[0].getLayerOutput(), false);
	}
	*/
	
	public void setError(float y, float d) { // blad sredniokwadratowy sieci
		this.error = this.error + 0.5f*((y - d) * (y - d));
	}

	public void setErrorZero() {
		this.error = 0;
	}

	public float getError() {
		return error;
	}

	public float getLayerLastSolution() {
		return layers[1].getLastSolution();
	}

	public void setWeightsZero() {
		for (Layer l : layers) {
			l.setNeuronsWeightsToZero();
		}
	}
	
	public void updateWeightsInLayersDIRECT() {
		layers[1].updateWeightsInNeuronsLayerDIRECT(this.learnRate);
		layers[0].updateWeightsInNeuronsLayerDIRECT(this.learnRate);
	}

	public void updateWeightsInLayers() { // jako argument dac wart wsp uczenia
											// sie
		// for (Layer l : layers) {
		// l.updateWeightsInNeuronsLayer(this.learnRate);
		// }
		// for (int i=layers.length;i<0;i--) {

		// }
		/*layers[1].updateWeightsInNeuronsLayer(this.learnRate);
		layers[0].updateWeightsInNeuronsLayer(this.learnRate);*/
		layers[1].updateWeightsInNeuronsLayer();
		layers[0].updateWeightsInNeuronsLayer();
	}
	
	public void updateWeightsInLayersGradSprzez(float paramGradSprzez) { // jako argument dac wart wsp uczenia
		// sie
		// for (Layer l : layers) {
		// l.updateWeightsInNeuronsLayer(this.learnRate);
		// }
		// for (int i=layers.length;i<0;i--) {

		// }
		/*layers[1].updateWeightsInNeuronsLayerGradSprzez(this.learnRate, paramGradSprzez);
		layers[0].updateWeightsInNeuronsLayerGradSprzez(this.learnRate, paramGradSprzez);*/
		layers[1].updateWeightsInNeuronsLayerGradSprzez(paramGradSprzez);
		layers[0].updateWeightsInNeuronsLayerGradSprzez(paramGradSprzez);
	}
	
	public void updateCopyWeightsInLayers(float alfa) {
		for (Layer l : layers) {
			l.updateCopyWeightsInNeuronsLayer(alfa);
		}
	}
	
	public void getOriginalWeights() {
		for (Layer l : layers) {
			l.getWeightsCopyToWeightsInNeuronsLayer();
		}
	}
	
	public void makeOriginalWeightsCopy() {
		for (Layer l :layers) {
			l.makeNeuronCopy();
		}
	}
	
	public void makeOriginalWeightsChangeCopy() {
		for (Layer l : layers) {
			l.makeWeightsChangeNeuronCopy();
		}
	}

	public void updateLearnRate() {
		if (this.error > (Math.sqrt(KW) * this.prevError)) {
			this.learnRate = PD * this.learnRate;
		} else {
			this.learnRate = PI * this.learnRate;
		}
		//this.learnRate = (Math.sqrt(this.error) > KW*this.prevError) ? PD*this.learnRate : PI*this.learnRate; 
	}

	public void setPrevError() {
		this.prevError = this.error;
	}

	public float getLearnRate() {
		return this.learnRate;
	}
	
	public void setLearnRate(float learnRate) {
		this.learnRate = learnRate;
	}
	
	public List<Float> getWeightsChangeTable() {
		List<Float> tmp = new ArrayList<Float>();
		for (Layer l : layers) {
			tmp.addAll(l.getWeightsChangeLayer());
		}
		return tmp;
	}
	
	public List<Float> getPElementTable() {
		List<Float> tmp = new ArrayList<Float>();
		for (Layer l : layers) {
			tmp.addAll(l.getPElementLayer());
		}
		return tmp;
	}
	
	/*-------------------------------------------------------------------------------*/
	/*------------- USTALANIE WAG POCZATKOWYCH NA SZTYWNO DO TESTOW------------------*/
	/* Ustalenie wag @newWeights w warstwie @indexL i neuronie @indexN---------------*/
	public void setWeightsByParamInLayer(int indexL, int indexN, float[] newWeights) {
		layers[indexL].neurons[indexN].setWeightsByParam(newWeights);
	}
	

	@Override
	public String toString() {
		String out;
		out = "WAGI NA WEJSCIU DANEJ ITERACJI ZEWNETRZNEJ: \n";
		out += layers[0].toString("Ukryta: ");
		out += layers[1].toString("Wyjsciowa: ");
		return out;
	}

	public String toStringWCH() {
		String out;
		out = layers[0].toStringWCH("Ukryta: ");
		out += layers[1].toStringWCH("Wyjsciowa: ");
		return out;
	}

	public String toStringX() {
		String out;
		out = layers[0].toStringX("Ukryta: ");
		out += layers[1].toStringX("Wyjsciowa: ");
		return out;
	}
}
