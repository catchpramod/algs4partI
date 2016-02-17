import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by pramod on 2/14/16.
 */
public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private int segmentCount = 0;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        if (points == null) throw new NullPointerException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException();
            for (int j = 0; j < points.length; j++) {
                if (i != j && points[i].compareTo(points[j]) == 0) throw new
                        IllegalArgumentException();

            }
        }
        lineSegments = new LineSegment[points.length];

        Point pi;
        Point pj;
        Point pk;
        Point pl;

        for (int i = 0; i < points.length; i++) {
            jloop:
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        pi = points[i];
                        pj = points[j];
                        pk = points[k];
                        pl = points[l];

                        if (pi.slopeTo(pj) == pi.slopeTo(pk) && pi.slopeTo(pj) == pi.slopeTo(pl)) {
                            // points are collinear
                            Point[] coPoints = new Point[4];
                            coPoints[0] = pi;
                            coPoints[1] = pj;
                            coPoints[2] = pk;
                            coPoints[3] = pl;
                            Arrays.sort(coPoints);
                            lineSegments[segmentCount++] = new LineSegment(coPoints[0], coPoints[3]);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {   // the number of line segments
        return segmentCount;
    }

    public LineSegment[] segments() {  // the line segments
        LineSegment[] ls = new LineSegment[segmentCount];
        for (int i = 0; i < segmentCount; i++) {
            ls[i] = this.lineSegments[i];
        }
        return ls;
    }

    public static void main(String[] args) {

        // read the N points from a file
        args = new String[1];
        args[0] = "test/input40.txt";
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
