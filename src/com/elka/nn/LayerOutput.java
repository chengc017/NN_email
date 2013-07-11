package com.elka.nn;

public class LayerOutput extends Layer implements LayerFunc {

	public LayerOutput(int size, int inputSize) {
		super(size, inputSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startLayerOutput(float[] inputX) {
		for (Neuron n : neurons) {
			n.setX(inputX);
			n.setU();
			n.setY(n.getU());
			// n.biActiveFunction(n.getU());
		}
	}

	@Override
	public void setLastLayersError(float target) {
		for (Neuron n : neurons) {
			for (int i = 0; i < n.getWeightsSize(); i++) {
				n.setWeightChange(i, 2 * (n.getY() - target) * n.getX(i)); // to
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
																			// tak
																			// naprawde
																			// wyjscie
																			// neuronu
																			// poprzedniej
																			// warstwy
																			// (czyli
																			// jakby
																			// y
																			// z
																			// poprzedniej
																			// warstwy
																			// (y
																			// =
																			// funkcja(u)
																			// gdzie
																			// u
																			// =
																			// suma
																			// (x_j
																			// *
																			// w_ij)
			}
		}
	}
}
