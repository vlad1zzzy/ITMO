package util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;

public class MatrixUtil {
    public static double[][] getI(int n) {
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++) {
            a[i][i] = 1;
        }
        return a;
    }

    public static double[][] getMatrixE(int n) {
        double[][] matrix = new double[n][n];
        IntStream.range(0, n).forEach(i -> matrix[i][i] = 1);
        return matrix;
    }

    public static double[] copy(double[] other) {
        return Arrays.copyOf(other, other.length);
    }

    public static int[] copy(int[] other) {
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

    public static void printSolution(double[] X, String name) {
        System.out.print("SOLUTION for " + name + ":  ");
        Arrays.stream(X).forEach(x -> System.out.print(x + " "));
        System.out.println();
    }
    public static void printSolution(double[] X, String name, int k) {
        printSolution(X, name);
        System.out.println("Number of iterations: " + k);
    }
    public static void printSolution(double[] X, String name, int k, double[] x0) {
        printSolution(X, name, k);
        System.out.print("x0: ");
        Arrays.stream(x0).forEach(x -> System.out.print(x + " "));
        System.out.println();
    }

    public static void printSolution(double[] X, String name, int k, double[] x0, List<Double> oneDim) {
        printSolution(X, name, k, x0);
        System.out.println("One dim search results: ");

        for (int i = 0; i < oneDim.size(); i++) {
            System.out.print(oneDim.get(i)+" ");
            if((i+1)%3 == 0){
                System.out.println();
            }
        }
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

    public static double[][] add(double[][] x, double[][] y) {
        int N = x.length;
        double[][] ans = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ans[i][j] = x[i][j] + y[i][j];
            }
        }
        return ans;
    }

    public static double[] subtract(double[] a, double[] b) {
        int N = a.length;
        double[] ans = new double[N];
        for (int i = 0; i < N; i++) {
            ans[i] = a[i] - b[i];
        }
        return ans;
    }

    public static double[][] subtract(double[][] a, double[][] b) {
        int N = a.length;
        double[][] ans = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ans[i][j] = a[i][j] - b[i][j];
            }
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

    public static double[][] multiply(double[][] A, double x) {
        int N = A.length;
        double[][] ans = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ans[i][j] += A[i][j] * x;
            }
        }
        return ans;
    }

    public static double[] multiply(double[][] A, double[] x) {
        int N = x.length;
        double[] ans = new double[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ans[i] += A[i][j] * x[j];
            }
        }
        return ans;
    }

    public static double[][] multiply(double[] x1, double[] x2) {
        int N = x1.length;
        double[][] ans = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ans[i][j] += x1[i] * x2[j];
            }
        }
        return ans;
    }

    public static double module(double[] x) {
        return sqrt(scalar(x, x));
    }

    public static double square(double a) {
        return a * a;
    }

    public static double cube(double a) {
        return a * a * a;
    }

    public static double moduleA(double[][] A) {
        double ans = -1;
        for (double[] row : A) {
            ans = Math.max(Arrays.stream(row).map(Math::abs).sum(), ans);
        }
        return ans;
    }

    public static double moduleReversedA(double[][] A) {
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

    public static double scalar(final double[] a, final double[] b) {
        return IntStream.range(0, a.length).mapToDouble(i -> a[i] * b[i]).reduce(0, Double::sum);
    }

    public static double[] reducedAddVectors(final double[] a, final double[] b, double k) {
        return add(a, multiply(b, k));
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

    public static boolean hasCholeskyDecomposition(double[][] a) {
        try {
            int n = a.length;
            double[][] l = new double[n][n];
            l[0][0] = mySqrt(a[0][0]);
            IntStream.range(1, n).forEach(j -> l[j][0] = a[j][0] / l[0][0]);
            IntStream.range(1, n).forEach(i -> {
                l[i][i] = mySqrt(a[i][i] - IntStream.range(0, i).mapToDouble(p -> mySqrt(l[i][p])).sum());
                if (i != n - 1) {
                    IntStream.range(i + 1, n).forEach(j ->
                            l[j][i] = (a[j][i] - IntStream.range(0, i).mapToDouble(p -> l[i][p] * l[j][p]).sum()) / l[i][i]);
                }
            });
            /*for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int finalI = i;
                    int finalJ = j;
                    System.out.print(IntStream.range(0,n).mapToDouble(q->l[finalI][q]*l[q][finalJ]).sum() + " ");
                }
                System.out.println();
            }*/
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static double mySqrt(double a) throws ArithmeticException{
        double b = sqrt(a);
        if (b!=b){
            throw new ArithmeticException();
        }
        return b;
    }


}
