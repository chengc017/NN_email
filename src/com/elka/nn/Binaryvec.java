package com.elka.nn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

public class Binaryvec {

	/**
	 * @param args
	 */
	private static Vector<double[]> dVec;
	private static double[] predictedOut;
	private static final int NUM_LAYERS = 2;
	private static double err;

	public static void main(String[] args) {
		dVec = new Vector<double[]>();
		dVec = dataSeriesVector();

		predictedOut = new double[5];
		predictedOut = dataSeriesOutArray();

		NeuralNet NN = new NeuralNet(0.001, NUM_LAYERS, 4, 6, 1);

		try {
			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(System.in));
			String path = System.getProperty("user.home");
			File textfile = new File(path, "binVEC.txt");
			PrintStream out = new PrintStream(new FileOutputStream(textfile));
			System.setOut(out);
			for (int k = 0; k < 5000; k++) {
				if (k > 0) {
					NN.setPrevError(); // poprzedni_blad = blad_aktualny (do
										// nastepnej iteracji)
				}
				NN.setErrorZero();
				NN.setWeightsZero();
				for (int i = 0; i < dVec.size(); i++) {
					NN.learnNet(dVec.elementAt(i), predictedOut[i]);
					NN.setError(NN.getLayerLastSolution(), predictedOut[i]);
					System.out.println("Iteracja zew: " + k + " Iteracja wew: "
							+ i + " " + " Wart. otrzymana: "
							+ NN.getLayerLastSolution() + "\t Wart. ocze: "
							+ predictedOut[i]);
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
			double test2 = NN
					.goForward(new double[] { 0.0, 1.0, 0.0, 0.0, 0.0 });
			System.out.println(test2);

			double test3 = NN
					.goForward(new double[] { 0.0, 0.0, 0.0, 1.0, 0.0 });
			System.out.println(test3);
			out.close();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error");
		}
		System.out.println("Blad :" + err / 2);

	}

	public static Vector<double[]> dataSeriesVector() {
		double[] x1 = new double[] { 1.0, 1.0, 1.0, 0.0, 0.0 };
		double[] x2 = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0 };
		double[] x3 = new double[] { 1.0, 0.0, 1.0, 0.0, 0.0 };
		double[] x4 = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0 };
		double[] x5 = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0 };

		Vector<double[]> vec = new Vector<double[]>();

		vec.add(x1);
		vec.add(x2);
		vec.add(x3);
		vec.add(x4);
		vec.add(x5);
		return vec;
	}

	public static double[] dataSeriesOutArray() {
		double[] out = new double[5];
		double d1 = 1.0;
		double d2 = 0.0;
		double d3 = 1.0;
		double d4 = 1.0;
		double d5 = 0.0;
		out[0] = d1;
		out[1] = d2;
		out[2] = d3;
		out[3] = d4;
		out[4] = d5;
		return out;
	}
}
