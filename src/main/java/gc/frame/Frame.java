package gc.frame;

import gc.seminar1.*;
import gc.seminar2.*;
import gc.seminar3.*;
import gc.seminar4.*;
import gc.seminar5.*;
import gc.seminar6.*;
import gc.seminar7.*;
import gc.seminar8.*;
import gc.seminar9.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Frame extends JFrame {

    final JPanel UPPER_BUTTONS_PANEL = new JPanel();
    final JPanel LOWER_BUTTONS_PANEL = new JPanel();
    JPanel currentPanel = new DefaultPanel();


    Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setLocationRelativeTo(null);


        setButtons();
        this.add(UPPER_BUTTONS_PANEL, BorderLayout.NORTH);
        this.add(LOWER_BUTTONS_PANEL, BorderLayout.SOUTH);

        this.add(currentPanel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }

    void setButtons() {
        setButtonsPanels(UPPER_BUTTONS_PANEL);
        setButtonsPanels(LOWER_BUTTONS_PANEL);

        GCButton[] GCButtons = {
                new GCButton("S1P1", e -> swapPanels(new S1P1())),
                new GCButton("S1P2", e -> swapPanels(new S1P2())),
                new GCButton("S1P3", e -> swapPanels(new S1P3())),
                new GCButton("S2P1", e -> swapPanels(new S2P1())),
                new GCButton("S2P2", e -> swapPanels(new S2P2())),
                new GCButton("S2P3", e -> swapPanels(new S2P3())),
                new GCButton("S3P1", e -> swapPanels(new S3P1())),
                new GCButton("S3P2", e -> swapPanels(new S3P2())),
                new GCButton("S4P1", e -> swapPanels(new S4P1())),
                new GCButton("S5P1", e -> swapPanels(new S5P1())),
                new GCButton("S6P1", e -> swapPanels(new S6P1())),
                new GCButton("S6P2", e -> swapPanels(new S6P2())),
                new GCButton("S6P3", e -> swapPanels(new S6P3())),
                new GCButton("S7P1", e -> swapPanels(new S7P1())),
                new GCButton("S8P1", e -> swapPanels(new S8P1())),
                new GCButton("S9P1", e -> swapPanels(new S9P1()))
        };



        //This codes handles the colors of the buttons
        //A button will turn blue if it's clicked, or yellow if it was clicked before
        Arrays.stream(GCButtons).forEach(
                button -> button.addActionListener(
                        n -> {
                            Arrays.stream(GCButtons).forEach(b -> {
                                if (Color.BLUE.equals(b.getForeground()))
                                    b.setForeground(Color.YELLOW);
                            });
                            button.setForeground(Color.BLUE);
                        }
                )
        );

        for (int i = 0; i < GCButtons.length; i++) {
            if (i < GCButtons.length / 2)
                UPPER_BUTTONS_PANEL.add(GCButtons[i]);
            else
                LOWER_BUTTONS_PANEL.add(GCButtons[i]);
        }
    }

    void setButtonsPanels(JPanel panel) {
        panel.setBackground(Color.GRAY);
        panel.setPreferredSize(new Dimension(Dimensions.WIDTH, 50));
    }
    void swapPanels(DefaultPanel defaultPanel) {
        this.remove(currentPanel);
        currentPanel = defaultPanel;
        this.add(currentPanel, BorderLayout.CENTER);
        this.pack();
        this.repaint();
    }
}
