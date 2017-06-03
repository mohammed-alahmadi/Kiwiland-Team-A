package nz.ac.aut.ense701.gameModel;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import org.junit.Test;

/**
 * The test class KiwiFactsTest
 *
 * @author mohammed
 */
public class KiwiFactsTest {

    private final KiwiFacts kiwiFacts;

    public KiwiFactsTest() {
        kiwiFacts = new KiwiFacts();
    }

    @Test
    public void testKiwiFact() {
        assertNotNull(kiwiFacts.getFact());
    }

    @Test
    public void testKiwiFactRandom() {
        String previous = kiwiFacts.getFact();
        String next;
        boolean isDifferent = false;
        for (int i = 0; i < 10; i++) {
            next = kiwiFacts.getFact();
            if (next.compareTo(previous) != 0) {
                isDifferent = true;
                break;
            }
        }
        assertTrue(isDifferent);
    }
}
