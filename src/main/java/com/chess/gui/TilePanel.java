package main.java.com.chess.gui;

import main.java.com.chess.engine.Board;
import main.java.com.chess.engine.Square;
import main.java.com.chess.engine.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * class TilePanel
 * Represent panel for a square on board
 */
public class TilePanel extends JPanel {
	private final Square square;
	private BoardPanel boardPanel;

	/**
	 * constructs the TilePanel by setting the initial board and pieces
	 * @param board underlying board
	 * @param boardPanel underlying panel for board
	 * @param rowIx row index of the tile/square
	 * @param colIx col index of the tile/square
	 */
	public TilePanel(Board board, BoardPanel boardPanel, int rowIx, int colIx) {
		super(new GridBagLayout());
		square = new Square(rowIx, colIx);
		this.boardPanel = boardPanel;
		assignTileColor();
		setPieceIcon(board);
		addMouseListener(new MoveListener());
	}


	void assignTileColor() {
		setBackground((square.rowIx()+square.colIx())%2==0 ? Preferences.LIGHT_COLOR : Preferences.DARK_COLOR);
	}
	/**
	 * set the correct icon for current piece on board
	 * @param board the underlying board
	 */
	void setPieceIcon(Board board) {
		this.removeAll();
		Piece currPiece = board.getPiece(square);
		if (currPiece!=null) {
			String fileName = Preferences.PIECE_ICON_PATH+currPiece.pieceName()+".png";
			try {
				BufferedImage image = ImageIO.read(new File(fileName));
				add(new JLabel(new ImageIcon(image)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		repaint();
	}

	private class MoveListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			boardPanel.handleClick(square);
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}

	// highlight self
	// highlight border

}
