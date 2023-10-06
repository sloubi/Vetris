package org.sloubi.view;

import org.sloubi.App;
import org.sloubi.model.Board;
import org.sloubi.model.BoardListener;
import org.sloubi.model.Score;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements KeyListener, BoardListener, ActionListener, MenuListener {

    private final Board board = new Board();
    private BoardPanel boardPanel;
    private RoundedPanel boardPanelContainer = new RoundedPanel();
    private final JButton closeButton = new JButton("X");
    private final MenuPanel menu = new MenuPanel();
    private final RightSidebar rightSidebar = new RightSidebar(board);
    private final LeftSidebar leftSidebar = new LeftSidebar(board);

    public MainFrame() {
        initComponents();
        buildFrame();
        eventHandling();
        board.start();
    }

    public void initComponents() {
        boardPanel = new BoardPanel(board);

        boardPanelContainer = new RoundedPanel();
        boardPanelContainer.add(boardPanel);
        boardPanelContainer.setFilled(true);
        boardPanelContainer.setBorderColor(new Color(80, 80, 80));
        boardPanelContainer.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        JPanel switcher = new JPanel();
        switcher.setOpaque(false);
        switcher.add(boardPanelContainer);
        switcher.add(menu);
        menu.setVisible(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 30, 60));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new TitlePanel(), BorderLayout.PAGE_START);
        mainPanel.add(switcher, BorderLayout.CENTER);
        mainPanel.add(rightSidebar, BorderLayout.EAST);
        mainPanel.add(leftSidebar, BorderLayout.WEST);

        initCloseButton();
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setOpaque(false);
        closePanel.add(closeButton);

        BackgroundContainer container = new BackgroundContainer();
        container.setLayout(new BorderLayout());
        container.add(closePanel, BorderLayout.PAGE_START);
        container.add(mainPanel, BorderLayout.CENTER);
        container.add(new Bottombar(board), BorderLayout.PAGE_END);

        setContentPane(container);
    }

    public void initCloseButton() {
        closeButton.setBackground(new Color(12, 12, 12));
        closeButton.setForeground(Color.white);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        closeButton.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();
            if (model.isRollover()) {
                closeButton.setBackground(new Color(168, 21, 21));
            } else {
                closeButton.setBackground(new Color(12, 12, 12));
            }
        });
    }

    public void eventHandling() {
        setFocusable(true);
        requestFocus();

        closeButton.addActionListener(this);

        FrameDragListener frameDragListener = new FrameDragListener(this);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);

        addKeyListener(this);

        menu.addListener(this);

        board.addListener(this);
    }

    public void buildFrame() {
        setTitle("Vetris");
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        if (App.prefs.getBoolean("fullScreen", false)) {
            setExtendedState(Frame.MAXIMIZED_BOTH);
        } else {
            pack();
            setLocationByPlatform(true);
        }

        setVisible(true);
    }

    public void quit() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void pause() {
        board.pause();
        boardPanelContainer.setVisible(board.getState() == Board.GameState.IN_GAME);
        menu.setState(board.getState());
        menu.setVisible(board.getState() == Board.GameState.PAUSED);

        // Seulement quand le plateau de jeu est affiché, sinon la fenêtre se base sur la taille du menu
        if (board.getState() == Board.GameState.IN_GAME) {
            updateSize();
        }
    }

    public void gameOver() {
        boardPanelContainer.setVisible(false);
        menu.setState(board.getState());
        menu.setVisible(true);
    }

    public void updateSize() {
        boardPanel.updateSize();
        boardPanelContainer.revalidate();

        if (!App.prefs.getBoolean("fullScreen", false)) {
            pack();
        }
    }

    /**
     * Close App
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        quit();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            board.tryToMove(0, 1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            board.tryToMove(1, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            board.tryToMove(-1, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            board.rotateShape(true);
        } else if (e.getKeyChar() == 'z') {
            board.rotateShape(false);
        } else if (e.getKeyChar() == 'c') {
            board.hold();
        } else if (e.getKeyCode() == KeyEvent.VK_PAUSE || e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (board.getState() == Board.GameState.OVER) {
                board.start();
            } else {
                pause();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            board.hardDrop();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void boardChanged() {
        boardPanel.repaint();
    }

    @Override
    public void holdChanged() {

    }

    @Override
    public void scoreChanged() {

    }

    @Override
    public void stateChanged() {
        boardPanel.repaint();

        // Quand on est en pause, la musique s'arrête
        if (App.prefs.getBoolean("music", true)) {
            if (board.getState() != Board.GameState.IN_GAME) {
                App.gameClip.stop();
            } else {
                App.gameClip.loop(Clip.LOOP_CONTINUOUSLY);
                App.gameClip.start();
            }
        }

        if (board.getState() == Board.GameState.OVER) {
            gameOver();
        }
    }

    @Override
    public void clockChanged() {

    }

    @Override
    public void nextShapeChanged() {

    }

    @Override
    public void newHighScore(Score score) {
        String response = JOptionPane.showInputDialog(this,
                board.getScore().getScore() + " is a new highscore! What's your name?",
                "New record!",
                JOptionPane.INFORMATION_MESSAGE);
        if (response != null) {
            score.setName(response);
            board.getHighscores().add(score);
            board.getHighscores().save();
        }
    }

    @Override
    public void userEvent(String event) {
        if (App.prefs.getBoolean("sound", true)) {
            Clip clip = switch (event) {
                case "tetris" -> App.wowClip;
                case "rotate", "move" -> App.beepClip;
                case "bottom" -> App.pop2Clip;
                case "hardDrop" -> App.pop1Clip;
                case "line" -> App.completeClip;
                case "vShape" -> App.warningClip;
                case "gameOver" -> App.clapClip;
                case "hold" -> App.clickClip;
                default -> throw new IllegalStateException("Unexpected value: " + event);
            };
            clip.setFramePosition(0);
            clip.start();
        }
    }

    @Override
    public void resumeClicked() {
        pause();
    }

    @Override
    public void newGameClicked() {
        board.start();
        boardPanelContainer.setVisible(true);
        menu.setVisible(false);
    }

    @Override
    public void quitClicked() {
        quit();
    }

    @Override
    public void highScoresClicked() {
        new HighScoresDialog(board.getHighscores());
        requestFocus();
    }

    @Override
    public void howToPlayClicked() {
        new HowToPlayDialog();
        requestFocus();
    }

    @Override
    public void optionsClicked() {
        new OptionsDialog(board.getState());
        leftSidebar.updateLayout();
        rightSidebar.updateLayout();
        requestFocus();
    }

    @Override
    public void aboutClicked() {
        new AboutDialog();
        requestFocus();
    }

    public static class FrameDragListener extends MouseAdapter {

        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }


}


