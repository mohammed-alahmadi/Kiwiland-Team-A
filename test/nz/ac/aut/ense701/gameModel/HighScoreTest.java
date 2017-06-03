package nz.ac.aut.ense701.gameModel;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *The test class HighScoreTest.
 * 
 * @author mohammed
 */
public class HighScoreTest {

    private final HighScores highScores;

    public HighScoreTest() {
        highScores = new HighScores();
    }

    @Before
    public void setUp() {
        highScores.addScore("1", 1000);
        highScores.addScore("2", 1000);
        highScores.addScore("3", 1000);
        highScores.addScore("4", 1000);
        highScores.addScore("5", 1000);
        highScores.addScore("6", 1000);
        highScores.addScore("7", 1000);
        highScores.addScore("8", 1000);
        highScores.addScore("9", 1000);
        highScores.addScore("10", 5000);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testScore() {
        assertEquals(0, highScores.getHighScores().get(0).getName().compareTo("10"));
    }

    @Test
    public void testNumberofTopScore() {
        highScores.addScore("10", 5000);
        assertEquals(10, highScores.getHighScores().size());
    }

    @Test
    public void testTopScore() {
        highScores.addScore("11", 50000);
        assertEquals(0, highScores.getHighScores().get(0).getName().compareTo("11"));
    }
}
