package eu.sloubi.view;

import eu.sloubi.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class HowToPlayDialog extends JDialog {

    JPanel contentPanel;

    public HowToPlayDialog() {
        initContentPanel();

        Font font = App.gameFont.deriveFont(24f);
        JLabel title = new JLabel("How to play Vetris?");
        title.setFont(font);
        title.setForeground(new Color(228, 80, 0));

        BackgroundContainer main = new BackgroundContainer();
        main.setBorder(BorderFactory.createLineBorder(Color.white));
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(new ClosePanel(this));
        main.add(title);
        main.add(contentPanel);

        getRootPane().registerKeyboardAction(e -> dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);

        getContentPane().add(main);
        setTitle("How to play?");
        setModal(true);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setSize(400, 300);
        setVisible(true);
    }

    private void initContentPanel() {
        JLabel text = new JLabel("""
                <html>
                Vetris is like a classic Tetris&reg; but with a new special piece, the V.<br>
                This special piece can't rotate.<br>
                <br>
                <b>Keys:</b><br>
                <kbd>Escape</kbd>: Pause<br>
                <kbd>C</kbd>: Hold<br>
                <kbd>Space</kbd>: Hard drop<br>
                <kbd>Down</kbd>: Soft drop<br>
                <kbd>Up</kbd>: Rotate right<br>
                <kbd>Z</kbd>: Rotate left<br>
                <kbd>Left</kbd>: Move left<br>
                <kbd>Right</kbd>: Move right
                </html>""");
        text.setFont(text.getFont().deriveFont(Font.PLAIN));
        text.setForeground(Color.white);

        contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        contentPanel.setOpaque(false);
        contentPanel.add(text);
    }

}
