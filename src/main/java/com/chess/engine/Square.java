//Position class solely for chess board
//Let's not use it for now
package main.java.com.chess.engine;

/**
 * Class Square
 * Represent a position on board with a row index and a column index
 * Validate input and provide convenience for packing the 2 values
 * Can be mapped to and from a string for easy testing and chess formality
 */
public class Square {
	private int rowIndex, colIndex;
	public static final String[] COL_NAMES = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
	public static final String[] ROW_NAMES = new String[]{"8", "7", "6", "5", "4", "3", "2", "1"};

	/**
	 *
	 * @return the row index of square
	 */
	public int rowIx() {
		return rowIndex;
	}

	/**
	 *
	 * @return the col index of square
	 */
	public int colIx() {
		return colIndex;
	}

	/**
	 *
	 * @param name the string representation of square
	 */
	public Square(String name) {
		name = name.toUpperCase();
		colIndex = java.util.Arrays.asList(COL_NAMES).indexOf("" + name.charAt(0));
		rowIndex = java.util.Arrays.asList(ROW_NAMES).indexOf("" + name.charAt(1));
		validateSquare();
	}

	/**
	 *
	 * @param rowIndex row index of the square on board
	 * @param colIndex col index of the square on board
	 */
	public Square(int rowIndex, int colIndex) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		validateSquare();
	}

	/**
	 *
	 * @param rowIndex row index to be checked
	 * @param colIndex col index to be checked
	 * @return if rowIndex and colIndex is in the correct range
	 */
	public static boolean isValidIndex(int rowIndex, int colIndex) {
		return rowIndex>=0 && rowIndex<Board.HEIGHT && colIndex>=0 && colIndex<Board.WIDTH;
	}

	/**
	 * check if the parameter is valid for square. If not valid, throw Index out of bound error
	 */
	private void validateSquare() {
		if (!isValidIndex(rowIndex, colIndex)) {
			throw new IndexOutOfBoundsException("Square index out of bound for input ("+rowIndex+","+colIndex+").");
		}
	}

	/**
	 *
	 * @return the string representation of square
	 */
	@Override
	public String toString() {
		return COL_NAMES[colIndex] + ROW_NAMES[rowIndex];
	}

	/**
	 *
	 * @param anotherObj the other object to compare to
	 * @return if the other object is a square and has the same content
	 */
	@Override
	public boolean equals(Object anotherObj) {
		if (!(anotherObj instanceof Square))
			return false;
		Square another = (Square) anotherObj;
		return hashCode()==another.hashCode();
	}

	/**
	 *
	 * @return the hashcode of Square object
	 */
	@Override
	public int hashCode() {
		return rowIndex*Board.WIDTH +colIndex;
	}

	/**
	 *
	 * @param rowIx the row index to check
	 * @param colIx the col index to check
	 * @return constructed square object if rowIx and colIx are valid, otherwise null
	 */
	public static Square getSquare(int rowIx, int colIx) {
		if (Square.isValidIndex(rowIx, colIx))
			return new Square(rowIx, colIx);
		return null;
	}

}