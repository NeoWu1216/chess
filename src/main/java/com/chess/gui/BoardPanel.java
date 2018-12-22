package main.java.com.chess.gui;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.BoardSaver;
import main.java.com.chess.engine.Move;
import main.java.com.chess.engine.Square;
import main.java.com.chess.engine.pieces.Piece;
import main.java.com.chess.engine.pieces.Queen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * class BoardPanel
 * A container panel for board
 * contains all TilePanels
 * contains all functionality specific to moves on board
 */
public class BoardPanel extends JPanel {
	private TilePanel[][] tiles = new TilePanel[Board.WIDTH][Board.HEIGHT];
	private Board board;
	private Game game;
	private List<Move> validMoves = new ArrayList<>();
	private Square lastPosClicked;
	private boolean frozen;
	private Stack<BoardSaver> boardSavers = new Stack<>();
	// each board savers only saves necessary elements for one move
	// To redo, just change Stack into ArrayList and keep track of index

	/**
	 * Construct the board panel
	 * @param game the underlying game
	 */
	public BoardPanel(Board board, Game game) {
		super(new GridLayout(Board.HEIGHT+1, Board.WIDTH+1));
		this.game = game;
		for (int rowIx = 0; rowIx < Board.HEIGHT; rowIx++) {
			for (int colIx = 0; colIx < Board.WIDTH; colIx++) {
				TilePanel tile = new TilePanel(board,this, rowIx, colIx);
				tiles[rowIx][colIx] = tile;
				add(tile);
			}
			addRowMarker(rowIx);
		}
		for (int colIx = 0; colIx < Board.WIDTH; colIx++) {
			addColMarker(Square.COL_NAMES[colIx]);
		}
		this.board = board;
		setBorder(BorderFactory.createEmptyBorder(40, 43, -20, -50));
		validate();
	}

	/**
	 * save all involved pieces before move
	 * @param move target move
	 */
	private void savePieceInfoBeforeMove(Move move) {
		BoardSaver saver = new BoardSaver();
		saver.saveBoardBeforeMove(board, move);
		boardSavers.push(saver);
	}

	/**
	 * restore saved Pieces for last move(i.e. undo)
	 * @return whether restore succeeds
	 */
	boolean restoreSavedPieces() {
		boolean canRestore = !boardSavers.empty();
		if (canRestore) {
			BoardSaver lastSaver = boardSavers.pop();
			lastSaver.restoreBoard(board);
			updatePiecesForMove(lastSaver.getMove());
		}
		return canRestore;
	}

	private TilePanel getTile(Square square) {
		return tiles[square.rowIx()][square.colIx()];
	}

	private JLabel createMarker(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(Preferences.MARKER_COLOR);
		label.setFont(Preferences.MARKER_FONT);
		return label;
	}

	private void addRowMarker(int rowIx) {
		JLabel rowMarker = createMarker(Square.ROW_NAMES[rowIx]);
		rowMarker.setBorder(BorderFactory.createEmptyBorder(0,8,0,0));
		add(rowMarker);
	}

	private void addColMarker(String colName) {
		JLabel colMarker = createMarker(colName);
		colMarker.setVerticalAlignment(SwingConstants.TOP);
		colMarker.setHorizontalAlignment(SwingConstants.CENTER);
		colMarker.setBorder(BorderFactory.createEmptyBorder(8,0,0,0));
		add(colMarker);
	}

	/**
	 * Freeze the whole board
	 */
	void freeze() {
		frozen = true;
	}

	/**
	 * Handle user's click for a square on board
	 * @param onPos where the click occurs on board
	 */
	void handleClick(Square onPos) {
		if (frozen)
			return;
		if (onPos != null) {
			if (lastPosClicked != null)
				removeHint(lastPosClicked);

			Move moveFound = validMoveTo(onPos);
			if (moveFound != null) {
				savePieceInfoBeforeMove(moveFound);
				board.takeMove(moveFound);
				if (board.hasPendingPromotion())
					board.promotePawn(Queen.LABEL); // Just for simplicity, always promote to queen
				game.notifyMoved();

				validMoves = new ArrayList<>();
				updatePiecesForMove(moveFound);
			} else {
				Piece piece = board.getPiece(onPos);
				if (piece != null && piece.isBlack()==game.isBlackToMove()) {
					validMoves = board.possibleNextMoves(onPos);
					setHint(onPos);
				} else {
					validMoves = new ArrayList<>();
				}
			}
			lastPosClicked = onPos;
		}
	}

	/**
	 * Update all pieces involved in a move
	 * @param move the target move
	 */
	private void updatePiecesForMove(Move move) {
		for (Square square : move.getAllSquares()) {
			getTile(square).setPieceIcon(board);
		}
	}

	/**
	 * set move hints for a position on board
	 * @param onPos the start position
	 */
	private void setHint(Square onPos) {
		getTile(onPos).setBackground(Preferences.SELECTION_COLOR);
		for (Move move : validMoves) {
			TilePanel tile = getTile(move.getEndPos());
			tile.setBorder(BorderFactory.createStrokeBorder(
				new BasicStroke(Preferences.BORDER_WIDTH), Preferences.SELECTION_COLOR));
		}
	}

	private void removeHint(Square onPos) {
		getTile(onPos).assignTileColor();
		for (Move move : validMoves) {
			TilePanel tile = getTile(move.getEndPos());
			tile.setBorder(null);
		}
	}

	private Move validMoveTo(Square square) {
		for (Move move : validMoves)
			if (square.equals(move.getEndPos()))
				return move;
		return null;
	}
}
