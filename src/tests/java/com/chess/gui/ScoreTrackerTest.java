package tests.java.com.chess.gui;

import main.java.com.chess.gui.ScoreTracker;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTrackerTest {

	@Test
	public void getScore() {
		for (boolean isBlack : new boolean[] {true, false}) {
			ScoreTracker scoreTracker = new ScoreTracker();
			float expectedScore = 0f;
			for (int i = 0; i < 10000; i++) {
				if (i % 2 == 0) {
					scoreTracker.notifyDraw();
					expectedScore += 0.5;
				}
				if (i % 3 == 0) {
					scoreTracker.notifyCheckmate(isBlack);
					expectedScore += 0;
				}

				if (i % 5 == 0) {
					scoreTracker.notifyCheckmate(!isBlack);
					expectedScore += 1;
				}

				if (i % 7 == 0) {
					scoreTracker.notifyResign(isBlack);
					expectedScore += 0;
				}

				if (i % 11 == 1) {
					scoreTracker.notifyResign(!isBlack);
					expectedScore += 1;
				}

				if (i % 13 == 0) {
					scoreTracker.notifyRestart();
				}

				assertEquals(expectedScore, new Float(scoreTracker.getScore(isBlack)), 0.001);
			}
		}
	}
}