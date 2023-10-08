package eu.sloubi.view;

import eu.sloubi.model.HighScores;
import eu.sloubi.model.WebHighScores;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class HighScoresDialog extends JDialog {

    public HighScoresDialog(HighScores localHighScores) {
        JTable tableLocal = new JTable(new HighScoresModel(localHighScores));
        TableColumnModel colModelLocal = tableLocal.getColumnModel();
        colModelLocal.getColumn(8).setPreferredWidth(200);
        JScrollPane scrollPaneLocal = new JScrollPane(tableLocal);
        scrollPaneLocal.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocal.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTable tableWeb = new JTable(new HighScoresModel(new WebHighScores()));
        TableColumnModel colModelWeb = tableWeb.getColumnModel();
        colModelWeb.getColumn(8).setPreferredWidth(200);
        JScrollPane scrollPaneWeb = new JScrollPane(tableWeb);
        scrollPaneWeb.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneWeb.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelLocal = new JLabel("Local High scores");
        labelLocal.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelWeb = new JLabel("Web High scores");
        labelWeb.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(labelLocal);
        main.add(scrollPaneLocal);
        main.add(labelWeb);
        main.add(scrollPaneWeb);

        getContentPane().add(main);
        setTitle("High Scores");
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
            case 8 ->
                    hs.get(rowIndex).getDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM));
            default -> null;
        };
    }
}
