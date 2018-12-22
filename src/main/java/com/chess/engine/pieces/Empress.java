package main.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;

import java.util.List;

/**
 * Class Empress
 * A Customized chess piece
 * Represent of empress with a binary color on a given board
 * Can be thought as a combination/compound of Rook and Knight (i.e. can move like a rook or a knight)
 * See https://en.wikipedia.org/wiki/Empress_(chess) for details
 */
public class Empress extends Piece{

	public static final String LABEL = "Empress";

	/**
	 *
	 * @param isBlack whether the empress is black (otherwise white)
	 * @param board the underlying board
	 */
	public Empress(boolean isBlack, Board board) {
		super(isBlack, board);
	}

	/**
	 *
	 * @return colorless pieceName of Empress
	 */
	@Override
	public String label() {
		return LABEL;
	}

	/**
	 *
	 * @param fromPos position of the piece on board
	 * @return all possible moves for empress at current position without considering King's safety
	 * Contains all move for Rook and Knight with same color at the same location
	 */
	@Override
	public List<Move> possibleNextMovesIgnoringCheck(Square fromPos) {
		return combinedNextMovesIgnoringCheck(
			new Knight(isBlack(), getBoard()),
			new Rook(isBlack(), getBoard()), fromPos);
	}
}
