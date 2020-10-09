package org.sloubi.view;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class OptionsDialog extends JDialog {
    public OptionsDialog() {
        JCheckBox musicCheckbox = new JCheckBox("Musique", false);
        musicCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    MainFrame.clip.start();
                }
                else {
                    MainFrame.clip.stop();
                }
            }
        });
        getContentPane().add(musicCheckbox);

        setTitle("Options");
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 200);
        setResizable(false);
    }
}
