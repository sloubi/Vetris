package eu.sloubi.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class ClosePanel extends JPanel {

    Window parentWindow;
    JButton closeButton;

    public ClosePanel(Window parentWindow) {
        this.parentWindow = parentWindow;
        initButton();

        setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(closeButton);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);
    }

    private void initButton() {
        closeButton = new JButton("X");
        closeButton.setBackground(new Color(12, 12, 12));
        closeButton.setForeground(Color.white);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.getModel().addChangeListener(e -> {
            ButtonModel buttonModel = (ButtonModel) e.getSource();
            if (buttonModel.isRollover()) {
                closeButton.setBackground(new Color(168, 21, 21));
            } else {
                closeButton.setBackground(new Color(12, 12, 12));
            }
        });

        closeButton.addActionListener(
                e -> EventQueue.invokeLater(() -> parentWindow.dispatchEvent(new WindowEvent(parentWindow, WindowEvent.WINDOW_CLOSING))));
    }

}
