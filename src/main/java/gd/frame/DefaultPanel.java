package gd.frame;

import gd.frame.Dimensions;

import javax.swing.*;
import java.awt.*;

public class DefaultPanel extends JPanel {

    public DefaultPanel() {
        this.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        this.setPreferredSize(Dimensions.PANEL_DEFAULT_DIMENSION);
        this.setMaximumSize(Dimensions.PANEL_DEFAULT_DIMENSION);
    }

    int unit = Dimensions.HEIGHT / 25;
    @Override
    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(1));
        for (int i = 0; i < Dimensions.HEIGHT; i += unit)
            g.drawLine(0, i, Dimensions.WIDTH, i);

        for (int i = 0; i < Dimensions.WIDTH; i += unit)
            g.drawLine(i, 0, i, Dimensions.HEIGHT);

    }
}

