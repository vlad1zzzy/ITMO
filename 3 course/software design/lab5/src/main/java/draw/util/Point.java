package draw.util;

public record Point(double x, double y) {
    public Point add(final Point point) {
        return new Point(x + point.x(), y + point.y());
    }

    public Point mul(final double value) {
        return new Point(x * value, y * value);
    }
}
