package eu.sloubi.view;

import eu.sloubi.model.HighScores;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class HighScoresDialog extends JDialog {
    public HighScoresDialog(HighScores hs) {
        JTable table = new JTable(new HighScoresModel(hs));
        TableColumnModel colModel=table.getColumnModel();
        colModel.getColumn(8).setPreferredWidth(200);

        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        setTitle("HighScores");
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        setSize(800, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class HighScoresModel extends AbstractTableModel {

    private final transient HighScores hs;
    private final String[] headers = {"Name", "Score", "Lines", "Level", "Time", "LPM", "TPM", "V Piece", "Date"};

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
            case 8 -> hs.get(rowIndex).getDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM));
            default -> null;
        };
    }
}
