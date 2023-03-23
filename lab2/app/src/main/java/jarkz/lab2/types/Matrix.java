package jarkz.lab2.types;

import java.util.List;

public interface Matrix<T> {

	public int getSize();
	public void setNumberAt(int rowPosition, int columnPosition, T number);

	public List<T> getRowAt(int rowPosition);
	public void setRowAt(int rowPosition, List<T> row);

	public List<T> getColumnAt(int columnPosition);
	public void setColumnAt(int columnPosition, List<T> column);

	public void toTransport();

	public List<List<T>> getMatrixAsLists();
}
