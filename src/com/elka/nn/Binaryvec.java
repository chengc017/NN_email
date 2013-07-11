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
	private static Vector<float[]> dVec;
	private static float[] predictedOut;
	private static final int NUM_LAYERS = 2;
	private static float err;
	
	private static PrintStream out;

	public static void main(String[] args) {
		dVec = new Vector<float[]>();
		dVec = dataSeriesVector();

		predictedOut = new float[5];
		predictedOut = dataSeriesOutArray();

		NeuralNet NN = new NeuralNet(0.001, NUM_LAYERS, 4, 6, 1);
		
		GradSprzez GS = new GradSprzez();
		MinKierunkowa MK = new MinKierunkowa(NN);

		try {
			if (System.getProperty("os.name").startsWith("Linux")) {
				out = new PrintStream(new FileOutputStream("/home/lukasz/Pulpit/DEBUG_SINX_mingrd_1.txt"));	
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
				NN.setLearnRate(MK.getParamOfMinKierunkowa(dVec, predictedOut));
				GS.makeGradSprzez(NN);
				System.out.println("WSP UCZENIA: " + NN.getLearnRate());
				//NN.updateWeightsInLayers();
				err = NN.getError();
				System.out.println("Iteracja zew: " + k
						+ " BLAD SREDNIOKWADR.: " + err);
				System.out
						.println("---------------------------------------------------------------------------------------");
				if (err  < 0.00001) {
					// System.out.println("iteracje= " +k);
					break;
				}
			}
			float test2 = NN
					.goForward(new float[] { 0.0, 1.0, 0.0, 0.0, 0.0 });
			System.out.println(test2);

			float test3 = NN
					.goForward(new float[] { 0.0, 0.0, 0.0, 1.0, 0.0 });
			System.out.println(test3);
			out.close();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error");
		}
		System.out.println("Blad :" + err / 2);

	}

	public static Vector<float[]> dataSeriesVector() {
		float[] x1 = new float[] { 1.0, 1.0, 1.0, 0.0, 0.0 };
		float[] x2 = new float[] { 0.0, 0.0, 0.0, 1.0, 1.0 };
		float[] x3 = new float[] { 1.0, 0.0, 1.0, 0.0, 0.0 };
		float[] x4 = new float[] { 1.0, 0.0, 0.0, 0.0, 0.0 };
		float[] x5 = new float[] { 0.0, 0.0, 0.0, 0.0, 1.0 };

		Vector<float[]> vec = new Vector<float[]>();

		vec.add(x1);
		vec.add(x2);
		vec.add(x3);
		vec.add(x4);
		vec.add(x5);
		return vec;
	}

	public static float[] dataSeriesOutArray() {
		float[] out = new float[5];
		float d1 = 1.0;
		float d2 = 0.0;
		float d3 = 1.0;
		float d4 = 1.0;
		float d5 = 0.0;
		out[0] = d1;
		out[1] = d2;
		out[2] = d3;
		out[3] = d4;
		out[4] = d5;
		return out;
	}
}
