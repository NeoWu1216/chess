package tests.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;
import main.java.com.chess.engine.pieces.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class KingTest {
	private Board board;

	@Before
	public void setUp() {
		board = new Board(BoardType.EMPTY);
		board.setPiece(new Square("A1"), new Rook(false, board));
		board.setPiece(new Square("H1"), new Rook(false, board));
		board.setPiece(new Square("A8"), new Rook(true, board));
		board.setPiece(new Square("H8"), new Rook(true, board));
	}

	@Test
	public void possibleNextMovesIgnoringCheck_normal() {
		board.takeMove(Move.getMove("E8D7"));
		King blackKing = board.getKing(true);
		King whiteKing = board.getKing(false);

		Set<String> nextMoveStrings = Move.toStringSet(blackKing.possibleNextMovesIgnoringCheck(new Square("D7")));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("D7C8", "D7D8", "D7E8", "D7C7", "D7E7", "D7E6", "D7D6", "D7C6"));
		assertEquals(realMoveStrings, nextMoveStrings);

		nextMoveStrings = Move.toStringSet(whiteKing.possibleNextMovesIgnoringCheck(new Square("E1")));
		realMoveStrings = new HashSet<>(
			Arrays.asList("E1D1", "E1D2", "E1E2", "E1F1", "E1F2", "E1C1,A1D1", "E1G1,H1F1"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}

	/**
	 * Case 1: unsafe square on rook's path but not on king's path is fine
	 */
	@Test
	public void possibleNextMovesIgnoringCheck_castling1() {
		board.setPiece(new Square("B5"), new Rook(true, board));
		King whiteKing = board.getKing(false);
		Set<String> nextMoveStrings = Move.toStringSet(whiteKing.possibleNextMovesIgnoringCheck(new Square("E1")));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("E1D1", "E1D2", "E1E2", "E1F1", "E1F2", "E1C1,A1D1", "E1G1,H1F1"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}


	/**
	 * Case 2: square in King's path is unsafe
	 */
	@Test
	public void possibleNextMovesIgnoringCheck_castling2() {
		King blackKing = board.getKing(true);
		assertFalse(blackKing.hasMoved());
		board.setPiece(new Square("E6"), new Bishop(false, board));

		Set<String> nextMoveStrings = Move.toStringSet(blackKing.possibleNextMovesIgnoringCheck(new Square("E8")));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("E8D8", "E8F8", "E8D7", "E8E7", "E8F7"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}

	/**
	 * Case 3: King is checked
	 */
	@Test
	public void possibleNextMovesIgnoringCheck_castling3() {
		King blackKing = board.getKing(true);
		assertFalse(blackKing.hasMoved());
		board.setPiece(new Square("E6"), new Rook(false, board));

		Set<String> nextMoveStrings = Move.toStringSet(blackKing.possibleNextMovesIgnoringCheck(new Square("E8")));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("E8D8", "E8F8", "E8D7", "E8E7", "E8F7"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}


	/**
	 * Case 4: square in King's path is unsafe
	 */
	@Test
	public void possibleNextMovesIgnoringCheck_castling4() {
		King whiteKing = board.getKing(false);
		assertFalse(whiteKing.hasMoved());
		board.setPiece(new Square("D3"), new Queen(true, board));

		Set<String> nextMoveStrings = Move.toStringSet(whiteKing.possibleNextMovesIgnoringCheck(new Square("E1")));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("E1D1", "E1D2", "E1E2", "E1F1", "E1F2"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}

	/**
	 * Case 5: squares in between is occupied
	 */
	@Test
	public void possibleNextMovesIgnoringCheck_castling5() {
		King whiteKing = board.getKing(false);
		assertFalse(whiteKing.hasMoved());
		board.setPiece(new Square("B1"), new Bishop(false, board));
		board.setPiece(new Square("F1"), new Bishop(true, board));

		Set<String> nextMoveStrings = Move.toStringSet(whiteKing.possibleNextMovesIgnoringCheck(new Square("E1")));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("E1D1", "E1D2", "E1E2", "E1F1", "E1F2"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}

	//testing if safe from threats of  knight, pawn, rook, bishop and queen, finally a fake one involving all safe
	@Test
	public void isSafe_checkKnight() {
		board.takeMove(Move.getMove("E8D7"));
		King blackKing = board.getKing(true);
		assertTrue(blackKing.isSafe(new Square("D7")));
		board.setPiece(new Square("F6"), new Knight(false, board));
		assertFalse(blackKing.isSafe(new Square("D7")));
	}

	@Test
	public void isSafe_checkPawn() {
		board.takeMove(Move.getMove("E8D7"));
		King blackKing = board.getKing(true);
		board.setPiece(new Square("E5"), new Knight(true, board));
		assertTrue(blackKing.isSafe(new Square("D7")));
		board.setPiece(new Square("E6"), new Pawn(false, board));
		assertFalse(blackKing.isSafe(new Square("D7")));
	}

	@Test
	public void isSafe_checkRook() {
		board.takeMove(Move.getMove("E8D7"));
		King blackKing = board.getKing(true);
		board.setPiece(new Square("C6"), new Pawn(true, board));
		assertTrue(blackKing.isSafe(new Square("D7")));
		board.setPiece(new Square("D4"), new Rook(false, board));
		assertFalse(blackKing.isSafe(new Square("D7")));
	}

	@Test
	public void isSafe_checkBishop() {
		board.takeMove(Move.getMove("E8D7"));
		King blackKing = board.getKing(true);
		board.setPiece(new Square("B7"), new Rook(true, board));
		assertTrue(blackKing.isSafe(new Square("D7")));
		board.setPiece(new Square("G4"), new Bishop(false, board));
		assertFalse(blackKing.isSafe(new Square("D7")));
	}

	@Test
	public void isSafe_checkQueen() {
		board.takeMove(Move.getMove("E8D7"));
		King blackKing = board.getKing(true);
		board.setPiece(new Square("E8"), new Bishop(true, board));
		assertTrue(blackKing.isSafe(new Square("D7")));
		board.setPiece(new Square("B5"), new Queen(false, board));
		assertFalse(blackKing.isSafe(new Square("D7")));
	}

	@Test
	public void isSafe_dangerousSafety() {
		board.takeMove(Move.getMove("E8D5"));
		King blackKing = board.getKing(true);
		board.setPiece(new Square("E4"), new Pawn(true, board));
		board.setPiece(new Square("G5"), new Pawn(true, board));
		board.setPiece(new Square("H5"), new Queen(false, board));
		board.setPiece(new Square("E6"), new Pawn(false, board));
		board.setPiece(new Square("D4"), new Pawn(false, board));
		board.setPiece(new Square("C6"), new Pawn(false, board));
		board.setPiece(new Square("C5"), new Bishop(false, board));
		board.setPiece(new Square("C4"), new Knight(false, board));
		board.setPiece(new Square("B5"), new Knight(false, board));
		assertTrue(blackKing.isSafe(new Square("D5")));
	}
}

