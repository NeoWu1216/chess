package main.java.com.chess.ai;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardSaver;
import main.java.com.chess.engine.Move;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AlphaBeta implements Strategy {
	private int depth;
	private Move result;

	public AlphaBeta(int depth) {
		this.depth = depth;
	}

	public AlphaBeta() {
		this.depth = 4;
	}

	@Override
	public Move nextMove(Board board, boolean isBlack) {
		int score = alphabeta(board, this.depth, -100000, 100000, isBlack);
		System.out.println(score);
		return result;
	}


	public int alphabeta(Board board, int depth, int alpha, int beta, boolean isBlack) {
		if (depth == 0)
			return Minimax.score(board);
		List<Move> possibleMoves = board.allPossibleNextMoves(isBlack);
		Collections.shuffle(possibleMoves);
		Move bestMove = null; int bestScore;
		if (!isBlack) {
			bestScore = -100000;
			for (Move move : possibleMoves) {
				BoardSaver saver = new BoardSaver();
				saver.saveBoardBeforeMove(board, move);
				board.takeMove(move);
				int score = alphabeta(board, depth-1, alpha, beta, !isBlack);
				if (score >= bestScore) {
					bestScore = score; bestMove = move;
				}
				saver.restoreBoard(board);

				alpha = Math.max(alpha, bestScore);
				if (alpha > beta) break;
			}
		} else {
			bestScore = 100000;
			for (Move move : possibleMoves) {
				BoardSaver saver = new BoardSaver();
				saver.saveBoardBeforeMove(board, move);
				board.takeMove(move);
				int score = alphabeta(board, depth-1, alpha, beta, !isBlack);
				if (score <= bestScore) {
					bestScore = score; bestMove = move;
				}
				saver.restoreBoard(board);

				beta = Math.min(beta, bestScore);
				if (alpha > beta) break;
			}
		}
		result = bestMove;
		return bestScore;
	}
}
