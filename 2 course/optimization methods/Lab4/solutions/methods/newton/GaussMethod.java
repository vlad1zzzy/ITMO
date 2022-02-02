package methods.newton;

import java.util.Arrays;

public class GaussMethod {
    public static double[] solve(double[][] A, double[] B) {
        int N = A.length;
        for (int k = 0; k < N; k++) {
            //ищем строку с максимальным ведущим элментом
            int max = getMax(A, k, N);

            //свапаем с текущей
            swap(A, B, k, max);

            //вычитаем строки
            for (int i = k + 1; i < N; i++) {
                double t = A[i][k] / A[k][k];
                B[i] -= t * B[k];
                for (int j = k; j < N; j++) {
                    A[i][j] -= t * A[k][j];
                }
            }
        }
        //проверяем на единственность решения
        for (int k = 0; k < N; k++) {
            if (manySolutions(A[k], B[k])) {
                System.out.println("Many solutions!");
            }
            if (noSolutions(A[k], B[k])) {
                System.out.println("No solutions!");
            }
        }
        //находим решение
        double[] X = new double[N];
        for (int k = N - 1; k >= 0; k--) {
            double sum = 0;
            for (int j = k + 1; j < N; j++) {
                sum += A[k][j] * X[j];
            }
            X[k] = (B[k] - sum) / A[k][k];
        }
        return X;
    }

    public static int getMax(double[][] A, int k, int N) {
        int max = k;
        for (int i = k + 1; i < N; i++) {
            if (Math.abs(A[i][k]) > Math.abs(A[max][k])) {
                max = i;
            }
        }
        return max;
    }

    public static void swap(double[][] A, double[] B, int k, int max) {
        double[] temp = A[k];
        A[k] = A[max];
        A[max] = temp;

        double t = B[k];
        B[k] = B[max];
        B[max] = t;
    }

    public static boolean manySolutions(double[] Ai, double Bi) {
        return Arrays.stream(Ai).allMatch(d -> Math.abs(d) < 1e-19) && Math.abs(Bi) < 1e-19;
    }

    public static boolean noSolutions(double[] Ai, double Bi) {
        return Arrays.stream(Ai).allMatch(d -> Math.abs(d) < 1e-19) && Math.abs(Bi) >= 1e-19;
    }
}