package gc.seminar6;

import gc.frame.DefaultPanel;
import gc.frame.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class S6P2 extends DefaultPanel {
    List<Point> polygon;
    Timer timer;
    int count1 = 0;
    int count2 = 0;
    public S6P2() {
        super();

        PolygonDrawer polygonDrawer = new PolygonDrawer(20);
        polygon = polygonDrawer.getPolygon();

        timer = new Timer(10, actionEvent -> {
            if (count1 < polygon.size())
                count1++;
            else if (count2 < polygon.size()) {
                timer.setDelay(50);
                count2++;
            } else
                timer.stop();
            this.repaint();
        });
        timer.start();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setStroke(new BasicStroke(5));

        g.setColor(Color.BLACK);
        for (int i = 0; i < count2; i++) {
            if (i == polygon.size() - 1) {
                Point p1 = polygon.get(i);
                Point p2 = polygon.get(0);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
                continue;
            }
            Point p1 = polygon.get(i);
            Point p2 = polygon.get(i + 1);

            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < count1; i++) {
            Point p = polygon.get(i);
            g.fillOval(p.x - 8, p.y - 8, 16, 16);
        }



    }

    class PolygonDrawer {

        int n;
        Point[] points;
        Random random = new Random();
        java.util.List<Point> hull = new ArrayList<>();

        public PolygonDrawer(int n) {
            this.n = n;
            points = new Point[n];
            for (int i = 0; i < points.length; i++) {
                int x = random.nextInt(40, Dimensions.WIDTH - 40);
                int y = random.nextInt(40, Dimensions.HEIGHT - 40);
                points[i] = new Point(x, y);
            }

            new ConvexHull().convexHull(points, points.length);
        }

        public List<Point> getPolygon() {
            return hull;
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


                int l = 0;
                for (int i = 1; i < n; i++)
                    if (points[i].x < points[l].x)
                        l = i;

                int p = l, q;
                do
                {
                    hull.add(points[p]);

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

}


