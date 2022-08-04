import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private Point[] points;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException(); // check for null points array;

        for (int i = 0; i < points.length; i++) { // check for null elements
            if (points[i] == null) throw new IllegalArgumentException();
        }

        this.points = points.clone(); // local array to avoid mutating elements;
        Arrays.sort(this.points);

        if (this.points.length > 1) {
            for (int i = 1; i < this.points.length; i++) { // check for duplicate points;
                if (this.points[i - 1].equals(this.points[i])) throw new IllegalArgumentException();
            }
        }

        if (this.points.length > 3) {
            Point[] temp = this.points.clone();
            for (Point p : this.points) {
                Arrays.sort(temp, p.slopeOrder());
                findSegments(temp, p);
            }
        }
    }

    public void findSegments(Point[] points, Point p) {
        int start = 1;
        double initialSlope = p.slopeTo(points[1]);
        double tempSlope;
        for (int i = 2; i < points.length; i++) {
            tempSlope = p.slopeTo(points[i]);
            if (Double.compare(tempSlope, initialSlope) != 0) {
                if (i - start >= 3) {
                    Point[] lines = addLine(points, p, start, i);
                    if (lines[0].compareTo(p) == 0) { this.segments.add(new LineSegment(lines[0], lines[1])); }
                }
                start = i;
                initialSlope = tempSlope;
            }
        }

        if (points.length - start >= 3) {
            Point[] lastPoints = addLine(points, p, start, points.length);
            if (lastPoints[0].compareTo(p) == 0) {
                this.segments.add(new LineSegment(lastPoints[0], lastPoints[1]));
            }
        }
    }

    public Point[] addLine(Point[] points, Point p, int start, int index) {
        ArrayList<Point> pointArrayList = new ArrayList<Point>();
        pointArrayList.add(p);

        for (int i = start; i < index; i++) {
            pointArrayList.add(points[i]);
        }

        Collections.sort(pointArrayList);
        return new Point[] { pointArrayList.get(0), pointArrayList.get(pointArrayList.size()-1)};
    }

    public int numberOfSegments() {
        return this.segments.size();
    }

    public LineSegment[] segments() {
        return this.segments.toArray(new LineSegment[this.segments.size()]);
    }
}

