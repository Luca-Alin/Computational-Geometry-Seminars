package gc.seminar6;

import gc.frame.DefaultPanel;
import gc.frame.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class S6P1 extends DefaultPanel {


    int n = 20;
    List<Point> pointList = new ArrayList<>();
    List<Segment> segmentsList = new ArrayList<>();
    List<Segment> goodSegments = new ArrayList<>();
    Random random = new Random();
    Timer timer;
    int count1 = 0;
    int count2 = 0;

    public S6P1() {
        super();

        for (int i = 0; i < n; i++) {
            int x = random.nextInt(40, Dimensions.WIDTH - 40);
            int y = random.nextInt(40, Dimensions.HEIGHT - 40);
            pointList.add(new Point(x, y));
        }

        for (int i = 0; i < pointList.size() - 1; i++) {
            for (int j = i + 1; j < pointList.size(); j++) {
                segmentsList.add(new Segment(
                        pointList.get(i), pointList.get(j)
                ));
            }
        }

        segmentsList.sort((s1, s2) -> {
            if (segmentSize(s1) - segmentSize(s2) < 0)
                return -1;
            else if (segmentSize(s1) - segmentSize(s2) == 0)
                return 0;
            else
                return 1;
        });

        for (int i = 0; i < segmentsList.size(); i++) {
            boolean good = true;
            for (int j = 0; j < goodSegments.size(); j++)
                if (doIntersect(segmentsList.get(i), goodSegments.get(j)))
                    good = false;

            if (good)
                goodSegments.add(segmentsList.get(i));
        }


        timer = new Timer(10, actionEvent -> {
            if (count1 < pointList.size())
                count1++;
            else if (count2 < goodSegments.size()) {
                timer.setDelay(50);
                count2++;
            }
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


        g.setStroke(new BasicStroke(4));

        g.setColor(Color.BLACK);
        for (int i = 0; i < count2; i++) {
            Segment s = goodSegments.get(i);
            g.drawLine((int) s.p1.x, (int) s.p1.y, (int) s.p2.x, (int) s.p2.y);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < count1; i++) {
            Point p = pointList.get(i);
            g.fillOval((int) (p.x - 5), (int) (p.y - 5), 10, 10);
        }
    }

    public double segmentSize(Segment s) {
        return Math.sqrt(
                    Math.pow(s.p1.x - s.p2.x, 2) + Math.pow(s.p1.y - s.p2.y, 2)
        );
    }

    class Point {
        double x;
        double y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
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

    public boolean doIntersect(Segment s, Segment p) {
        Point s1 = s.p1;
        Point s2 = s.p2;
        Point p1 = p.p1;
        Point p2 = p.p2;


        return determinant(p2, p1, s1) * determinant(p2, p1, s2) < 0 && determinant(s2, s1, p1) * determinant(s2, s1, p2) < 0;
    }
    public double determinant(Point p, Point q, Point r) {
        return (p.x * q.y) + (q.x * r.y) + (r.x * p.y) -
                (q.y * r.x) - (p.x * r.y) - (p.y * q.x);
    }
}