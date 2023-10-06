package eu.sloubi;

import eu.sloubi.view.MainFrame;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

public class App {
    public static final Font gameFont;
    public static final Font barFont;
    public static final Clip gameClip;
    public static final Clip wowClip;
    public static final Clip completeClip;
    public static final Clip warningClip;
    public static final Clip beepClip;
    public static final Clip clapClip;
    public static final Clip pop1Clip;
    public static final Clip pop2Clip;
    public static final Clip clickClip;

    public static final Preferences prefs = Preferences.userNodeForPackage(App.class);
    public static final String VERSION = "1.0";

    static {
        InputStream is1 = MainFrame.class.getResourceAsStream("/fonts/Comic Kings.ttf");
        InputStream is2 = MainFrame.class.getResourceAsStream("/fonts/Anton.ttf");

        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, is1);
            barFont = Font.createFont(Font.TRUETYPE_FONT, is2);
        } catch (FontFormatException | IOException e) {
            throw new IllegalStateException(e);
        }

        try {
            gameClip = AudioSystem.getClip();
            wowClip = AudioSystem.getClip();
            completeClip = AudioSystem.getClip();
            warningClip = AudioSystem.getClip();
            beepClip = AudioSystem.getClip();
            clapClip = AudioSystem.getClip();
            pop1Clip = AudioSystem.getClip();
            pop2Clip = AudioSystem.getClip();
            clickClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) {
        initClips();
        new MainFrame();
    }

    public static void initClips() {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(MainFrame.class.getResourceAsStream("/sounds/tetris-ragtime.wav"));
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedInputStream);
            gameClip.open(ais);
            gameClip.loop(Clip.LOOP_CONTINUOUSLY);
            gameClip.stop();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new IllegalStateException(e);
        }

        initClip(wowClip, "/sounds/wow.wav");
        initClip(completeClip, "/sounds/complete.wav");
        initClip(warningClip, "/sounds/warning.wav");
        initClip(beepClip, "/sounds/beep.wav");
        initClip(clapClip, "/sounds/clap.wav");
        initClip(pop1Clip, "/sounds/pop1.wav");
        initClip(pop2Clip, "/sounds/pop2.wav");
        initClip(clickClip, "/sounds/click.wav");
    }

    private static void initClip(Clip clip, String filename) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(MainFrame.class.getResourceAsStream(filename));
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedInputStream);
            clip.open(ais);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new IllegalStateException(e);
        }
    }
}
