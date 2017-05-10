package nz.ac.aut.ense701.gui;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mohammed
 * 
 */
public class SoundTest {
    
    public SoundTest() {
    }

    /**
     * Test of getStringRepresentation method, of class Fauna.
     */
    @Test
    public void testGetSound() {
        PlaySound playSound = new PlaySound();
        int result = 0;
        playSound.setSound(PlaySound.SOUND.HAZARD);
        playSound.playSound();
        if (playSound.getClip() != null) {
            result++;
        }
        playSound.setSound(PlaySound.SOUND.KIWI);
        playSound.playSound();
        if (playSound.getClip() != null) {
            result++;
        }
        playSound.setSound(PlaySound.SOUND.LOSE);
        playSound.playSound();
        if (playSound.getClip() != null) {
            result++;
        }
        playSound.setSound(PlaySound.SOUND.WIN);
        playSound.playSound();
        if (playSound.getClip() != null) {
            result++;
        }
        int expResult = 4;
        assertEquals(expResult, result);
    }
    
}
