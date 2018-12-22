package main.java.com.chess.engine;

import java.util.*;

/**
 * Represent a series of moves for pieces from a Square to another
 * In terms of single chess move, the max chaining is 2 (For Enpassant and castling)
 * endPos = null represent throwing the piece out of the board
 * Can be mapped to and from a string
 */
public class Move {
	private Square startPos, endPos;
	private Move nextMove;
	public static final String NULL_STRING = "XX";

	/**
	 *
	 * @param startPos Square where the piece starts at
	 * @param endPos Square where the piece lands on
	 * @param nextMove the next movement in the chain
	 */
	public Move(Square startPos, Square endPos, Move nextMove) {
		this.startPos = startPos;
		this.endPos = endPos;
		this.nextMove = nextMove;
	}

	/**
	 *
	 * @return where the piece starts at
	 */
	public Square getStartPos() {
		return startPos;
	}

	/**
	 *
	 * @return where the piece lands on
	 */
	public Square getEndPos() {
		return endPos;
	}

	/**
	 *
	 * @return the next move on the chain
	 */
	public Move getNextMove() {
		return nextMove;
	}

	/**
	 *
	 * @param nextMove the next move on the chain
	 */
	public void setNextMove(Move nextMove) {
		this.nextMove = nextMove;
	}

	/**
	 *
	 * @return the string representation of the move
	 */
	@Override
	public String toString() {
		String res = startPos.toString()+((endPos==null) ? NULL_STRING : endPos.toString());
		if (nextMove != null) {
			res += ","+nextMove.toString();
		}
		return res;
	}

	/**
	 * Helper function
	 * Convert collection of moves to their string representations for equality checking
	 * @param moves a collection of moves
	 * @return the set of string representations
	 */
	public static Set<String> toStringSet(Collection<Move> moves) {
		Set<String> res = new HashSet<>();
		for (Move move : moves) {
			res.add(move.toString());
		}
		return res;
	}

	/**
	 *
	 * @param description string representation of the chain of moves
	 * @return recursively constructed move object
	 */
	public static Move getMove(String description) {
		description = description.toUpperCase();
		if (description.length()==0) {
			return null;
		} else {
			Square startPos = new Square(description.substring(0,2));
			String secondHalf = description.substring(2,4);
			Square endPos = (secondHalf.equals(NULL_STRING)) ? null : new Square(secondHalf);
			return new Move(startPos, endPos, (description.length()==4) ? null : getMove(description.substring(5)));
		}
	}

	/**
	 *
	 * @return get all valid squares involved in the move (both start and end positions)
	 */
	public Set<Square> getAllSquares() {
		Set<Square> res = new HashSet<>();
		if (nextMove != null)
			res = nextMove.getAllSquares();
		if (startPos != null)
			res.add(startPos);
		if (endPos != null)
			res.add(endPos);
		return res;
	}
}
