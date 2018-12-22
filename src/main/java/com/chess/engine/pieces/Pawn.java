package main.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Pawn
 * Represent of pawn with a binary color on a given board
 * Have 4 special kinds of movements:
 * 1. can move 2 squares up (from player's side) if not moved previously, if there is no piece on the way
 * 2. can move 1 square up, if there is no piece at that location
 * 3. can capture a piece or an empty square on 1 by 1 diagonal location up.
 * 4. En passant: If an enemy pawn uses movement 1 on previous move and is on piece's left or right,
 *    can take it down and go diagonally towards enemy's column
 * Note I didn't represent promotion as a movement (see class Board), since it's beyond the power of squares
 */
public class Pawn extends Piece {

	public static final String LABEL = "Pawn";

	/**
	 *
	 * @param isBlack whether the pawn is black (otherwise white)
	 * @param board the underlying board
	 */
	public Pawn(boolean isBlack, Board board) {
		super(isBlack, board);
	}

	/**
	 *
	 * @return colorless pieceName of Pawn
	 */
	@Override
	public String label() {
		return LABEL;
	}

	/**
	 *
	 * @param fromPos position of the piece on board
	 * @return all possible moves for pawn at current position without considering King's safety
	 * Have 4 special kinds of movements:
	 * 1. can move 2 squares up (from player's side) if not moved previously, if there is no piece on the way
	 * 2. can move 1 square up, if there is no piece at that location
	 * 3. can capture a piece or an empty square on 1 by 1 diagonal location up.
	 * 4. En passant: If an enemy pawn uses movement 1 on previous move and is on piece's left or right,
	 *    can take it down and go diagonally towards enemy's column
	 */
	@Override
	public List<Move> possibleNextMovesIgnoringCheck(Square fromPos) {
		List<Move> nextMoves = new ArrayList<>();
		int rowIxToCheck = fromPos.rowIx()+(isBlack() ? 1 : -1);
		int rowIxFirstMove =  fromPos.rowIx()+(isBlack() ? 2 : -2); // 2 square away, which is allowed for first move
		for (int horiInc = -1; horiInc <= 1; horiInc++) {
			int colIxToCheck = fromPos.colIx()+horiInc;
			if (horiInc != 0) {
				//Capture Piece 1 square diagonally
				addMoveCapturingPiece(fromPos, rowIxToCheck, colIxToCheck, nextMoves);
				//En Passant
				addMoveEnPassant(fromPos, rowIxToCheck, rowIxFirstMove, colIxToCheck, nextMoves);
			} else {
				// Move 1 square
				boolean noObstacle = addMoveTakingEmptySquare(fromPos, rowIxToCheck, colIxToCheck, nextMoves);
				// Move 2 squares
				if (!hasMoved() && noObstacle)
					addMoveTakingEmptySquare(fromPos, rowIxFirstMove, colIxToCheck, nextMoves);
			}
		}

		return nextMoves;
	}


	/**
	 * Helper function: handles enPassant
	 * @param fromPos position where the piece moves from on board
	 * @param rowIxToCheck rowIx where pawn capture piece
	 * @param rowIxFirstMove rowIx 2 squares up (allowed on first move), where the enemy piece moved from
	 * @param colIxToCheck colIx where pawn capture piece
	 * @param nextMoves collection where you need to add the move
	 */
	private void addMoveEnPassant(Square fromPos, int rowIxToCheck, int rowIxFirstMove, int colIxToCheck, List<Move> nextMoves) {
		Move lastMove = getBoard().getLastMove();
		// precondition positions for last move of enemy Pawn
		Square enemyPawnFrom = Square.getSquare(rowIxFirstMove, colIxToCheck);
		Square enemyPawnTo = Square.getSquare(fromPos.rowIx(),colIxToCheck);
		if (enemyPawnFrom!=null && enemyPawnTo!=null) {
			if (lastMove != null && lastMove.getStartPos().equals(enemyPawnFrom) && lastMove.getEndPos().equals(enemyPawnTo)) {
				// Since pawn can only move front, can ignore checking color
				if (getBoard().getPiece(enemyPawnTo).label().equals(label())) {
					Move enPassant = new Move(fromPos, new Square(rowIxToCheck, colIxToCheck), null);
					enPassant.setNextMove(new Move(enemyPawnTo, null, null));
					nextMoves.add(enPassant);
				}
			}
		}
	}
}