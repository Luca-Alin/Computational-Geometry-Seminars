package gc.seminar4;

import gc.frame.DefaultPanel;
import gc.frame.Dimensions;

import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class S4P1 extends DefaultPanel {

    int n;
    List<Point> points;
    Random random = new Random();
    int lowerHull;
    javax.swing.Timer timer;
    int count1 = 0, count2 = 0;
    List<List<Point>> myHull = new ArrayList<>();

    public S4P1() {
        super();

        n = 40;
        points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(40, Dimensions.WIDTH - 40);
            int y = random.nextInt(40, Dimensions.HEIGHT - 40);
            points.add(new Point(x, y));
        }

        ConvexHull convexHull = new ConvexHull();
        convexHull.convexHull(points);



        timer = new Timer(2, actionEvent -> {
            if (count1 < points.size()) count1++;
            else if (count2 < myHull.size() - 1) {
                timer.setDelay(25);
                count2++;
            }
            else timer.stop();
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
            g.drawOval(points.get(i).x, points.get(i).y, 6, 6);
        }

        g.setColor(Color.RED);
        int i = count2;
        List<Point> awt = myHull.get(i);
        for (int j = 0; j < awt.size() - 1; j++) {
            if (j >= lowerHull - 1)
                g.setColor(Color.BLUE);
            Point p1 = awt.get(j);
            Point p2 = awt.get(j + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }



    }


    class ConvexHull {

        static long crossProduct(Point O, Point A, Point B) {
            return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
        }


        List<Point> convexHull(List<Point> points) {
            int n = points.size(), k = 0;

            if (n <= 3) return points;

            List<Point> hullPoints = new ArrayList<>(2 * n);

            points.sort((p1, p2) -> {
                if (p1.x == p2.x) return p1.y - p2.y;
                return p1.x - p2.x;
            });

            for (int i = 0; i < n; ++i) {

                while (k >= 2 && crossProduct(hullPoints.get(k - 2), hullPoints.get(k - 1), points.get(i)) <= 0) {
                    hullPoints.remove(--k);
                }
                hullPoints.add(points.get(i));
                myHull.add(new ArrayList<>(hullPoints));
                k++;
            }

            lowerHull = hullPoints.size();

            for (int i = n - 2, t = k; i >= 0; --i) {

                while (k > t && crossProduct(hullPoints.get(k - 2), hullPoints.get(k - 1), points.get(i)) <= 0) {
                    hullPoints.remove(--k);
                }
                hullPoints.add(points.get(i));
                myHull.add(new ArrayList<>(hullPoints));
                k++;
            }
            return hullPoints;
        }
    }
}
