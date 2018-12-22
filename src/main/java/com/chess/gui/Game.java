package main.java.com.chess.gui;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * class Game
 * contains the GUI for the game and internal logic
 */
public class Game {
	private JFrame gameFrame;
	private Board board;
	private BoardPanel boardPanel;
	private JPanel topPanel;
	private JPanel bottomPanel;

	private JButton undoButton;
	private JButton resignButton;
	private Commenter commenter;
	private boolean isBlackToMove;
	private ScoreTracker scoreTracker;
	private JLabel whiteScoreDisplay;
	private JLabel blackScoreDisplay;
	private JMenuBar menuBar;
	private BoardType mode;  // which mode board in (normal or customized)

	/**
	 * constructor for game
	 * create the main gameFrame, Board Panel for display and buttons
	 */
	public Game() {
		this.mode = BoardType.NORMAL;
		startGame(new ScoreTracker(), mode);
	}


	private void startGame(ScoreTracker scoreTracker, BoardType mode) {
		this.scoreTracker = scoreTracker;
		isBlackToMove = false;

		this.board = new Board(mode);
		this.menuBar = new JMenuBar();
		initializeGameFrame();
		initializeBoardPanel();
		initializeBottomPanel();
		initializeTopPanel();
		initializeOption();
		initializeMode();

		setBackground();
		this.gameFrame.setJMenuBar(menuBar);
		this.gameFrame.setVisible(true);
	}



	private void initializeGameFrame() {
		this.gameFrame = new JFrame(Preferences.GAME_TITLE);
		this.gameFrame.setSize(Preferences.GAME_DIMENSION);
		this.gameFrame.setLayout(new BorderLayout());
		this.gameFrame.setResizable(false);
	}

	private void initializeBoardPanel() {
		this.boardPanel = new BoardPanel(board, this);
		this.gameFrame.add(boardPanel, BorderLayout.CENTER);
	}


	private void initializeOption() {
		final JMenu menu = new JMenu(Preferences.OPTION_TEXT);
		menuBar.add(menu);
		final JMenuItem restart = new JMenuItem(Preferences.OPTION_RESTART_TEXT);
		restart.addActionListener(e -> {
			restartGame();
		});
		menu.setFont(Preferences.MENU_FONT);
		restart.setFont(Preferences.MENU_FONT);
		menu.add(restart);
	}


	private void initializeMode() {
		final JMenu menu = new JMenu(Preferences.MODE_TEXT);
		menu.setFont(Preferences.MENU_FONT);
		menuBar.add(menu);
		initializeModeItem(menu, BoardType.NORMAL, Preferences.MODE_NORMAL_TEXT);
		initializeModeItem(menu, BoardType.CUSTOMIZED, Preferences.MODE_CUSTOM_TEXT);
	}

	private void initializeModeItem(JMenu menu, BoardType type, String text) {
		final JCheckBoxMenuItem item = new JCheckBoxMenuItem(text);
		item.addActionListener(e -> {
			if (mode != type) {
				mode = type;
				restartGame();
			} else {
				item.setState(true);
			}
		});
		item.setFont(Preferences.MENU_FONT);
		if (mode == type) {
			item.setState(true);
		}
		menu.add(item);
	}

	private void restartGame() {
		gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
		scoreTracker.notifyRestart();
		startGame(scoreTracker, mode);
	}


	private void initializeBottomPanel() {
		undoButton = createUndoButton(Preferences.UNDO_BUTTON_TEXT);
		resignButton = createResignButton(Preferences.RESIGN_BUTTON_TEXT);
		JLabel message = createLabel();
		commenter = new Commenter(message);
		commenter.notifyToMove(isBlackToMove);

		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(resignButton, BorderLayout.WEST);
		bottomPanel.add(undoButton, BorderLayout.EAST);
		bottomPanel.add(message);
		gameFrame.add(bottomPanel, BorderLayout.SOUTH);
	}

	private void initializeTopPanel() {
		whiteScoreDisplay = createLabel();
		blackScoreDisplay = createLabel();
		blackScoreDisplay.setForeground(Color.BLACK);

		topPanel = new JPanel(new BorderLayout());
		topPanel.add(whiteScoreDisplay, BorderLayout.WEST);
		topPanel.add(blackScoreDisplay, BorderLayout.EAST);
		gameFrame.add(topPanel, BorderLayout.NORTH);

		updateScoreDisplay();
	}

	private void setBackground() {
		Color color = Preferences.BACKGROUND_COLOR;
		bottomPanel.setBackground(color);
		boardPanel.setBackground(color);
		topPanel.setBackground(color);
	}



	public Board getBoard() {
		return board;
	}

	boolean isBlackToMove() {
		return isBlackToMove;
	}




	private void updateScoreDisplay() {
		whiteScoreDisplay.setText("Score White ----- "+(scoreTracker.getScore(false)));
		blackScoreDisplay.setText((scoreTracker.getScore(true))+" ------ Score Black");
	}


	/**
	 * called when boardPanel finished taking move
	 */
	void notifyMoved() {
		isBlackToMove = !isBlackToMove;
		boolean inCheck = !board.isSafe(isBlackToMove);
		boolean hasNoValidMove = board.allPossibleNextMoves(isBlackToMove).size() == 0;
		if (hasNoValidMove && inCheck) {
			commenter.notifyCheckmate(isBlackToMove);
			scoreTracker.notifyCheckmate(isBlackToMove);
			freezeScreen();
		} else if (hasNoValidMove) {
			commenter.notifyDraw();
			scoreTracker.notifyDraw();
			freezeScreen();
		} else if (inCheck) {
			commenter.notifyInCheck(isBlackToMove);
		} else {
			commenter.notifyToMove(isBlackToMove);
		}
		updateScoreDisplay();
	}


	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setPreferredSize(Preferences.BUTTON_DIMENSION);
		button.setFont(Preferences.BUTTON_FONT);
		button.setFocusPainted(false);
		return button;
	}


	private JLabel createLabel() {
		JLabel label = new JLabel();
		label.setFont(Preferences.COMMENT_FONT);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		return label;
	}

	private JButton createResignButton(String text) {
		JButton button = createButton(text);
		button.addActionListener(e -> {
			scoreTracker.notifyResign(isBlackToMove);
			commenter.notifyResign(isBlackToMove);
			freezeScreen();
			updateScoreDisplay();
		});
		return button;
	}

	private JButton createUndoButton(String text) {
		JButton button = createButton(text);
		button.addActionListener(e -> {
			boolean restored = boardPanel.restoreSavedPieces();
			if (restored)
				notifyMoved();
		});
		return button;
	}

	private void freezeScreen() {
		undoButton.setEnabled(false);
		resignButton.setEnabled(false);
		boardPanel.freeze();
	}
}
