package com.elka.nn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Sinx {
	private static double[] data;
	private static final int FROM = 0;
	private static final int TO = 10;
	private static final int NUM_LAYERS = 2;
	private static final int ITER = 100;
	public static final double DX = 15.0 / (TO - 1);

	// private static double[] x;
	private static double err;
	private static PrintStream out;

	//
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double[] x = new double[1];
		data = new double[TO - FROM];
		data = dataSeries(FROM, TO);
		// data = dataSeries(FROM, TO);

		NeuralNet NN = new NeuralNet(0.001, NUM_LAYERS, 7, 2, 1);

		/*----- NADAWANIE NA SZTYWNO WAG DLA NEURONOW WEJSCIOWYCH----------*/

		/*
		 * // List<double[]> FL = new ArrayList<double[]>(); // lista na wagi
		 * double[] newWeightsW1 = new double[2];
		 * 
		 * double[] WN0 = new double[]{0.0392f , -0.0107f}; double[] WN1 = new
		 * double[]{0.0141f, 0.0629f}; double[] WN2 = new double[]{-0.0344f,
		 * 0.0365f}; double[] WN3 = new double[]{0.0992f, -0.0902f}; double[]
		 * WN4 = new double[]{-0.0401f, -0.0612f}; double[] WN5 = new
		 * double[]{-0.0035f, 0.0087f}; double[] WN6 = new double[]{-0.0522f,
		 * -0.0079f}; FL.add(WN0); FL.add(WN1); FL.add(WN2); FL.add(WN3);
		 * FL.add(WN4); FL.add(WN5); FL.add(WN6);
		 * 
		 * int w1L = 0; for (int iN=0; iN<NN.layers[w1L].neurons.length; iN++) {
		 * newWeightsW1 = ; NN.setWeightsByParamInLayer(w1L, iN, newWeightsW1);
		 * }
		 * 
		 * 
		 * 
		 * //----- NADAWANIE NA SZTYWNO WAG DLA NEURONU WYJSCIOWEGO----------
		 * double[] newWeightsW2 = new double[8]; -0.0992f ,0.0693f ,0.0267f
		 * ,0.0300f ,0.0834f ,0.0145f ,-0.0704f ,0.0872f };
		 * 
		 * int iL = 1; // bo warstwa pierwsza int iN = 0; // bo jest jeden
		 * neuron na pozycji 0
		 * 
		 * for (int k=0; k<newWeightsW2.length; k++) { newWeightsW2[k] = k*0.13f
		 * + 0.1f; } NN.setWeightsByParamInLayer(iL, iN, newWeightsW2);
		 */

		/*----- NADAWANIE NA SZTYWNO WAG DLA NEURONU WEJSCIOWEGO----------*/

		double[] newWeightsW1 = new double[2];
		int w1L = 0;
		for (int iN = 0; iN < NN.layers[w1L].neurons.length; iN++) {
			for (int k = 0; k < newWeightsW1.length; k++) {
				newWeightsW1[k] = (iN + 1) * 0.07 + k * 0.2;
			}
			NN.setWeightsByParamInLayer(w1L, iN, newWeightsW1);
		}

		/*----- NADAWANIE NA SZTYWNO WAG DLA NEURONU WYJSCIOWEGO----------*/
		double[] newWeightsW2 = new double[8];

		int iL = 1; // bo warstwa pierwsza
		int iN = 0; // bo jest jeden neuron na pozycji 0

		for (int k = 0; k < newWeightsW2.length; k++) {
			newWeightsW2[k] = k * 0.13 + 0.1;
		}
		NN.setWeightsByParamInLayer(iL, iN, newWeightsW2);

		/*
		 * for (int i=0; i<data.length; i++) { System.out.println("Iteracja: "
		 * +i+ " "+ "Wart: " +data[i]); }
		 */
		GradSprzez GS = new GradSprzez();
		MinKierunkowa MK = new MinKierunkowa(NN);

		try {
			if (System.getProperty("os.name").startsWith("Linux")) {
				out = new PrintStream(new FileOutputStream(
						"/home/lukasz/Pulpit/DEBUG_SINX_proba.txt"));
			} else if (System.getProperty("os.name").startsWith("Windows")) {
				String path = System.getProperty("user.home");
				File textfile = new File(path, "TEST_MIN_KIER_gold.txt");
				out = new PrintStream(new FileOutputStream(textfile));
			} else {
				System.out.println("Nie wiem jaki system - ERROR");
			}
			System.setOut(out);
			int tmp;
			for (int k = 0; k < ITER; k++) {
				 /* if (k > 0) { 
					  NN.setPrevError(); // poprzedni_blad =
				  }*/
				  //blad_aktualny (d nastepnej iteracji) }
				 
				NN.setErrorZero();
				NN.setWeightsZero();
				System.out.println(NN.toString());
				tmp = k + 1;
				for (int i = 0; i < data.length; i++) {
					x = new double[] { 0.1 + DX * i };
					NN.learnNet(x, data[i]);
					// System.out.println(NN.toStringX());
					NN.setError(NN.getLayerLastSolution(), data[i]);
					if (k == ITER - 1) { // wypisanie wynikow dla ostatniej
											// iteracji zewnetrznej
						int tmpa = i + 1;
						/*
						 * System.out.println("Iteracja zew: " + tmp +
						 * " Iteracja wew: " + tmpa + " " + " probka: " + x[0] +
						 * "\n" + " d(" + tmpa + ")" + data[i] + " y(" + tmpa +
						 * ")" + NN.getLayerLastSolution());
						 */
						System.out.println("d(" + tmpa + ")" + data[i] + " y("
								+ tmpa + ")" + NN.getLayerLastSolution());

					}
				}
				// System.out.println(NN.toStringWCH());
				System.out.println("WSP UCZENIA: " + NN.getLearnRate());
				// adaptacyjny dobor wspolcz uczenia
				// NN.updateWeightsInLayers(); // jaka kolejnosc?
				err = NN.getError();
				System.out.println("Iteracja zew: " + tmp
						+ " BLAD SREDNIOKWADR.: " + err);
				// System.out.println(NN.toString());
				/*
				 * if (k > 4) { NN.updateLearnRate(); }
				 */
				GS.makeGradSprzez(NN);
				/*
				 * if (tmp != 27 && tmp != 20) {
				 * NN.setLearnRate(MK.getParamOfMinKierunkowa((data), false)); }
				 * else { NN.setLearnRate(MK.getParamOfMinKierunkowa((data),
				 * true)); }
				 */
				/*if (k > 4) {
					NN.updateLearnRate();
				}*/
				NN.setLearnRate(MK.getParamOfMinKierunkowa(data, false));
				NN.updateWeightsInLayersDIRECT();
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

		/*
		 * double y1 = NN.goForward(new double[] { 2.5f });
		 * System.out.println("Wynik 2.5= " + y1);
		 */
	}

	private static double[] dataSeries(int from, int to) {
		double[] data = new double[to - from];
		double x;
		for (int i = from; i < to; i++) {
			x = 0.1 + DX * i;
			/*
			 * if (i == 0) { data[i] = 100; } else if (i != 0) {
			 */
			data[i] = (double) (Math.sin(x) / x);
			// }
		}
		return data;
	}

	/*
	 * private static double[] dataSeriesEX(int from, int to) { double[] data =
	 * new double[to - from]; for (int i = from; i < to; i++) { data[i] =
	 * (Math.pow(Math.E, i)); } return data; }
	 */

	private static double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.####");
		return Double.valueOf(twoDForm.format(d));
	}
}
