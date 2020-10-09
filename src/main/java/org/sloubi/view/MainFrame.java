package org.sloubi.view;

import org.sloubi.model.Board;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MainFrame extends JFrame {
    private Font gameFont;
    public static Clip clip;
    public static Clip clipWow;

    public MainFrame() {
        setTitle("Vetris");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        InputStream is = MainFrame.class.getResourceAsStream("/sounds/tetris-ragtime.wav");
        try {
            clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.stop();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        InputStream isWow = MainFrame.class.getResourceAsStream("/sounds/wow.wav");
        try {
            clipWow = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(isWow);
            clipWow.open(ais);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        initFont();

        Board board = new Board();
        BoardPanel boardPanel = new BoardPanel(board, gameFont);

        Container mainPanel = new Container();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(new Sidebar(board, boardPanel, gameFont), BorderLayout.EAST);

        setContentPane(mainPanel);
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }

    public void initFont() {
        String fName = "/fonts/Comic Kings.ttf";
        InputStream is = MainFrame.class.getResourceAsStream(fName);
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}

class Container extends JPanel {

    public Container() {
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        Color color1 = new Color(11, 12, 16);
        Color color2 = new Color(43, 43, 44);
        GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
