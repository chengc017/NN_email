package com.elka.nn;

public interface LayerFunc {
	public void startLayerOutput(float[] inputX);

	public void setLastLayersError(float target);
}
