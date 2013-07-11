package com.elka.nn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SinXCosX {
	private static float[] data;
	private static final int FROM = 0;
	private static final int TO = 50;
	private static final int NUM_LAYERS = 2;

	private static float[] x;
	private static float err;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		x = new float[1];
		data = new float[TO - FROM];
		data = dataSeriesSXCX(FROM, TO);
		// data = dataSeries(FROM, TO);
		NeuralNet NN = new NeuralNet(0.00001, NUM_LAYERS, 5, 3, 1); // (liczba
																	// warstw,
																	// l.
																	// neurWarstwy1,
																	// l.
																	// wejscWarstwy1,
																	// l.neuronowWarstwy
																	// 2)
		/*
		 * for (int i=0; i<data.length; i++) { System.out.println("Iteracja: "
		 * +i+ " "+ "Wart: " +data[i]); }
		 */
		try {
			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(System.in));
			String path = System.getProperty("user.home");
			File textfile = new File(path, "sinxcosxA.txt");
			PrintStream out = new PrintStream(new FileOutputStream(textfile));
			System.setOut(out);
			for (int k = 0; k < 5000; k++) {
				if (k > 0) {
					NN.setPrevError(); // poprzedni_blad = blad_aktualny (do
										// nastepnej iteracji)
				}
				NN.setErrorZero();
				NN.setWeightsZero();
				for (int i = 0; i < data.length; i++) {
					x = new float[] { i, i + 10 };
					NN.learnNet(x, data[i]);
					NN.setError(NN.getLayerLastSolution(), data[i]);
					System.out.println("Iteracja zew: " + k + " Iteracja wew: "
							+ i + " " + " Wart. otrzymana: "
							+ NN.getLayerLastSolution() + " Wart. ocze: "
							+ data[i]);
				}
				if (k > 5) {
					NN.updateLearnRate();
				}
				System.out.println("WSP UCZENIA: " + NN.getLearnRate());
				NN.updateWeightsInLayers();
				err = NN.getError();
				System.out.println("Iteracja zew: " + k
						+ " BLAD SREDNIOKWADR.: " + err / 2);
				System.out
						.println("---------------------------------------------------------------------------------------");
				if (err / 2 < 0.00001) {
					// System.out.println("iteracje= " +k);
					break;
				}
			}
			out.close();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error");
		}
		System.out.println("Blad :" + err / 2);

		float y1 = NN.goForward(new float[] { 2.5 });
		System.out.println("Wynik 2.5= " + y1);
	}

	private static float[] dataSeriesSXCX(int from, int to) {
		float[] data = new float[to - from];
		for (int i = from; i < to; i++) {
			data[i] = (Math.sin(i) + Math.cos(i + 10)) / 2;
		}
		return data;
	}
}
