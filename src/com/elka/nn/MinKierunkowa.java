package com.elka.nn;

public class MinKierunkowa {

	private double firstParam;
	private double secParam;
	
	public MinKierunkowa() {
		this.firstParam = 0.0;
		this.secParam = 1E-5;
	}
	
	private double doMinKierunkowa(NeuralNet Network, double data, double param) {
		Network.goForward(inputX)
		
		
		return ;
		
	}
	
	
	
	
	
	
	// Trzeba stworzyc w NeuraltNet procedure, ktora uaktualnia te WeightsCopy:
	// dla ka¿dej warstwy: dla ka¿dego neuronu w warstwie trzeba zachowaæ w_i, potem pod w_i
	// wgraæ wcopy_i i uaktualnic te w_i poprzez ten alfa * -weightsChange. (bo weightsChange = -p_i)
	// Mamy wtedy ladne podstawione_tymczasowe w_i, liczymy sobie spokojnie te bledy w funkcji ff
	// (tylko tez tam w goForward trzeba by zwracac bledy zeby moc porownac) i liczymy dalej ten wspolczynnik.
	
	// gradienty sprzezone to juz chyba bedize troche latwiej :)
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
