package methods;

import generator.FilesUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class LUMethod {
    public static double[] solve(final ProfileMatrix matrix, double[] b) {
        if (!matrix.isConvertedToLu()) {
            matrix.convertToLU();
        }
        int n = b.length;
        double[] y = new double[n];
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            int finalI = i;
            y[i] = (b[i] - IntStream.range(0, i).mapToDouble(j -> matrix.getFromL(finalI, j) * y[j]).sum()) / matrix.getFromL(i, i);
        }
        for (int i = n - 1; i > -1; i--) {
            int finalI = i;
            x[i] = y[i] - IntStream.range(i, n).mapToDouble(j -> matrix.getFromU(finalI, j) * x[j]).sum();
        }
        return x;
    }

    public static double[] solve(String in) {
        LUData data = readLU(in);
        assert data != null;
        return solve(data.getMatrix(), data.getB());
    }

    public static void solve(String in, String out) {
        FilesUtil.write(solve(in),out);
    }

    public static LUData readLU(String in) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(in))) {
            int n = Integer.parseInt(reader.readLine());
            ProfileMatrix profileMatrix = new ProfileMatrix(
                    FilesUtil.readArrayDouble(reader),
                    FilesUtil.readArrayDouble(reader),
                    FilesUtil.readArrayDouble(reader),
                    FilesUtil.readArrayInt(reader));
            double[] b = FilesUtil.readArrayDouble(reader);
            double[] x = FilesUtil.readArrayDouble(reader);
            return new LUData(profileMatrix, b, x, n);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
