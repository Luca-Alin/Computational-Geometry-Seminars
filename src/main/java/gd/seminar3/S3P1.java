package gd.seminar3;

import gd.frame.DefaultPanel;
import gd.frame.Dimensions;

import java.util.*;
import java.awt.*;
import java.util.List;

public class S3P1 extends DefaultPanel {


    int n = 10;

    List<Segment> list = new ArrayList<>();
    List<Segment> minSumSegments;
    int minSum = Integer.MAX_VALUE;
    Random random = new Random();

    public S3P1() {
        super();

        for (int i = 0; i < n; i++) {
            int x1 = random.nextInt(40, Dimensions.WIDTH - 40);
            int y1 = random.nextInt(40, Dimensions.HEIGHT - 40);
            int x2 = random.nextInt(40, Dimensions.WIDTH - 40);
            int y2 = random.nextInt(40, Dimensions.HEIGHT - 40);

            list.add(new Segment(new Point(x1, y1), new Point(x2, y2)));
        }

        heapPermutation(list, list.size(), list.size());
    }


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setStroke(new BasicStroke(4));
        g.setColor(Color.BLUE);
        for (Segment s: minSumSegments) {
            g.drawLine(s.p1.x, s.p1.y, s.p2.x, s.p2.y);
        }
    }

    class Segment {
        Point p1;
        Point p2;

        public Segment(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public String toString() {
            return "Segment{" + "p1=" + p1 + ", p2=" + p2 + '}';
        }
    }

    void heapPermutation(List<Segment> a, int size, int n) {
        if (size == 1) {
            int kappa = segmentsSum(a);
            if (kappa < minSum)
                minSumSegments = new ArrayList<>(a);
        }

        for (int i = 0; i < size; i++) {
            heapPermutation(a, size - 1, n);

            if (size % 2 == 1) {
                Segment temp = a.get(0);
                a.set(0, a.get(size - 1));
                a.set(size - 1, temp);
            } else {
                Segment temp = a.get(i);
                a.set(i, a.get(size - 1));
                a.set(size - 1, temp);
            }
        }
    }

    int distance(Point p1, Point p2) {
        return (int) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    int segmentsSum(List<Segment> segmentList) {
        int sum = 0;
        for (int i = 0; i < segmentList.size(); i++) {
            Segment sg = segmentList.get(i);
            sum += distance(sg.p1, sg.p2);
        }
        return sum;
    }
}
