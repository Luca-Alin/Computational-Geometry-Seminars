package gc.seminar3;

import gc.frame.DefaultPanel;
import gc.frame.Dimensions;

import java.util.*;
import java.awt.*;
import java.util.List;

public class S3P1 extends DefaultPanel {


    int n = 8;

    List<Point> list = new ArrayList<>();
    List<Point> minSumSegments;
    int minSum = Integer.MAX_VALUE;
    Random random = new Random();

    public S3P1() {
        super();

        for (int i = 0; i < n; i++) {
            int x = random.nextInt(40, Dimensions.WIDTH - 40);
            int y = random.nextInt(40, Dimensions.HEIGHT - 40);

            list.add(new Point(x, y));
        }

        heapPermutation(list, list.size());

    }


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setStroke(new BasicStroke(4));


        for (int i = 0; i < minSumSegments.size(); i += 2) {
            if (i % 4 == 0)
                g.setColor(new Color(50, 50, 150));
            else
                g.setColor(new Color(150, 150, 250));
            Point p1 = minSumSegments.get(i);
            Point p2 = minSumSegments.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < minSumSegments.size(); i++) {
            Point p = minSumSegments.get(i);
            g.fillOval(p.x - 6, p.y - 6, 12, 12);
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

    void heapPermutation(List<Point> a, int size) {
        if (size == 1) {
            int kappa = segmentsSum(a);
            if (kappa < minSum)
            {
                minSumSegments = new ArrayList<>(a);
            }
        }

        for (int i = 0; i < size; i++) {
            heapPermutation(a, size - 1);

            if (size % 2 == 1) {
                Point temp = a.get(0);
                a.set(0, a.get(size - 1));
                a.set(size - 1, temp);
            } else {
                Point temp = a.get(i);
                a.set(i, a.get(size - 1));
                a.set(size - 1, temp);
            }
        }
    }

    int distance(Point p1, Point p2) {
        return (int) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    int segmentsSum(List<Point> segmentList) {
        int sum = 0;
        for (int i = 0; i < segmentList.size(); i +=2 ) {
            Point p1 = segmentList.get(i);
            Point p2 = segmentList.get(i + 1);
            sum += distance(p1, p2);
        }
        return sum;
    }
}
