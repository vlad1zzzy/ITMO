package methods;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class MatrixUtil {
    public static double[] copy(double[] other){
        return Arrays.copyOf(other, other.length);
    }
    public static int[] copy(int[] other){
        return Arrays.copyOf(other, other.length);
    }
    public static void printM(double[][] a) {
        for (double[] doubles : a) {
            for (int j = 0; j < a.length; j++) {
                System.out.print(doubles[j] + " ");
            }
            System.out.println();
        }
    }

    public static void printMatrix(double[][] A, double[] B) {
        int N = B.length;
        System.out.println("MATRIX :");
        IntStream.range(0, N).forEach(i -> {
            Arrays.stream(A[i]).forEach(aj -> System.out.print(aj + " "));
            System.out.println("   " + B[i]);
        });
        System.out.println();
    }
    public static void printSolution(double[] X) {
        System.out.println("SOLUTION :");
        Arrays.stream(X).forEach(x -> System.out.print(x + " "));
        System.out.println();
    }

    public static double[] add(double[] x, double[] y) {
        int N = x.length;
        double[] ans = new double[N];
        for (int i = 0; i < N; i++) {
            ans[i] = x[i] + y[i];
        }
        return ans;
    }

    public static double[] subtract(double[] x, double[] y) {
        int N = x.length;
        double[] ans = new double[N];
        for (int i = 0; i < N; i++) {
            ans[i] = x[i] - y[i];
        }
        return ans;
    }

    public static double[] multiply(double[] x, double k) {
        int N = x.length;
        double[] ans = new double[N];
        for (int i = 0; i < N; i++) {
            ans[i] = x[i] * k;
        }
        return ans;
    }

    @SuppressWarnings("all")
    public static double module(double[] x) {
        return Math.sqrt(scalar(x, x));
    }

    public static double moduleA(double[][] A) {
        double ans = -1;
        for (double[] row : A) {
            ans = Math.max(Arrays.stream(row).map(Math::abs).sum(), ans);
        }
        return ans;
    }

    public static double moduleReversedA(double[][] A) {
        // = 1
        double ans = 10e10;
        for (double[] row : A) {
            ans = Math.min(Arrays.stream(row).map(Math::abs).sum(), ans);
        }
        return 1 / ans;
    }

    public static double[][] reverseA(double[][] A) {
        double[][] Ar = new double[A.length][A.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                Ar[i][j] = A[j][i];
            }
        }
        return Ar;
    }

    public static double[] getAx(double[][] A, double[] x) {
        int N = x.length;
        double[] ans = new double[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ans[i] += A[i][j] * x[j];
            }
        }
        return ans;
    }

    public static double scalar(double[] x, double[] y) {
        int N = x.length;
        double ans = 0;
        for (int i = 0; i < N; i++) {
            ans += x[i] * y[i];
        }
        return ans;
    }

    public static double[][] generateRandomMatrix(int n) {
        Random random = new Random();
        double[][] A = new double[n][n];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                A[i][j] = n * n * random.nextDouble();
            }
        }
        return A;
    }

    public static void printArray(String name, double[] array) {
        System.out.print(name + ": ");
        Arrays.stream(array).forEach(el -> System.out.print(el + " "));
        System.out.println();
    }

    public static void printArray(String name, int[] array) {
        System.out.print(name + ": ");
        Arrays.stream(array).forEach(el -> System.out.print(el + " "));
        System.out.println();
    }
}
