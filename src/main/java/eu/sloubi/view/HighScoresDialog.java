package eu.sloubi.view;

import eu.sloubi.App;
import eu.sloubi.model.HighScores;
import eu.sloubi.model.WebHighScores;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class HighScoresDialog extends JDialog {

    public HighScoresDialog(HighScores localHighScores) {
        Font font = App.gameFont.deriveFont(24f);
        JLabel labelLocal = new JLabel("Local High scores");
        labelLocal.setFont(font);
        labelLocal.setForeground(new Color(228, 80, 0));
        labelLocal.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelWeb = new JLabel("Web High scores");
        labelWeb.setFont(font);
        labelWeb.setForeground(new Color(228, 80, 0));
        labelWeb.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton closeButton = new JButton("X");
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
        closeButton.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        closePanel.setOpaque(false);
        closePanel.add(closeButton);

        BackgroundContainer main = new BackgroundContainer();
        main.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(closePanel);
        main.add(labelLocal);
        main.add(createTable(localHighScores));
        main.add(Box.createVerticalStrut(30));
        main.add(labelWeb);
        main.add(createTable(new WebHighScores()));

        getContentPane().add(main);
        setTitle("High Scores");
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        setUndecorated(true);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JScrollPane createTable(HighScores localHighScores) {
        JTable table = new JTable(new HighScoresModel(localHighScores));
        table.setOpaque(false);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setBackground(Color.black);
        table.setForeground(Color.white);
        table.setShowGrid(false);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(false);

        final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);
        renderer.setForeground(Color.white);
        renderer.setBorder(BorderFactory.createEmptyBorder());
        renderer.setFont(renderer.getFont().deriveFont(Font.BOLD, 24f));
        table.getTableHeader().setDefaultRenderer(renderer);

        TableColumnModel colModelLocal = table.getColumnModel();
        colModelLocal.getColumn(8).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setOpaque(false);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        return scrollPane;
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
