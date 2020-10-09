package org.sloubi.view;

import org.sloubi.model.Shape;
import org.sloubi.model.Tetromino;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutDialog extends JDialog {

    JLabel link;
    ShapePanel icon;
    JPanel textPane;
    JPanel buttonPane;

    public AboutDialog() {
        initLink();
        initIcon();
        initTextPane();
        initButtonPane();

        setTitle("A propos");
        setLayout(new BorderLayout());
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 200);
        setResizable(false);

        add(icon, BorderLayout.WEST);
        add(textPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    private void initIcon() {
        icon = new ShapePanel(new Shape(Tetromino.TShape), 20);
    }

    private void initLink() {
        link = new JLabel("<html><u>sloubi.eu</u></html>");
        link.setFont(link.getFont().deriveFont(Font.PLAIN));
        link.setForeground(Color.blue);
        link.setCursor(new Cursor(Cursor.HAND_CURSOR));

        link.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://sloubi.eu"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }
        });
    }

    private void initTextPane() {
        textPane = new JPanel();
        textPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 0);


        JLabel title = new JLabel("Vetris v1");
        textPane.add(title, gbc);

        JPanel authorPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel author = new JLabel("Par Sloubi, ");
        author.setFont(author.getFont().deriveFont(Font.PLAIN));
        authorPane.add(author);
        authorPane.add(link);

        textPane.add(authorPane, gbc);
        textPane.add(new JLabel(" "), gbc);

        JLabel createdAt = new JLabel("Créé en Septembre 2020");
        createdAt.setFont(createdAt.getFont().deriveFont(Font.PLAIN));
        createdAt.setForeground(Color.darkGray);
        textPane.add(createdAt, gbc);
    }

    private void initButtonPane() {
        JButton close = new JButton("Fermer");
        close.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            dispose();
          }
        });

        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(close);
    }
}
