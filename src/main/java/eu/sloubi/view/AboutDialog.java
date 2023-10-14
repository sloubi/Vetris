package eu.sloubi.view;

import eu.sloubi.App;
import eu.sloubi.model.Shape;
import eu.sloubi.model.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutDialog extends JDialog {

    JLabel link;
    ShapePanel icon;
    JPanel textPane;

    public AboutDialog() {
        initLink();
        initIcon();
        initTextPane();

        BackgroundContainer main = new BackgroundContainer();
        main.setBorder(BorderFactory.createLineBorder(Color.white));
        main.setLayout(new BorderLayout());
        main.add(new ClosePanel(this), BorderLayout.NORTH);
        main.add(icon, BorderLayout.WEST);
        main.add(textPane, BorderLayout.CENTER);

        getRootPane().registerKeyboardAction(e -> dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);

        getContentPane().add(main);
        setTitle("About");
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        setUndecorated(true);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initIcon() {
        icon = new ShapePanel(new Shape(Tetromino.V_SHAPE), 20, 1);
    }

    private void initLink() {
        link = new JLabel("<html><u>sloubi.eu</u></html>");
        link.setFont(link.getFont().deriveFont(Font.PLAIN));
        link.setForeground(Color.white);
        link.setCursor(new Cursor(Cursor.HAND_CURSOR));

        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://sloubi.eu"));
                } catch (IOException | URISyntaxException ex) {
                    throw new IllegalStateException(ex);
                }
            }
        });
    }

    private void initTextPane() {
        textPane = new JPanel();
        textPane.setLayout(new GridBagLayout());
        textPane.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 0);

        Font font = App.gameFont.deriveFont(24f);
        JLabel title = new JLabel("Vetris " + App.VERSION);
        title.setFont(font);
        title.setForeground(new Color(228, 80, 0));
        textPane.add(title, gbc);

        JPanel authorPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        authorPane.setOpaque(false);
        JLabel author = new JLabel("By Sloubi, ");
        author.setFont(author.getFont().deriveFont(Font.PLAIN));
        author.setForeground(Color.white);
        authorPane.add(author);
        authorPane.add(link);

        textPane.add(authorPane, gbc);
        textPane.add(new JLabel(" "), gbc);

        JLabel createdAt = new JLabel("Created in September 2020");
        createdAt.setFont(createdAt.getFont().deriveFont(Font.PLAIN));
        createdAt.setForeground(Color.gray);
        textPane.add(createdAt, gbc);
    }

}
