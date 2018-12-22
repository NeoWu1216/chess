package tests.java.com.chess.engine;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;
import main.java.com.chess.engine.pieces.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class BoardTest {

	@Test
	public void possibleNextMoves() {
		Board board = new Board(BoardType.CUSTOMIZED);
		assertEquals(Move.toStringSet(board.possibleNextMoves(new Square("G1"))),
			new HashSet<>(Arrays.asList("G1F3", "G1H3")));
	}

	/**
	 * walk through the whole Fool's mate example
	 */
	@Test
	public void allPossibleNextMoves() {
		Board board = new Board();
		Set<String> whiteMoves = new HashSet<>(Arrays.asList("A2A3","A2A4","B2B3","B2B4","C2C3","C2C4",
			"D2D3","D2D4","E2E3","E2E4", "F2F3","F2F4","G2G3","G2G4", "H2H3","H2H4","B1A3","B1C3","G1F3", "G1H3"));
		assertEquals(whiteMoves, Move.toStringSet(board.allPossibleNextMoves(false)));
		board.takeMove(Move.getMove("F2F3"));
		whiteMoves.remove("F2F3");
		whiteMoves.remove("F2F4");
		whiteMoves.remove("G1F3");
		whiteMoves.add("E1F2");
		whiteMoves.add("F3F4");
		// In opening, opponent's move can't disrupt original set of moves

		Set<String> blackMoves =  new HashSet<>(Arrays.asList("A7A5","A7A6","B7B5","B7B6","C7C5","C7C6","D7D5","D7D6",
			"E7E5","E7E6","F7F5","F7F6","G7G5","G7G6","H7H5","H7H6","B8A6","B8C6","G8F6","G8H6"));
		assertEquals(blackMoves, Move.toStringSet(board.allPossibleNextMoves(true)));
		board.takeMove(Move.getMove("E7E5"));
		blackMoves.remove("E7E5");
		blackMoves.remove("E7E6");
		blackMoves.addAll(Arrays.asList("D8E7","D8F6","D8G5","D8H4","E8E7","F8E7","F8D6","F8C5","F8B4","F8A3","G8E7","E5E4"));

		assertEquals(whiteMoves, Move.toStringSet(board.allPossibleNextMoves(false)));
		assertTrue(whiteMoves.contains("G2G4"));
		board.takeMove(Move.getMove("G2G4"));

		assertEquals(blackMoves, Move.toStringSet(board.allPossibleNextMoves(true)));
		assertTrue(blackMoves.contains("D8H4"));
		board.takeMove(Move.getMove("D8H4"));

		assertEquals(0, board.allPossibleNextMoves(false).size());
		assertTrue(board.isCheckmated(false));
	}

	@Test
	public void testingPromotionWhite() {
		Board board = new Board(BoardType.EMPTY);
		board.setPiece(new Square("B7"),new Pawn(false, board));
		assertFalse(board.hasPendingPromotion());
		board.takeMove(Move.getMove("B7B8"), true);
		assertTrue(board.hasPendingPromotion());
		board.promotePawn(Knight.LABEL);
		assertEquals(new Knight(false, board).pieceName(), board.getPiece(new Square("B8")).pieceName());

		board.setPiece(new Square("H7"),new Pawn(false, board));
		board.takeMove(Move.getMove("H7H8"));
		assertTrue(board.hasPendingPromotion());
		board.promotePawn(Bishop.LABEL);
		assertEquals(new Bishop(false, board).pieceName(), board.getPiece(new Square("H8")).pieceName());
	}

	@Test
	public void testingPromotionBlack() {
		Board board = new Board(BoardType.EMPTY);
		board.setPiece(new Square("C2"), new Pawn(true, board));
		board.takeMove(Move.getMove("C2C1"));
		assertTrue(board.hasPendingPromotion());
		board.promotePawn(Queen.LABEL);
		assertEquals(new Queen(true, board).pieceName(), board.getPiece(new Square("C1")).pieceName());

		board.setPiece(new Square("D2"), new Pawn(true, board));
		board.takeMove(Move.getMove("D2D1"));
		assertTrue(board.hasPendingPromotion());
		board.promotePawn(Rook.LABEL);
		assertEquals(new Rook(true, board).pieceName(), board.getPiece(new Square("D1")).pieceName());
	}




	// For Extra Credit, testing Stalemates
	// https://en.wikipedia.org/wiki/Stalemate#Simple_examples
	@Test
	public void testSimpleStalemate1() {
		Board board = new Board(BoardType.EMPTY);
		board.takeMove(Move.getMove("E8F8"), false);
		board.takeMove(Move.getMove("E1F6"), false);
		board.setPiece(new Square("F7"), new Pawn(false, board));
		assertTrue(board.inStalemate(true));
		assertFalse(board.isCheckmated(true));
	}

	@Test
	public void testSimpleStalemate2() {
		Board board = new Board(BoardType.EMPTY);
		board.takeMove(Move.getMove("E8A8"), false);
		board.takeMove(Move.getMove("E1B6"), false);
		board.setPiece(new Square("B8"), new Bishop(true, board));
		board.setPiece(new Square("H8"), new Rook(false, board));
		assertTrue(board.inStalemate(true));
		assertFalse(board.isCheckmated(true));
	}

	@Test
	public void testSimpleStalemate3() {
		Board board = new Board(BoardType.EMPTY);
		board.takeSimulatedMove(Move.getMove("E8A1"));
		board.takeSimulatedMove(Move.getMove("E1C3"));
		board.setPiece(new Square("B2"), new Rook(false, board));
		assertTrue(board.inStalemate(true));
		assertFalse(board.isCheckmated(true));
	}

	@Test
	public void testSimpleStalemate4() {
		Board board = new Board(BoardType.EMPTY);
		board.takeMove(Move.getMove("E8A1"), false);
		board.takeMove(Move.getMove("E1G5"), false);
		board.setPiece(new Square("A2"), new Pawn(true, board));
		board.setPiece(new Square("B3"), new Queen(false, board));
		assertTrue(board.inStalemate(true));
		assertFalse(board.isCheckmated(true));
	}

	@Test
	public void testSimpleStalemate5() {
		Board board = new Board(BoardType.EMPTY);
		board.takeMove(Move.getMove("E8A8"), false);
		board.takeMove(Move.getMove("E1A6"), false);
		board.setPiece(new Square("F4"), new Bishop(false, board));
		board.setPiece(new Square("A7"), new Pawn(false, board));
		assertTrue(board.inStalemate(true));
		assertFalse(board.isCheckmated(true));
	}


	//For Extra Credit, testing Checkmates
	//https://en.wikipedia.org/wiki/Checkmate#Examples
	@Test
	public void testFoolMate() {
		Board board = new Board();
		board.takeMove(Move.getMove("F2F3"));
		board.takeMove(Move.getMove("E7E5"));
		board.takeMove(Move.getMove("G2G4"));
		board.takeMove(Move.getMove("D8H4"));
		assertTrue(board.isCheckmated(false));
		assertFalse(board.inStalemate(false));
	}

	@Test
	public void testGameOfCentryMate() {
		Board board = new Board(BoardType.EMPTY);
		board.takeMove(Move.getMove("E8G7"));
		board.takeMove(Move.getMove("E1C1"));
		board.setPiece(new Square("B8"), new Queen(false, board));
		board.setPiece(new Square("E5"), new Knight(false, board));
		board.setPiece(new Square("G2"), new Pawn(false, board));
		board.setPiece(new Square("H4"), new Pawn(false, board));
		board.setPiece(new Square("H5"), new Pawn(true, board));
		board.setPiece(new Square("G6"), new Pawn(true, board));
		board.setPiece(new Square("F7"), new Pawn(true, board));
		board.setPiece(new Square("C6"), new Pawn(true, board));
		board.setPiece(new Square("B5"), new Pawn(true, board));
		board.setPiece(new Square("B4"), new Bishop(true, board));
		board.setPiece(new Square("B3"), new Bishop(true, board));
		board.setPiece(new Square("C3"), new Knight(true, board));
		board.setPiece(new Square("C2"), new Rook(true, board));
		assertTrue(board.isCheckmated(false));
		assertFalse(board.inStalemate(false));
		board.printBoard(); //Example how I know that the board is correct
	}

	@Test
	public void testCheckMateWithRook() {
		Board board = new Board(BoardType.EMPTY);
		board.takeMove(Move.getMove("E8H5"));
		board.takeMove(Move.getMove("E1F5"));
		board.setPiece(new Square("H1"), new Rook(false, board));
		assertTrue(board.isCheckmated(true));
		assertFalse(board.inStalemate(true));
	}
}
