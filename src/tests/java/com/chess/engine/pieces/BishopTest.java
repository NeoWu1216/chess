package tests.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.pieces.Bishop;
import main.java.com.chess.engine.pieces.Queen;
import main.java.com.chess.engine.Square;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class BishopTest {
	@Test
	public void possibleNextMovesIgnoringCheck_Up() {
		Board board = new Board(BoardType.EMPTY);
		Bishop rook = new Bishop(true, board);
		Square pos = new Square("C7");
		board.setPiece(pos, rook);
		board.setPiece(new Square("F4"), new Queen(false, board));
		board.setPiece(new Square("B6"), new Queen(true, board));
		Set<String> nextMoveStrings = Move.toStringSet(rook.possibleNextMovesIgnoringCheck(pos));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("C7B8","C7D8","C7D6","C7E5","C7F4"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}

	@Test
	public void possibleNextMovesIgnoringCheck_Down() {
		Board board = new Board(BoardType.EMPTY);
		Bishop bishop = new Bishop(true, board);
		Square pos = new Square("B2");
		board.setPiece(pos, bishop);
		board.setPiece(new Square("C3"), new Queen(false, board));
		board.setPiece(new Square("A3"), new Queen(true, board));
		Set<String> nextMoveStrings = Move.toStringSet(bishop.possibleNextMovesIgnoringCheck(pos));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("B2C3","B2C1","B2A1"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}
}