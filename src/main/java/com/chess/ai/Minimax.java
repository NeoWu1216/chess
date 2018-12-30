package main.java.com.chess.ai;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardSaver;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;
import main.java.com.chess.engine.pieces.*;

import java.util.*;

public class Minimax implements Strategy{
	private int depth;
	private Move result;

	public Minimax(int depth) {
		this.depth = depth;
	}

	public Minimax() {
		this.depth = 3;
	}

	@Override
	public Move nextMove(Board board, boolean isBlack) {
		int score = minimax(board, isBlack, depth);
//		System.out.println(score);
		return result;
	}

	private int minimax(Board board, boolean isBlack, int depth) {
		int sign = (isBlack ? -1 : 1);
		if (depth == 0)
			return score(board);
		List<Move> possibleMoves = board.allPossibleNextMoves(isBlack);
		Collections.shuffle(possibleMoves);
		int bestScore = -sign*100000; Move bestMove = null;
		for (Move move : possibleMoves) {
			BoardSaver saver = new BoardSaver();
			saver.saveBoardBeforeMove(board, move);
			board.takeMove(move);
			int score = minimax(board, !isBlack, depth-1);
			if (sign*score > sign*bestScore) {
				bestScore = score; bestMove = move;
			}
			saver.restoreBoard(board);
		}
		result = bestMove;
		return bestScore;
	}

	private Move simpleTake(Board board, boolean isBlack) {
		BoardSaver saver = new BoardSaver();
		List<Move> possibleMoves = board.allPossibleNextMoves(isBlack);
		Collections.shuffle(possibleMoves);
		int bestScore = -1000; Move bestMove = null;
		for (Move move : possibleMoves) {
			saver.saveBoardBeforeMove(board, move);
			board.takeMove(move);
			int score = (isBlack ? -1 : 1) * score(board);
			if (score > bestScore) {
				bestScore = score; bestMove = move;
			}
			saver.restoreBoard(board);
		}
		return bestMove;
	}

	/**
	 * get evaluation score of the board (white - black)
	 * @param board underlying board
	 * @return score of the board (white - black)
	 */
	static int score(Board board) {
		int score = 0;

		Map<String, Integer> labelDict = new HashMap<>();
		labelDict.put(King.LABEL, 10000);
		labelDict.put(Queen.LABEL, 90);
		labelDict.put(Empress.LABEL, 80);
		labelDict.put(Princess.LABEL, 70);
		labelDict.put(Rook.LABEL, 50);
		labelDict.put(Bishop.LABEL, 30);
		labelDict.put(Knight.LABEL, 30);
		labelDict.put(Pawn.LABEL, 10);


		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx++)
			for (int colIx = 0; colIx < Board.WIDTH; colIx++) {
				Square pos = new Square(rowIx, colIx);
				Piece piece = board.getPiece(pos);
				if (piece != null)
					score += (piece.isBlack() ? -1 : 1) * labelDict.get(piece.label());
			}
		return score;
	}
}
