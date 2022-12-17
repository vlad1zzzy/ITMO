package draw;

import draw.util.Point;

public interface DrawApi {
    int getDrawingAreaWidth();

    int getDrawingAreaHeight();

    void addCircle(Point center, double radius);

    void addLine(Point first, Point second);

    void paint();
}
