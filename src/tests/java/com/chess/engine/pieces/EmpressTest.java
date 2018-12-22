package tests.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.pieces.Empress;
import main.java.com.chess.engine.pieces.Pawn;
import main.java.com.chess.engine.Square;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class EmpressTest {
	private Board board;
	private Empress empress;
	private Square posEmpress;

	@Before
	public void setUp() {
		board = new Board(BoardType.EMPTY);
		empress = new Empress(false, board);
		posEmpress = new Square("E4");
		board.setPiece(posEmpress, empress);
		board.takeSimulatedMove(Move.getMove("E8D8"));
		board.takeSimulatedMove(Move.getMove("E1D1"));
	}

	/**
	 * Test based on the first diagram of https://en.wikipedia.org/wiki/Empress_(chess)#Movement
	 */
	@Test
	public void possibleNextMovesIgnoringCheck_WithPawns() {
		board.setPiece(new Square("D5"), new Pawn(false, board));
		board.setPiece(new Square("F3"), new Pawn(false, board));
		board.setPiece(new Square("G4"), new Pawn(false, board));
		board.setPiece(new Square("E7"), new Pawn(true, board));
		Set<String> nextMoveStrings = Move.toStringSet(empress.possibleNextMovesIgnoringCheck(posEmpress));
		Set<String> realMoveStrings = new HashSet<>();
		realMoveStrings.add("E4D6");
		realMoveStrings.add("E4F6");
		realMoveStrings.add("E4D2");
		realMoveStrings.add("E4F2");
		realMoveStrings.add("E4C5");
		realMoveStrings.add("E4G5");
		realMoveStrings.add("E4C3");
		realMoveStrings.add("E4G3");

		realMoveStrings.add("E4D4");
		realMoveStrings.add("E4C4");
		realMoveStrings.add("E4B4");
		realMoveStrings.add("E4A4");
		realMoveStrings.add("E4E3");
		realMoveStrings.add("E4E2");
		realMoveStrings.add("E4E1");

		realMoveStrings.add("E4F4");
		realMoveStrings.add("E4E5");
		realMoveStrings.add("E4E6");
		realMoveStrings.add("E4E7");
		assertEquals(realMoveStrings, nextMoveStrings);
		board.printBoard();
	}

	/**
	 * Test based on the second diagram of https://en.wikipedia.org/wiki/Empress_(chess)#Movement
	 */
	@Test
	public void possibleNextMovesIgnoringCheck_MaxRange() {
		Set<String> nextMoveStrings = Move.toStringSet(empress.possibleNextMovesIgnoringCheck(posEmpress));
		Set<String> realMoveStrings = new HashSet<>();
		realMoveStrings.add("E4D6");
		realMoveStrings.add("E4F6");
		realMoveStrings.add("E4D2");
		realMoveStrings.add("E4F2");
		realMoveStrings.add("E4C5");
		realMoveStrings.add("E4G5");
		realMoveStrings.add("E4C3");
		realMoveStrings.add("E4G3");

		realMoveStrings.add("E4D4");
		realMoveStrings.add("E4C4");
		realMoveStrings.add("E4B4");
		realMoveStrings.add("E4A4");
		realMoveStrings.add("E4E3");
		realMoveStrings.add("E4E2");
		realMoveStrings.add("E4E1");

		realMoveStrings.add("E4F4");
		realMoveStrings.add("E4G4");
		realMoveStrings.add("E4H4");
		realMoveStrings.add("E4E5");
		realMoveStrings.add("E4E6");
		realMoveStrings.add("E4E7");
		realMoveStrings.add("E4E8");
		assertEquals(realMoveStrings, nextMoveStrings);
	}


}