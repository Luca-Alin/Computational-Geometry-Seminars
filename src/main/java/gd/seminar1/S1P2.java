package gd.seminar1;

import gd.frame.DefaultPanel;
import gd.frame.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class S1P2 extends DefaultPanel {

    int n;
    Point[] points;
    boolean[] pointWasVisited;

    Timer timer;
    int count1 = 0;
    int count2 = 0;
    List<ConnectedPoints> linkedPoints = new ArrayList<>();
    Random random = new Random();

    public S1P2() {
        super();
        n = 20;
        points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(20, Dimensions.WIDTH - 20);
            int y = random.nextInt(20, Dimensions.HEIGHT - 20);
            points[i] = new Point(x, y);
        }

        pointWasVisited = new boolean[n];
        Arrays.fill(pointWasVisited, false);

        for (int i = 0; i < n; i++) {
            if (pointWasVisited[i]) continue;
            int distance = Integer.MAX_VALUE;
            int aux = 0;
            for (int j = i + 1; j < n; j++) {
                if (pointWasVisited[j]) continue;
                if (calculateDistance(points[i], points[j]) < distance) {
                    distance = calculateDistance(points[i], points[j]);
                    aux = j;
                }
            }

            pointWasVisited[i] = pointWasVisited[aux] = true;
            linkedPoints.add(new ConnectedPoints(points[i], points[aux]));
        }

        timer = new Timer(50, actionevent -> {
            if (count1 < n) count1++;
            else if (count2 < n / 2) count2++;
            else timer.stop();
            this.repaint();
        });
        timer.start();
    }


    public int calculateDistance(Point p1, Point p2) {
        return (int) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));
        for (int i = 0; i < count1; i++)
            g.drawOval(points[i].x - 4, points[i].y - 4, 6, 6);

        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));
        for (int i = 0; i < count2; i++) {
            ConnectedPoints c = linkedPoints.get(i);
            g.drawLine(c.p1.x, c.p1.y, c.p2.x, c.p2.y);
        }
    }
}


class ConnectedPoints {
    Point p1;
    Point p2;

    public ConnectedPoints(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
