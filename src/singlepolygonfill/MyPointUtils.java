package singlepolygonfill;

public class MyPointUtils {

    private static final double EPS = 1e-5;

    public static int sign(double x) {
        if (x > EPS) return 1;
        if (x < -EPS) return -1;
        return 0;
    }

    public static double dot(MyPoint p1, MyPoint p2) { // dot product
        return p1.x * p2.x + p1.y * p2.y;
    }

    public static double cross(MyPoint p1, MyPoint p2) { // cross product
        return p1.x * p2.y - p1.y * p2.x;
    }

    public static MyPoint minus(MyPoint p1, MyPoint p2) { // dot product
        return new MyPoint(p1.x - p2.x, p1.y - p2.y);
    }

    public static double distance(MyPoint p1, MyPoint p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static boolean equals(MyPoint p1, MyPoint p2) {
        return sign(p1.x - p2.x) == 0 && sign(p1.y - p2.y) == 0;
    }

    public static MyPoint higher(MyPoint p1, MyPoint p2) {
        if (p1.y > p2.y) return p1;
        return p2;
    }

    public static MyPoint intersect(MyPoint p1, MyPoint p2, MyPoint p3, MyPoint p4) {
        double s1 = cross(minus(p3, p1), minus(p4, p1));
        double s2 = cross(minus(p3, p2), minus(p4, p2));
        double s3 = cross(minus(p1, p3), minus(p2, p3));
        double s4 = cross(minus(p1, p4), minus(p2, p4));
        if (sign(s1) * sign(s2) < 0 && sign(s3) * sign(s4) < 0)
            return new MyPoint((s1 * p2.x - s2 * p1.x) / (s1 - s2),
                    (s1 * p2.y - s2 * p1.y) / (s1 - s2));
        if (sign(s1) == 0 && sign(dot(minus(p3, p1), minus(p4, p1))) <= 0) return new MyPoint(p1);
        if (sign(s2) == 0 && sign(dot(minus(p3, p2), minus(p4, p2))) <= 0) return new MyPoint(p2);
        if (sign(s3) == 0 && sign(dot(minus(p1, p3), minus(p2, p3))) <= 0) return new MyPoint(p3);
        if (sign(s4) == 0 && sign(dot(minus(p1, p4), minus(p2, p4))) <= 0) return new MyPoint(p4);
        return MyPoint.NAN;
    }
}
