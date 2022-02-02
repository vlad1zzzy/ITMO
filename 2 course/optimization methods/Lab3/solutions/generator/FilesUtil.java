package generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilesUtil {
    public static void writeArrayDouble(double[] arr, BufferedWriter writer) throws IOException {
        for (double v : arr) {
            writer.write(v + " ");
        }
        writer.newLine();
    }

    public static void writeArrayInt(int[] arr, BufferedWriter writer) throws IOException {
        for (int v : arr) {
            writer.write(v + " ");
        }
        writer.newLine();
    }

    public static double[] readArrayDouble(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        List<Double> list = Arrays.stream(s.split(" ")).map(Double::parseDouble).collect(Collectors.toList());
        double[] arr = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static int[] readArrayInt(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        List<Integer> list = Arrays.stream(s.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static void write(double[] arr, String out){
        Path path = Path.of(out);
        final Path parent = path.getParent();
        if (parent != null && Files.notExists(parent)) {
            try {
                Files.createDirectories(parent);
            } catch (final IOException ignored) {
            }
        }
        try(BufferedWriter writer = Files.newBufferedWriter(path)) {
            writeArrayDouble(arr, writer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
