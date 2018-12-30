package main.java.com.chess.engine.pieces;
import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;

import java.util.List;

/**
 * Class Piece
 * Represent a chess piece with a binary color on a given board.
 * Keep track of if a piece has moved before in case of castling and 2 step pawn move.
 * Each piece has a label, which should ideally be the colorless piece name.
 * The main responsibility for Piece is to return a set of valid movement
 * without consideration to King's safety (except castling) at a given position on board.
 */
public abstract class Piece {
	private boolean isBlack;
	private Board board;
	private boolean hasMoved;
	public abstract String label();


	/**
	 *
	 * @param isBlack: whether piece is black (otherwise white)
	 * @param board: the underlying board
	 */
	public Piece(boolean isBlack, Board board) {
		this.isBlack = isBlack;
		this.board = board;
		this.hasMoved = false;
	}

	/**
	 * Move a piece without knowing where it goes (like touch file)
	 */
	public void move() {
		this.hasMoved = true;
	}

	/**
	 *
	 * @return external information to be saved for piece (just hasMoved for now)
	 */
	public boolean pieceInfo() {
		return hasMoved;
	}

	/**
	 *
	 * @param pieceInfo saved external information
	 */
	public void restorePieceInfo(boolean pieceInfo) {
		hasMoved = pieceInfo;
	}

	/**
	 *
	 * @return if a piece has been moved
	 */
	public boolean hasMoved() {
		return hasMoved;
	}


	/**
	 *
	 * @return if a piece is black (otherwise white)
	 */
	public boolean isBlack() {
		return isBlack;
	}

	/**
	 *
	 * @return the underlying board
	 */
	public Board getBoard() {
		return board;
	}

	public String pieceName() {
		return (isBlack ? "Black " : "White ")+ label();
	}

	/**
	 * @param fromPos position of the piece on board
	 * @return all possible moves for current piece at current position without considering King's safety
	 */
	public abstract List<Move> possibleNextMovesIgnoringCheck(Square fromPos);


	/**
	 * Helper function
	 * Add a move if the position where it moves to has a piece with opposite color
	 * @param fromPos position where the piece moves from on board
	 * @param toRowIx rowIx where the piece moves to on board
	 * @param toColIx colIx where the piece moves to on board
	 * @param moves collection where you need to add the move
	 * @return if it's a valid piece to take
	 */
	boolean addMoveCapturingPiece(Square fromPos, int toRowIx, int toColIx, List<Move> moves)
	{
		Square toPos = Square.getSquare(toRowIx, toColIx);
		if (toPos != null) {
			Piece piece = board.getPiece(toPos);
			if (piece != null && (piece.isBlack() != this.isBlack())) {
				moves.add(new Move(fromPos, toPos, null));
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper function:
	 * Add a move if the position where it moves to is empty
	 * @param fromPos position where the piece moves from on board
	 * @param toRowIx rowIx where the piece moves to on board
	 * @param toColIx colIx where the piece moves to on board
	 * @param moves collection where you need to add the move
	 * @return if it's a valid empty square to take
	 */
	boolean addMoveTakingEmptySquare(Square fromPos, int toRowIx, int toColIx, List<Move> moves) {
		Square toPos = Square.getSquare(toRowIx, toColIx);
		if (toPos != null) {
			Piece piece = board.getPiece(toPos);
			if (piece == null) {
				moves.add(new Move(fromPos, toPos, null));
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper function
	 * Add a move if the position where it moves to is either empty or with the opposite color
	 * @param fromPos position where the piece moves from on board
	 * @param toRowIx rowIx where the piece moves to on board
	 * @param toColIx colIx where the piece moves to on board
	 * @param moves collection where you need to add the move
	 * @return if it's a valid square to take
	 */
	boolean addMoveCapturingOrTaking(Square fromPos, int toRowIx, int toColIx, List<Move> moves) {
		boolean captured = addMoveCapturingPiece(fromPos, toRowIx, toColIx, moves);
		boolean taken = addMoveTakingEmptySquare(fromPos, toRowIx, toColIx, moves);
		return captured || taken;
	}


	/**
	 * Helper function
	 * Add all moves along a ray (i.e. a direction defined by rowInc and colInc) until hitting an piece or a wall
	 * If hit a piece, decide whether or not to take it (if opposite color)
	 * @param fromPos position where the piece moves from on board
	 * @param moves collection where you need to add the move
	 * @param rowInc the unit increment of row during travelling along the ray
	 * @param colInc the unit increment of column during travelling along the ray
	 */
	void addMoveAlongRay(Square fromPos, List<Move> moves, int rowInc, int colInc) {
		int rowIx = fromPos.rowIx()+rowInc, colIx = fromPos.colIx()+colInc;
		while (true) {
			Square posToCheck = Square.getSquare(rowIx, colIx);
			if (posToCheck==null)
				break;
			Piece pieceToCheck = board.getPiece(posToCheck);
			if (pieceToCheck!=null)
				break;
			moves.add(new Move(fromPos, posToCheck, null));
			rowIx += rowInc;
			colIx += colInc;
		}
		addMoveCapturingPiece(fromPos, rowIx, colIx, moves);
	}


	/**
	 *
	 * @param piece1 first piece
	 * @param piece2 second piece
	 * @param atPos a position on board
	 * @return combined next moves (ignoring check) of 2 pieces at a given position
	 */
	List<Move> combinedNextMovesIgnoringCheck (Piece piece1, Piece piece2, Square atPos) {
		List<Move> moves= piece1.possibleNextMovesIgnoringCheck(atPos);
		moves.addAll(piece2.possibleNextMovesIgnoringCheck(atPos));
		return moves;
	}

}


