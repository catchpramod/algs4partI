import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by pramod on 2/15/16.
 */
public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private int segmentCount = 0;

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        if (points == null) throw new NullPointerException();
        lineSegments = new LineSegment[points.length];

        for (int i = 0; i < points.length; i++) {
            //copy points array
            Point[] cPoints = new Point[points.length - 1];
            int k = 0;
            for (int j = 0; j < points.length; j++) {
                if (i != j) {
                    if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
                    cPoints[k++] = points[j];
                }

            }

            //sort by slope with points[i]
            Arrays.sort(cPoints, points[i].slopeOrder());
            Point largest = cPoints[0];
            Point smallest = cPoints[0];
            int ccount = 1;
            double cslope = points[i].slopeTo(cPoints[0]);
            for (int j = 1; j < points.length - 1; j++) {
                double islope = points[i].slopeTo(cPoints[j]);
                if (cslope == islope || cslope == -islope) {
                    if (points[i].compareTo(cPoints[j]) < 0) {
                        if (cPoints[j].compareTo(largest) > 0) largest = cPoints[j];
                    } else {
                        if (cPoints[j].compareTo(smallest) < 0) smallest = cPoints[j];
                    }
                    ccount++;

                } else {
                    if (ccount >= 3 && points[i].compareTo(smallest) < 0)
                        lineSegments[segmentCount++] = new LineSegment(points[i], largest);
                    largest = cPoints[j];
                    smallest = cPoints[j];
                    cslope = points[i].slopeTo(cPoints[j]);
                    ccount = 1;
                }
            }
            if (ccount >= 3 && points[i].compareTo(smallest) < 0)
                lineSegments[segmentCount++] = new LineSegment(points[i], largest);
        }


    }

    public int numberOfSegments() {// the number of line segments
        return segmentCount;
    }

    public LineSegment[] segments() {  // the line segments
        LineSegment[] lineSegments = new LineSegment[segmentCount];
        for (int i = 0; i < segmentCount; i++) {
            lineSegments[i] = this.lineSegments[i];
        }
        return lineSegments;
    }

    public static void main(String[] args) {

        // read the N points from a file
        args = new String[1];
        args[0] = "test/input20.txt";
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}

