package nz.ac.aut.ense701.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.HighScores;
import nz.ac.aut.ense701.gui.KiwiCountUI;

/**
 * Kiwi Count Project
 * 
 * @author AS
 * @version 2011
 */
public class Main 
{
    /**
     * Main method of Kiwi Count.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        final HighScores highScores = new HighScores(); 
        // create the game object
        final Game game = new Game();
        // create the GUI for the game
        final KiwiCountUI  gui  = new KiwiCountUI(game, highScores);
        // make the GUI visible
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                gui.setTitle("Kiwi Island");
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension screenDimension = toolkit.getScreenSize();
                Dimension frameDimension = gui.getSize();
                gui.setLocation((screenDimension.width - frameDimension.width) / 2,
                        (screenDimension.height - frameDimension.height) / 2);
                gui.setVisible(true);
            }
        });
    }

}
