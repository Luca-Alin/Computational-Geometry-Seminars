package gd.seminar2;

import gd.frame.DefaultPanel;
import gd.frame.Dimensions;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class S2P3 extends DefaultPanel {

    Random random = new Random();
    int n;
    Point[] points;

    public S2P3() {
        super();

        n = 20;
        points = new Point[n];
        for (int i = 0; i < points.length; i++) {
            int x = random.nextInt(Dimensions.WIDTH / 32 * 7, Dimensions.WIDTH / 32 * 25);
            int y = random.nextInt(Dimensions.HEIGHT / 32 * 7, Dimensions.HEIGHT / 32 * 25);
            points[i] = new Point(x, y);
        }

    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setStroke(new BasicStroke(6));
        g.setColor(Color.RED);
        Arrays.stream(points).forEach(point -> g.drawOval(point.x, point.y, 6, 6));
    }
}
