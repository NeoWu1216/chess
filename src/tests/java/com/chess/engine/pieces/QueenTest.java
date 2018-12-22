package tests.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.pieces.Pawn;
import main.java.com.chess.engine.pieces.Queen;
import main.java.com.chess.engine.Square;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class QueenTest {

	@Test
	public void possibleNextMovesIgnoringCheck() {
		Board board = new Board(BoardType.EMPTY);
		Queen rook = new Queen(true, board);
		Square pos = new Square("G2");
		board.setPiece(pos, rook);
		board.setPiece(new Square("G4"), new Pawn(false, board));
		board.setPiece(new Square("E2"), new Pawn(true, board));
		board.setPiece(new Square("E4"), new Queen(false, board));
		Set<String> nextMoveStrings = Move.toStringSet(rook.possibleNextMovesIgnoringCheck(pos));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("G2G1","G2H2","G2F1","G2H1","G2F2","G2H3","G2G3","G2F3","G2E4", "G2G4"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}
}