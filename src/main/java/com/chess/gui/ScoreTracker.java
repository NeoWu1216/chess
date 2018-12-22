package main.java.com.chess.gui;


/**
 * class ScoreTracker:
 * tracks scores for both players
 * updated/notified for end game positions (draw, checkmate, resign, restart etc)
 */
public class ScoreTracker {
	private Integer scores[] = new Integer[] {0, 0};
	private Integer halfScores[] = new Integer[] {0,0};

	/**
	 *
	 * @param isBlack black's score or white
	 * @return score for given color
	 */
	public String getScore(boolean isBlack) {
		String result = Integer.toString(scores[getIndex(isBlack)]);
		if (halfScores[getIndex(isBlack)] != 0)
			result += '.'+Integer.toString(halfScores[getIndex(isBlack)]);
		return result;
	}

	private int getIndex(boolean isBlack) {
		return isBlack ? 1 : 0;
	}

	private void incScore (boolean isBlack) {
		scores[getIndex(isBlack)] += 1;
	}

	public void notifyCheckmate(boolean isBlackToMove) {
		incScore(!isBlackToMove);
	}

	public void notifyDraw() {
		for (int i = 0; i <= 1; i++) {
			halfScores[i] += 5;
			if (halfScores[i] == 10) {
				scores[i] += 1;
				halfScores[i] = 0;
			}
		}
	}

	public void notifyResign(boolean isBlackToMove) {
		incScore(!isBlackToMove);
	}

	public void notifyRestart() {
		// Shouldn't matter
	}
}
