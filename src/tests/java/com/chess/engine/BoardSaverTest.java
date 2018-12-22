package tests.java.com.chess.engine;

import main.java.com.chess.engine.*;
import main.java.com.chess.engine.pieces.King;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardSaverTest {
	@Test
	public void testSavingAndRestoringPieces(){
		Board board = new Board(BoardType.EMPTY);
		BoardSaver saver = new BoardSaver();
		Move nextMove = Move.getMove("E1D2");
		assertNotNull(nextMove);
		saver.saveBoardBeforeMove(board, nextMove);
		board.takeMove(nextMove, false);
		assertEquals(nextMove.toString(), saver.getMove().toString());
		saver.restoreBoard(board);
		assertEquals("E1", board.getKingPosition(false).toString());
		assertNotNull(board.getPiece(new Square("E1")));
		assertEquals(new King(false, board).pieceName(), board.getPiece(new Square("E1")).pieceName());
	}
}