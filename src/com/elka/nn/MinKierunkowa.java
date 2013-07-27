package com.elka.nn;

import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class MinKierunkowa {

	private double firstParam; // a
	private double secParam; // b
	private NeuralNet Network;

	private final double GOLDEN_PARAM = 0.61803398;
	private final double EPS = 0.000001;

	private int file_count;

	// private ArrayList<double>[] dataList; // tu zeby mi ladnie iterowa� w
	// petli po kolejnych pr�bkach ;) <-- @TODO

	public MinKierunkowa(NeuralNet NetCopy) {
		this.firstParam = 0.0;
		this.secParam = 1E-5;
		this.Network = NetCopy;
		this.file_count = 0;
	}

	private double goForwardMinKierunkowa(double[] data, double param) {
		// Network.makeOriginalWeightsCopy(); // zeby mi oryginalne wagi nie
		// zniknely
		Network.getOriginalWeights();
		Network.updateCopyWeightsInLayers(param);
		return Network.goThroughLearning(data);
	}

	public double getParamOfMinKierunkowa(double[] data, boolean czyRysowac) {
		// Stworzenie plikow do rysowania wykresiku
		String filename_a = "/home/lukasz/Pulpit/test_a_" + this.file_count
				+ ".m";
		String filename_b = "/home/lukasz/Pulpit/test_b_" + this.file_count
				+ ".m";

		ChartDrawer cd_a = new ChartDrawer(filename_a, czyRysowac);
		ChartDrawer cd_b = new ChartDrawer(filename_b, czyRysowac);
		if (czyRysowac) {
			cd_a.writeToFile("clear all;");
			cd_b.writeToFile("clear all;");
		}

		this.file_count++;
		Network.makeOriginalWeightsCopy(); // zeby mi oryginalne wagi nie
											// zniknely
		firstParam = 0.0;
		secParam = 1E-5;
		// secParam = 0.00001;
		double ff1 = goForwardMinKierunkowa(data, this.firstParam);
		double ff2 = goForwardMinKierunkowa(data, this.secParam);
		String onePoint_ff1 = "y(" + this.firstParam + ")=" + ff1 + ";";
		String onePoint_ff2 = "y(" + this.secParam + ")=" + ff2 + ";";
		/*if (czyRysowac) {
			cd_a.writeToFile(onePoint_ff1);
			cd_a.writeToFile(onePoint_ff2);
		}*/		
		int it = 0;
		while (ff1 > ff2) {
			it++;
			firstParam = secParam;
			secParam = 2 * secParam;
			ff1 = goForwardMinKierunkowa(data, this.firstParam);
			ff2 = goForwardMinKierunkowa(data, this.secParam);
			// wpisywanie do pliku (wykres)
			if (czyRysowac) {
//				onePoint_ff1 = "y(" + this.firstParam + ")=" + ff1 + ";";
//				onePoint_ff2 = "y(" + this.secParam + ")=" + ff2 + ";";
				onePoint_ff1 = "krok(" + it + ")=" + firstParam + ";" + " y(" + it + ")="
						+ ff1 + ";";
				onePoint_ff2 = "krok_b(" + it + ")=" + secParam + ";" + " y_b(" + it + ")="
						+ ff2 + ";";
				cd_a.writeToFile(onePoint_ff1);
				cd_a.writeToFile(onePoint_ff2);
			}
		}
		// dodanie polecenia rysuajcego plot
		if (czyRysowac) {
			cd_a.addPlotDefs();
		}

		double al1 = secParam - 0.613 * (secParam - firstParam);
		double al2 = firstParam + 0.613 * (secParam - firstParam);
		Network.getOriginalWeights();
		for (int i = 0; i < 5; i++) {
			// while ((secParam-firstParam) > EPS) {
			ff1 = goForwardMinKierunkowa(data, al2);
			ff2 = goForwardMinKierunkowa(data, al1);
			/*
			 * if (i == 2) { System.out.println("FF1: " + ff1 + " FF2: " + ff2);
			 * }
			 */
			int k = i + 1;
			if (czyRysowac) {
			onePoint_ff1 = "krok(" + k + ")=" + al2 + ";" + " y(" + k + ")="
					+ ff1 + ";";
			onePoint_ff2 = "krok_b(" + k + ")=" + al1 + ";" + " y_b(" + k + ")="
					+ ff2 + ";";
			cd_b.writeToFile(onePoint_ff1);
			cd_b.writeToFile(onePoint_ff2);
			}
			
			if ((ff2 - ff1) < EPS)
				break;
			
			if (ff1 > ff2) {
				secParam = al2;
				al2 = al1;
				al1 = 0.613 * firstParam + (1 - 0.613) * secParam;
				// al1 = 0.613*firstParam+(1-0.613)*secParam;
				// al1 = secParam - GOLDEN_PARAM * (secParam - firstParam);
			} else {
				firstParam = al1;
				al1 = al2;
				al2 = 0.613 * secParam + (1 - 0.613) * firstParam;
				// al2 = 0.613*secParam+(1-0.613)*firstParam;]
				// al2 = firstParam + GOLDEN_PARAM * (secParam - firstParam);
			}
			Network.getOriginalWeights();
		}
		Network.getOriginalWeights();
		if (czyRysowac) {
			cd_b.addPlotDefs();
		}
		// double ret = (firstParam + secParam)/2.0;
		System.out.println("WSP ZWRACANY PRZEZ MIN KIER: " + secParam);
		return secParam;
	}
	
	public double testOfNewGoldParam(double[] data) {
		Network.makeOriginalWeightsCopy();
		firstParam = 0.0;	// a
		secParam = 15.0001;	// b
		
		double ff1 = goForwardMinKierunkowa(data, this.firstParam);
		double ff2 = goForwardMinKierunkowa(data, this.secParam);
		
		while (ff1 > ff2) {
			firstParam = secParam;
			secParam = 2 * secParam;
			ff1 = goForwardMinKierunkowa(data, this.firstParam);
			ff2 = goForwardMinKierunkowa(data, this.secParam);
		}
		
		double xL = secParam - GOLDEN_PARAM * (secParam - firstParam);
		double xR = firstParam + GOLDEN_PARAM * (secParam - firstParam);
		
		while ((secParam - firstParam) < EPS) {
			Network.getOriginalWeights();
			ff1 = goForwardMinKierunkowa(data, xR);
			ff2 = goForwardMinKierunkowa(data, xL);
			if (ff1 > ff2) {
				secParam = xR;
				xR = xL;
				xL = firstParam + (1-GOLDEN_PARAM)*(secParam-firstParam);
			} else {
				firstParam = xL;
				xL = xR;
				xR = secParam - (1-GOLDEN_PARAM)*(secParam-firstParam);
			}
		}
		Network.getOriginalWeights();
		double out = (firstParam + secParam) / 2.0;
		System.out.println("WSP ZWRACANY PRZEZ MIN KIER: " + out);
		return out;
	}

	public double getParamOfMinKierunkowa(Vector<double[]> dVec, double[] data) {
		Network.makeOriginalWeightsCopy(); // zeby mi oryginalne wagi nie
											// zniknely
		firstParam = 0.0f;
		secParam = 1E-9f;// 0.001;
		double ff1 = goForwardMinKierunkowa(dVec, data, this.firstParam);
		double ff2 = goForwardMinKierunkowa(dVec, data, this.secParam);
		while (ff1 > ff2) {
			firstParam = secParam;
			secParam = 2 * secParam;
			ff1 = goForwardMinKierunkowa(dVec, data, this.firstParam);
			ff2 = goForwardMinKierunkowa(dVec, data, this.secParam);
		}

		double al1 = secParam - 0.613f * (secParam - firstParam);
		double al2 = 0.613f * (secParam - firstParam) + firstParam;
		Network.getOriginalWeights();
		for (int i = 0; i < 45; i++) {
			ff1 = goForwardMinKierunkowa(dVec, data, al2);
			ff2 = goForwardMinKierunkowa(dVec, data, al1);
			
			if (Math.abs(ff1 - ff2) < EPS)
				break;
			
			if (ff1 > ff2) {
				secParam = al2;
				al2 = al1;
				al1 = 0.613f * firstParam + (1f - 0.613f) * secParam;
			} else {
				firstParam = al1;
				al1 = al2;
				al2 = 0.613f * secParam + (1f - 0.613f) * firstParam;
			}
			Network.getOriginalWeights();
		}
		return secParam;
	}

	private double goForwardMinKierunkowa(Vector<double[]> dVec, double[] data,
			double param) {
		// Network.makeOriginalWeightsCopy(); // zeby mi oryginalne wagi nie
		// zniknely
		Network.getOriginalWeights();
		Network.updateCopyWeightsInLayers(param);
		return Network.goThroughLearning(dVec, data);
	}

	// Trzeba stworzyc w NeuraltNet procedure, ktora uaktualnia te WeightsCopy:
	// dla ka�dej warstwy: dla ka�dego neuronu w warstwie trzeba zachowa� w_i,
	// potem pod w_i
	// wgra� wcopy_i i uaktualnic te w_i poprzez ten alfa * -weightsChange. (bo
	// weightsChange = -p_i)
	// Mamy wtedy ladne podstawione_tymczasowe w_i, liczymy sobie spokojnie te
	// bledy w funkcji ff
	// (tylko tez tam w goForward trzeba by zwracac bledy zeby moc porownac) i
	// liczymy dalej ten wspolczynnik.

	// gradienty sprzezone to juz chyba bedize troche latwiej :)

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
