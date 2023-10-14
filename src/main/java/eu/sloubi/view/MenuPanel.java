package eu.sloubi.view;

import eu.sloubi.App;
import eu.sloubi.model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends RoundedPanel implements ActionListener {
    private final transient MenuListener listener;
    private final JButton game = new JButton("Resume");
    private final JButton options = new JButton("Options");
    private final JButton highscores = new JButton("HighScores");
    private final JButton howToPlay = new JButton("How to play?");
    private final JButton about = new JButton("About");
    private final JButton quit = new JButton("Quit");
    private Board.GameState state = Board.GameState.PAUSED;

    public MenuPanel(MenuListener listener) {
        this.listener = listener;

        setLayout(new GridLayout(6, 1, 0, 10));
        setOpaque(false);

        initButton(game);
        initButton(options);
        initButton(highscores);
        initButton(howToPlay);
        initButton(about);
        initButton(quit);

        setBorder(BorderFactory.createEmptyBorder(100, 10, 20, 10));
        setTitle("PAUSED");
        setTitleColor(new Color(228, 80, 0));
        setTitleSize(35f);
        setTitleTopMargin(60);
        setFilled(true);
        setBorderColor(Color.black);
    }

    public void initButton(JButton button) {
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setFont(App.barFont.deriveFont(24f));
        button.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(228, 80, 0));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.black);
            }
        });
        button.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();
            if (model.isRollover()) {
                button.setBackground(new Color(228, 80, 0));
            } else {
                button.setBackground(Color.black);
            }
        });

        add(button);
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(game)) {
            if (state == Board.GameState.PAUSED) {
                listener.resumeClicked();
            } else {
                listener.newGameClicked();
            }
        } else if (e.getSource().equals(quit)) {
            listener.quitClicked();
        } else if (e.getSource().equals(options)) {
            listener.optionsClicked();
        } else if (e.getSource().equals(highscores)) {
            listener.highScoresClicked();
        } else if (e.getSource().equals(howToPlay)) {
            listener.howToPlayClicked();
        } else if (e.getSource().equals(about)) {
            listener.aboutClicked();
        }
    }

    public void update() {
        if (state == Board.GameState.PAUSED) {
            game.setText("Resume");
            setTitle("PAUSED");
        } else {
            game.setText("New game");
            setTitle("GAME OVER");
        }
    }

    public void setState(Board.GameState state) {
        this.state = state;
        update();
    }
}
