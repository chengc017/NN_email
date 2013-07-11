package com.elka.nn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SinX_MinKierGrad {
	private static float[] data;
	private static final int FROM = 0;
	private static final int TO = 101;
	private static final int NUM_LAYERS = 2;

	// private static float[] x;
	private static float err;
	private static PrintStream out;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		float[] x = new float[1];
		data = new float[TO - FROM];
		data = dataSeries(FROM, TO);
		// data = dataSeries(FROM, TO);
		NeuralNet NN = new NeuralNet(0.05, NUM_LAYERS, 15, 2, 1);
		/*
		 * for (int i=0; i<data.length; i++) { System.out.println("Iteracja: "
		 * +i+ " "+ "Wart: " +data[i]); }
		 */
		
		GradSprzez GS = new GradSprzez();
		MinKierunkowa MK = new MinKierunkowa(NN);
		
		try {
			if (System.getProperty("os.name").startsWith("Linux")) {
				out = new PrintStream(new FileOutputStream("/home/lukasz/Pulpit/DEBUG_SINX_mingrd_6.txt"));	
			} else if (System.getProperty("os.name").startsWith("Windows")) {
				String path = System.getProperty("user.home");
				File textfile = new File(path, "sinxB_GS_MINK.txt");
				out = new PrintStream(new FileOutputStream(textfile));
			} else {
				System.out.println("Nie wiem jaki system - ERROR");
			}
			System.setOut(out);
			for (int k = 0; k < 5000; k++) {
/*				if (k > 0) {
					NN.setPrevError(); // poprzedni_blad = blad_aktualny (do
										// nastepnej iteracji)
				}*/
				NN.setErrorZero();
				NN.setWeightsZero();
				//System.out.println(NN.toString());
				for (int i = 0; i < data.length; i++) {
					x = (i == 0) || (i == 1) ? new float[] { 0.1+0.15*(i) } : new float[] { 0.1+0.15*(i-1) } ;
					NN.learnNet(x, data[i]);
					//System.out.println(NN.toStringX());
					NN.setError(NN.getLayerLastSolution(), data[i]);
					System.out.println("Iteracja zew: " + k + " Iteracja wew: "
							+ i + " " + " Wart. otrzymana: "
							+ NN.getLayerLastSolution() + " Wart. ocze: "
							+ data[i]);
				}
				//System.out.println(NN.toStringWCH());
				// System.out.println("WSP UCZENIA: " + NN.getLearnRate());
				NN.setLearnRate(MK.getParamOfMinKierunkowa(data));
				/*if (k > 5) {
					NN.updateLearnRate();
				}*/
				GS.makeGradSprzez(NN);
				System.out.println("WSP UCZENIA: " + NN.getLearnRate());
				// adaptacyjny dobor wspolcz uczenia
				//NN.updateWeightsInLayers(); // jaka kolejnosc?
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

		float y1 = NN.goForward(new float[] { 2.5 });
		System.out.println("Wynik 2.5= " + y1);
	}

	private static float[] dataSeries(int from, int to) {
		//float dx = 15.0/100.0;
		float x;
		float[] data = new float[to - from];
		for (int i = from; i < to; i++) {
			if (i == 0 || i == 1) {
				x = 0.1+0.15*(i);
				data[i] = 100*Math.sin(x) / x;
			} 
			else if (i > 1) {
				x = 0.1+0.15*(i-1);
				data[i] = 100*Math.sin(x) / x;
			}
		}
		return data;
	}
	/*
	private static float[] dataSeriesEX(int from, int to) {
		float[] data = new float[to - from];
		for (int i = from; i < to; i++) {
			data[i] = (Math.pow(Math.E, i));
		}
		return data;
	}
	*/
}
