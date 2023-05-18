package gc.frame;

import javax.swing.*;
import java.awt.*;

public class DefaultPanel extends JPanel {

    public DefaultPanel() {
        this.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        this.setPreferredSize(Dimensions.PANEL_DEFAULT_DIMENSION);
        this.setMaximumSize(Dimensions.PANEL_DEFAULT_DIMENSION);
    }

    private final int UNIT = Dimensions.HEIGHT / 25;

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(1));
        for (int i = 0; i < Dimensions.HEIGHT; i += UNIT)
            g.drawLine(0, i, Dimensions.WIDTH, i);

        for (int i = 0; i < Dimensions.WIDTH; i += UNIT)
            g.drawLine(i, 0, i, Dimensions.HEIGHT);

    }
}

