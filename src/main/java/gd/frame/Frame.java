package gd.frame;

import gd.seminar1.*;
import gd.seminar2.*;
import gd.seminar3.*;
import gd.seminar4.*;
import gd.seminar5.*;
import gd.seminar6.*;
import gd.seminar7.*;
import gd.seminar8.*;
import gd.seminar9.*;
import gd.seminar10.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Frame extends JFrame {

    JPanel currentPanel = new DefaultPanel();
    JPanel gray = new JPanel();

    Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        setButtons();
        gray.setBackground(Color.GRAY);
        buttonsPanel.setBackground(Color.GRAY);
        this.add(buttonsPanel, BorderLayout.NORTH);
        this.add(gray, BorderLayout.WEST);
        this.add(gray, BorderLayout.EAST);
        this.add(gray, BorderLayout.SOUTH);
        this.add(currentPanel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }

    JPanel buttonsPanel = new JPanel();

    void setButtons() {
        buttonsPanel.setPreferredSize(new Dimension(Dimensions.WIDTH, 50));
        MyButton[] myButtons = {
                new MyButton("S1P1", e -> swapPanels(new S1P1())),
                new MyButton("S1P2", e -> swapPanels(new S1P2())),
                new MyButton("S1P3", e -> swapPanels(new S1P3())),
                new MyButton("S2P1", e -> swapPanels(new S2P1())),
                new MyButton("S2P2", e -> swapPanels(new S2P2())),
                new MyButton("S2P3", e -> swapPanels(new S2P3())),
                new MyButton("S3P1", e -> swapPanels(new S3P1())),
                new MyButton("S3P2", e -> swapPanels(new S3P2())),
                new MyButton("S4P1", e -> swapPanels(new S4P1())),
                new MyButton("S5P1", e -> swapPanels(new S5P1())),
                new MyButton("S6P1", e -> swapPanels(new S6P1())),
                new MyButton("S6P2", e -> swapPanels(new S6P2())),
                new MyButton("S6P3", e -> swapPanels(new S6P3())),
                new MyButton("S7P1", e -> swapPanels(new S7P1())),
                new MyButton("S8P1", e -> swapPanels(new S8P1())),
                new MyButton("S9P1", e -> swapPanels(new S9P1()))
        };

        Arrays.stream(myButtons).forEach(
                button -> button.addActionListener(
                        n -> {
                            Arrays.stream(myButtons).forEach(b -> {
                                if (Color.BLUE.equals(b.getForeground()))
                                    b.setForeground(Color.YELLOW);
                            });
                            button.setForeground(Color.BLUE);
                        }
                )
        );

        for (MyButton m : myButtons)
            buttonsPanel.add(m);
    }

    void swapPanels(JPanel panel) {
        this.remove(currentPanel);
        currentPanel = panel;
        this.add(currentPanel, BorderLayout.CENTER);
        this.pack();
        this.repaint();
    }


}
