package tests.java.com.chess.engine;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class MoveTest {
	private List<Square> squares;
	@Before
	public void setUp() throws Exception {
		squares = new ArrayList<>();
		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx++)
			for (int colIx = 0; colIx < Board.WIDTH; colIx++)
				squares.add(new Square(rowIx, colIx));
		squares.add(null);
	}

	@Test
	public void getStartPos() {
		for (Square startPos:squares) {
			if (startPos != null) {
				for (Square endPos:squares) {
					Move move = new Move(startPos, endPos, null);
					assertEquals(move.getStartPos(), startPos);
				}
			}
		}
	}

	@Test
	public void getEndPos() {
		for (Square startPos:squares) {
			if (startPos != null) {
				for (Square endPos:squares) {
					Move move = new Move(startPos, endPos, null);
					assertEquals(move.getEndPos(), endPos);
				}
			}
		}
	}

	@Test
	public void getNextMove() {
		Move move = new Move(squares.get(0), squares.get(1), null);
		Move move1 = new Move(squares.get(1),squares.get(2),move);
		Move move2 = new Move(squares.get(2), squares.get(3), move1);
		assertEquals(move2.getNextMove().getNextMove().getStartPos(), squares.get(0));
	}

	@Test
	public void setNextMove() {
		Move toBeSet = new Move(squares.get(1),squares.get(2),null);

		Move move = new Move(squares.get(0), squares.get(1), null);
		move.setNextMove(toBeSet);
		Move move1 = new Move(squares.get(0), squares.get(1), toBeSet);
		assertEquals(move.toString(), move1.toString());
	}

	@Test
	public void testToString() {
		Move move = new Move(squares.get(0), squares.get(1), null);
		Move move1 = new Move(squares.get(1), squares.get(2), move);
		String toCompare = move1.toString();
		move1.setNextMove(null);
		assertEquals(toCompare, move1.toString()+','+move.toString());
	}

	@Test
	public void toStringSet() {
		Move move = new Move(squares.get(0), squares.get(1), null);
		Move move1 = new Move(squares.get(1), squares.get(2), null);
		List<String> concatMoveStrings = Arrays.asList(move.toString(),move1.toString());
		List<Move> moves = Arrays.asList(move,move1);
		assertEquals(new HashSet<>(concatMoveStrings),Move.toStringSet(moves));
	}

	@Test
	public void getMove() {
		for (Square startPos:squares) {
			if (startPos != null) {
				for (Square endPos:squares) {
					Move move = new Move(startPos, endPos, null);
					assertNotNull(Move.getMove(move.toString()));
					assertEquals(Move.getMove(move.toString()).toString(), move.toString());
				}
			}
		}
		Move move = new Move(squares.get(0), squares.get(1), null);
		Move move1 = new Move(squares.get(1),squares.get(2),move);
		Move move2 = new Move(squares.get(2), squares.get(3), move1);
		assertNotNull(Move.getMove(move2.toString()));
		assertEquals(Move.getMove(move2.toString()).toString(), move2.toString());
		assertNull(Move.getMove(""));
	}

	@Test
	public void getAllSquares() {
		Move move = new Move(squares.get(0), squares.get(1), null);
		Move move1 = new Move(squares.get(1),squares.get(2),move);
		Move move2 = new Move(squares.get(2), squares.get(3), move1);
		assertEquals(move2.getAllSquares(), new HashSet<>(squares.subList(0, 4)));
	}
}