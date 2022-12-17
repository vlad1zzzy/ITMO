package graph;

import draw.DrawApi;
import graph.util.Edge;
import graph.util.Vertex;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EdgesGraph extends Graph {
    private final Map<Vertex, Integer> vertices = new HashMap<>();
    private final List<Edge> edges = new ArrayList<>();

    @Override
    public void addEdge(final Vertex v1, final Vertex v2) {
        vertices.putIfAbsent(v1, vertices.size());
        vertices.putIfAbsent(v2, vertices.size());
        edges.add(new Edge(v1, v2));
    }

    @Override
    public int getVerticesCount() {
        return vertices.size();
    }

    @Override
    public List<Pair<Integer, Integer>> getEdges() {
        return edges
                .stream()
                .map(edge -> new Pair<>(vertices.get(edge.v1()), vertices.get(edge.v2())))
                .collect(Collectors.toList());
    }

    public EdgesGraph(final DrawApi drawApi) {
        super(drawApi);
    }
}
