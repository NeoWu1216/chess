package main.java.com.chess.engine;

/**
 * enum BoardType
 * contains 3 types of board:
 * 1. An empty board except with 2 kings
 * 2. the normal/classic board
 * 3. customized board (i.e. classic board with Rook->Empress, Bishop->Princess)
 */
public enum BoardType {
	EMPTY, NORMAL, CUSTOMIZED
}
