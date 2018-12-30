package main.java.com.chess.ai;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;

import java.util.List;
import java.util.Random;

public class Naive implements Strategy{
	@Override
	public Move nextMove(Board board, boolean isBlack) {
		Random randomGenerator = new Random();
		List<Move> possibleMoves = board.allPossibleNextMoves(isBlack);
		if (possibleMoves.size() == 0)
			return null;
		return possibleMoves.get(randomGenerator.nextInt(possibleMoves.size()));
	}
}
