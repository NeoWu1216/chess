package tests.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.pieces.Queen;
import main.java.com.chess.engine.pieces.Rook;
import main.java.com.chess.engine.Square;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RookTest {

	@Test
	public void possibleNextMovesIgnoringCheck() {
		Board board = new Board(BoardType.EMPTY);
		Rook rook = new Rook(true, board);
		Square pos = new Square("B2");
		board.setPiece(pos, rook);
		board.setPiece(new Square("D2"), new Queen(false, board));
		board.setPiece(new Square("B5"), new Queen(true, board));
		Set<String> nextMoveStrings = Move.toStringSet(rook.possibleNextMovesIgnoringCheck(pos));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("B2B1", "B2A2", "B2C2", "B2D2", "B2B3", "B2B4"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}

	@Test
	public void possibleNextMovesIgnoringCheck_UpRight() {
		Board board = new Board(BoardType.EMPTY);
		Rook rook = new Rook(true, board);
		Square pos = new Square("G6");
		board.setPiece(pos, rook);
		board.setPiece(new Square("G5"), new Queen(false, board));
		board.setPiece(new Square("F6"), new Queen(true, board));
		Set<String> nextMoveStrings = Move.toStringSet(rook.possibleNextMovesIgnoringCheck(pos));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("G6H6", "G6G5", "G6G7", "G6G8"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}
}

