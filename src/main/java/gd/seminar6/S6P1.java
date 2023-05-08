package gd.seminar6;

import gd.frame.DefaultPanel;
import gd.frame.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class Segment {
    Point p1;
    Point p2;

    public Segment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean intersects(Segment other) {
        int dx1 = this.p2.x - this.p1.x;
        int dy1 = this.p2.y - this.p1.y;
        int dx2 = other.p2.x - other.p1.x;
        int dy2 = other.p2.y - other.p1.y;

        int det1 = dx1 * (other.p1.y - this.p1.y) - dy1 * (other.p1.x - this.p1.x);
        int det2 = dx1 * (other.p2.y - this.p1.y) - dy1 * (other.p2.x - this.p1.x);
        int det3 = dx2 * (this.p1.y - other.p1.y) - dy2 * (this.p1.x - other.p1.x);
        int det4 = dx2 * (this.p2.y - other.p1.y) - dy2 * (this.p2.x - other.p1.x);

        if ((det1 < 0 && det2 > 0) || (det1 > 0 && det2 < 0) ||
                (det3 < 0 && det4 > 0) || (det3 > 0 && det4 < 0)) {
            return true;
        }

        if (det1 == 0 && isBetween(other.p1.x, this.p1.x, this.p2.x) && isBetween(other.p1.y, this.p1.y, this.p2.y)) {
            return true;
        }
        if (det2 == 0 && isBetween(other.p2.x, this.p1.x, this.p2.x) && isBetween(other.p2.y, this.p1.y, this.p2.y)) {
            return true;
        }
        if (det3 == 0 && isBetween(this.p1.x, other.p1.x, other.p2.x) && isBetween(this.p1.y, other.p1.y, other.p2.y)) {
            return true;
        }
        if (det4 == 0 && isBetween(this.p2.x, other.p1.x, other.p2.x) && isBetween(this.p2.y, other.p1.y, other.p2.y)) {
            return true;
        }

        return false;
    }

    private boolean isBetween(int value, int start, int end) {
        return value >= Math.min(start, end) && value <= Math.max(start, end);
    }

}

public class S6P1 extends DefaultPanel {

    int n = 20;
    List<Segment> segmentList = new ArrayList<>();
    List<Segment> ss = new ArrayList<>();
    List<Point> pointList = new ArrayList<>();
    Random random = new Random();

    public S6P1() {
        super();

        for (int i = 0; i < n; i++) {
            pointList.add(new Point(random.nextInt(40, Dimensions.WIDTH - 40),
                    random.nextInt(40, Dimensions.HEIGHT - 40)));
        }

        for (int i = 0; i < pointList.size(); i++) {
            for (int j = 0; j < pointList.size(); j++) {
                if (i == j)
                    continue;
                segmentList.add(new Segment(pointList.get(i), pointList.get(j)));
            }
        }

        segmentList.sort((s1, s2) ->
                segmentSize(s1) - segmentSize(s2)
        );


        ss.add(segmentList.get(0));
        for (int i = 0; i < segmentList.size(); i++) {
            Segment s = segmentList.get(i);
            boolean good = false;
            for (int j = 0; j < ss.size(); j++)
                if (s.intersects(ss.get(j)))
                    good = true;
                else if (s.p1.equals(ss.get(j).p1) || s.p2.equals(ss.get(j).p2))
                    good = true;

            if (!good)
                ss.add(s);
        }


    }

    public int segmentSize(Segment s) {
        Point p1 = s.p1;
        Point p2 = s.p2;
        return (int) Math.sqrt(
                Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2)
        );
    }


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setStroke(new BasicStroke(4));
        g.setColor(Color.RED);

        for (int i = 0; i < pointList.size(); i++)
            g.fillOval(pointList.get(i).x - 5, pointList.get(i).y - 5, 10, 10);

        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        for (int i = 0; i < ss.size(); i++) {
            Segment s = ss.get(i);
            g.drawLine(s.p1.x, s.p1.y, s.p2.x, s.p2.y);
        }
    }
}
