package eu.sloubi.view;

import eu.sloubi.model.BoardListener;
import eu.sloubi.model.Score;
import eu.sloubi.App;
import eu.sloubi.model.Board;

import javax.swing.*;
import java.awt.*;

public class LeftSidebar extends JPanel implements BoardListener {
    private final Board board;
    private final JLabel lines = new JLabel();
    private final JLabel lpm = new JLabel();
    private final JLabel tpm = new JLabel("0");
    private final ShapePanel holdShapePanel;
    private final RoundedPanel lpmPanel;
    private final RoundedPanel tpmPanel;

    public LeftSidebar(Board board) {
        this.board = board;
        this.board.addListener(this);

        holdShapePanel = new ShapePanel(board.getHoldedShape(), 10, 1);

        RoundedPanel holdPanel = new RoundedPanel();
        holdPanel.setBorder(BorderFactory.createEmptyBorder(35, 10, 10, 10));
        holdPanel.setTitle("HOLD");
        holdPanel.add(holdShapePanel);

        RoundedPanel linesPanel = new RoundedPanel();
        linesPanel.setTitle("LINES");
        linesPanel.setFilled(true);
        linesPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 4, 30));
        linesPanel.add(lines);
        lines.setFont(App.barFont.deriveFont(24f));

        lpmPanel = new RoundedPanel();
        lpmPanel.setTitle("LPM");
        lpmPanel.setFilled(true);
        lpmPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 4, 30));
        lpmPanel.add(lpm);
        lpm.setFont(App.barFont.deriveFont(24f));

        tpmPanel = new RoundedPanel();
        tpmPanel.setTitle("TPM");
        tpmPanel.setFilled(true);
        tpmPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 4, 30));
        tpmPanel.add(tpm);
        tpm.setFont(App.barFont.deriveFont(24f));

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 26, 0);

        // On fixe la largeur des blocs pour que le layout ne change pas quand
        // un chiffre est ajouté à une des valeurs
        // Exemple : quand les lignes passent de 99 à 100
        Dimension dim = new Dimension(holdPanel.getPreferredSize().width, 80);
        linesPanel.setPreferredSize(dim);
        lpmPanel.setPreferredSize(dim);
        tpmPanel.setPreferredSize(dim);

        container.add(holdPanel, gbc);
        container.add(linesPanel, gbc);
        container.add(lpmPanel, gbc);
        container.add(tpmPanel, gbc);

        add(container);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        scoreChanged();
        updateLayout();
    }

    public void updateLayout() {
        lpmPanel.setVisible(App.prefs.getBoolean("showLinesPerMinute", false));
        tpmPanel.setVisible(App.prefs.getBoolean("showTetriminosPerMinute", false));
    }

    @Override
    public void boardChanged() {

    }

    @Override
    public void holdChanged() {
        holdShapePanel.setShape(board.getHoldedShape());
    }

    @Override
    public void scoreChanged() {
        lines.setText(String.valueOf(board.getScore().getLines()));
        lpm.setText(String.valueOf(board.getScore().getLPM()));
        tpm.setText(String.valueOf(board.getScore().getTPM()));
    }

    @Override
    public void stateChanged() {

    }

    @Override
    public void clockChanged() {

    }

    @Override
    public void nextShapeChanged() {

    }

    @Override
    public void newHighScore(Score score) {

    }

    @Override
    public void userEvent(String event) {
        if (event.equals("bottom")) {
            tpm.setText(String.valueOf(board.getScore().getTPM()));
        }
    }
}
