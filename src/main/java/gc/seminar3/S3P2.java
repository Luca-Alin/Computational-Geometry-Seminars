package gc.seminar3;

import gc.frame.DefaultPanel;
import gc.frame.Dimensions;

import javax.swing.Timer;
import java.awt.*;
import java.util.Queue;
import java.util.*;

public class S3P2 extends DefaultPanel {
    int n = 15;

    ArrayList<Segment> segmentList = new ArrayList<>();
    ArrayList<Point> instersectingPoints;
    Random random = new Random();

    int count = 0;
    Timer timer;
    public S3P2() {
        super();

        for (int i = 0; i < n; i++) {
            Point p1 = new Point(random.nextInt(0, Dimensions.WIDTH),
                    random.nextInt(0, Dimensions.HEIGHT));
            Point p2 = new Point(random.nextInt(0, Dimensions.WIDTH),
                    random.nextInt(0, Dimensions.HEIGHT));

            segmentList.add(new Segment(p1, p2));
        }

        BentleyOttmann bentleyOttmann = new BentleyOttmann(segmentList);
        bentleyOttmann.find_intersections();
        instersectingPoints = bentleyOttmann.get_intersections();





        instersectingPoints.sort((p1, p2) -> {
            if (p1.x > p2.x)
                return 1;
            else if (p1.x < p2.x)
                return -1;
            return 0;
        });


        timer = new Timer(15, actionEvent -> {
            if (count < Dimensions.WIDTH) {
                count+=12;
            } else {
                timer.stop();
            }
            this.removeAll();
            this.repaint();
        });
        timer.start();
    }
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setStroke(new BasicStroke(4));

        for (int i = 0; i < segmentList.size(); i++) {
            if (i % 2 == 0)
                g.setColor(new Color(50, 50, 150));
            else if (i % 3 == 0)
                g.setColor(new Color(100, 100, 200));
            else
                g.setColor(new Color(150, 150, 250));
            Segment s = segmentList.get(i);
            g.drawLine((int) s.p1.x, (int) s.p1.y, (int) s.p2.x, (int) s.p2.y);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < instersectingPoints.size(); i++) {
            Point p = instersectingPoints.get(i);
            if (p.x < count) {
                g.fillOval((int) p.x - 4, (int) p.y - 4, 10, 10);
            }
        }

        g.setStroke(new BasicStroke(8));
        g.setColor(new Color(0f,0f,0f,.5f ));
        g.drawLine(count, 0, count, Dimensions.HEIGHT);

    }


    public class Segment {

        private Point p1;
        private Point p2;
        double value;

        Segment(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
            this.calculate_value(this.first().x);
        }

        public Point first() {
            if (p1.x <= p2.x) {
                return p1;
            } else {
                return p2;
            }
        }

        public Point second() {
            if (p1.x <= p2.x) {
                return p2;
            } else {
                return p1;
            }
        }

        public void calculate_value(double value) {
            double x1 = this.first().x;
            double x2 = this.second().x;
            double y1 = this.first().y;
            double y2 = this.second().y;
            this.value = y1 + (((y2 - y1) / (x2 - x1)) * (value - x1));
        }

        public void set_value(double value) {
            this.value = value;
        }

        public double get_value() {
            return this.value;
        }

    }

    public class Point {

        public double x;
        public double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public class Event {

        private Point point;
        private ArrayList<Segment> segments;
        private double value;
        private int type;

        Event(Point p, Segment s, int type) {
            this.point = p;
            this.segments = new ArrayList<>(Arrays.asList(s));
            this.value = p.x;
            this.type = type;
        }

        Event(Point p, ArrayList<Segment> s, int type) {
            this.point = p;
            this.segments = s;
            this.value = p.x;
            this.type = type;
        }

        public void add_point(Point p) {
            this.point = p;
        }

        public Point get_point() {
            return this.point;
        }

        public void add_segment(Segment s) {
            this.segments.add(s);
        }

        public ArrayList<Segment> get_segments() {
            return this.segments;
        }

        public void set_type(int type) {
            this.type = type;
        }

        public int get_type() {
            return this.type;
        }

        public void set_value(double value) {
            this.value = value;
        }

        public double get_value() {
            return this.value;
        }

    }

    public class BentleyOttmann {

        private Queue<Event> Q;
        private NavigableSet<Segment> T;
        private ArrayList<Point> X;

        BentleyOttmann(ArrayList<Segment> input_data) {
            this.Q = new PriorityQueue<>(new event_comparator());
            this.T = new TreeSet<>(new segment_comparator());
            this.X = new ArrayList<>();
            for (Segment s : input_data) {
                this.Q.add(new Event(s.first(), s, 0));
                this.Q.add(new Event(s.second(), s, 1));
            }
        }

        public void find_intersections() {
            while (!this.Q.isEmpty()) {
                Event e = this.Q.poll();
                double L = e.get_value();
                switch (e.get_type()) {
                    case 0:
                        for (Segment s : e.get_segments()) {
                            this.recalculate(L);
                            this.T.add(s);
                            if (this.T.lower(s) != null) {
                                Segment r = this.T.lower(s);
                                this.report_intersection(r, s, L);
                            }
                            if (this.T.higher(s) != null) {
                                Segment t = this.T.higher(s);
                                this.report_intersection(t, s, L);
                            }
                            if (this.T.lower(s) != null && this.T.higher(s) != null) {
                                Segment r = this.T.lower(s);
                                Segment t = this.T.higher(s);
                                this.remove_future(r, t);
                            }
                        }
                        break;
                    case 1:
                        for (Segment s : e.get_segments()) {
                            if (this.T.lower(s) != null && this.T.higher(s) != null) {
                                Segment r = this.T.lower(s);
                                Segment t = this.T.higher(s);
                                this.report_intersection(r, t, L);
                            }
                            this.T.remove(s);
                        }
                        break;
                    case 2:
                        Segment s_1 = e.get_segments().get(0);
                        Segment s_2 = e.get_segments().get(1);
                        this.swap(s_1, s_2);
                        if (s_1.get_value() < s_2.get_value()) {
                            if (this.T.higher(s_1) != null) {
                                Segment t = this.T.higher(s_1);
                                this.report_intersection(t, s_1, L);
                                this.remove_future(t, s_2);
                            }
                            if (this.T.lower(s_2) != null) {
                                Segment r = this.T.lower(s_2);
                                this.report_intersection(r, s_2, L);
                                this.remove_future(r, s_1);
                            }
                        } else {
                            if (this.T.higher(s_2) != null) {
                                Segment t = this.T.higher(s_2);
                                this.report_intersection(t, s_2, L);
                                this.remove_future(t, s_1);
                            }
                            if (this.T.lower(s_1) != null) {
                                Segment r = this.T.lower(s_1);
                                this.report_intersection(r, s_1, L);
                                this.remove_future(r, s_2);
                            }
                        }
                        this.X.add(e.get_point());
                        break;
                }
            }
        }

        private boolean report_intersection(Segment s_1, Segment s_2, double L) {
            double x1 = s_1.first().x;
            double y1 = s_1.first().y;
            double x2 = s_1.second().x;
            double y2 = s_1.second().y;
            double x3 = s_2.first().x;
            double y3 = s_2.first().y;
            double x4 = s_2.second().x;
            double y4 = s_2.second().y;
            double r = (x2 - x1) * (y4 - y3) - (y2 - y1) * (x4 - x3);
            if (r != 0) {
                double t = ((x3 - x1) * (y4 - y3) - (y3 - y1) * (x4 - x3)) / r;
                double u = ((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) / r;
                if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                    double x_c = x1 + t * (x2 - x1);
                    double y_c = y1 + t * (y2 - y1);
                    if (x_c > L) {
                        this.Q.add(new Event(new Point(x_c, y_c), new ArrayList<>(Arrays.asList(s_1, s_2)), 2));
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean remove_future(Segment s_1, Segment s_2) {
            for (Event e : this.Q) {
                if (e.get_type() == 2) {
                    if ((e.get_segments().get(0) == s_1 && e.get_segments().get(1) == s_2) || (e.get_segments().get(0) == s_2 && e.get_segments().get(1) == s_1)) {
                        this.Q.remove(e);
                        return true;
                    }
                }
            }
            return false;
        }

        private void swap(Segment s_1, Segment s_2) {
            this.T.remove(s_1);
            this.T.remove(s_2);
            double value = s_1.get_value();
            s_1.set_value(s_2.get_value());
            s_2.set_value(value);
            this.T.add(s_1);
            this.T.add(s_2);
        }

        private void recalculate(double L) {
            Iterator<Segment> iter = this.T.iterator();
            while (iter.hasNext()) {
                iter.next().calculate_value(L);
            }
        }

        public void print_intersections() {
            for (Point p : this.X) {
                System.out.println("(" + p.x + ", " + p.y + ")");
            }
        }

        public ArrayList<Point> get_intersections() {
            //X.forEach(n -> System.out.println(n.x + " " + n.y));
            return this.X;
        }

        private class event_comparator implements Comparator<Event> {
            @Override
            public int compare(Event e_1, Event e_2) {
                if (e_1.get_value() > e_2.get_value()) {
                    return 1;
                }
                if (e_1.get_value() < e_2.get_value()) {
                    return -1;
                }
                return 0;
            }
        }

        private class segment_comparator implements Comparator<Segment> {
            @Override
            public int compare(Segment s_1, Segment s_2) {
                if (s_1.get_value() < s_2.get_value()) {
                    return 1;
                }
                if (s_1.get_value() > s_2.get_value()) {
                    return -1;
                }
                return 0;
            }
        }
    }
}
