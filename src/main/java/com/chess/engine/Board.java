package main.java.com.chess.engine;

import main.java.com.chess.engine.pieces.*;

import java.util.ArrayList;
import java.util.List;

/**
 * class Board
 * contain pieces, positions of kings, fixed WIDTH and HEIGHT, and other state variables
 * The method that are most useful for real game is possibleNextMoves, isCheckmated, inStalemate, takeMove
 * possibleNextMoves returns all possible/valid moves for players of given color to play next for a given piece
 * isChecked and inStalemate simply checks for end positions
 * takeMove is when just player taking a move (note it doesn't check if it's valid or not due to flexibility)
 */
public class Board {
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;

	private Piece[][] board = new Piece[HEIGHT][WIDTH];
	private Square[] kingPositions = new Square[2];  //White, Black
	private Move lastMove;
	private PawnPromotionHandler promotionChecker = new PawnPromotionHandler(this);

	/**
	 * Construct the normal board (initial chess board)
	 */
	public Board() {
		normalBoard();
	}


	/**
	 * Construct board based on type
	 * @param type a member of enum BoardType
	 */
	public Board(BoardType type) {
		switch (type) {
			case EMPTY:
				emptyBoard();
				break;
			case NORMAL:
				normalBoard();
				break;
			case CUSTOMIZED:
				customizedBoard();
				break;
		}
	}


	/**
	 * Construct the normal board
	 */
	private void normalBoard() {
		for (int rowIndex : new int[]{0, 7}) {
			boolean isBlack = rowIndex == 0;
			this.board[rowIndex][0] = new Rook(isBlack, this);
			this.board[rowIndex][1] = new Knight(isBlack, this);
			this.board[rowIndex][2] = new Bishop(isBlack, this);
			this.board[rowIndex][3] = new Queen(isBlack, this);
			this.board[rowIndex][4] = new King(isBlack, this);
			setKingPosition(isBlack, new Square(rowIndex, 4));
			this.board[rowIndex][5] = new Bishop(isBlack, this);
			this.board[rowIndex][6] = new Knight(isBlack, this);
			this.board[rowIndex][7] = new Rook(isBlack, this);
		}

		for (int colIndex = 0; colIndex < WIDTH; colIndex++) {
			this.board[1][colIndex] = new Pawn(true, this);
			this.board[6][colIndex] = new Pawn(false, this);
		}
	}

	/**
	 * Construct the board with special pieces
	 */
	private void customizedBoard() {
		for (int rowIndex : new int[]{0, 7}) {
			boolean isBlack = rowIndex == 0;
			this.board[rowIndex][0] = new Empress(isBlack, this);
			this.board[rowIndex][1] = new Knight(isBlack, this);
			this.board[rowIndex][2] = new Princess(isBlack, this);
			this.board[rowIndex][3] = new Queen(isBlack, this);
			this.board[rowIndex][4] = new King(isBlack, this);
			setKingPosition(isBlack, new Square(rowIndex, 4));
			this.board[rowIndex][5] = new Princess(isBlack, this);
			this.board[rowIndex][6] = new Knight(isBlack, this);
			this.board[rowIndex][7] = new Empress(isBlack, this);
		}

		for (int colIndex = 0; colIndex < WIDTH; colIndex++) {
			this.board[1][colIndex] = new Pawn(true, this);
			this.board[6][colIndex] = new Pawn(false, this);
		}
	}

	/**
	 * Construct the empty board (just containing 2 Kings)
	 */
	private void emptyBoard() {
		board = new Piece[HEIGHT][WIDTH];
		for (int rowIndex : new int[]{0, 7}) {
			boolean isBlack = rowIndex == 0;
			this.board[rowIndex][4] = new King(isBlack, this);
			setKingPosition(isBlack, new Square(rowIndex, 4));
		}
	}

	/**
	 *
	 * @param pos position to get the piece
	 * @return the piece at the position
	 */
	public Piece getPiece(Square pos) {
		return this.board[pos.rowIx()][pos.colIx()];
	}

	/**
	 *
	 * @param pos position to set the piece
	 * @param piece the piece to set to position
	 */
	public void setPiece(Square pos, Piece piece) {
		this.board[pos.rowIx()][pos.colIx()] = piece;
	}

	/**
	 *
	 * @return the last real move on board
	 */
	public Move getLastMove() {
		return lastMove;
	}

	/**
	 *
	 * @param lastMove the last move on board
	 */
	public void setLastMove(Move lastMove) {
		this.lastMove = lastMove;
	}

	/**
	 *
	 * @param isBlack whether wanted king is black (otherwise white)
	 * @return position of the wanted king
	 */
	public Square getKingPosition(boolean isBlack) {
		return kingPositions[isBlack ? 1 : 0];
	}

	/**
	 *
	 * @param isBlack whether wanted king is black (otherwise white)
	 * @return the wanted king
	 */
	public King getKing(boolean isBlack) {
		return (King) getPiece(getKingPosition(isBlack));
	}

	/**
	 *
	 * @param isBlack whether the target king is black (otherwise white)
	 * @param pos new Square for the target king to be in
	 */
	void setKingPosition(boolean isBlack, Square pos) {
		kingPositions[isBlack ? 1 : 0] = pos;
	}


	/**
	 *
	 * @param promotionChecker pawn promotion handler to be set
	 */
	void setPromotionChecker(PawnPromotionHandler promotionChecker) {
		this.promotionChecker = promotionChecker;
	}

	/**
	 * Promotes the pawn into another type of piece (Rook, Knight, Bishop or Queen)
	 * @param label the colorless pieceName you want the pawn to have after promotion
	 */
	public void promotePawn(String label) {
		promotionChecker.promotePawn(label);
	}

	/**
	 *
	 * @return whether there is a pending promotion needs to be resolved
	 */
	public boolean hasPendingPromotion() {
		return promotionChecker.hasPendingPromotion();
	}

	/**
	 * move all piece involved in a single move (after touch, it has moved)
	 * must be called before real move to avoid side effects
	 * @param move:  move to be taken
	 */
	private void touchPieces(Move move) {
		if (move != null) {
			getPiece(move.getStartPos()).move();
			touchPieces(move.getNextMove());
		}
	}


	/**
	 * Take a move on board
	 * If not updateState, it means only for simulation
	 * Note it doesn't check if it's valid or not, since it can act like a board editor
	 * @param move move to be taken
	 * @param updateState if you want to update state of board (like last move)
	 */
	public void takeMove(Move move, boolean updateState) {
		if (updateState)
			takeMove(move);
		else
			takeSimulatedMove(move);
	}


	/**
	 * Take a simulated move on board (state not updated)
	 * @param move move to be taken
	 */
	public void takeSimulatedMove(Move move) {
		if (move != null) {
			Square startPos = move.getStartPos(), endPos = move.getEndPos();
			if (endPos != null) {
				Piece pieceToMove = getPiece(startPos);
				setPiece(endPos, pieceToMove);
				if (pieceToMove.label().equals(King.LABEL))
					setKingPosition(pieceToMove.isBlack(), endPos);
			}
			setPiece(startPos, null);
			takeSimulatedMove(move.getNextMove());
		}
	}

	/**
	 * Take a real move on board
	 * @param move move to be taken
	 */
	public void takeMove(Move move) {
		promotionChecker.checkAndUpdate(move);
		touchPieces(move);
		lastMove = move;
		takeSimulatedMove(move);
	}


	/**
	 * @param pos Square for the target piece
	 * @return possible moves to play next for piece on the square while protecting your king
	 */
	public List<Move> possibleNextMoves(Square pos) {
		List<Move> nextMoves = new ArrayList<>();
		Piece piece = getPiece(pos);

		if (piece != null) {
			for (Move move : piece.possibleNextMovesIgnoringCheck(pos)) {
				BoardSaver saver = new BoardSaver();
				saver.saveBoardBeforeMove(this, move);

				takeMove(move, false); // with new BoardSaver, updateState doesn't matter
				King toProtect = getKing(piece.isBlack());
				if (toProtect.isSafe(getKingPosition(piece.isBlack()))) {
					nextMoves.add(move);
				}

				saver.restoreBoard(this);
			}
		}
		return nextMoves;
	}

	/**
	 *
	 * @param isBlackToMove whether or not the side to move is black (white otherwise)
	 * @return all possible next moves for a player by following all chess rules
	 */
	public List<Move> allPossibleNextMoves(boolean isBlackToMove) {
		List<Move> nextMoves = new ArrayList<>();
		for (int rowIx = 0; rowIx < HEIGHT; rowIx++) {
			for (int colIx = 0; colIx < WIDTH; colIx++) {
				Square pos = new Square(rowIx, colIx);
				if (getPiece(pos)!=null && getPiece(pos).isBlack() == isBlackToMove)
					nextMoves.addAll(possibleNextMoves(pos));
			}
		}
		return nextMoves;
	}

	/**
	 *
	 * @param isBlack if king is black or white
	 * @return if the king with correct color is safe (i.e. not in check)
	 */
	public boolean isSafe (boolean isBlack) {
		return getKing(isBlack).isSafe(getKingPosition(isBlack));
	}

	/**
	 *
	 * @param isBlackToMove whether or not the side to move is black (white otherwise)
	 * @return whether or not the side to move is already checkmated
	 */
	public boolean isCheckmated (boolean isBlackToMove) {
		List<Move> moves = allPossibleNextMoves(isBlackToMove);
		return moves.size()==0 && !isSafe(isBlackToMove);
	}

	/**
	 *
	 * @param isBlackToMove whether or not the side to move is black (white otherwise)
	 * @return whether or not both sides are drawn
	 */
	public boolean inStalemate (boolean isBlackToMove) {
		List<Move> moves = allPossibleNextMoves(isBlackToMove);
		return moves.size()==0 && isSafe(isBlackToMove);
	}


	/**
	 * print the board in plain text (mainly for debugging purposes)
	 */
	public void printBoard() {
		int padding = 15;
		for (int rowIx = 0; rowIx < HEIGHT; rowIx++) {
			for (int colIx = 0; colIx < WIDTH; colIx++) {
				Piece piece = getPiece(new Square(rowIx, colIx));
				System.out.print("|"+String.format("%1$-" + padding + "s", (piece==null) ? " " : piece.pieceName()));
			}
			System.out.println('|');
		}
	}
}
