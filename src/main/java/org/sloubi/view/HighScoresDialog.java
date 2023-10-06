package org.sloubi.view;

import org.sloubi.model.HighScores;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class HighScoresDialog extends JDialog {
    public HighScoresDialog(HighScores hs) {
        JTable table = new JTable(new HighScoresModel(hs));

        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        setTitle("HighScores");
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class HighScoresModel extends AbstractTableModel {

    private final HighScores hs;
    private final transient String[] headers = {"Name", "Score", "Lines", "Level", "Time", "LPM", "TPM", "V Piece"};

    public HighScoresModel(HighScores hs) {
        this.hs = hs;
    }

    @Override
    public int getRowCount() {
        return hs.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> hs.get(rowIndex).getName();
            case 1 -> hs.get(rowIndex).getScore();
            case 2 -> hs.get(rowIndex).getLines();
            case 3 -> hs.get(rowIndex).getLevel();
            case 4 -> hs.get(rowIndex).getSeconds();
            case 5 -> hs.get(rowIndex).getLPM();
            case 6 -> hs.get(rowIndex).getTPM();
            case 7 -> hs.get(rowIndex).isvShapeActive() ? "yes" : "no";
            default -> null;
        };
    }
}
