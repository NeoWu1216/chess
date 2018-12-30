package main.java.com.chess.gui;


import main.java.com.chess.ai.AlphaBeta;
import main.java.com.chess.ai.Minimax;
import main.java.com.chess.ai.Strategy;

import java.awt.*;

import static java.awt.event.KeyEvent.*;

/**
 * class Preferences
 * Contains all the constants for the GUI part
 */
final class Preferences {
	static final Color LIGHT_COLOR = new Color(232, 235, 239);
	static final Color DARK_COLOR = new Color(125, 135, 150);
	static final Color SELECTION_COLOR_DARK = new Color(255,191,0);
	static final Color SELECTION_COLOR_LIGHT = new Color(12, 230,44);
	static final Color MARKER_COLOR = Color.BLACK;
	static final Color BACKGROUND_COLOR = new Color(80,100,180);
	// Colors based on https://www.chess.com/forum/view/general/best-chess-board-color-setting

	static final float BORDER_WIDTH = 3.5f;
	static final Dimension GAME_DIMENSION = new Dimension(950, 950);
	static final Dimension BUTTON_DIMENSION = new Dimension(150,  50);

	static final String PIECE_ICON_PATH = "src/main/resources/";
	static final String GAME_TITLE = "Chess";
	static final String UNDO_BUTTON_TEXT = "Undo";
	static final String RESIGN_BUTTON_TEXT = "Resign";
	static final String OPTION_TEXT = "Options";
	static final String OPTION_RESTART_TEXT = "Restart";
	static final String MODE_TEXT = "Mode";
	static final String MODE_NORMAL_TEXT = "Classic";
	static final String MODE_CUSTOM_TEXT = "Special";
	static final String PLAYER_TEXT = "Play With";
	static final String PLAYER_FRIEND_TEXT = "Friend";
	static final String PLAYER_WHITE_AT_TEXT = "White AI";
	static final String PLAYER_BLACK_AI_TEXT = "Black AI";

	static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 20);
	static final Font MENU_FONT = new Font("Arial", Font.PLAIN, 16);
	static final Font MARKER_FONT = new Font("Arial", Font.BOLD, 16);
	static final Font COMMENT_FONT = new Font("Arial", Font.BOLD, 35);

	static final Strategy AI = new Minimax(3);

	static final int UNDO_KEY = VK_U;
	static final int RESTART_KEY = VK_R;
}
