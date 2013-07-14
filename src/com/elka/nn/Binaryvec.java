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
	
	private static PrintStream out;

	public static void main(String[] args) {
		dVec = new Vector<double[]>();
		dVec = dataSeriesVector();

		predictedOut = new double[5];
		predictedOut = dataSeriesOutArray();

		NeuralNet NN = new NeuralNet(0.001f, NUM_LAYERS, 4, 6, 1);
		
		GradSprzez GS = new GradSprzez();
		MinKierunkowa MK = new MinKierunkowa(NN);

		try {
			if (System.getProperty("os.name").startsWith("Linux")) {
				out = new PrintStream(new FileOutputStream("/home/lukasz/Pulpit/BINARY_VEC.txt"));	
			} else if (System.getProperty("os.name").startsWith("Windows")) {
				String path = System.getProperty("user.home");
				File textfile = new File(path, "sinxB_GS_MINK.txt");
				out = new PrintStream(new FileOutputStream(textfile));
			} else {
				System.out.println("Nie wiem jaki system - ERROR");
			}
			System.setOut(out);
			for (int k = 0; k < 5000; k++) {
				/*if (k > 0) {
					NN.setPrevError(); // poprzedni_blad = blad_aktualny (do
										// nastepnej iteracji)
				}*/
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
				/*if (k > 5) {
					NN.updateLearnRate();
				}*/
				//NN.setLearnRate(MK.getParamOfMinKierunkowa(dVec, predictedOut));
				
				System.out.println("WSP UCZENIA: " + NN.getLearnRate());
				//NN.updateWeightsInLayers();
				err = NN.getError();
				System.out.println("Iteracja zew: " + k
						+ " BLAD SREDNIOKWADR.: " + err);
				GS.makeGradSprzez(NN);
				//NN.setLearnRate(MK.getParamOfMinKierunkowa(dVec, predictedOut));
				NN.updateWeightsInLayersDIRECT();
				System.out
						.println("---------------------------------------------------------------------------------------");
				if (err  < 0.00001) {
					// System.out.println("iteracje= " +k);
					break;
				}
			}
			double test2 = NN
					.goForward(new double[] { 0.0f, 1.0f, 0.0f, 0.0f, 0.0f });
			System.out.println(test2);

			double test3 = NN
					.goForward(new double[] { 0.0f, 0.0f, 0.0f, 1.0f, 0.0f });
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
		double[] x1 = new double[] { 1.0f, 1.0f, 1.0f, 0.0f, 0.0f };
		double[] x2 = new double[] { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f };
		double[] x3 = new double[] { 1.0f, 0.0f, 1.0f, 0.0f, 0.0f };
		double[] x4 = new double[] { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f };
		double[] x5 = new double[] { 0.0f, 0.0f, 0.0f, 0.0f, 1.0f };

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
		double d1 = 1.0f;
		double d2 = 0.0f;
		double d3 = 1.0f;
		double d4 = 1.0f;
		double d5 = 0.0f;
		out[0] = d1;
		out[1] = d2;
		out[2] = d3;
		out[3] = d4;
		out[4] = d5;
		return out;
	}
}
