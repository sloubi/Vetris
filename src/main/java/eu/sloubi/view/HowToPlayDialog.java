package eu.sloubi.view;

import eu.sloubi.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class HowToPlayDialog extends JDialog {

    JPanel contentPanel;

    public HowToPlayDialog(Frame parent) {
        initContentPanel();

        Font font = App.gameFont.deriveFont(24f);
        JLabel title = new JLabel("How to play Vetris?");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        setLocationRelativeTo(parent);
        setSize(400, 430);
        setVisible(true);
    }

    private void initContentPanel() {
        JLabel text = new JLabel("""
                <html>
                Vetris is like a classic Tetris&reg; but with a new special piece, the V.<br>
                This special piece can't rotate.<br>
                <br>
                <b>Keys:</b><br>
                <table>
                <tr><td><b><kbd>Escape</kbd></b></td><td>Pause</td></tr>
                <tr><td><kbd>C</kbd></td><td>Hold</td></tr>
                <tr><td><kbd><b>Space</b></kbd></td><td>Hard drop</td></tr>
                <tr><td><kbd><b>Down</b></kbd></td><td>Soft drop</td></tr>
                <tr><td><kbd><b>Up / Enter</b></kbd></td><td>Rotate right</td></tr>
                <tr><td><kbd><b>Z</b></kbd></td><td>Rotate left</td></tr>
                <tr><td><kbd><b>Left</b></kbd></td><td>Move left</td></tr>
                <tr><td><kbd><b>Right</b></kbd></td><td>Move right</td></tr>
                </table>
                </html>""");
        text.setFont(text.getFont().deriveFont(Font.PLAIN));
        text.setForeground(Color.white);

        contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        contentPanel.setOpaque(false);
        contentPanel.add(text);
    }

}
