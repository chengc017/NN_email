package com.elka.nn;

public interface LayerFunc {
	public void startLayerOutput(double[] inputX);

	public void setLastLayersError(double target);
}
