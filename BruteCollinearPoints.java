import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class BruteCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        this.points = points.clone();

        if (this.points == null) { throw new IllegalArgumentException(); }
        for (int i = 0; i < this.points.length; i++) {
            if (this.points[i] == null) throw new IllegalArgumentException("Cannot have null entries in array");
        }

        Arrays.sort(points);
        Arrays.sort(points, new Point(0, 0).slopeOrder());

        for (int i = 0; i < points.length-3; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int h = k+1; h < points.length; h++) {
                            Double slope1 = points[i].slopeTo(points[j]);
                            Double slope2 = points[i].slopeTo(points[k]);
                            Double slope3 = points[i].slopeTo(points[h]);
                            if (Double.compare(slope1, slope2) == 0 && Double.compare(slope2, slope3) == 0) {
                                this.lineSegments.add(new LineSegment(points[i], points[h]));
                            }
                        }
                    }
                }
            }
        segments = this.lineSegments.toArray(new LineSegment[0]);
        }

    // finds all line segments containing 4 points
    public int numberOfSegments()  {
        return this.lineSegments.size();
    }

    // the number of line segments
    public LineSegment[] segments() {
        return this.segments;
    }
}
