package org.sloubi;

import org.sloubi.view.MainFrame;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

public class App
{
    public static Font gameFont;
    public static Font barFont;
    public static Clip gameClip;
    public static Clip wowClip;
    public static Clip completeClip;
    public static Clip warningClip;
    public static Clip beepClip;
    public static Clip clapClip;
    public static Clip pop1Clip;
    public static Clip pop2Clip;
    public static Clip clickClip;
    public static final Preferences prefs = Preferences.userNodeForPackage(org.sloubi.App.class);
    public static String version = "1.0";


    public static void main( String[] args )
    {
        initFonts();
        initClips();

        MainFrame frame = new MainFrame();
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

        wowClip = initClip("/sounds/wow.wav");
        completeClip = initClip("/sounds/complete.wav");
        warningClip = initClip("/sounds/warning.wav");
        beepClip = initClip("/sounds/beep.wav");
        clapClip = initClip("/sounds/clap.wav");
        pop1Clip = initClip("/sounds/pop1.wav");
        pop2Clip = initClip("/sounds/pop2.wav");
        clickClip = initClip("/sounds/click.wav");
    }

    private static Clip initClip(String filename) {
        InputStream is = MainFrame.class.getResourceAsStream(filename);
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            clip.open(ais);
            return clip;
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }
}
