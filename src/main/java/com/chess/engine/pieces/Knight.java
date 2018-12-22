package main.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Knight
 * Represent of knight with a binary color on a given board
 * Can Capture or Move to 1 square vertically and 2 squares horizontally or 2 squares vertically and 1 square horizontally
 */
public class Knight extends Piece {

	public static final String LABEL = "Knight";

	/**
	 *
	 * @param isBlack whether the knight is black (otherwise white)
	 * @param board the underlying board
	 */
	public Knight(boolean isBlack, Board board) {
		super(isBlack, board);
	}

	/**
	 *
	 * @return colorless pieceName of Knight
	 */
	@Override
	public String label() {
		return LABEL;
	}

	/**
	 *
	 * @param fromPos position of the piece on board
	 * @return all possible moves for queen at current position without considering King's safety
	 * Contains all move capturing piece or taking a square on the other valid diagonal endpoint of a 1 by 2 rectangle
	 */
	@Override
	public List<Move> possibleNextMovesIgnoringCheck(Square fromPos) {
		List<Move> nextMoves = new ArrayList<>();
		for (int colInc = -2; colInc <= +2; colInc+=1) {
			if (colInc != 0) {
				int colIxToCheck = fromPos.colIx() + colInc;
				int rowInc = 3 - Math.abs(colInc);
				for (int rowIxToCheck : new int[]{fromPos.rowIx() + rowInc, fromPos.rowIx() - rowInc}) {
					addMoveCapturingOrTaking(fromPos, rowIxToCheck, colIxToCheck, nextMoves);
				}
			}
		}
		return nextMoves;
	}
}
