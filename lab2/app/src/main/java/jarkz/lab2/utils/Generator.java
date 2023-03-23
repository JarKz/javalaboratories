package jarkz.lab2.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

import jarkz.lab2.types.*;

public class Generator {
	public static Matrix<Integer> generateMatrix(int size, int bounds){
		if (bounds < 0)
			bounds = -bounds;
		List<List<Integer>> pseudoMatrix = new ArrayList<>();
		for (int count = 0; count < size; count++){
			List<Integer> row = new ArrayList<>();
			var random = ThreadLocalRandom.current();
			for (int innerCount = 0; innerCount < size; innerCount++){
				row.add(random.nextInt(-bounds, bounds));
			}
			pseudoMatrix.add(row);
		}
		return new Matrix2D<>(pseudoMatrix);
	}

	public static Matrix<Long> generateMatrix(int size, long bounds){
		if (bounds < 0)
			bounds = -bounds;
		List<List<Long>> pseudoMatrix = new ArrayList<>();
		for (int count = 0; count < size; count++){
			List<Long> row = new ArrayList<>();
			var random = ThreadLocalRandom.current();
			for (int innerCount = 0; innerCount < size; innerCount++){
				row.add(random.nextLong(-bounds, bounds));
			}
			pseudoMatrix.add(row);
		}
		return new Matrix2D<>(pseudoMatrix);
	}

	public static Matrix<Float> generateMatrix(int size, float bounds){
		if (Float.compare(bounds, 0) < 0)
			bounds = -bounds;
		List<List<Float>> pseudoMatrix = new ArrayList<>();
		for (int count = 0; count < size; count++){
			List<Float> row = new ArrayList<>();
			var random = ThreadLocalRandom.current();
			for (int innerCount = 0; innerCount < size; innerCount++){
				row.add(random.nextFloat(-bounds, bounds));
			}
			pseudoMatrix.add(row);
		}
		return new Matrix2D<>(pseudoMatrix);
	}

	public static Matrix<Double> generateMatrix(int size, double bounds){
		if (Double.compare(bounds, 0) < 0)
			bounds = -bounds;
		List<List<Double>> pseudoMatrix = new ArrayList<>();
		for (int count = 0; count < size; count++){
			List<Double> row = new ArrayList<>();
			var random = ThreadLocalRandom.current();
			for (int innerCount = 0; innerCount < size; innerCount++){
				row.add(random.nextDouble(-bounds, bounds));
			}
			pseudoMatrix.add(row);
		}
		return new Matrix2D<>(pseudoMatrix);
	}
}
