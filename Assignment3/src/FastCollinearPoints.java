import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by pramod on 2/15/16.
 */
public class FastCollinearPoints {
    private Queue<LineSegment> lineSegments;
    private int segmentCount = 0;

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        if (points == null) throw new NullPointerException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException();
        }

        lineSegments = new Queue<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            //copy natural sorted array
            Point[] cPoints = new Point[points.length];
            for (int j = 0; j < points.length; j++) {
                if (i != j && points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
                cPoints[j] = points[j];
            }

            //sort by slope with points[i]
            Arrays.sort(cPoints, points[i].slopeOrder());

            Point largest = cPoints[0];
            Point smallest = cPoints[0];
            int ccount = 1;
            for (int j = 1; j < points.length; j++) {
                if (points[i].slopeTo(cPoints[j - 1]) == points[i].slopeTo(cPoints[j])) {
                    if (smallest.compareTo(cPoints[j]) > 0)
                        smallest = cPoints[j];
                    else if (largest.compareTo(cPoints[j]) < 0)
                        largest = cPoints[j];
                    ccount++;
                } else {
                    if (ccount >= 3 && points[i].compareTo(smallest) < 0) {
                        lineSegments.enqueue(new LineSegment(points[i], largest));
                        segmentCount++;
                    }
                    ccount = 1;
                    smallest = cPoints[j];
                    largest = cPoints[j];
                }
            }
            if (ccount >= 3 && points[i].compareTo(smallest) < 0) {
                lineSegments.enqueue(new LineSegment(points[i], largest));
                segmentCount++;
            }
        }
    }

    public int numberOfSegments() {    // the number of line segments
        return segmentCount;
    }

    public LineSegment[] segments() {  // the line segments
        LineSegment[] ls = new LineSegment[segmentCount];
        int i = 0;
        for (LineSegment segment : lineSegments) {
            ls[i++] = segment;
        }
        return ls;
    }

    public static void main(String[] args) {

        // read the N points from a file
        args = new String[1];
        args[0] = "test/rs1423.txt";
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

