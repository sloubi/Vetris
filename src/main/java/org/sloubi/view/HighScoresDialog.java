package org.sloubi.view;

import org.sloubi.model.HighScores;
import org.sloubi.model.Score;

import javax.swing.*;
import java.awt.*;

public class HighScoresDialog extends JDialog {
    public HighScoresDialog(HighScores hs) {
        for (Score score : hs) {
            getContentPane().add(new JLabel(score.toString()));
        }

        setTitle("HighScores");
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 200);
        setResizable(false);
        setVisible(true);
    }
}
