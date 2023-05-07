package gd.seminar4;

import gd.frame.DefaultPanel;
import gd.frame.Dimensions;

import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class S4P1 extends DefaultPanel {

    int n;
    List<Point> points;
    Random random = new Random();
    javax.swing.Timer timer;
    int count1 = 0, count2 = 0, count3 = 0;
    List<List<Point>> upperHull = new ArrayList<>();
    List<List<Point>> aux = new ArrayList<>();
    List<List<Point>> lowerHull = new ArrayList<>();

    public S4P1() {
        super();

        n = 10;
        points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(40, Dimensions.WIDTH - 40);
            int y = random.nextInt(40, Dimensions.HEIGHT - 40);
            points.add(new Point(x, y));
        }

        ConvexHull convexHull = new ConvexHull();
        List<Point> ans = convexHull.convexHull(points);


        timer = new Timer(200, actionEvent -> {
            if (count1 < points.size())
                count1++;
            else if (count2 < upperHull.size() - 1)
                count2++;
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
            g.drawOval(points.get(i).x, points.get(i).y, 6, 6);
        }

        for (int i = 0; i <= count2; i++) {
            List<Point> awt = upperHull.get(i);
            for (int j = 0; j < awt.size() - 1; j++) {
                Point p1 = awt.get(j);
                Point p2 = awt.get(j + 1);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }






    }




























    class ConvexHull {


        // Cross product of two vectors OA and OB
        // returns positive for counter clockwise
        // turn and negative for clockwise turn
        static long crossProduct(Point O, Point A, Point B) {
            return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
        }

        // Returns a list of points on the convex hull
        // in counter-clockwise order
        List<Point> convexHull(List<Point> points) {
            int n = points.size(), k = 0, fresco = 0;

            if (n <= 3)
                return points;

            List<Point> hullPoints = new ArrayList<>(2 * n);

            // Sort points lexicographically
            Collections.sort(points, new Comparator<Point>() {
                @Override
                public int compare(Point p1, Point p2) {
                    if (p1.x == p2.x)
                        return p1.y - p2.y;
                    return p1.x - p2.x;
                }
            });

            // Build lower hull
            for (int i = 0; i < n; ++i) {
                // If the point at K-1 position is not a part
                // of hull as vector from ans[k-2] to ans[k-1]
                // and ans[k-2] to A[i] has a clockwise turn
                while (k >= 2
                        && crossProduct(hullPoints.get(k - 2),
                        hullPoints.get(k - 1), points.get(i))
                        <= 0) {
                    hullPoints.remove(--k);
                }
                hullPoints.add(points.get(i));
                List<Point> list = new ArrayList<>(hullPoints);
                lowerHull.add(list);
                k++;
            }

            // Build upper hull
            for (int i = n - 2, t = k; i >= 0; --i) {

                // If the point at K-1 position is not a part
                // of hull as vector from ans[k-2] to ans[k-1]
                // and ans[k-2] to A[i] has a clockwise turn
                while (k > t && crossProduct(hullPoints.get(k - 2), hullPoints.get(k - 1), points.get(i)) <= 0) {
                    hullPoints.remove(--k);
                }
                hullPoints.add(points.get(i));
                List<Point> list = new ArrayList<>(hullPoints);
                upperHull.add(list);
                k++;
            }

            for (int i = 0; i < upperHull.size(); i++) {
                List<Point> list = upperHull.get(i);
                int kappa = lowerHull.get(lowerHull.size() - 1).size();
                int fr = upperHull.get(i).size();
//                System.out.println(list.subList(kappa, fr));
                aux.add(new ArrayList<>(upperHull.get(i).subList(kappa, fr)));
//                System.out.println(aux.size());
            }
//            System.out.println("--------------------------------------------------------------------------------------------");
            for (int i = 0; i < lowerHull.size(); i++) {
                List<Point> list = lowerHull.get(i);
//                System.out.println(list);
            }
            // Resize the array to desired size

            return hullPoints;
        }


    }
}
