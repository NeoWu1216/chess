package main.java.com.chess.gui;

import javax.swing.*;
import java.awt.*;


/**
 * Class Commenter
 * Shows comment/message to user, such as which Player should move or checkmate.
 * Updated after each move (whether endgame, in check ...) or resign
 */
class Commenter {
	private JLabel message;

	Commenter(JLabel message) {
		this.message = message;
	}

	private String repr(boolean isBlack) {
		return isBlack ? "Black" : "White";
	}

	private String toMoveMessage(boolean isBlackToMove) {
		return repr(isBlackToMove)+" to move!";
	}

	private String winningMessage (boolean isBlackToWin) {return repr(isBlackToWin)+" wins!";}

	void notifyCheckmate (boolean isBlackToMove) {
		message.setText("Checkmate, "+ winningMessage(!isBlackToMove));
		message.setForeground(!isBlackToMove ? Color.BLACK : Color.WHITE);

	}

	void notifyResign (boolean isBlackToMove) {
		message.setText(repr(isBlackToMove)+" resigned, "+winningMessage(!isBlackToMove));
		message.setForeground(!isBlackToMove ? Color.BLACK : Color.WHITE);
	}

	void notifyDraw () {
		message.setText("Draw!");
	}

	void notifyInCheck (boolean isBlackToMove) {
		message.setText("Check, "+ toMoveMessage(isBlackToMove));
		message.setForeground(isBlackToMove ? Color.BLACK : Color.WHITE);
	}

	void notifyToMove (boolean isBlackToMove) {
		message.setText(toMoveMessage(isBlackToMove));
		message.setForeground(isBlackToMove ? Color.BLACK : Color.WHITE);
	}

}
