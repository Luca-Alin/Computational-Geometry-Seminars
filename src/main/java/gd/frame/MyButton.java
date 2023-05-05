package gd.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MyButton extends JButton {

    MyButton(String text, ActionListener a) {
        this.setFocusable(false);
        this.setFont(new Font("Verdana", Font.BOLD, 16));
        this.setText(text);
        this.addActionListener(a);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.LIGHT_GRAY);
    }
}
