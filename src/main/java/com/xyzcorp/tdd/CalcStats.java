package com.xyzcorp.tdd;

public class CalcStats {

	private int[] array;

	public CalcStats(int[] array) {
		this.array = array;
	}

	public int getMinimum() {
        int result = array[0];
        for (int i = 1; i < array.length; i++) {
        	   if (array[i] < result) {
        		   result = array[i];
        	   }
        }
        return result;
	}

	
}
