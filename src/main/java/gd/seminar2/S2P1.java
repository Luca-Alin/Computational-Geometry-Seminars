package gd.seminar2;

import gd.frame.DefaultPanel;
import gd.frame.Dimensions;

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
    public S2P1() {
        super();

        int n = 20;
        int qx = Dimensions.WIDTH / 2;
        int qy = Dimensions.HEIGHT / 2;
        int d = 300;

        points = new Point[n];
        for (int i = 0; i < points.length; i++) {
            int x = random.nextInt(40, Dimensions.WIDTH - 40);
            int y = random.nextInt(40, Dimensions.HEIGHT - 40);
            points[i] = new Point(x, y);
            if (calculateDistance(points[i], new Point(qx, qy)) <= d)
                closePoints.add(points[i]);
        }


    }
    public int calculateDistance(Point p1, Point p2) {
        return (int) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);


    }
}
