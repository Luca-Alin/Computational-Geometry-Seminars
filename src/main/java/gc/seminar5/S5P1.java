package gc.seminar5;

import gc.frame.DefaultPanel;
import gc.frame.Dimensions;

import java.util.*;
import java.awt.*;
import java.util.List;

public class S5P1 extends DefaultPanel {

    int n;
    Point[] points;
    Random random = new Random();
    List<List<Point>> convexHullList = new ArrayList<>();
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    javax.swing.Timer timer;
    public S5P1() {
        super();

        n = 40;
        points = new Point[n];
        for (int i = 0; i < points.length; i++) {
            int x = random.nextInt(40, Dimensions.WIDTH - 40);
            int y = random.nextInt(40, Dimensions.HEIGHT - 40);
            points[i] = new Point(x, y);
        }

        new ConvexHull().convexHull(points, points.length);


        timer = new javax.swing.Timer(2, actionEvent -> {
            if (count1 < points.length)
                count1++;
            else if (count2 < convexHullList.size() - 1) {
                timer.setDelay(50);
                count2++;
            }
            else if (count3 == 0)
                count3++;
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

        for (int i = 0; i < count1; i++) {
            g.drawOval(points[i].x, points[i].y, 6, 6);
        }

        g.setColor(Color.RED);
        int j = count2;
        List<Point> awt = convexHullList.get(j);
        for (int i = 0; i < awt.size() - 1; i++) {
            Point p1 = awt.get(i);
            Point p2 = awt.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        if (count3 > 0) {
            awt = convexHullList.get(convexHullList.size() - 1);
            Point p1 = awt.get(0);
            Point p2 = awt.get(awt.size() - 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

    }


    class ConvexHull
    {
        int orientation(Point p, Point q, Point r)
        {
            int val = (q.y - p.y) * (r.x - q.x) -
                    (q.x - p.x) * (r.y - q.y);

            if (val == 0) return 0;

            return (val > 0)? 1: 2;
        }

        void convexHull(Point[] points, int n)
        {
            if (n < 3) return;

            java.util.List<Point> hull = new ArrayList<>();

            int l = 0;
            for (int i = 1; i < n; i++)
                if (points[i].x < points[l].x)
                    l = i;

            int p = l, q;
            do
            {
                hull.add(points[p]);
                convexHullList.add(new ArrayList<>(hull));

                q = (p + 1) % n;

                for (int i = 0; i < n; i++)
                {
                    if (orientation(points[p], points[i], points[q]) == 2)
                        q = i;
                }


                p = q;

            } while (p != l);
        }
    }
}
