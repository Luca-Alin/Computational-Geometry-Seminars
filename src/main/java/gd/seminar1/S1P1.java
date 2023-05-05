package gd.seminar1;

import gd.frame.DefaultPanel;
import gd.frame.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class S1P1 extends DefaultPanel {

    int n;
    Point[] points;
    Segment[] segments;
    Random random = new Random();
    Timer timer;
    int count = 0;
    int count2 = 0;

    public S1P1() {
        super();
        n = 20;
        points = new Point[n];

        int x1 = Integer.MAX_VALUE, x2 = 0, y1 = Integer.MAX_VALUE, y2 = 0;
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(20, Dimensions.WIDTH - 20);
            int y = random.nextInt(20, Dimensions.HEIGHT - 20);
            points[i] = new Point(x, y);

            if (x < x1) x1 = x;
            else if (x > x2) x2 = x;

            if (y < y1) y1 = y;
            else if (y > y2) y2 = y;
        }

        segments = new Segment[4];
        segments[0] = new Segment(new Point(x1, y1), new Point(x2, y1));
        segments[1] = new Segment(new Point(x2, y1), new Point(x2, y2));
        segments[2] = new Segment(new Point(x1, y2), new Point(x2, y2));
        segments[3] = new Segment(new Point(x1, y1), new Point(x1, y2));


        timer = new Timer(50, actionEvent -> {
            if (count < n)
                count++;
            else if (count2 < 4) {
                count2++;
            } else {
                timer.stop();
            }

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
        for (int i = 0; i < count; i++)
            g.drawOval(points[i].x - 4, points[i].y - 4, 6, 6);


        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));
        for (int i = 0; i < count2; i++) {
            Segment s = segments[i];
            g.drawLine(s.p1.x, s.p1.y, s.p2.x, s.p2.y);
        }
    }
}

class Segment {
    Point p1;
    Point p2;

    public Segment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
