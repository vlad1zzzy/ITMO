import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class QuadraticFunctionFactory {
    public static QuadraticFunctionDiagonalised randomD(int n, double maxX) {
        Random random = new Random();
        double minX = 2;
        maxX--;
        double[] a = new double[n];
        double[] b = new double[n];
        double c = 0;
        for (int j = 0; j < n; j++) {
            b[j] = minX + (maxX - minX) * random.nextDouble();
        }
        Set<Double> set = new TreeSet<>();
        set.add(1.0);
        set.add(maxX + 1);
        while (set.size() != n) {
            set.add(minX + (maxX - minX) * random.nextDouble());
        }
        int i = 0;
        for (Double val : set) {
            a[i] = val;
            i++;
        }
        return new QuadraticFunctionDiagonalised(a, b, c);
    }

    public static QuadraticFunctionNotDiagonalised randomND(int n, double maxX) {
        Random random = new Random();
        double minX = 1;
        double[][] a = new double[n][n];
        double[] b = new double[n];
        double c = 0;
        for (int j = 0; j < n; j++) {
            b[j] = minX + (maxX - minX) * random.nextDouble();
            for (int i = 0; i < n; i++) {
                a[i][j] = minX + (maxX - minX) * random.nextDouble();
            }
        }
        return new QuadraticFunctionNotDiagonalised(a, b, c);
    }
}
