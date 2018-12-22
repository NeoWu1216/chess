package tests.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;
import main.java.com.chess.engine.pieces.Knight;
import main.java.com.chess.engine.pieces.Piece;
import main.java.com.chess.engine.Square;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PieceTest {
	Piece piece;
	Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board(BoardType.NORMAL);
		piece = new Knight(true, board);
	}

	@Test
	public void testMove() {
		assertFalse(piece.hasMoved());
		piece.move();
		assertTrue(piece.hasMoved());
	}

	@Test
	public void isBlack() {
		assertTrue(piece.isBlack());
	}

	@Test
	public void testName() {
		Set<String> labels = new HashSet<>();
		Set<String> pieceNames = new HashSet<>();
		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx+=Board.HEIGHT -1) {
			for (int colIx = 0; colIx < 5; colIx++) {
				Piece piece = board.getPiece(new Square(rowIx, colIx));
				if (piece != null) {
					assertFalse(pieceNames.contains(piece.pieceName()));
					pieceNames.add(piece.pieceName());
					assertEquals(!piece.isBlack(), labels.contains(piece.label()));
					labels.add(piece.label());
				}
			}
		}

		Piece blackPawn1 = board.getPiece(new Square("a7"));
		Piece blackPawn2 = board.getPiece(new Square("b7"));
		Piece whitePawn1 = board.getPiece(new Square("c2"));
		Piece whitePawn2 = board.getPiece(new Square("e2"));
		assertEquals(blackPawn1.pieceName(), blackPawn2.pieceName());
		assertEquals(whitePawn1.pieceName(), whitePawn2.pieceName());
		assertNotEquals(blackPawn1.pieceName(), whitePawn1.pieceName());
		assertNotEquals(blackPawn2.pieceName(), whitePawn2.pieceName());
		assertEquals(blackPawn1.label(), blackPawn2.label());
		assertEquals(whitePawn1.label(), whitePawn2.label());
	}

}