package tests.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.pieces.Pawn;
import main.java.com.chess.engine.pieces.Piece;
import main.java.com.chess.engine.pieces.Rook;
import main.java.com.chess.engine.Square;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PawnTest {
	@Test
	public void possibleNextMovesIgnoringCheck_basics() {
		Board board = new Board(BoardType.EMPTY);
		Square posBlackPawn = new Square("B7");
		Piece blackPawn = new Pawn(true, board);
		board.setPiece(posBlackPawn, blackPawn);
		board.setPiece(new Square("A6"), new Rook(true, board));
		board.setPiece(new Square("C6"), new Rook(false, board));
		Set<String> nextMoveStrings = Move.toStringSet(blackPawn.possibleNextMovesIgnoringCheck(posBlackPawn));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("B7C6", "B7B6", "B7B5"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}

	@Test
	public void possibleNextMovesIgnoringCheck_basics_first_move() {
		Board board = new Board(BoardType.EMPTY);
		Square posBlackPawn = new Square("B7");
		Piece blackPawn = new Pawn(true, board);
		board.setPiece(posBlackPawn, blackPawn);

		board.setPiece(new Square("B6"), new Rook(true, board));
		Set<String> nextMoveStrings = Move.toStringSet(blackPawn.possibleNextMovesIgnoringCheck(posBlackPawn));
		Set<String> realMoveStrings = new HashSet<>();
		assertEquals(realMoveStrings, nextMoveStrings);
		board.setPiece(new Square("B6"), null);


		board.setPiece(new Square("B5"), new Rook(false, board));
		nextMoveStrings = Move.toStringSet(blackPawn.possibleNextMovesIgnoringCheck(posBlackPawn));
		realMoveStrings = new HashSet<>(Arrays.asList("B7B6"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}

	@Test
	public void possibleNextMovesIgnoringCheck_Enpassant1() {
		Board board = new Board(BoardType.EMPTY);
		Square posPawn = new Square("G4");
		Piece pawn = new Pawn(true, board);
		board.setPiece(posPawn, pawn);
		pawn.move();
		board.setPiece(new Square("H2"), new Pawn(false, board));
		board.takeMove(Move.getMove("H2H4"));
		Set<String> nextMoveStrings = Move.toStringSet(pawn.possibleNextMovesIgnoringCheck(posPawn));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("G4G3","G4H3,H4"+Move.NULL_STRING));
		assertEquals(realMoveStrings, nextMoveStrings);

	}

	@Test
	public void possibleNextMovesIgnoringCheck_Enpassant2() {
		Board board = new Board(BoardType.EMPTY);
		Square posPawn = new Square("A5");
		Piece pawn = new Pawn(false, board);
		board.setPiece(posPawn, pawn);
		pawn.move();
		board.setPiece(new Square("B7"), new Pawn(true, board));
		board.takeMove(Move.getMove("B7B5"));
		Set<String> nextMoveStrings = Move.toStringSet(pawn.possibleNextMovesIgnoringCheck(posPawn));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("A5A6","A5B6,B5"+Move.NULL_STRING));
		assertEquals(realMoveStrings, nextMoveStrings);
	}

	@Test
	public void possibleNextMovesIgnoringCheck_Enpassant3() {
		Board board = new Board(BoardType.EMPTY);
		Square posBlackPawn = new Square("A5");
		Piece blackPawn = new Pawn(false, board);
		board.setPiece(posBlackPawn, blackPawn);
		blackPawn.move();
		board.setPiece(new Square("B7"), new Rook(true, board));
		board.takeMove(Move.getMove("B7B5"));
		Set<String> nextMoveStrings = Move.toStringSet(blackPawn.possibleNextMovesIgnoringCheck(posBlackPawn));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("A5A6"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}


	@Test
	public void possibleNextMovesIgnoringCheck_ToBePromote() {
		Board board = new Board(BoardType.EMPTY);
		Square posBlackPawn = new Square("B2");
		Piece blackPawn = new Pawn(true, board);
		board.setPiece(posBlackPawn, blackPawn);
		blackPawn.move();
		board.setPiece(new Square("C1"), new Rook(false, board));
		Set<String> nextMoveStrings = Move.toStringSet(blackPawn.possibleNextMovesIgnoringCheck(posBlackPawn));
		Set<String> realMoveStrings = new HashSet<>(
			Arrays.asList("B2C1","B2B1"));
		assertEquals(realMoveStrings, nextMoveStrings);
	}

}