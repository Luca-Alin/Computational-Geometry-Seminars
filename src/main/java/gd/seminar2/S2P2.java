package gd.seminar2;

import gd.frame.DefaultPanel;
import gd.frame.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class S2P2 extends DefaultPanel {

    Random random = new Random();
    int n;
    Point[] points;
    Point t1;
    Point t2;
    Point t3;
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    Timer timer;

    public S2P2() {
        super();

        int n = 5;
        points = new Point[n];
        for (int i = 0; i < points.length; i++) {
            int x = random.nextInt(40, Dimensions.WIDTH - 40);
            int y = random.nextInt(40, Dimensions.HEIGHT - 40);
            points[i] = new Point(x, y);
        }


        int minArea = Integer.MAX_VALUE;

        for (int i = 0; i < points.length - 2; i++)
            for (int j = i + 1; j < points.length - 1; j++)
                for (int k = j + 1; k < points.length; k++)
                    if (triangleArea(points[i], points[j], points[k]) < minArea) {
                        minArea = triangleArea(points[i], points[j], points[k]);
                        t1 = points[i];
                        t2 = points[j];
                        t3 = points[k];
                    }
        System.out.println(minArea);
        System.out.println(t1.x + " " + t1.y);
        System.out.println(t2.x + " " + t2.y);
        System.out.println(t3.x + " " + t3.y);

        timer = new Timer(100, actionEvent -> {
            if (count1 < points.length)
                count1++;
            else if (count2 < 3)
                count2++;
            else if (count3 < 3)
                count3++;
            else
                timer.stop();
            repaint();
        });
        timer.start();

    }

    public int triangleArea(Point p1, Point p2, Point p3) {
        int x1 = p1.x, x2 = p2.x, x3 = p3.x;
        int y1 = p1.y, y2 = p2.y, y3 = p3.y;

        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));
        for (int i = 0; i < count1; i++)
            g.drawOval(points[i].x - 6, points[i].y - 6, 6, 6);

        g.setColor(Color.RED);
        if (count2 >= 1)
            g.drawOval(t1.x - 6, t1.y - 6, 6, 6);
        if (count2 >= 2)
            g.drawOval(t2.x - 6, t2.y - 6, 6, 6);
        if (count2 >= 3)
            g.drawOval(t3.x - 6, t3.y - 6, 6, 6);

        g.setColor(Color.BLUE);
        if (count3 >= 1)
            g.drawLine(t1.x - 2, t1.y - 2, t2.x - 2, t2.y - 2);
        if (count3 >= 2)
            g.drawLine(t2.x - 2, t2.y - 2, t3.x - 2, t3.y - 2);
        if (count3 >= 3)
            g.drawLine(t3.x - 2, t3.y - 2, t1.x - 2, t1.y - 2);

    }
}
