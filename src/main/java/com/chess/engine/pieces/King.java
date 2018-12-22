package main.java.com.chess.engine.pieces;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class King
 * Represent of king with a binary color on a given board
 * Have 2 special kinds of movements:
 * 1. can capture or move to neighboring square vertically horizontally or diagonally
 * 2. can castle with a rook with same color under following conditions:
 * -- Neither of king nor rook has moved before
 * -- There is no piece in between of the rook and king
 * -- Every piece on the path of King's movement (inclusive) needs to be safe
 * The King class also defines the notion of safety, as it's crucial to checks, checkmates and draws
 */
public class King extends Piece {

	public static final String LABEL = "King";

	/**
	 *
	 * @param isBlack whether the king is black (otherwise white)
	 * @param board the underlying board
	 */
	public King(boolean isBlack, Board board) {
		super(isBlack, board);
	}

	/**
	 *
	 * @return colorless pieceName of King
	 */
	@Override
	public String label() {
		return LABEL;
	}

	/**
	 *
	 * @param fromPos position of the piece on board
	 * @return all possible moves for king (except castling) at current position without considering King's safety
	 * contain move to capture or move to neighboring square vertically horizontally or diagonally
	 */
	private List<Move> possibleNextMovesWithNoCastling(Square fromPos) {
		List<Move> nextMoves = new ArrayList<>();
		for (int rowInc = -1; rowInc <= 1; rowInc+=1) {
			for (int colInc = -1; colInc <= 1; colInc += 1) {
				if ((rowInc != 0) || (colInc != 0)) {
					addMoveCapturingOrTaking(fromPos,
						fromPos.rowIx() + rowInc, fromPos.colIx() + colInc, nextMoves);
				}
			}
		}
		return nextMoves;
	}

	/**
	 *
	 * @param fromPos position of the piece on board
	 * @return all possible moves for king at current position without considering King's safety
	 * Note castling must considering king's safety, as it needs to ensure all conditions specified above
	 */
	@Override
	public List<Move> possibleNextMovesIgnoringCheck(Square fromPos) {
		List<Move> nextMoves = possibleNextMovesWithNoCastling(fromPos);

		//castling
		if (!hasMoved()) {
			for (int colDiff = -4; colDiff <= 3; colDiff += 7) {
				Square rookPos = Square.getSquare(fromPos.rowIx(), fromPos.colIx()+colDiff);
				if (rookPos == null)
					continue;
				Piece rook = getBoard().getPiece(rookPos);
				if ((rook != null) && (rook.isBlack()==isBlack()) && (rook.label().equals("Rook")) && !rook.hasMoved()) {

					boolean canProceed = true;
					if (!isSafe(fromPos))
						canProceed = false;
					int colIxToCheck = fromPos.colIx()+Integer.signum(colDiff);
					for (; colIxToCheck != rookPos.colIx(); colIxToCheck+=Integer.signum(colDiff)) {
						Square intermediate = new Square(fromPos.rowIx(), colIxToCheck);
						//check if there is anything in between
						if (getBoard().getPiece(intermediate) != null)
							canProceed = false;
						//check if enemy piece threatening king's path
						if (intermediate.colIx() != rookPos.colIx()+1 && !isSafe(intermediate))
							canProceed = false;
					}
					if (canProceed) {
						int kingCol = Integer.signum(colDiff)*2+fromPos.colIx();
						int rookCol = Integer.signum(colDiff)+fromPos.colIx();
						Move castling = new Move(fromPos, new Square(fromPos.rowIx(), kingCol), null);
						castling.setNextMove(new Move(rookPos, new Square(fromPos.rowIx(), rookCol), null));
						nextMoves.add(castling);
					}
				}
			}
		}
		return nextMoves;
	}

	/**
	 *
	 * @param atPos position of the (Imaginary) king
	 * @return if the king is safe at the position
	 */
	public boolean isSafe(Square atPos) {
		Piece pawn = new Pawn(isBlack(), getBoard());
		Piece knight = new Knight(isBlack(), getBoard());
		Piece bishop = new Bishop(isBlack(), getBoard());
		Piece queen = new Queen(isBlack(), getBoard());
		Piece rook = new Rook(isBlack(), getBoard());
		Piece princess = new Princess(isBlack(), getBoard());
		Piece empress = new Empress(isBlack(), getBoard());
		for (Piece piece : new Piece[] {pawn, knight, bishop, queen, rook, princess, empress, this}) {
			if (hasOpposition(atPos, piece)) {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @param atPos atPos position of the (Imaginary) king
	 * @param substitute the piece you want to substitute to check for opposition
	 * @return if there is a opposition after substitution for the result 2 pieces
	 * Note: the opposition is defined as 2 pieces with same label and different color that can capture each other
	 * Opposition is symmetric for all pieces (for fairness)
	 * Example usage: checking if king is safe can be reduced to checking opposition for all possible types of attacker.
	 * So instead of checking if you can get attacked, check if you can attack the attacker after substitution
	 */
	private boolean hasOpposition(Square atPos, Piece substitute) {
		Collection<Move> movesToCheck;
		if (substitute.label().equals(LABEL))
			movesToCheck = possibleNextMovesWithNoCastling(atPos);
		else
			movesToCheck = substitute.possibleNextMovesIgnoringCheck(atPos);

		for (Move move : movesToCheck) {
			if (move.getEndPos()!=null) {
				Piece toCheck = (getBoard().getPiece(move.getEndPos()));
				if (toCheck != null && toCheck.isBlack()!=isBlack() && toCheck.label().equals(substitute.label()))
					return true;
			}
		}
		return false;
	}
}
