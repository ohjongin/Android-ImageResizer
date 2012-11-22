package com.svenkapudija.imageresizer;

import android.content.Context;

public enum DimensionUnit {

	PX,
	DP;

	public static int convertToPixels(DimensionUnit unit, int size, Context ... context) throws ImageResizerException {
		if(unit == DimensionUnit.PX) {
			return size;
		}
		
		if(context == null || context.length == 0 || context[0] == null) {
			throw new ImageResizerException("If you want 'DP units' please provide a Context parameter.");
		}
		
		if(unit == DimensionUnit.DP) {
			float density = context[0].getResources().getDisplayMetrics().density;
			size = convertDpToPx(density, size);
		} else{
			throw new ImageResizerException("Unknown unit '" + unit + "'. Could not convert it into pixels.");
		}
		
		return size;
	}
	
	private static int convertDpToPx(float density, int dps) {
		return (int) (dps * density + 0.5f);
	}
	
}
