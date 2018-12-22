package tests.java.com.chess.engine;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.PawnPromotionHandler;
import main.java.com.chess.engine.Square;
import main.java.com.chess.engine.pieces.Knight;
import main.java.com.chess.engine.pieces.Pawn;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PawnPromotionHandlerTest {
	private Board board;
	private PawnPromotionHandler promotionChecker;

	@Before
	public void setUp(){
		board = new Board();
		promotionChecker = new PawnPromotionHandler(board);
	}

	@Test
	public void testingCheckAndUpdate() {
		board.setPiece(new Square("B7"),new Pawn(false, board));
		assertFalse(promotionChecker.hasPendingPromotion());
		promotionChecker.checkAndUpdate(Move.getMove("B7B8"));
		assertTrue(promotionChecker.hasPendingPromotion());
		boolean throwPendingPromotion = false;
		try {
			promotionChecker.checkAndUpdate(Move.getMove("B8C8"));
		} catch (Exception except) {
			throwPendingPromotion = true;
		}
		assertTrue(throwPendingPromotion);

	}

	@Test
	public void testingPromotion() {
		board.setPiece(new Square("B7"),new Pawn(false, board));
		assertFalse(promotionChecker.hasPendingPromotion());
		boolean throwNoPromotion = false;
		try {
			promotionChecker.promotePawn(Knight.LABEL);
		} catch (Exception except) {
			throwNoPromotion = true;
		}
		assertTrue(throwNoPromotion);

		promotionChecker.checkAndUpdate(Move.getMove("B7B8"));
		board.takeMove(Move.getMove("B7B8"));
		assertTrue(promotionChecker.hasPendingPromotion());
		boolean throwFakePromotion = false;
		try {
			promotionChecker.promotePawn("hello world");
		} catch (Exception except) {
			throwFakePromotion = true;
		}
		assertTrue(throwFakePromotion);
		promotionChecker.promotePawn(Knight.LABEL);
		assertEquals(new Knight(false, board).pieceName(), board.getPiece(new Square("B8")).pieceName());
	}
}