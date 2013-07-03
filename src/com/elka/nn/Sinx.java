package com.elka.nn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Sinx {
	private static double[] data;
	private static final int FROM = 0;
	private static final int TO = 45;
	private static final int NUM_LAYERS = 2;

	// private static double[] x;
	private static double err;
	private static PrintStream out;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] x = new double[1];
		data = new double[TO - FROM];
		data = dataSeries(FROM, TO);
		// data = dataSeries(FROM, TO);
		NeuralNet NN = new NeuralNet(0.001, NUM_LAYERS, 3, 2, 1);
		/*
		 * for (int i=0; i<data.length; i++) { System.out.println("Iteracja: "
		 * +i+ " "+ "Wart: " +data[i]); }
		 */
		try {
			if (System.getProperty("os.name").startsWith("Linux")) {
				out = new PrintStream(new FileOutputStream("/home/lukasz/Pulpit/DEBUG_SINX1.txt"));	
			} else if (System.getProperty("os.name").startsWith("Windows")) {
				String path = System.getProperty("user.home");
				File textfile = new File(path, "sinxB.txt");
				out = new PrintStream(new FileOutputStream(textfile));
			} else {
				System.out.println("Nie wiem jaki system - ERROR");
			}
			System.setOut(out);
			for (int k = 0; k < 1000; k++) {
				if (k > 0) {
					NN.setPrevError(); // poprzedni_blad = blad_aktualny (do
										// nastepnej iteracji)
				}
				NN.setErrorZero();
				NN.setWeightsZero();
				// System.out.println(NN.toString());
				for (int i = 0; i < data.length; i++) {
					x = new double[] { i };
					NN.learnNet(x, data[i]);
					// System.out.println(NN.toStringX());
					NN.setError(NN.getLayerLastSolution(), data[i]);
					System.out.println("Iteracja zew: " + k + " Iteracja wew: "
							+ i + " " + " Wart. otrzymana: "
							+ NN.getLayerLastSolution() + " Wart. ocze: "
							+ data[i]);
				}
				// System.out.println(NN.toStringWCH());
				// System.out.println("WSP UCZENIA: " + NN.getLearnRate());
				if (k > 5) {
					NN.updateLearnRate();
				}
				System.out.println("WSP UCZENIA: " + NN.getLearnRate());
				// adaptacyjny dobor wspolcz uczenia
				NN.updateWeightsInLayers(); // jaka kolejnosc?
				err = NN.getError();
				System.out.println("Iteracja zew: " + k
						+ " BLAD SREDNIOKWADR.: " + err);
				System.out
						.println("---------------------------------------------------------------------------------------");
				if (err < 0.00001) {
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

		double y1 = NN.goForward(new double[] { 2.5 });
		System.out.println("Wynik 2.5= " + y1);
	}

	private static double[] dataSeries(int from, int to) {
		double[] data = new double[to - from];
		for (int i = from; i < to; i++) {
			if (i == 0) {
				data[i] = 1;
			} else if (i != 0) {
				data[i] = Math.sin(i) / i;
			}
		}
		return data;
	}
	/*
	private static double[] dataSeriesEX(int from, int to) {
		double[] data = new double[to - from];
		for (int i = from; i < to; i++) {
			data[i] = (Math.pow(Math.E, i));
		}
		return data;
	}
	*/
}
