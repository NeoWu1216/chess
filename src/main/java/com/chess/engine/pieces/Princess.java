package main.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;

import java.util.List;

/**
 * Class Princess
 * A Customized chess piece
 * Represent of princess with a binary color on a given board
 * Can be thought as a combination/compound of Bishop and Knight (i.e. can move like a bishop or a knight)
 * See https://en.wikipedia.org/wiki/Princess_(chess) for details
 */
public class Princess extends Piece{

	public static final String LABEL = "Princess";

	/**
	 *
	 * @param isBlack whether the princess is black (otherwise white)
	 * @param board the underlying board
	 */
	public Princess(boolean isBlack, Board board) {
		super(isBlack, board);
	}

	/**
	 *
	 * @return colorless pieceName of Princess
	 */
	@Override
	public String label() {
		return LABEL;
	}

	/**
	 *
	 * @param fromPos position of the piece on board
	 * @return all possible moves for princess at current position without considering King's safety
	 * Contains all move for Bishop and Knight with same color at the same location
	 */
	@Override
	public List<Move> possibleNextMovesIgnoringCheck(Square fromPos) {
		return combinedNextMovesIgnoringCheck(
			new Knight(isBlack(), getBoard()),
			new Bishop(isBlack(), getBoard()), fromPos);
	}
}
