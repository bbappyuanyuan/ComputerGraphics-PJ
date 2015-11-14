package singlepolygonfill;

import java.util.Comparator;
import java.util.Vector;

public class MyPolygonUtils {

    public static Vector<MyHorizontalLine> getInterior(Vector<MyPoint> polygon, int width, int height) {
        Vector<MyHorizontalLine> ret = new Vector<MyHorizontalLine>();
        int n = polygon.size();
        if (n < 3) return ret;
        System.out.println("========= polygon =========");
        for (MyPoint p : polygon)
            System.out.println(p.x + ", " + p.y);
        polygon.add(polygon.firstElement());
        for (int y = 0; y < height; ++y) {
            MyPoint q1 = new MyPoint(-1, y);
            MyPoint q2 = new MyPoint(width, y);
            Vector<Double> cp = new Vector<Double>();
            for (int i = 0; i < n; ++i) {
                MyPoint p = MyPointUtils.intersect(q1, q2, polygon.get(i), polygon.get(i + 1));
                if (MyPointUtils.sign(polygon.get(i).y - polygon.get(i + 1).y) == 0 && MyPointUtils.sign(polygon.get(i).y - y) == 0)
                    continue;
                if (!p.equals(MyPoint.NAN) && !MyPointUtils.equals(p, MyPointUtils.higher(polygon.get(i), polygon.get(i + 1))))
                    cp.add(p.x);
            }
            cp.sort(new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    if (o1 < o2) return -1;
                    if (o1 > o2) return 1;
                    return 0;
                }
            });
            if (cp.size() > 0) {
                System.out.println("========= y=" + y + " =========");
                if (cp.size() % 2 != 0)
                    System.out.println("OMG!!!!");
                for (double x : cp)
                    System.out.print(x + " ");
                System.out.println();
            }
            for (int i = 0; i + 1 < cp.size(); i += 2)
                ret.add(new MyHorizontalLine(y, cp.get(i), cp.get(i + 1)));

        }
        return ret;
    }
}
