import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MatrixCell {
    private List<Point> pointsToScan ;
    private Dimension size ;
    private int locationX, locationY ;
    public MatrixCell(Dimension size, int locationX, int locationY){
        this.pointsToScan = new ArrayList<>() ;
        this.size = size ;
        this.locationX = locationX ;
        this.locationY = locationY ;
    }

    public Dimension getSize() {
        return size;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public List<Point> getPointsToScan() {
        return pointsToScan;
    }

    public void add(Point point) {
        this.pointsToScan.add(point);
    }
}
