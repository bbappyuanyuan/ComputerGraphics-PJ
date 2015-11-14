package singlepolygonfill;

public class MyPoint {

    public static final MyPoint NAN = new MyPoint(0, 0);

    public double x;
    public double y;

    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public MyPoint(MyPoint p) {
        this.x = p.x;
        this.y = p.y;
    }
}
