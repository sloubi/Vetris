package org.sloubi.view;

import org.sloubi.App;
import org.sloubi.model.Board;

import javax.swing.*;
import java.awt.event.*;

public class OptionsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner gridSizeSpinner;
    private JSpinner squareSizeSpinner;
    private JCheckBox reliefOnThePiecesCheckBox;
    private JCheckBox musicCheckBox;
    private JCheckBox vPieceCheckBox;
    private JCheckBox soundEffectsCheckBox;
	private JCheckBox showTetriminosPerMinuteCheckBox;
    private JCheckBox showLinesPerMinuteCheckBox;
    private JCheckBox showSecondsCheckBox;
    private final Board.GameState gameState;

    public OptionsDialog(Board.GameState gameState) {
        this.gameState = gameState;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        gridSizeSpinner.setModel(new SpinnerNumberModel(2, 0, 20, 1));
        squareSizeSpinner.setModel(new SpinnerNumberModel(20, 4, 40, 1));

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        load();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK() {
        save();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void load() {
        musicCheckBox.setSelected(App.prefs.getBoolean("music", true));
        soundEffectsCheckBox.setSelected(App.prefs.getBoolean("sound", true));
        vPieceCheckBox.setSelected(App.prefs.getBoolean("vshape", true));
        reliefOnThePiecesCheckBox.setSelected(App.prefs.getBoolean("reliefOnThePieces", true));
        gridSizeSpinner.setValue(App.prefs.getInt("gridSize", 2));
        squareSizeSpinner.setValue(App.prefs.getInt("squareSize", 20));
        showTetriminosPerMinuteCheckBox.setSelected(App.prefs.getBoolean("showTetriminosPerMinute", false));
        showLinesPerMinuteCheckBox.setSelected(App.prefs.getBoolean("showLinesPerMinute", false));
        showSecondsCheckBox.setSelected(App.prefs.getBoolean("showSeconds", false));

        if (gameState == Board.GameState.InGame)
            vPieceCheckBox.setEnabled(false);
    }

    private void save() {
        App.prefs.putBoolean("music", musicCheckBox.isSelected());
        App.prefs.putBoolean("sound", soundEffectsCheckBox.isSelected());
        App.prefs.putBoolean("vshape", vPieceCheckBox.isSelected());
        App.prefs.putBoolean("reliefOnThePieces", reliefOnThePiecesCheckBox.isSelected());
        App.prefs.putInt("gridSize", (Integer) gridSizeSpinner.getValue());
        App.prefs.putInt("squareSize", (Integer) squareSizeSpinner.getValue());
        App.prefs.putBoolean("showTetriminosPerMinute", showTetriminosPerMinuteCheckBox.isSelected());
        App.prefs.putBoolean("showLinesPerMinute", showLinesPerMinuteCheckBox.isSelected());
        App.prefs.putBoolean("showSeconds", showSecondsCheckBox.isSelected());
    }
}
