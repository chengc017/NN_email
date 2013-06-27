package com.elka.nn;

public class BackProp {

	/**
	 * @TODO Trzeba to jakos uwinac w oddzielna klase, tak mysle No i posprzatac
	 *       kod, w sensie ze dac dwie klasy potomne, neuron_wyjsciowy i
	 *       warstwa_wyjsciowa + usunac te wszystkie smieci kodowe. No i nauczyc
	 *       ja aproksymacji funkcji sinx/x i jakis ladny wykresik moze
	 */
	private static final int NUM_LAYERS = 2;

	public static void main(String[] args) {
		double[] x1 = new double[] { 1.0, 1.0, 1.0, 0.0, 0.0 };
		double d1 = 1.0;

		double[] x2 = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0 };
		double d2 = 0.0;

		double[] x3 = new double[] { 1.0, 0.0, 1.0, 0.0, 0.0 };
		double d3 = 1.0;

		double[] x4 = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0 };
		double d4 = 1.0;

		double[] x5 = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0 };
		double d5 = 0.0;

		double blad;

		NeuralNet NN = new NeuralNet(0.001, NUM_LAYERS, 2, 6, 1);

		System.out.println("Wartosc wag przed nauczeniem: ");
		System.out.println(NN.toString());
		// NN.learnNet(inputX, target)

		for (int k = 0; k < 1500000; k++) {
			NN.setErrorZero();
			NN.learnNet(x1, d1);
			NN.setError(NN.getLayerLastSolution(), d1);
			NN.learnNet(x2, d2);
			NN.setError(NN.getLayerLastSolution(), d2);
			NN.learnNet(x3, d3);
			NN.setError(NN.getLayerLastSolution(), d3);
			NN.learnNet(x4, d4);
			NN.setError(NN.getLayerLastSolution(), d4);
			NN.learnNet(x5, d5);
			NN.setError(NN.getLayerLastSolution(), d5);

			blad = NN.getError(); // blad ogolny sieci (sredniokwadratowy)

			if ((blad / 2) < 0.00001) {
				System.out.println("Blad: " + blad + "\ni: " + k + "\n");
				break;
			}
		}
		blad = NN.getError();
		System.out.println("BLAD: " + blad / 2);

		System.out.println("Wartosc wag po nauczeniu: ");
		System.out.println(NN.toString());

		double test1 = NN.goForward(x1);
		System.out.println(test1);

		double test2 = NN.goForward(new double[] { 0.0, 1.0, 0.0, 0.0, 0.0 });
		System.out.println(test2);

		double test3 = NN.goForward(new double[] { 0.0, 0.0, 0.0, 1.0, 0.0 });
		System.out.println(test3);

		double test4 = NN.goForward(x4);
		System.out.println(test4);

		double test5 = NN.goForward(x2);
		System.out.println(test5);

		double test6 = NN.goForward(x3);
		System.out.println(test6);
	}

}
