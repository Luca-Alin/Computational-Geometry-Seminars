package gd.seminar3;

import gd.frame.DefaultPanel;
import gd.frame.Dimensions;

import java.awt.*;
import java.util.*;
import java.util.List;

public class S3P2 extends DefaultPanel {

    int n = 20;
    ArrayList<Segment> segmentList = new ArrayList<>();
    ArrayList<Point> instersectingPoints = new ArrayList<>();
    Random random = new Random();

    public S3P2() {
        super();

        for (int i = 0; i < n; i++) {
            Point p1 = new Point(random.nextDouble(40, Dimensions.WIDTH - 40),
                    random.nextDouble(40, Dimensions.HEIGHT - 40));
            Point p2 = new Point(random.nextDouble(40, Dimensions.WIDTH - 40),
                    random.nextDouble(40, Dimensions.HEIGHT - 40));

            segmentList.add(new Segment(p1, p2));
        }

        BentleyOttmann bentleyOttmann = new BentleyOttmann(segmentList);
        bentleyOttmann.find_intersections();
        instersectingPoints = bentleyOttmann.get_intersections();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setStroke(new BasicStroke(4));
        g.setColor(Color.BLACK);

        for (int i = 0; i < segmentList.size(); i++) {
            Segment s = segmentList.get(i);
            g.drawLine((int) s.p_1.x_coord, (int) s.p_1.y_coord, (int) s.p_2.x_coord, (int) s.p_2.y_coord);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < instersectingPoints.size(); i++) {
            Point p = instersectingPoints.get(i);
            g.fillOval((int) p.get_x_coord(), (int) p.get_y_coord(), 6, 6);
        }

    }

    public class Segment {

        private Point p_1;
        private Point p_2;
        double value;

        Segment(Point p_1, Point p_2) {
            this.p_1 = p_1;
            this.p_2 = p_2;
            this.calculate_value(this.first().get_x_coord());
        }

        public Point first() {
            if (p_1.get_x_coord() <= p_2.get_x_coord()) {
                return p_1;
            } else {
                return p_2;
            }
        }

        public Point second() {
            if (p_1.get_x_coord() <= p_2.get_x_coord()) {
                return p_2;
            } else {
                return p_1;
            }
        }

        public void calculate_value(double value) {
            double x1 = this.first().get_x_coord();
            double x2 = this.second().get_x_coord();
            double y1 = this.first().get_y_coord();
            double y2 = this.second().get_y_coord();
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

        private double x_coord;
        private double y_coord;

        Point(double x, double y) {
            this.x_coord = x;
            this.y_coord = y;
        }

        public double get_x_coord() {
            return this.x_coord;
        }

        public void set_x_coord(double x_coord) {
            this.x_coord = x_coord;
        }

        public double get_y_coord() {
            return this.y_coord;
        }

        public void set_y_coord(double y_coord) {
            this.y_coord = y_coord;
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
            this.value = p.get_x_coord();
            this.type = type;
        }

        Event(Point p, ArrayList<Segment> s, int type) {
            this.point = p;
            this.segments = s;
            this.value = p.get_x_coord();
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
            double x1 = s_1.first().get_x_coord();
            double y1 = s_1.first().get_y_coord();
            double x2 = s_1.second().get_x_coord();
            double y2 = s_1.second().get_y_coord();
            double x3 = s_2.first().get_x_coord();
            double y3 = s_2.first().get_y_coord();
            double x4 = s_2.second().get_x_coord();
            double y4 = s_2.second().get_y_coord();
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
                System.out.println("(" + p.get_x_coord() + ", " + p.get_y_coord() + ")");
            }
        }

        public ArrayList<Point> get_intersections() {
            X.forEach(n -> System.out.println(n.get_x_coord() + " " + n.get_y_coord()));
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
