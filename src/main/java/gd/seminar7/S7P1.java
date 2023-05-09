package gd.seminar7;

import gd.frame.DefaultPanel;
import gd.frame.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class S7P1 extends DefaultPanel {

    int count = 0;
    Timer timer;
    public S7P1() {

    }


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        draw(g);

    }

    void draw(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        g.fillOval(300, 300, 300, 300);
    }
}
