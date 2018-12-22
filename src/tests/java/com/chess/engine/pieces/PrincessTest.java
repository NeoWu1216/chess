package tests.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.pieces.Pawn;
import main.java.com.chess.engine.pieces.Princess;
import main.java.com.chess.engine.Square;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PrincessTest {
	private Board board;
	private Princess princess;
	private Square posPrincess;

	@Before
	public void setUp() {
		board = new Board(BoardType.EMPTY);
		princess = new Princess(false, board);
		posPrincess = new Square("E4");
		board.setPiece(posPrincess, princess);
	}

	/**
	 * Test based on the first diagram of https://en.wikipedia.org/wiki/Princess_(chess)#Movement
	 */
	@Test
	public void possibleNextMovesIgnoringCheck_WithPawns() {
		board.setPiece(new Square("E5"), new Pawn(false, board));
		board.setPiece(new Square("F4"), new Pawn(false, board));
		board.setPiece(new Square("G6"), new Pawn(false, board));
		board.setPiece(new Square("C2"), new Pawn(true, board));
		Set<String> nextMoveStrings = Move.toStringSet(princess.possibleNextMovesIgnoringCheck(posPrincess));
		Set<String> realMoveStrings = new HashSet<>();
		realMoveStrings.add("E4D6");
		realMoveStrings.add("E4F6");
		realMoveStrings.add("E4D2");
		realMoveStrings.add("E4F2");
		realMoveStrings.add("E4C5");
		realMoveStrings.add("E4G5");
		realMoveStrings.add("E4C3");
		realMoveStrings.add("E4G3");

		realMoveStrings.add("E4D5");
		realMoveStrings.add("E4C6");
		realMoveStrings.add("E4B7");
		realMoveStrings.add("E4A8");
		realMoveStrings.add("E4F3");
		realMoveStrings.add("E4G2");
		realMoveStrings.add("E4H1");

		realMoveStrings.add("E4D3");
		realMoveStrings.add("E4C2");
		realMoveStrings.add("E4F5");
		assertEquals(realMoveStrings, nextMoveStrings);
		board.printBoard();
	}

	/**
	 * Test based on the second diagram of https://en.wikipedia.org/wiki/Princess_(chess)#Movement
	 */
	@Test
	public void possibleNextMovesIgnoringCheck_MaxRange() {
		Set<String> nextMoveStrings = Move.toStringSet(princess.possibleNextMovesIgnoringCheck(posPrincess));
		Set<String> realMoveStrings = new HashSet<>();
		realMoveStrings.add("E4D6");
		realMoveStrings.add("E4F6");
		realMoveStrings.add("E4D2");
		realMoveStrings.add("E4F2");
		realMoveStrings.add("E4C5");
		realMoveStrings.add("E4G5");
		realMoveStrings.add("E4C3");
		realMoveStrings.add("E4G3");

		realMoveStrings.add("E4D5");
		realMoveStrings.add("E4C6");
		realMoveStrings.add("E4B7");
		realMoveStrings.add("E4A8");
		realMoveStrings.add("E4F3");
		realMoveStrings.add("E4G2");
		realMoveStrings.add("E4H1");

		realMoveStrings.add("E4D3");
		realMoveStrings.add("E4C2");
		realMoveStrings.add("E4B1");
		realMoveStrings.add("E4F5");
		realMoveStrings.add("E4G6");
		realMoveStrings.add("E4H7");
		assertEquals(realMoveStrings, nextMoveStrings);
	}


}