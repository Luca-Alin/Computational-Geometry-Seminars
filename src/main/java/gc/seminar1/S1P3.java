package gc.seminar1;

import gc.frame.DefaultPanel;
import gc.frame.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class S1P3 extends DefaultPanel {

    int n;
    int qx;
    int qy;
    int circleRadius;
    Point[] points;
    Random random = new Random();
    Timer timer;
    int count1 = 0;
    int count2 = 0;

    public S1P3() {
        super();

        n = 40;
        qx = Dimensions.WIDTH / 2;
        qy = Dimensions.HEIGHT / 2;
        points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(Dimensions.WIDTH / 8 * 2, Dimensions.WIDTH / 8 * 6);
            int y = random.nextInt(Dimensions.HEIGHT / 8 * 2, Dimensions.HEIGHT / 8 * 6);
            points[i] = new Point(x, y);
        }

        circleRadius = 0;
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            if (calculateDistance(point, new Point(qx, qy)) > circleRadius)
                circleRadius = calculateDistance(point, new Point(qx, qy));
        }

        timer = new Timer(50, actionEvent -> {
            if (count1 < n)
                count1++;
            else if (count2 == 0)
                count2 = 1;
            else
                timer.stop();
            this.repaint();
        });
        timer.start();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));
        for (int i = 0; i < count1; i++) {
            g.drawOval(points[i].x - 4, points[i].y - 4, 6, 6);
        }

        g.setColor(Color.RED);
        if (count2 == 1)
            g.drawOval(qx - circleRadius, qy - circleRadius, circleRadius * 2, circleRadius * 2);
    }

    public int calculateDistance(Point p1, Point p2) {
        return (int) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }
}
