package main.java.com.chess.engine;

import main.java.com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

/**
 * class BoardSaver
 * saves only necessary state information that can be changed through the move on board
 * can be useful to handle checking if King is safe after move, or for undo functionality
 */
public class BoardSaver {
	private Map<Square, Piece> savedPieces = new HashMap<>();
	private Square[] kingPositions = new Square[2];
	private Map<Square, Boolean> savedPiecesInfo = new HashMap<>();
	private Move lastMove;
	private Move move;
	private PawnPromotionHandler pawnPromotionHandler;
	private int moveCount;

	/**
	 *
	 * @return the move that causes the save action
	 */
	public Move getMove() {
		return move;
	}

	/**
	 * Save all piece information specific to pieces changed in the move
	 * Save all relevant board information
	 * @param board board to save piece info
	 * @param move move that causes the saving action
	 */
	public void saveBoardBeforeMove(Board board, Move move) {
		this.move = move;
		kingPositions[0] = board.getKingPosition(false);
		kingPositions[1] = board.getKingPosition(true);
		if (move != null) {
			for (Square square : move.getAllSquares()) {
				Piece piece = board.getPiece(square);
				savedPieces.put(square, piece);
				if (piece != null)
					savedPiecesInfo.put(square, piece.pieceInfo());
			}
		}
		lastMove = board.getLastMove();
		moveCount = board.getMoveCount();
		pawnPromotionHandler = new PawnPromotionHandler(board);
	}

	/**
	 * restore the board based on info collected
	 * @param board board to be restored
	 */
	public void restoreBoard(Board board) {
		board.setKingPosition(false, kingPositions[0]);
		board.setKingPosition(true, kingPositions[1]);
		for (Square pos : savedPieces.keySet()) {
			Piece piece = savedPieces.get(pos);
			board.setPiece(pos, piece);
			if (piece != null)
				piece.restorePieceInfo(savedPiecesInfo.get(pos));
		}
		board.setLastMove(lastMove);
		board.setMoveCount(moveCount);
		board.setPromotionChecker(pawnPromotionHandler);
	}
}
