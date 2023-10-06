package eu.sloubi.view;

import javax.swing.*;

public class HowToPlayDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public HowToPlayDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK() {
        dispose();
    }
}
