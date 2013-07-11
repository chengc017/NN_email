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
	private static float[] data;
	private static final int FROM = 0;
	private static final int TO = 100;
	private static final int NUM_LAYERS = 2;
	private static final int ITER = 5000;
	public static final float DX = 15.0f/(TO-1);
	
	
	// private static float[] x;
	private static float err;
	private static PrintStream out;
	//
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		float[] x = new float[1];
		data = new float[TO - FROM];
		data = dataSeries(FROM, TO);
		// data = dataSeries(FROM, TO);
		
		
		NeuralNet NN = new NeuralNet(0.001f, NUM_LAYERS, 7, 2, 1);
		
		/*----- NADAWANIE NA SZTYWNO WAG DLA NEURONOW WEJSCIOWYCH----------*/
		
		
		/*List<float[]> FL = new ArrayList<float[]>();	// lista na wagi
		float[] newWeightsW1 = new float[2];
		
		float[] WN0 = new float[]{0.0392f
				,				 -0.0107f};
		float[] WN1 = new float[]{0.0141f,
								  0.0629f};
		float[] WN2 = new float[]{-0.0344f,
								  0.0365f};
		float[] WN3 = new float[]{0.0992f,
								  -0.0902f};
		float[] WN4 = new float[]{-0.0401f,
								  -0.0612f};
		float[] WN5 = new float[]{-0.0035f,
								  0.0087f};
		float[] WN6 = new float[]{-0.0522f,
								  -0.0079f};
		FL.add(WN0);
		FL.add(WN1);
		FL.add(WN2);
		FL.add(WN3);
		FL.add(WN4);
		FL.add(WN5);
		FL.add(WN6);
		
		int w1L = 0;
		for (int iN=0; iN<NN.layers[w1L].neurons.length; iN++) {
			newWeightsW1 = FL.get(iN);
			NN.setWeightsByParamInLayer(w1L, iN, newWeightsW1);
		}
		
		
		
		----- NADAWANIE NA SZTYWNO WAG DLA NEURONU WYJSCIOWEGO----------
		float[] newWeightsW2 = new float[]{
				 -0.0992f
				,0.0693f
				,0.0267f
				,0.0300f
				,0.0834f
				,0.0145f
				,-0.0704f
				,0.0872f
				};
		
		int iL = 1;  // bo warstwa pierwsza
		int iN = 0;  // bo jest jeden neuron na pozycji 0
			
		for (int k=0; k<newWeightsW2.length; k++) {
				newWeightsW2[k] = k*0.13f + 0.1f;
		}
		NN.setWeightsByParamInLayer(iL, iN, newWeightsW2);*/
		
		
		
		/*
		 * for (int i=0; i<data.length; i++) { System.out.println("Iteracja: "
		 * +i+ " "+ "Wart: " +data[i]); }
		 */
		GradSprzez GS = new GradSprzez();
		MinKierunkowa MK = new MinKierunkowa(NN);
		
		try {
			if (System.getProperty("os.name").startsWith("Linux")) {
				out = new PrintStream(new FileOutputStream("/home/lukasz/Pulpit/DEBUG_SINX_proba.txt"));	
			} else if (System.getProperty("os.name").startsWith("Windows")) {
				String path = System.getProperty("user.home");
				File textfile = new File(path, "TEST_12.txt");
				out = new PrintStream(new FileOutputStream(textfile));
			} else {
				System.out.println("Nie wiem jaki system - ERROR");
			}
			System.setOut(out);
			int tmp;
			for (int k = 0; k < ITER; k++) {
			/*	if (k > 0) {
					NN.setPrevError(); // poprzedni_blad = blad_aktualny (do
										// nastepnej iteracji)
				}*/
				NN.setErrorZero();
				NN.setWeightsZero();
				System.out.println(NN.toString());
				tmp = k+1;
				for (int i = 0; i < data.length; i++) {
					x = new float[] { 0.1f+DX*i };
					NN.learnNet(x, data[i]);
					//System.out.println(NN.toStringX());
					NN.setError(NN.getLayerLastSolution(), data[i]);
					if (k == ITER-1) {	// wypisanie wynikow dla ostatniej iteracji zewnetrznej
						int tmpa = i+1;
						/*System.out.println("Iteracja zew: " + tmp + " Iteracja wew: "
								+ tmpa + " " + " probka: " + x[0] + "\n" +  " d(" + tmpa + ")"
								+ data[i] + " y(" + tmpa + ")"
								+ NN.getLayerLastSolution());*/
						System.out.println("d(" + tmpa + ")"
								+ data[i] + " y(" + tmpa + ")"
								+ NN.getLayerLastSolution());
						
					}
				}
				// System.out.println(NN.toStringWCH());
				// System.out.println("WSP UCZENIA: " + NN.getLearnRate());
				/*if (k > 5) {
					NN.updateLearnRate();
				}*/
				System.out.println("WSP UCZENIA: " + NN.getLearnRate());
				// adaptacyjny dobor wspolcz uczenia
				//NN.updateWeightsInLayers(); // jaka kolejnosc?
				err = NN.getError();
				System.out.println("Iteracja zew: " + tmp
						+ " BLAD SREDNIOKWADR.: " + err);
				//System.out.println(NN.toString());
				/*if (k > 4) {
					NN.updateLearnRate();
				}*/
				GS.makeGradSprzez(NN);
				//NN.setLearnRate(MK.getParamOfMinKierunkowa(data));
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

		/*float y1 = NN.goForward(new float[] { 2.5f });
		System.out.println("Wynik 2.5= " + y1);*/
	}

	private static float[] dataSeries(int from, int to) {
		float[] data = new float[to - from];
		float x;
		for (int i = from; i < to; i++) {
			x = 0.1f+DX*i;
			/*if (i == 0) {
				data[i] = 100;
			} else if (i != 0) {*/
				data[i] = (float) (Math.sin(x) / x);
//			}
		}
		return data;
	}
	
	private static float roundTwoDecimals(float d) {
        DecimalFormat twoDForm = new DecimalFormat("#.####");
        return Float.valueOf(twoDForm.format(d));
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
