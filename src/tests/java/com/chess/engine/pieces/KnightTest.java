package tests.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.pieces.Knight;
import main.java.com.chess.engine.pieces.Queen;
import main.java.com.chess.engine.Square;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class KnightTest {

	@Test
	public void possibleNextMovesIgnoringCheck() {
		Board board = new Board(BoardType.EMPTY);
		Knight knight = new Knight(true, board);
		Square pos = new Square("B4");
		Set<String> nextMoveStrings = Move.toStringSet(knight.possibleNextMovesIgnoringCheck(pos));
		Set<String> realMoveStrings = new HashSet<>();
		realMoveStrings.add("B4C6");
		realMoveStrings.add("B4C2");
		realMoveStrings.add("B4D5");
		realMoveStrings.add("B4D3");
		realMoveStrings.add("B4A6");
		realMoveStrings.add("B4A2");
		assertEquals(realMoveStrings, nextMoveStrings);

		board.setPiece(new Square("C6"), new Queen(true, board));
		board.setPiece(new Square("D3"), new Queen(false, board));
		realMoveStrings.remove("B4C6");
		nextMoveStrings = Move.toStringSet(knight.possibleNextMovesIgnoringCheck(pos));
		assertEquals(realMoveStrings, nextMoveStrings);
	}
}