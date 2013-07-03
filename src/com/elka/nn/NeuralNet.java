package com.elka.nn;

public class NeuralNet {

	// public final int NUM_LAYERS = 2;

	// TODO Zrobic wywylywanie sieci z parametrami okreslajacymi ilosc warstw, i
	// ile input itp
	/*
	 * private final int NUM_NEUR_HID = 2; private final int NUM_NEUR_OUT = 1;
	 * private final int NODES_NUM = 6; // wektor 5 danych + 1 polaryzacja
	 * private final int SINX_NODES = 2; // jedna dana + 1 polaryzacja
	 */
	private double error;
	private double prevError;
	private double learnRate;

	private final double KW = 1.04;
	private final double PD = 0.7;
	private final double PI = 1.05;

	private Layer[] layers;

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

	public NeuralNet(double learnRate, final int numOfLayers,
			final int numNeurons1, final int numNeuron1Size,
			final int numNeurons2) {
		this.error = 0;
		this.prevError = 0;
		this.learnRate = learnRate;
		layers = new Layer[numOfLayers];
		layers[0] = new Layer(numNeurons1, numNeuron1Size); // warstwa ukryta
		layers[1] = new Layer(numNeurons2, numNeurons1 + 1); // warstwa wyjsciowa (tyle ile w ukrytej + 1 (polaryzacja)
	}

	public void learnNet(double[] inputX, double target) {
		layers[0].startLayer(inputX, true);
		layers[1].startLayer(layers[0].getLayerOutput(), false); // przekazuje wyjscie warstwy ukrytej
																 // na wejscie warstwy wyjsciowej
		layers[1].setLastLayersError(target); // ustawiam sobie odpowiednie
												// zmiany wag w warstwie
												// wyjsciowej (ale NIE ZMIENIAM)
		// layers[1].updateWeightsInNeuronsLayer();
		layers[0].setLayersError(layers[1], target); // ustawiam sobie
														// odpowiednie zmiany
														// wag w warstwie
														// ukrytej (ale NIE
														// ZMIENIAM)
		// layers[0].updateWeightsInNeuronsLayer();
	}
	
	public double goForward(double[] inputX) {
		layers[0].startLayer(inputX, true);
		layers[1].startLayer(layers[0].getLayerOutput(), false);
		return layers[1].getLastSolution();
	}

	public double goThroughLearning(double[] data) {
		// System.out.println(NN.toString());
		for (int i = 0; i < data.length; i++) {
			double[] x = new double[] { i };
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
	public double goForwardMinKierunkowa(double[] inputX, double alfa) {
		layers[0].startLayer(inputX, true);
		layers[1].startLayer(layers[0].getLayerOutput(), false);
	}
	*/
	
	public void setError(double y, double d) { // blad sredniokwadratowy sieci
		this.error = this.error + 0.5*((y - d) * (y - d));
	}

	public void setErrorZero() {
		this.error = 0;
	}

	public double getError() {
		return error;
	}

	public double getLayerLastSolution() {
		return layers[1].getLastSolution();
	}

	public void setWeightsZero() {
		for (Layer l : layers) {
			l.setNeuronsWeightsToZero();
		}
	}

	public void updateWeightsInLayers() { // jako argument dac wart wsp uczenia
											// sie
		// for (Layer l : layers) {
		// l.updateWeightsInNeuronsLayer(this.learnRate);
		// }
		// for (int i=layers.length;i<0;i--) {

		// }
		layers[1].updateWeightsInNeuronsLayer(this.learnRate);
		layers[0].updateWeightsInNeuronsLayer(this.learnRate);
	}
	
	public void updateCopyWeightsInLayers(double alfa) {
		for (Layer l : layers) {
			l.updateCopyWeightsInNeuronsLayer(alfa);
		}
	}
	
	public void getOriginalWeights() {
		for (Layer l : layers) {
			l.getWeightsCopyToWeightsInNeuronsLayer();
		}
	}

	public void updateLearnRate() {
		if (Math.sqrt(this.error) > KW * this.prevError) {
			this.learnRate = PD * this.learnRate;
		} else {
			this.learnRate = PI * this.learnRate;
		}
		
		//this.learnRate = (Math.sqrt(this.error) > KW*this.prevError) ? PD*this.learnRate : PI*this.learnRate; 
	}

	public void setPrevError() {
		this.prevError = Math.sqrt(this.error);
	}

	public double getLearnRate() {
		return this.learnRate;
	}
	

	@Override
	public String toString() {
		String out;
		out = layers[0].toString("Ukryta: ");
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
