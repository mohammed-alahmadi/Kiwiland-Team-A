package nz.ac.aut.ense701.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.HighScores;

public class MenuDialog extends javax.swing.JDialog {

    private final HighScores highScores;
    private String playerName;
    private BufferedImage logo;
    private static Game game;

    /**
     * Creates new form MenuDialog
     *
     * @param parent
     * @param modal
     * @param highScores
     */
    public MenuDialog(java.awt.Frame parent, boolean modal, HighScores highScores, Game game) {
        super(parent, modal);
        this.game = game;
        this.highScores = highScores;
        try {
            setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
            setTitle("Kiwi Island Menu");
            initComponents();
            logo = ImageIO.read(Class.class.getResourceAsStream("/nz/ac/aut/ense701/res/kiwilandlogo.png"));
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenDimension = toolkit.getScreenSize();
            Dimension frameDimension = getSize();
            setLocation((screenDimension.width - frameDimension.width) / 2,
                    (screenDimension.height - frameDimension.height) / 2);
        } catch (IOException ex) {
            Logger.getLogger(MenuDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(logo, (getWidth() - logo.getWidth())/2, 20, this); // see javadoc for more info on the parameters 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        lblPlayerName = new javax.swing.JLabel();
        txtPlayerName = new javax.swing.JTextField();
        btnPlay = new javax.swing.JButton();
        btnRules = new javax.swing.JButton();
        btnHighScore = new javax.swing.JButton();
        btnLevel = new javax.swing.JButton();
        btnQuit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel.setPreferredSize(new java.awt.Dimension(858, 638));

        lblPlayerName.setText("ENTER NAME:");

        txtPlayerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlayerNameActionPerformed(evt);
            }
        });

        btnPlay.setText("Play");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        btnRules.setText("Rules");
        btnRules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRulesActionPerformed(evt);
            }
        });

        btnHighScore.setText("High Score");
        btnHighScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHighScoreActionPerformed(evt);
            }
        });

        btnLevel.setText("Level");
        btnLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLevelActionPerformed(evt);
            }
        });

        btnQuit.setText("Quit");
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap(233, Short.MAX_VALUE)
                .addComponent(btnHighScore)
                .addGap(18, 18, 18)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblPlayerName)
                    .addComponent(txtPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlay)
                    .addComponent(btnRules))
                .addGap(18, 18, 18)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnQuit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(234, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(330, 330, 330)
                .addComponent(lblPlayerName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnPlay)
                .addGap(40, 40, 40)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRules)
                    .addComponent(btnHighScore)
                    .addComponent(btnLevel))
                .addGap(40, 40, 40)
                .addComponent(btnQuit)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPlayerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlayerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlayerNameActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        if (!txtPlayerName.getText().isEmpty()) {
            playerName = txtPlayerName.getText();
            game.createNewGame();
            setVisible(false);
        }
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnRulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRulesActionPerformed
        RulesDialog rulesDialog = new RulesDialog((Frame) getParent(), true);
        rulesDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        rulesDialog.pack();
        rulesDialog.setVisible(true);
    }//GEN-LAST:event_btnRulesActionPerformed

    private void btnHighScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHighScoreActionPerformed
        HighScoreDialog highScoreDialog = new HighScoreDialog((Frame) getParent(), true, highScores);
        highScoreDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        highScoreDialog.pack();
        highScoreDialog.setVisible(true);
    }//GEN-LAST:event_btnHighScoreActionPerformed

    private void btnQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitActionPerformed
        highScores.saveHighScores();
        System.exit(0);
    }//GEN-LAST:event_btnQuitActionPerformed

    private void btnLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLevelActionPerformed
        LevelSelectorDialog levelSelector = new LevelSelectorDialog((Frame) getParent(), true, game);
        levelSelector.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        levelSelector.pack();
        levelSelector.setLocationRelativeTo(this);
        levelSelector.setVisible(true);
    }//GEN-LAST:event_btnLevelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MenuDialog dialog = new MenuDialog(new javax.swing.JFrame(), true, null, game);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHighScore;
    private javax.swing.JButton btnLevel;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnQuit;
    private javax.swing.JButton btnRules;
    private javax.swing.JLabel lblPlayerName;
    private javax.swing.JPanel panel;
    private javax.swing.JTextField txtPlayerName;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return playerName;
    }
}
