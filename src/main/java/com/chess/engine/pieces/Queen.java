package main.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;

import java.util.List;

/**
 * Class Queen
 * Represent of rook with a binary color on a given board
 * Can be thought as a combination/compound of Bishop and Rook (i.e. can move like a rook or a bishop)
 * Can Move horizontally or vertically or diagonally until hitting a piece or boundary of the board
 */
public class Queen extends Piece {

	public static final String LABEL = "Queen";

	/**
	 *
	 * @param isBlack whether the queen is black (otherwise white)
	 * @param board the underlying board
	 */
	public Queen(boolean isBlack, Board board) {
		super(isBlack, board);
	}

	/**
	 *
	 * @return colorless pieceName of Queen
	 */
	@Override
	public String label() {
		return LABEL;
	}

	/**
	 *
	 * @param fromPos position of the piece on board
	 * @return all possible moves for queen at current position without considering King's safety
	 * Contains all move for Bishop and Rook with same color at the same location
	 * that is, Contains all move Horizontally or Vertically or Diagonally before hitting a piece or boundary of board
	 */
	@Override
	public List<Move> possibleNextMovesIgnoringCheck(Square fromPos) {
		return combinedNextMovesIgnoringCheck(
			new Rook(isBlack(), getBoard()),
			new Bishop(isBlack(), getBoard()), fromPos);
	}
}
