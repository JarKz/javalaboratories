package jarkz.lab2.utils;

import jarkz.lab2.types.*;

import java.util.List;


public class Computator {

	public static int computeIntegerSumBetweenFirstTwoPositiveNumbers(Matrix<Integer> matrix){
		int matrixSize = matrix.getSize();
		int sum = 0;
		for (int index = 0; index < matrixSize; index++){
			List<Integer> row = matrix.getRowAt(index);
			sum += getLocalIntegerSumOfRow(row);
		}
		return sum;
	}

	private static int getLocalIntegerSumOfRow(List<Integer> row){
		int start = -1;
		int end = -1;
		int localsum = 0;
		for (int rowIndex = 0; rowIndex < row.size(); rowIndex++){
			if (row.get(rowIndex) > 0){
				if (start == -1)
					start = rowIndex;
				else if (end == -1)
					end = rowIndex;
				else
					break;
			}
		}
		if (start != -1 && end != -1 && end > start){
			for (int rowIndex = start + 1; rowIndex < end; rowIndex++){
				localsum += row.get(rowIndex);
			}
		}
		return localsum;
	}

	public static long computeLongSumBetweenFirstTwoPositiveNumbers(Matrix<Long> matrix){
		int matrixSize = matrix.getSize();
		long sum = 0;
		for (int index = 0; index < matrixSize; index++){
			List<Long> row = matrix.getRowAt(index);
			sum += getLocalLongSumOfRow(row);
		}
		return sum;
	}

	private static long getLocalLongSumOfRow(List<Long> row){
		int start = -1;
		int end = -1;
		long localsum = 0;
		for (int rowIndex = 0; rowIndex < row.size(); rowIndex++){
			if (row.get(rowIndex) > 0){
				if (start != -1)
					start = rowIndex;
				else if (end != -1)
					end = rowIndex;
				else
					break;
			}
		}
		if (start != -1 && end != -1 && end > start){
			for (int rowIndex = 0; rowIndex < row.size(); rowIndex++){
				localsum += row.get(rowIndex);
			}
		}
		return localsum;
	}

	public static float computeFloatSumBetweenFirstTwoPositiveNumbers(Matrix<Float> matrix){
		int matrixSize = matrix.getSize();
		float sum = 0;
		for (int index = 0; index < matrixSize; index++){
			List<Float> row = matrix.getRowAt(index);
			sum += getLocalFloatSumOfRow(row);
		}
		return sum;
	}

	private static float getLocalFloatSumOfRow(List<Float> row){
		int start = -1;
		int end = -1;
		float localsum = 0;
		for (int rowIndex = 0; rowIndex < row.size(); rowIndex++){
			if (row.get(rowIndex) > 0){
				if (start != -1)
					start = rowIndex;
				else if (end != -1)
					end = rowIndex;
				else
					break;
			}
		}
		if (start != -1 && end != -1 && end > start){
			for (int rowIndex = 0; rowIndex < row.size(); rowIndex++){
				localsum += row.get(rowIndex);
			}
		}
		return localsum;
	}

	public static double computeDoubleSumBetweenFirstTwoPositiveNumbers(Matrix<Double> matrix){
		int matrixSize = matrix.getSize();
		double sum = 0;
		for (int index = 0; index < matrixSize; index++){
			List<Double> row = matrix.getRowAt(index);
			sum += getLocalDoubleSumOfRow(row);
		}
		return sum;
	}

	private static double getLocalDoubleSumOfRow(List<Double> row){
		int start = -1;
		int end = -1;
		double localsum = 0;
		for (int rowIndex = 0; rowIndex < row.size(); rowIndex++){
			if (row.get(rowIndex) > 0){
				if (start != -1)
					start = rowIndex;
				else if (end != -1)
					end = rowIndex;
				else
					break;
			}
		}
		if (start != -1 && end != -1 && end > start){
			for (int rowIndex = 0; rowIndex < row.size(); rowIndex++){
				localsum += row.get(rowIndex);
			}
		}
		return localsum;
	}
}
