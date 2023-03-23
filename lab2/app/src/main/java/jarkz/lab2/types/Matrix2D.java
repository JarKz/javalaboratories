package jarkz.lab2.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Matrix2D<T> implements Matrix<T> {

	private int size = 0;
	private List<List<T>> matrixAsTwoLists;

	private Matrix2D(){
		//Do not use this constructor
	}

	public Matrix2D(T[][] matrix){
		if (!(matrix.length != 0 && matrix[0].length != 0 && matrix.length == matrix[0].length))
			throw new IllegalArgumentException(
				String.format("Matrix size must be greater than zero, and row and column must be equals! Actual size – row: %d, column: %d",
					matrix.length,
					matrix.length != 0 ? matrix[0].length : 0));
		T firstElement = matrix[0][0];
		boolean isInteger = firstElement instanceof Integer;
		boolean isLong = firstElement instanceof Long;
		boolean isFloat = firstElement instanceof Float;
		boolean isDouble = firstElement instanceof Double;
		if (!(isInteger || isLong || isFloat || isDouble))
			throw new IllegalArgumentException("Invalid type! Must be Integer, or Long, or Float, or Double!");
		size = matrix.length;
		matrixAsTwoLists = new ArrayList<>();
		int index = 0;
		for (T[] row : matrix){
			matrixAsTwoLists.add(new ArrayList<>());
			for (T column : row){
				matrixAsTwoLists.get(index).add(column);
			}
			index++;
		}
	}

	public Matrix2D(List<List<T>> matrix){
		if (!(!matrix.isEmpty() && !matrix.get(0).isEmpty() && matrix.size() == matrix.get(0).size()))
			throw new IllegalArgumentException(
				String.format("Matrix size must be greater than zero, and row and column must be equals! Actual size – row: %d, column: %d",
					matrix.size(),
					matrix.size() != 0 ? matrix.get(0).size() : 0));
		T firstElement = matrix.get(0).get(0);
		boolean isInteger = firstElement instanceof Integer;
		boolean isLong = firstElement instanceof Long;
		boolean isFloat = firstElement instanceof Float;
		boolean isDouble = firstElement instanceof Double;
		if (!(isInteger || isLong || isFloat || isDouble))
			throw new IllegalArgumentException("Invalid type! Must be Integer, or Long, or Float, or Double!");
		size = matrix.size();
		matrixAsTwoLists = new ArrayList<>();
		for (List<T> row : matrix){
			matrixAsTwoLists.add(new ArrayList<>(row));
		}
	}

	public static <T> Matrix<T> getInstance(T[][] matrix){
		return new Matrix2D<T>(matrix);
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder("Matrix[");
		builder.append(matrixAsTwoLists.stream().map(row -> row.toString()).collect(Collectors.joining("\n       ")));
		builder.append("]");
		return builder.toString();
	}

	public int getSize(){
		return size;
	}

	public void setNumberAt(int rowPosition, int columnPosition, T number){
		checkRowPosition(rowPosition);
		checkColumnPosition(columnPosition);
		matrixAsTwoLists.get(rowPosition).set(columnPosition, number);
	}

	public List<T> getRowAt(int rowPosition){
		checkRowPosition(rowPosition);
		return new ArrayList<>(matrixAsTwoLists.get(rowPosition));
	}

	public void setRowAt(int rowPosition, List<T> row){
		checkRowPosition(rowPosition);
		if (row.size() != size)
			throw new IllegalArgumentException(
				String.format("Received row size not equals matrix size! Row size: %d, matrix size: %d",
				row.size(),
				size));
		matrixAsTwoLists.set(rowPosition, new ArrayList<>(row));
	}

	public List<T> getColumnAt(int columnPosition){
		checkColumnPosition(columnPosition);
		List<T> column = new ArrayList<>();
		for (List<T> row : matrixAsTwoLists){
			column.add(row.get(columnPosition));
		}
		return column;
	}

	public void setColumnAt(int columnPosition, List<T> column){
		checkColumnPosition(columnPosition);
		if (column.size() != size)
			throw new IllegalArgumentException(
				String.format("Received row size not equals matrix size! Row size: %d, matrix size: %d",
				column.size(),
				size));
		Iterator<T> columnIterable = column.iterator();
		for (List<T> row : matrixAsTwoLists){
			row.set(columnPosition, columnIterable.next());
		}
	}

	private void checkRowPosition(int rowPosition) throws IllegalArgumentException {
		if (rowPosition < 0 || rowPosition > size - 1)
			throw new IllegalArgumentException(
				String.format("Received row position greater than matrix size or less than zero! Row position: %d, matrix size: %d",
				rowPosition,
				size));
	}

	private void checkColumnPosition(int columnPosition) throws IllegalArgumentException {
		if (columnPosition < 0 || columnPosition > size - 1)
			throw new IllegalArgumentException(
				String.format("Received column position greater than matrix size or less than zero! Column position: %d, matrix size: %d",
				columnPosition,
				size));
	}

	public void toTransport(){
		List<List<T>> transportedMatrix = new ArrayList<>();
		for (int column = 0; column < size; column++){
			transportedMatrix.add(new ArrayList<>(matrixAsTwoLists.get(column)));
			for (int row = 0; row < size; row++){
				transportedMatrix.get(column).set(row, matrixAsTwoLists.get(row).get(column));
			}
		}
		matrixAsTwoLists = transportedMatrix;
	}


	public List<List<T>> getMatrixAsLists(){
		List<List<T>> matrix = new ArrayList<>();
		for (List<T> row : matrixAsTwoLists){
			matrix.add(new ArrayList<>(row));
		}
		return matrix;
	}
}
