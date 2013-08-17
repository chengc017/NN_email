package com.elka.nn;

public class FlowVariables {

	private boolean isWordsSPAMOn;
	private boolean isWordsGOODOn;
	private boolean isWeightsOn;
	
	public final int WORDSIZE = 15;
	public final int OTHERSIZE = 3;
	public final int LAYERS = 2;
	public int NEURONS_HID = 6;
	public final int NEURONS_OUT = 1;
	public final int NUM_INPUT = WORDSIZE + OTHERSIZE + 1;
	public final double LEARN_RATE = 0.0001;
	
	public FlowVariables() {
		this.isWordsSPAMOn = false;
		this.isWordsGOODOn = false;
		this.isWeightsOn = false;
	}
	
	public void setWordsSPAMOn(boolean b) {
		this.isWordsSPAMOn = b;
	}
	
	public void setWordsGOODOn(boolean b) {
		this.isWordsGOODOn = b;
	}
	
	public boolean getWordsSPAMOn() {
		return this.isWordsSPAMOn;
	}
	
	public boolean getWordsGOODOn() {
		return this.isWordsGOODOn;
	}
	
	public void setWeightsOn(boolean b) {
		this.isWeightsOn = b;
	}
	
	public boolean getWeightsOn() {
		return this.isWeightsOn;
	}
	
	public void setNumberOfNeurons(int number) {
		this.NEURONS_HID = number; 
	}
	
	public int getNumberOfNeurons() {
		return this.NEURONS_HID;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
