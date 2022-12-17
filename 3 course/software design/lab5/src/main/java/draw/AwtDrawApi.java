package draw;

import draw.util.Point;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class AwtDrawApi implements DrawApi {
    ArrayList<Ellipse2D.Double> circles = new ArrayList<>();
    ArrayList<Line2D.Double> lines = new ArrayList<>();

    private final int width;
    private final int height;

    public class AwtFrame extends Frame {
        @Override
        public void paint(final Graphics g) {
            final Graphics2D ga = (Graphics2D) g;
            ga.setPaint(Color.green);
            circles.forEach(ga::fill);
            ga.setPaint(Color.black);
            lines.forEach(ga::draw);
        }
    }

    public AwtDrawApi(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getDrawingAreaWidth() {
        return width;
    }

    @Override
    public int getDrawingAreaHeight() {
        return height;
    }

    @Override
    public void addCircle(final draw.util.Point center, final double radius) {
        circles.add(new Ellipse2D.Double(center.x() - radius, center.y() - radius, radius * 2, radius * 2));
    }

    @Override
    public void addLine(final draw.util.Point first, final Point second) {
        lines.add(new Line2D.Double(first.x(), first.y(), second.x(), second.y()));
    }

    @Override
    public void paint() {
        final Frame frame = new AwtFrame();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(getDrawingAreaWidth(), getDrawingAreaHeight());
        frame.setVisible(true);
    }
}
