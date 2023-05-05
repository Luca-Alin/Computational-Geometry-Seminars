package gd.seminar2;

import org.junit.jupiter.api.Assertions;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class S2P2Test {

    @org.junit.jupiter.api.Test
    void triangleArea() {
        S2P2 s2P2 = new S2P2();

        Point p1 = new Point(2, 4);
        Point p2 = new Point(3, -6);
        Point p3 = new Point(7, 8);

        Assertions.assertTrue(s2P2.triangleArea(p1, p2, p3) == 27);
    }
}