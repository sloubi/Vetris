package org.sloubi.view;

import org.sloubi.App;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ItemEvent;

public class OptionsDialog extends JDialog {
    public OptionsDialog() {
        JCheckBox musicCheckbox = new JCheckBox("Musique", false);
        musicCheckbox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                App.gameClip.loop(Clip.LOOP_CONTINUOUSLY);
                App.gameClip.start();
            }
            else {
                App.gameClip.stop();
            }
        });
        getContentPane().add(musicCheckbox);

        setTitle("Options");
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 200);
        setResizable(false);
        setVisible(true);
    }
}
