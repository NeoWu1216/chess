package main.java.com.chess.gui;


import java.awt.*;

/**
 * class Preferences
 * Contains all the constants for the GUI part
 */
final class Preferences {
	static final Color LIGHT_COLOR = new Color(232, 235, 239);
	static final Color DARK_COLOR = new Color(125, 135, 150);
	static final Color SELECTION_COLOR = new Color(12, 230,44);
	static final Color MARKER_COLOR = Color.BLACK;
	static final Color BACKGROUND_COLOR = new Color(80,100,180);
	// Colors based on https://www.chess.com/forum/view/general/best-chess-board-color-setting

	static final float BORDER_WIDTH = 2.1f;
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

	static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 20);
	static final Font MENU_FONT = new Font("Arial", Font.PLAIN, 16);
	static final Font MARKER_FONT = new Font("Arial", Font.BOLD, 16);
	static final Font COMMENT_FONT = new Font("Arial", Font.BOLD, 35);

}
