package org.sloubi;

import org.sloubi.view.MainFrame;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class App
{
    public static Font gameFont;
    public static Font barFont;
    public static Clip gameClip;
    public static Clip wowClip;
    public static String version = "1.0";


    public static void main( String[] args )
    {
        initFonts();
        initClips();

        MainFrame frame = new MainFrame();

        int screen = 2;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        frame.setLocation(gd[screen].getDefaultConfiguration().getBounds().x, frame.getY());
    }

    public static void initFonts() {
        InputStream is1 = MainFrame.class.getResourceAsStream("/fonts/Comic Kings.ttf");
        InputStream is2 = MainFrame.class.getResourceAsStream("/fonts/Anton.ttf");
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, is1);
            barFont = Font.createFont(Font.TRUETYPE_FONT, is2);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void initClips() {
        InputStream is = MainFrame.class.getResourceAsStream("/sounds/tetris-ragtime.wav");
        try {
            gameClip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            gameClip.open(ais);
            gameClip.loop(Clip.LOOP_CONTINUOUSLY);
            gameClip.stop();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }

        InputStream isWow = MainFrame.class.getResourceAsStream("/sounds/wow.wav");
        try {
            wowClip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(isWow);
            wowClip.open(ais);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
