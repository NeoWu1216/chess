package main.java.com.chess;

import main.java.com.chess.engine.Board;
import main.java.com.chess.gui.Game;

/**
 * Class Launcher
 * launch the Chess game
 */
public class Launcher {
	public static void main(String [] args) {
		launch();
	}

	public static void launch() {
		new Game();
	}
}
