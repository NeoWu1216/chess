package main.java.com.chess.engine;

import main.java.com.chess.engine.pieces.*;
import sun.plugin.dom.exception.InvalidStateException;

/**
 * Class PawnPromotionHandler
 * Handles pawn promotion for board
 */
public class PawnPromotionHandler {
	private Board board;
	private Square posPawnPromotion;

	/**
	 * Construct PawnPromotionHandler for a given board
	 * @param board the underlying board
	 */
	public PawnPromotionHandler(Board board) {
		this.board = board;
	}

	/**
	 *
	 * @return if there is a pending promotion
	 */
	public boolean hasPendingPromotion() {
		return posPawnPromotion != null;
	}

	/**
	 * Update pawn promotion state before move
	 * Throw an exception if you have a pending promotion
	 */
	public void checkAndUpdate(Move move) {
		if (hasPendingPromotion()) {
			throw new InvalidStateException("Pawn waiting to be promoted");
		}
		if (move != null) {
			Square startPos = move.getStartPos(), endPos = move.getEndPos();
			if (endPos != null) {
				Piece pieceToMove = board.getPiece(startPos);
				if (pieceToMove.label().equals(Pawn.LABEL) && (endPos.rowIx() % 7) == 0)
					posPawnPromotion = endPos;
			}
		}
	}

	/**
	 * Promotes the pawn into another type of piece (Rook, Knight, Bishop or Queen)
	 * @param label the colorless pieceName you want the pawn to have after promotion
	 */
	public void promotePawn(String label) {
		if (!hasPendingPromotion()) {
			throw new UnsupportedOperationException("No pawn to be promoted");
		}
		boolean isBlack = board.getPiece(posPawnPromotion).isBlack();
		Piece newPiece;
		switch (label) {
			case Rook.LABEL:
				newPiece = new Rook(isBlack, board);
				break;
			case Bishop.LABEL:
				newPiece = new Bishop(isBlack, board);
				break;
			case Knight.LABEL:
				newPiece = new Knight(isBlack, board);
				break;
			case Queen.LABEL:
				newPiece = new Queen(isBlack, board);
				break;
			default:
				throw new IllegalArgumentException("Label not recognized");
		}
		board.setPiece(posPawnPromotion, newPiece);
		posPawnPromotion = null;
	}
}
