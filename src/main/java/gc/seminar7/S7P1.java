package gc.seminar7;

import gc.frame.DefaultPanel;
import gc.seminar6.S6P1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class S7P1 extends DefaultPanel implements MouseListener {

    java.util.List<Point> pointList = new ArrayList<>();
    java.util.List<Segment> segmentList = new ArrayList<>();
    java.util.List<Segment> allSegments = new ArrayList<>();
    java.util.List<Segment> triangulatedSegments = new ArrayList<>();
    JButton button = new JButton("Start");
    int count = 0;
    Timer timer;
    public S7P1() {
        this.addMouseListener(this);

        button.addActionListener(actionEvent -> triangulation());
        this.add(button);
    }

    void triangulation() {
        Point p1 = pointList.get(0);
        Point p2 = pointList.get(pointList.size() - 1);
        segmentList.add(new Segment(p1, p2));

        this.repaint();


        for (int i = 0; i < pointList.size() - 1; i++)
            for (int j = i + 1; j < pointList.size(); j++)
                allSegments.add(
                        new Segment(pointList.get(i), pointList.get(j))
                );

        for (int i = 0; i < allSegments.size(); i++) {
            boolean good = true;
            for (int j = 0; j < triangulatedSegments.size(); j++) {
                if (doIntersect(allSegments.get(i), triangulatedSegments.get(j)))
                    good = false;
            }
            if (good)
                triangulatedSegments.add(allSegments.get(i));
        }

        this.repaint();
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

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setStroke(new BasicStroke(6));
        g.setColor(Color.RED);

        for (int i = 0; i < pointList.size(); i++) {
            Point point = pointList.get(i);
            int x = (int) point.x;
            int y = (int) point.y;
            g.fillOval(x, y, 6, 6);
        }


        for (int i = 0; i < segmentList.size(); i++) {
            Point p1 = segmentList.get(i).p1;
            Point p2 = segmentList.get(i).p2;
            int x1 = (int) p1.x;
            int y1 = (int) p1.y;
            int x2 = (int) p2.x;
            int y2 = (int) p2.y;
            g.drawLine(x1, y1, x2, y2);
        }

//        for (int i = 0; i < triangulatedSegments.size(); i++) {
//            Point p1 = triangulatedSegments.get(i).p1;
//            Point p2 = triangulatedSegments.get(i).p2;
//            int x1 = (int) p1.x;
//            int y1 = (int) p1.y;
//            int x2 = (int) p2.x;
//            int y2 = (int) p2.y;
//            g.drawLine(x1, y1, x2, y2);
//        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        pointList.add(new Point(e.getX(), e.getY()));
        if (pointList.size() >= 2) {
            segmentList.add(new Segment(
                    pointList.get(pointList.size() - 2),
                    pointList.get(pointList.size() - 1)
            ));
        }
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
