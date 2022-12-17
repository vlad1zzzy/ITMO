package graph;

import draw.DrawApi;
import draw.util.Point;
import graph.util.Vertex;
import javafx.util.Pair;

import java.util.List;

import static java.lang.Math.*;

abstract public class Graph {
    private final DrawApi drawApi;

    abstract public  void addEdge(Vertex v1, Vertex v2);

    abstract public int getVerticesCount();

    abstract List<Pair<Integer, Integer>> getEdges();

    public Graph(final DrawApi drawApi) {
        this.drawApi = drawApi;
    }

    public void draw() {
        final int verticesCount = getVerticesCount();

        final Point center = new Point(drawApi.getDrawingAreaWidth() / 2.0, drawApi.getDrawingAreaHeight() / 2.0);
        final double radius = min(drawApi.getDrawingAreaWidth(), drawApi.getDrawingAreaHeight()) * 0.3;

        final Point[] vertexCoordinates = new Point[verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            final double circleId = i * 2 * PI / verticesCount;
            final Point point = new Point(cos(circleId), sin(circleId))
                    .mul(radius)
                    .add(center);
            drawApi.addCircle(point, 10);
            vertexCoordinates[i] = point;
        }

        for (final Pair<Integer, Integer> edge : getEdges()) {
            drawApi.addLine(vertexCoordinates[edge.getKey()], vertexCoordinates[edge.getValue()]);
        }

        drawApi.paint();
    }
}
