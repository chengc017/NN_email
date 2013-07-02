package com.elka.nn;

public class MinKierunkowa {

	private double[] weightCopy;
	private double[] prevWeightCopy;
	
	public MinKierunkowa(double alfa, double[] p_i) {
		this.weightCopy = this.prevWeightCopy *alfa * p_i;
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
