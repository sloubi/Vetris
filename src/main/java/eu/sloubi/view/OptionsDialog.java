package eu.sloubi.view;

import eu.sloubi.App;
import eu.sloubi.model.Board;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

public class OptionsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner gridSizeSpinner;
    private JSpinner squareSizeSpinner;
    private Checkbox reliefEffectOnTheCheckbox;
    private Checkbox musicCheckbox;
    private Checkbox vPieceCheckbox;
    private Checkbox soundEffectsCheckbox;
    private Checkbox showTetriminosPerMinuteCheckbox;
    private Checkbox showLinesPerMinuteCheckbox;
    private Checkbox showSecondsCheckbox;
    private Checkbox fullScreenCheckbox;
    private JPanel gamePanel;
    private JPanel displayPanel;
    private JPanel statsPanel;
    private JLabel dialogTitle;
    private final Board.GameState gameState;

    public OptionsDialog(Board.GameState gameState) {
        this.gameState = gameState;

        BackgroundContainer main = new BackgroundContainer();
        main.setBorder(BorderFactory.createLineBorder(Color.white));
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(new ClosePanel(this));
        main.add(contentPane);

        initTitledPanel();
        initButtons();

        Font font = App.gameFont.deriveFont(24f);
        dialogTitle.setFont(font);
        dialogTitle.setForeground(new Color(228, 80, 0));

        setContentPane(main);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        gridSizeSpinner.setModel(new SpinnerNumberModel(2, 0, 20, 1));
        squareSizeSpinner.setModel(new SpinnerNumberModel(20, 4, 40, 1));

        // call onCancel() on ESCAPE
         getRootPane().registerKeyboardAction(e -> onCancel(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);

        load();
        setResizable(true);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initTitledPanel() {
        TitledBorder gametitledBorder = BorderFactory.createTitledBorder("Game");
        gametitledBorder.setTitleColor(Color.white);
        gamePanel.setBorder(gametitledBorder);

        TitledBorder statsTitledBorder = BorderFactory.createTitledBorder("Statistics");
        statsTitledBorder.setTitleColor(Color.white);
        statsPanel.setBorder(statsTitledBorder);

        TitledBorder displayTitledBorder = BorderFactory.createTitledBorder("Display");
        displayTitledBorder.setTitleColor(Color.white);
        displayPanel.setBorder(displayTitledBorder);
    }

    private void onOK() {
        save();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void load() {
        musicCheckbox.setSelected(App.prefs.getBoolean("music", true));
        soundEffectsCheckbox.setSelected(App.prefs.getBoolean("sound", true));
        vPieceCheckbox.setSelected(App.prefs.getBoolean("vshape", true));
        reliefEffectOnTheCheckbox.setSelected(App.prefs.getBoolean("reliefOnThePieces", true));
        gridSizeSpinner.setValue(App.prefs.getInt("gridSize", 2));
        squareSizeSpinner.setValue(App.prefs.getInt("squareSize", 20));
        showTetriminosPerMinuteCheckbox.setSelected(App.prefs.getBoolean("showTetriminosPerMinute", false));
        showLinesPerMinuteCheckbox.setSelected(App.prefs.getBoolean("showLinesPerMinute", false));
        showSecondsCheckbox.setSelected(App.prefs.getBoolean("showSeconds", false));
        fullScreenCheckbox.setSelected(App.prefs.getBoolean("fullScreen", false));

        if (gameState == Board.GameState.IN_GAME) {
            vPieceCheckbox.setEnabled(false);
        }
    }

    private void save() {
        if (App.prefs.getBoolean("fullScreen", false) != fullScreenCheckbox.isSelected()) {
            JOptionPane.showMessageDialog(null,
                    "You need to restart the game to apply your modification.",
                    "Full screen",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        App.prefs.putBoolean("music", musicCheckbox.isSelected());
        App.prefs.putBoolean("sound", soundEffectsCheckbox.isSelected());
        App.prefs.putBoolean("vshape", vPieceCheckbox.isSelected());
        App.prefs.putBoolean("reliefOnThePieces", reliefEffectOnTheCheckbox.isSelected());
        App.prefs.putInt("gridSize", (Integer) gridSizeSpinner.getValue());
        App.prefs.putInt("squareSize", (Integer) squareSizeSpinner.getValue());
        App.prefs.putBoolean("showTetriminosPerMinute", showTetriminosPerMinuteCheckbox.isSelected());
        App.prefs.putBoolean("showLinesPerMinute", showLinesPerMinuteCheckbox.isSelected());
        App.prefs.putBoolean("showSeconds", showSecondsCheckbox.isSelected());
        App.prefs.putBoolean("fullScreen", fullScreenCheckbox.isSelected());
    }

    public void initButtons() {
        buttonOK.setOpaque(true);
        buttonOK.setBackground(Color.black);
        buttonOK.setForeground(Color.white);
        buttonOK.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(228, 80, 0)),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)));

        buttonCancel.setOpaque(true);
        buttonCancel.setBackground(Color.black);
        buttonCancel.setForeground(Color.white);
        buttonCancel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(228, 80, 0)),
                BorderFactory.createEmptyBorder(6, 15, 6, 15)));

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
    }

}
