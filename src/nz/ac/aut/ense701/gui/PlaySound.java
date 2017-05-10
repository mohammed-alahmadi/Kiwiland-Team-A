package nz.ac.aut.ense701.gui;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaySound {

    /**
     * @return the clip
     */
    public Clip getClip() {
        return clip;
    }
    
    public enum SOUND {LOSE, WIN, KIWI, HAZARD};
    
    private String soundURL;
    private Clip clip;
    
    public void setSound(SOUND sound) {
        switch(sound) {
            case LOSE:
                soundURL = "/nz/ac/aut/ense701/res/lose.wav";
                break;
            case WIN:
                soundURL = "/nz/ac/aut/ense701/res/win.wav";
                break;
            case KIWI:
                soundURL = "/nz/ac/aut/ense701/res/kiwi.wav";
                break;
            case HAZARD:
                soundURL = "/nz/ac/aut/ense701/res/hazard.wav";
                
        }
    }
    
    public void playSound() {
  
        AudioInputStream audioInputStream = null;
        try {
            InputStream inputStream = getClass().getResourceAsStream(soundURL);
            audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            clip = AudioSystem.getClip();
            getClip().open(audioInputStream);
            getClip().start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println(ex);
        } finally {
            try {
                if (audioInputStream != null) {
                    audioInputStream.close();
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
    
}
