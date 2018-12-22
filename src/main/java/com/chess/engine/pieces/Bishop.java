package main.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Bishop
 * Represent of bishop with a binary color on a given board
 * Can Move diagonally until hitting a piece or boundary of the board
 */
public class Bishop extends Piece {

	public static final String LABEL = "Bishop";

	/**
	 *
	 * @param isBlack whether the bishop is black (otherwise white)
	 * @param board the underlying board
	 */
	public Bishop(boolean isBlack, Board board) {
		super(isBlack, board);
	}

	/**
	 *
	 * @return colorless pieceName of Bishop
	 */
	@Override
	public String label() {
		return LABEL;
	}

	/**
	 *
	 * @param fromPos position of the piece on board
	 * @return all possible moves for bishop at current position without considering King's safety
	 * Contains all move diagonally before hitting a piece or boundary of board
	 */
	public List<Move> possibleNextMovesIgnoringCheck(Square fromPos) {
		List<Move> nextMoves = new ArrayList<>();
		addMoveAlongRay(fromPos, nextMoves, -1, 1);
		addMoveAlongRay(fromPos, nextMoves, 1, -1);
		addMoveAlongRay(fromPos, nextMoves, -1, -1);
		addMoveAlongRay(fromPos, nextMoves, 1, 1);
		return nextMoves;
	}
}
