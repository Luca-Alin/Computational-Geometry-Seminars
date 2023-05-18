package gc.seminar2;

import gc.frame.DefaultPanel;
import gc.frame.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class S2P1 extends DefaultPanel {

    Random random = new Random();
    int n;
    int qx;
    int qy;
    int d;
    Point[] points;
    java.util.List<Point> closePoints = new ArrayList<>();
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    Timer timer;

    public S2P1() {
        super();

        n = 20;
        qx = Dimensions.WIDTH / 2;
        qy = Dimensions.HEIGHT / 2;
        d = 300;

        points = new Point[n];
        for (int i = 0; i < points.length; i++) {
            int x = random.nextInt(40, Dimensions.WIDTH - 40);
            int y = random.nextInt(40, Dimensions.HEIGHT - 40);
            points[i] = new Point(x, y);
            if (calculateDistance(points[i], new Point(qx, qy)) <= d)
                closePoints.add(points[i]);
        }

        timer = new Timer(50, actionEvent -> {
            if (count1 < points.length)
                count1++;
            else
                if (count2 < closePoints.size())
                    count2++;
                else if (count3 == 0)
                    count3++;
                else
                    timer.stop();
            repaint();
        });
        timer.start();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));
        for (int i = 0; i < count1; i++)
            g.drawOval(points[i].x - 6, points[i].y - 6, 6, 6);

        g.setColor(Color.BLUE);
        for (int i = 0; i < count2; i++)
            g.drawOval(closePoints.get(i).x - 6, closePoints.get(i).y - 6, 6, 6);

        g.setColor(Color.RED);

        if (count3 == 1)
            g.drawOval(qx - d, qy - d, d * 2, d * 2);
    }

    public int calculateDistance(Point p1, Point p2) {
        return (int) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }
}
