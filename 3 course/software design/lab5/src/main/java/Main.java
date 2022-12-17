import draw.AwtDrawApi;
import draw.DrawApi;
import draw.JavaFxDrawApi;
import graph.Graph;
import graph.EdgesGraph;
import graph.MatrixGraph;
import graph.util.Vertex;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String ...args) throws ParseException, IOException {
        final CommandLine cmd = getCmd(args);

        final int width = getNumber(cmd, 'w', 800);
        final int height = getNumber(cmd, 'h', 600);
        final DrawApi drawApi = getDrawApi(cmd, width, height);
        final Graph graph = getGraph(cmd, drawApi);
        final File file = getFile(cmd);

        readFile(file, graph);

        graph.draw();
    }

    private static CommandLine getCmd(final String ...args) throws ParseException {
        final Options options = new Options();
        options.addOption("d", "draw", true, "draw api");
        options.addOption("g", "graph", true, "graph type");
        options.addOption("f", "file", true, "file name");
        options.addOption("w", "width", true, "width");
        options.addOption("h", "height", true, "height");
        final CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

    private static int getNumber(final CommandLine cmd, final char value, final Integer defaultValue) {
        try {
            return Integer.parseInt(cmd.getOptionValue(value));
        } catch (final NumberFormatException ignore) {
            return defaultValue;
        }
    }

    private static DrawApi getDrawApi(final CommandLine cmd, final int width, final int height) {
        return switch (cmd.getOptionValue('d')) {
            case "fx" -> new JavaFxDrawApi(width, height);
            case "awt" -> new AwtDrawApi(width, height);
            default -> throw new IllegalArgumentException("Invalid draw api");
        };
    }

    private static Graph getGraph(final CommandLine cmd, final DrawApi drawApi) {
        return switch (cmd.getOptionValue('g')) {
            case "edges" -> new EdgesGraph(drawApi);
            case "matrix" -> new MatrixGraph(drawApi);
            default -> throw new IllegalArgumentException("Invalid graph api");
        };
    }

    private static File getFile(final CommandLine cmd) {
        final String filename = cmd.getOptionValue('f');
        final Path path = Paths.get("src/files/" + Objects.requireNonNullElse(filename, "example.txt"));
        return path.toFile();
    }

    public static void readFile(final File file, final Graph graph) throws IOException {
        final List<String> lines = Files.lines(file.toPath()).collect(Collectors.toList());

        if (graph instanceof EdgesGraph) {
            lines.forEach(line -> {
                final var ids = Arrays
                        .stream(line.split("\\s+"))
                        .map(Integer::parseInt)
                        .map(Vertex::new)
                        .collect(Collectors.toList());
                graph.addEdge(ids.get(0), ids.get(1));
            });
        } else {
            for (int i = 0; i < lines.size(); i++) {
                final List<Integer> list = Arrays
                        .stream(lines.get(i).split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j) > 0) {
                        graph.addEdge(new Vertex(i), new Vertex(j));
                    }
                }
            }
        }
    }
}
