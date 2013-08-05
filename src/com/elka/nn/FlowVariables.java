package com.elka.nn;

public class FlowVariables {

	private boolean isWordsOn;
	private boolean isWeightsOn;
	
	public final int WORDSIZE = 5;
	public final int LAYERS = 2;
	public final int NEURONS_HID = 6;
	public final int NEURONS_OUT = 1;
	public final int NUM_INPUT = WORDSIZE + 1;
	public final double LEARN_RATE = 0.0001;
	
	public FlowVariables() {
		this.isWordsOn = false;
		this.isWeightsOn = false;
	}
	
	public void setWordsOn(boolean b) {
		this.isWordsOn = b;
	}
	
	public void setWeightsOn(boolean b) {
		this.isWeightsOn = b;
	}
	
	public boolean getWordsOn() {
		return this.isWordsOn;
	}
	
	public boolean getWeightsOn() {
		return this.isWeightsOn;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
