package graph;

import draw.DrawApi;
import graph.util.Vertex;
import javafx.util.Pair;

import java.util.*;

public class MatrixGraph extends Graph {
    private final List<Vertex> vertices = new ArrayList<>();
    private final Map<Vertex, Integer> verticesIds = new HashMap<>();

    private final List<List<Integer>> matrix = new ArrayList<>(new ArrayList<>());

    private int addVertex(final Vertex vertex) {
        verticesIds.computeIfAbsent(vertex, id -> {
            vertices.add(id);
            matrix.forEach(row -> row.add(0));
            matrix.add(new ArrayList<>(Collections.nCopies(matrix.size() + 1, 0)));
            return vertices.size() - 1;
        });

        return verticesIds.get(vertex);
    }

    @Override
    public void addEdge(final Vertex v1, final Vertex v2) {
        final int id1 = addVertex(v1);
        final int id2 = addVertex(v2);
        final int prev = matrix.get(id1).get(id2);
        matrix.get(id1).set(id2, prev + 1);
    }

    @Override
    public int getVerticesCount() {
        return vertices.size();
    }

    @Override
    public List<Pair<Integer, Integer>> getEdges() {
        final List<Pair<Integer, Integer>> edges = new ArrayList<>();

        for (int i = 0; i < matrix.size(); i++) {
            final List<Integer> list = matrix.get(i);
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j) > 0) {
                    edges.add(new Pair<>(i, j));
                }
            }
        }

        return edges;
    }

    public MatrixGraph(final DrawApi drawApi) {
        super(drawApi);
    }
}
