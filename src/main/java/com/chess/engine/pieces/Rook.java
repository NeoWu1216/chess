package main.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Rook
 * Represent of rook with a binary color on a given board
 * Can Move horizontally or vertically until hitting a piece or boundary of the board
 */
public class Rook extends Piece {

	public static final String LABEL = "Rook";

	/**
	 *
	 * @param isBlack whether the rook is black (otherwise white)
	 * @param board the underlying board
	 */
	public Rook(boolean isBlack, Board board) {
		super(isBlack, board);
	}

	/**
	 *
	 * @return colorless pieceName of Rook
	 */
	@Override
	public String label() {
		return LABEL;
	}

	/**
	 *
	 * @param fromPos position of the piece on board
	 * @return all possible moves for rook at current position without considering King's safety
	 * Contains all move Horizontally or Vertically before hitting a piece or boundary of board
	 */
	@Override
	public List<Move> possibleNextMovesIgnoringCheck(Square fromPos) {
		List<Move> nextMoves = new ArrayList<>();
		addMoveAlongRay(fromPos, nextMoves, 0, 1);
		addMoveAlongRay(fromPos, nextMoves, 1, 0);
		addMoveAlongRay(fromPos, nextMoves, 0, -1);
		addMoveAlongRay(fromPos, nextMoves, -1, 0);
		return nextMoves;
	}
}
