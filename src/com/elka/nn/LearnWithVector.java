package com.elka.nn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

public class LearnWithVector {

	private static Vector<double[]> dVec;
	private static double[] predictedOut;
	private static final int NUM_LAYERS = 2;
	private static double err;
	
	private NeuralNet NN;
	private GradSprzez GS;
	
//	private static PrintStream out;

	public LearnWithVector(NeuralNet NN) {
		dVec = new Vector<double[]>();
		this.dataSeriesVector();

		predictedOut = new double[5];
		this.dataSeriesOutArray();

		//this.NN = new NeuralNet(0.1, NUM_LAYERS, 3, 6, 1);
		this.NN = NN;
		GS = new GradSprzez();
	}
		/*------------ WAGI NA SZTYWNO DLA NEURONOW WEJSCIOWYCH -----------------*/
		/*double[] newWeightsW1 = new double[6];
		int w1L = 0;
		for (int iN=0; iN<NN.layers[w1L].neurons.length; iN++) {
			for (int k=0; k<newWeightsW1.length; k++) {
					newWeightsW1[k] = (iN+1)*0.07 + k*0.2;
			}
			NN.setWeightsByParamInLayer(w1L, iN, newWeightsW1);
		}
		
		*/
		/*----- NADAWANIE NA SZTYWNO WAG DLA NEURONU WYJSCIOWEGO----------*/
		/*double[] newWeightsW2 = new double[5];
		
		int iL = 1;  // bo warstwa pierwsza
		int iN = 0;  // bo jest jeden neuron na pozycji 0
			
		for (int k=0; k<newWeightsW2.length; k++) {
				newWeightsW2[k] = k*0.13 + 0.1;
		}
		NN.setWeightsByParamInLayer(iL, iN, newWeightsW2);
		*/
		
		//MinKierunkowa MK = new MinKierunkowa(NN);
	public void setWeights() {
		try {
			/*if (System.getProperty("os.name").startsWith("Linux")) {
				out = new PrintStream(new FileOutputStream("/home/lukasz/Pulpit/BINARY_VEC.txt"));	
			} else if (System.getProperty("os.name").startsWith("Windows")) {
				String path = System.getProperty("user.home");
				File textfile = new File(path, "sinxB_GS_MINK_BIN.txt");
				out = new PrintStream(new FileOutputStream(textfile));
			} else {
				System.out.println("Nie wiem jaki system - ERROR");
				System.exit(1);
			}
			System.setOut(out);*/
			for (int k = 0; k < 1500; k++) {
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
				NN.updateWeightsInLayers();
				NN.updateWeightsInLayersDIRECT();
				System.out
						.println("---------------------------------------------------------------------------------------");
				if (err  < 0.00001) {
					// System.out.println("iteracje= " +k);
					break;
				}
			}
//			out.close();
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error");
		}
		System.out.println("Blad :" + err / 2);

	}

	public void dataSeriesVector() {
		double[] x1 = new double[] { 1.0, 1.0, 1.0, 0.0, 0.0 };
		double[] x2 = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0 };
		double[] x3 = new double[] { 1.0, 0.0, 1.0, 0.0, 0.0 };
		double[] x4 = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0 };
		double[] x5 = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0 };

		//dVec = new Vector<double[]>();

		dVec.add(x1);
		dVec.add(x2);
		dVec.add(x3);
		dVec.add(x4);
		dVec.add(x5);
	}

	public void dataSeriesOutArray() {
		double d1 = 1.0f;
		double d2 = 0.0f;
		double d3 = 1.0f;
		double d4 = 1.0f;
		double d5 = 0.0f;
		predictedOut[0] = d1;
		predictedOut[1] = d2;
		predictedOut[2] = d3;
		predictedOut[3] = d4;
		predictedOut[4] = d5;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}