package main.java.com.chess.ai;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Move;

public interface Strategy {
	Move nextMove(Board board, boolean isBlack);
}
