package tests.java.com.chess.engine;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Square;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SquareTest{

	private List<Square> squares;
	@Before
	public void setUp(){
		squares = new ArrayList<>();
		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx++)
			for (int colIx = 0; colIx < Board.WIDTH; colIx++)
				squares.add(new Square(rowIx, colIx));
	}

	@Test
	public void testInvalidIxs() {
		boolean caught = false;
		try {
			new Square(Board.HEIGHT, Board.WIDTH);
		} catch (IndexOutOfBoundsException ib) {
			caught = true;
		}
		assertTrue(caught);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testInvalidString() {
		new Square("E9");
	}

	// Below are for testing individual methods
	@Test
	public void getRowIndex() {
		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx++)
			for (int colIx = 0; colIx < Board.WIDTH; colIx++)
				assertEquals(new Square(rowIx, colIx).rowIx(), rowIx);
	}

	@Test
	public void getColIndex() {
		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx++)
			for (int colIx = 0; colIx < Board.WIDTH; colIx++)
				assertEquals(new Square(rowIx, colIx).colIx(), colIx);
	}

	@Test
	public void isValidIndex() {
		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx++)
			for (int colIx = 0; colIx < Board.WIDTH; colIx++)
				assertTrue(Square.isValidIndex(rowIx, colIx));

		for (int colIx = 0; colIx < Board.WIDTH; colIx++) {
			assertFalse(Square.isValidIndex(-1, colIx));
			assertFalse(Square.isValidIndex(Board.HEIGHT, colIx));
		}

		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx++) {
			assertFalse(Square.isValidIndex(rowIx, -1));
			assertFalse(Square.isValidIndex(rowIx, Board.WIDTH));
		}
	}


	@Test
	public void equals() {
		for (Square square1 : squares) {
			for (Square square2 : squares)  {
				boolean sameRow = square1.rowIx()==square2.rowIx();
				boolean sameCol = square1.colIx()==square2.colIx();
				assertEquals(sameRow&&sameCol, square1.equals(square2));
			}
			assertNotEquals(square1, new Board());
		}
	}


	@Test
	public void testFromToString() {
		for (Square square1 : squares) {
			assertEquals(new Square(square1.toString()), square1);
			for (Square square2 : squares)  {
				assertEquals(square1.equals(square2), square1.toString().equals(square2.toString()));
			}
		}
		assertEquals("C7", new Square(1,2).toString().toUpperCase());
		assertEquals(new Square("C7"), new Square(1,2));
		assertEquals(new Square("H8"), new Square(0, 7));
	}

	@Test
	public void testHashCode() {
		for (Square square1 : squares) {
			for (Square square2 : squares)  {
				assertEquals(square1.equals(square2), square1.hashCode()==square2.hashCode());
			}
		}
	}

	@Test
	public void getSquare() {
		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx++)
			for (int colIx = 0; colIx < Board.WIDTH; colIx++)
				assertNotNull(Square.getSquare(rowIx, colIx));

		for (int colIx = 0; colIx < Board.WIDTH; colIx++) {
			assertNull(Square.getSquare(-1, colIx));
			assertNull(Square.getSquare(Board.HEIGHT, colIx));
		}

		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx++) {
			assertNull(Square.getSquare(rowIx, -1));
			assertNull(Square.getSquare(rowIx, Board.WIDTH));
		}
	}
}

