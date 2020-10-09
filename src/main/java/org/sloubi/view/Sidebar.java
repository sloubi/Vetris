package org.sloubi.view;

import org.sloubi.model.Board;
import org.sloubi.model.BoardListener;
import org.sloubi.model.HighScores;
import org.sloubi.model.Score;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Sidebar extends JPanel implements BoardListener, ActionListener {

    private final Color primaryColor = new Color(69, 162, 158);
    private final Color secondaryColor = Color.white;
    private final Board board;
    private final BoardPanel boardPanel;
    private final JLabel score = new JLabel("0");
    private final JLabel lines = new JLabel("0");
    private final JLabel level = new JLabel("1");
    private final JLabel time = new JLabel("0");
    private final JButton scoreButton = new JButton();
    private final JButton optionButton = new JButton();
    private final JButton aboutButton = new JButton();
    private ShapePanel nextShapePanel;
    private final Font gameFont;
    private JPanel mainPanel;
    private JPanel scorePanel;
    private JPanel nextPanel;
    private JPanel statsPanel;
    private JPanel buttonPanel;
    private final Font statsFont = new Font("Georgia", Font.PLAIN, 13);

    public Sidebar(Board board, BoardPanel boardPanel, Font gameFont) {
        this.board = board;
        this.boardPanel = boardPanel;
        this.gameFont = gameFont;
        this.board.addListener(this);
        scoreButton.addActionListener(this);
        optionButton.addActionListener(this);
        aboutButton.addActionListener(this);

        initComponents();

        setOpaque(false);
        setLayout(new BorderLayout(0, 0));
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);

        setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 30));

        scoreChanged();
    }

    private void initComponents() {
        initScorePanel();
        initNextPanel();
        initStatsPanel();
        initMainPanel();
        initButtonPanel();
    }

    private void initMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 26, 0);

        mainPanel.add(new TitlePanel(gameFont), gbc);
        mainPanel.add(scorePanel, gbc);
        mainPanel.add(nextPanel, gbc);
        mainPanel.add(statsPanel, gbc);
    }

    private void initScorePanel() {
        score.setForeground(primaryColor);
        score.setFont(gameFont.deriveFont(25f));

        scorePanel = new JPanel();
        scorePanel.setOpaque(false);
        scorePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(secondaryColor, 2),
                "Score",
                0,
                0,
                gameFont.deriveFont(15f),
                primaryColor));
        scorePanel.add(score);
    }

    private void initNextPanel() {
        nextShapePanel = new ShapePanel(board.getNextShape(), 20);
        nextPanel = new JPanel();
        nextPanel.setOpaque(false);
        nextPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(secondaryColor, 2),
                "Piece suivante",
                0,
                0,
                gameFont.deriveFont(15f),
                primaryColor));
        nextPanel.setPreferredSize(new Dimension(100, 130));
        nextPanel.add(nextShapePanel);
    }

    private void initStatsPanel() {
        statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 2));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(secondaryColor, 2),
                "Statistiques",
                0,
                0,
                gameFont.deriveFont(15f),
                primaryColor),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        initStatLabel("Niveau", level);
        initStatLabel("Lignes", lines);
        initStatLabel("Temps", time);
    }

    private void initStatLabel(String name, JLabel value) {
        JLabel labelName = new JLabel(name + " :");
        labelName.setFont(statsFont);
        labelName.setForeground(primaryColor);
        statsPanel.add(labelName);

        value.setFont(statsFont);
        value.setForeground(primaryColor);
        statsPanel.add(value);
    }

    private void initButtonPanel() {
        buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(scoreButton);
        buttonPanel.add(optionButton);
        buttonPanel.add(aboutButton);

        initButton(scoreButton, "medal.png");
        initButton(optionButton, "wrench.png");
        initButton(aboutButton, "info-circle.png");
    }

    private void initButton(JButton button, String filename) {
        URL url = getClass().getResource("/images/" + filename);
        ImageIcon imageIcon = new ImageIcon(url);
        button.setFocusPainted(false);
        button.setIcon(imageIcon);
        button.setBackground(primaryColor);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border line = new LineBorder(secondaryColor);
        button.setBorder(BorderFactory.createCompoundBorder(line, margin));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void boardChanged() {

    }

    @Override
    public void scoreChanged() {
        Score s = board.getScore();
        level.setText(String.valueOf(s.getLevel()));
        score.setText(String.valueOf(s.getScore()));
        lines.setText(String.valueOf(s.getLines()));
    }

    @Override
    public void stateChanged() {
    }

    @Override
    public void clockChanged() {
        time.setText(String.valueOf(board.getSeconds()));
    }

    @Override
    public void nextShapeChanged() {
        nextShapePanel.setShape(board.getNextShape());
    }

    @Override
    public void newHighScore(Score score) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(scoreButton)) {
            board.pause();

            JDialog dialog = new JDialog();
            HighScores hs = board.getHighscores();
            for (Score score : hs) {
                dialog.getContentPane().add(new JLabel(score.toString()));
            }

            dialog.setTitle("A propos");
            dialog.getContentPane().setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
            dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            dialog.setLocationRelativeTo(null);
            dialog.setSize(300, 200);
            dialog.setResizable(false);
            dialog.setVisible(true);
            boardPanel.requestFocus();
        }
        else if (e.getSource().equals(aboutButton)) {
            AboutDialog ad = new AboutDialog();
            ad.setVisible(true);
            // On redonne le focus sinon les touches ne fonctionnent pas
            boardPanel.requestFocus();
        }
        else if (e.getSource().equals(optionButton)) {
            OptionsDialog od = new OptionsDialog();
            od.setVisible(true);
            boardPanel.requestFocus();
        }
    }
}

