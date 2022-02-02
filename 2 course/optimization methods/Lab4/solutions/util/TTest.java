package util;


import methods.marquardt.First;
import methods.marquardt.Second;
import methods.newton.ClassicMethod;
import methods.newton.DescentDirectionMethod;
import methods.powell.DfpMethod;
import methods.powell.PowellMethod;
import methods.newton.OneDimSearchMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static util.MatrixUtil.*;

public class TTest {

    private final static double EPS = 1E-5;
    private final static int BORDER = 10;

    public static void main(String[] args) {
        GradFunction function1 = new AbstractGradFunction() {
            @Override
            public double[] findAp(double[] p) {
                return new double[0];
            }

            @Override
            public double findFx(double[] x) {
                return Arrays.stream(x).map(Math::sin).sum();
            }

            @Override
            public double findFdkX(int k, double[] x) {
                return Math.cos(x[k]);
            }

            @Override
            public int getDimension() {
                return 2;
            }

            @Override
            public double[][] getHessian(double[] x) {
                double[][] hessian = new double[2][2];
                IntStream.range(0, 2).forEach(i -> hessian[i][i] = Math.sin(x[i]));
                return hessian;
            }
        };
        /*
        test();
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();*/
    }

    private static void test() {
        List<GradFunction> gradFunctionList = List.of(
                new QuadraticFunction(new double[][]{{24, EPS}, {EPS, 14}}, new double[]{-5, 10}, -11),
                new QuadraticFunction(new double[][]{{64, 126}, {126, 64}}, new double[]{-10, 30}, 13),
                new QuadraticFunction(new double[][]{{35, -69}, {-69, 35}}, new double[]{-34, 25}, -24));


        double[] x0 = new double[2];
        Arrays.fill(x0, 1);
        for (GradFunction function : gradFunctionList) {
            printTest(function, x0);
        }
    }

    private static void test1() {
        GradFunction function1 = new AbstractGradFunction() {
            @Override
            public double[] findAp(double[] p) {
                return new double[0];
            }

            @Override
            public double findFx(double[] x) {
                return square(x[0]) + square(x[1]) - 1.2 * x[0] * x[1];
            }

            @Override
            public double findFdkX(int k, double[] xv) {
                double x = xv[0];
                double y = xv[1];
                if (k == 0) {
                    return 2 * x - 1.2 * y;
                }
                return 2 * y - 1.2 * x;
            }

            @Override
            public int getDimension() {
                return 2;
            }

            @Override
            public double[][] getHessian(double[] xv) {
                double[][] hessian = new double[2][2];
                hessian[0][0] = 2;
                hessian[0][1] = hessian[1][0] = -1.2;
                hessian[1][1] = 2;
                return hessian;

            }
        };
        printTest(function1, new double[]{4, 1});
    }

    private static void test2() {
        GradFunction function2 = new AbstractGradFunction() {
            @Override
            public double[] findAp(double[] p) {
                return new double[0];
            }

            @Override
            public double findFx(double[] x) {
                return 100 * square(x[1] - square(x[0])) + square(1 - x[0]);
            }

            @Override
            public double findFdkX(int k, double[] xv) {
                double x = xv[0];
                double y = xv[1];
                if (k == 0) {
                    return 2 * (200 * cube(x) - 200 * x * y + x - 1);
                }
                return 200 * (y - square(x));
            }

            @Override
            public int getDimension() {
                return 2;
            }

            @Override
            public double[][] getHessian(double[] xv) {
                double x = xv[0];
                double y = xv[1];
                double[][] hessian = new double[2][2];
                hessian[0][0] = -400 * (y - square(x)) + 800 * square(x) + 2;
                hessian[0][1] = hessian[1][0] = -400 * x;
                hessian[1][1] = 200;
                return hessian;

            }
        };
        printTest(function2, new double[]{1, 1.5});
    }

    private static void test3() {
        GradFunction function3 = new AbstractGradFunction() {
            @Override
            public double[] findAp(double[] p) {
                return new double[0];
            }

            @Override
            public double findFx(double[] X) {
                double x = X[0];
                double y = X[1];
                return square(square(x) + y - 11) + square(x + square(y) - 7);
            }

            @Override
            public double findFdkX(int k, double[] xv) {
                double x = xv[0];
                double y = xv[1];
                if (k == 0) {
                    return 4 * x * (square(x) + y - 11) + 2 * x + 2 * square(y) - 14;
                }
                return 2 * x * x + 4 * y * (x + y * y - 7) + 2 * y - 22;
            }

            @Override
            public int getDimension() {
                return 2;
            }

            @Override
            public double[][] getHessian(double[] xv) {
                double x = xv[0];
                double y = xv[1];
                double[][] hessian = new double[2][2];
                hessian[0][0] = 12 * x * x + 4 * y - 42;
                hessian[0][1] = hessian[1][0] = 4 * (x + y);
                hessian[1][1] = 4 * x + 12 * y * y - 26;
                return hessian;
            }
        };
        printTest(function3, new double[]{0.2, -4});
    }

    private static void test4() {
        GradFunction function4 = new AbstractGradFunction() {
            @Override
            public double[] findAp(double[] p) {
                return new double[0];
            }

            @Override
            public double findFx(double[] X) {
                double x = X[0];
                double y = X[1];
                double z = X[2];
                double k = X[3];
                return square(x + 10 * y) + 5 * square(z - k) + square(square(y - 2 * z)) + 10 * square(square(x - k));
            }

            @Override
            public double findFdkX(int kk, double[] X) {
                double x = X[0];
                double y = X[1];
                double z = X[2];
                double k = X[3];
                return switch (kk) {
                    case 0 -> 40 * cube(x - k) + 2 * (x + 10 * y);
                    case 1 -> 20 * (x + 10 * y) + 4 * cube(y - 2 * z);
                    case 2 -> 10 * (z - k) - 8 * cube(y - 2 * z);
                    default -> -40 * cube(x - k) - 10 * (z - k);
                };
            }

            @Override
            public int getDimension() {
                return 4;
            }

            @Override
            public double[][] getHessian(double[] X) {
                double x = X[0];
                double y = X[1];
                double z = X[2];
                double k = X[3];
                return new double[][]{
                        {120 * square(x - k) + 2, 20, 0, -120 * square(x - k)},
                        {20, 12 * square(y - 2 * z) + 200, -24 * square(y - 2 * z), 0},
                        {0, -24 * square(y - 2 * z), 48 * square(y - 2 * z) + 10, -10},
                        {-120 * square(x - k), 0, -10, 120 * square(x - k) + 10}
                };
            }
        };
        printTest(function4, new double[]{2, 3, 4, 5});
    }

    private static void test5() {
        GradFunction function5 = new AbstractGradFunction() {
            @Override
            public double[] findAp(double[] p) {
                return new double[0];
            }

            @Override
            public double findFx(double[] X) {
                double x = X[0];
                double y = X[1];
                return 100
                        - 2 / (1 + square((x - 1) / 2) + square((y - 1) / 3))
                        - 1 / (1 + square((x - 2) / 2) + square((y - 1) / 3));
            }

            @Override
            public double findFdkX(int kk, double[] X) {
                double x = X[0];
                double y = X[1];
                if (kk == 0) {
                    return 648 * (x - 2) / square(9 * square(x) - 36 * x + 4 * square(y) - 8 * y + 76) +
                            (x - 1) / square(square((x - 1) / 2) + square((y - 1) / 3) + 1);
                }

                return 2 * (y - 1) / 9 *
                        (2 / square(square((x - 1) / 2) + square((y - 1) / 3) + 1) +
                                1 / square(square((x - 2) / 2)) + square((y - 1) / 3) + 1);
            }

            @Override
            public int getDimension() {
                return 2;
            }

            @Override
            public double[][] getHessian(double[] X) {
                double x = X[0];
                double y = X[1];

                return new double[][]{{-square(-2 + x) / cube(2 * (1 + square(-2 + x) / 4 + square(-1 + y) / 9)) + 1 / square(2 * square(1 + (-2 + x) / 4 + square(-1 + y) / 9)) - square(-1 + x) / cube(1 + square(-1 + x) / 4 + square(-1 + y) / 9) + 1 / square(1 + square(-1 + x) / 4 + square(-1 + y) / 9),
                        (-2 * (-2 + x) * (-1 + y)) / (9 * cube(1 + square(-2 + x) / 4 + square(-1 + y) / 9)) - (4 * (-1 + x) * (-1 + y)) / (9 * cube(1 + square(-1 + x) / 4 + square(-1 + y) / 9))},
                        {(-2 * (-2 + x) * (-1 + y)) / (9 * cube(1 + square(-2 + x) / 4 + square(-1 + y) / 9)) - (4 * (-1 + x) * (-1 + y)) / (9 * cube(1 + square(-1 + x) / 4 + square(-1 + y) / 9)),
                                2 / (9 * square(1 + square(-2 + x) / 4 + square(-1 + y) / 9)) + 4 / (9 * square(1 + square(-1 + x) / 4 + square(-1 + y) / 9)) - (8 * square(-1 + y)) / (81 * cube(1 + square(-2 + x) / 4 + square(-1 + y) / 9)) - (16 * square(-1 + y)) / (81 * cube(1 + square(-1 + x) / 4 + square(-1 + y) / 9))}};
            }
        };
        printTest(function5, new double[]{1.5, 1.5});
    }

    private static void test6() {
        GradFunction function6 = new AbstractGradFunction() {

            @Override
            public double[] findAp(double[] p) {
                return new double[0];
            }

            @Override
            public double findFx(double[] x) {
                return IntStream.range(0, getDimension() - 1).mapToDouble(i -> 100 * square(x[i + 1] - square(x[i])) + square(1 - x[i])).sum();
            }

            @Override
            public double findFdkX(int kk, double[] x) {
                int n = getDimension();
                return switch (kk) {
                    case 0 -> 2 * (200 * cube(x[0]) - 200 * x[0] * x[1] + x[0] - 1);
                    case 99 -> 200 * (x[99] - square(x[98]));
                    default -> 2 * (200 * cube(x[kk]) - 200 * x[kk] * x[kk + 1] + x[kk] - 1) + 200 * (x[kk] - square(x[kk - 1]));

                };
            }

            @Override
            public int getDimension() {
                return 100;
            }

            @Override
            public double[][] getHessian(double[] x) {
                int n = getDimension();
                double[][] hessian = new double[n][n];
                hessian[0][0] = -400 * (x[1] - square(x[0])) + 800 * square(x[0]) + 2;
                hessian[0][1] = -400 * x[0];
                hessian[99][99] = 200;
                hessian[99][98] = -400 * x[98];
                for (int i = 1; i < n - 1; i++) {
                    hessian[i][i - 1] = -400 * x[i - 1];
                    hessian[i][i] = 1200 * square(x[i]) - 400 * x[1] + 202;
                    hessian[i][i + 1] = -400 * x[i];
                }

                return hessian;
            }
        };
        double[] x = new double[function6.getDimension()];
        Arrays.fill(x, 4);
        printTest(function6, x);
    }

    private static void printTest(GradFunction function, double[] x0) {
        double[] res1 = ClassicMethod.solve(function, Arrays.copyOf(x0, x0.length), EPS);
        double[] res2 = OneDimSearchMethod.solve(function, Arrays.copyOf(x0, x0.length), EPS, -BORDER, BORDER);
        double[] res3 = DescentDirectionMethod.solve(function, Arrays.copyOf(x0, x0.length), EPS, -BORDER, BORDER);
        GreatDescentGradient.gradient(x0, function,MinimisationMethod.BRENT,EPS, -BORDER, BORDER);
        double[] res4 = DfpMethod.solve(function, Arrays.copyOf(x0, x0.length), MinimisationMethod.BRENT, EPS, -BORDER, BORDER);
        double[] res5 = PowellMethod.solve(function, Arrays.copyOf(x0, x0.length), MinimisationMethod.BRENT, EPS, -BORDER, BORDER);
        double[] res6 = First.solve(function, Arrays.copyOf(x0, x0.length), EPS, 500000, 0.9).getAnswer();
        double[] res7 = Second.solve(function, Arrays.copyOf(x0, x0.length), EPS).getAnswer();
        //System.out.println(function.findFx(res1));
        /*System.out.println(function.findFx(res2));
        System.out.println(function.findFx(res3));
        System.out.println(function.findFx(res4));
        System.out.println(function.findFx(res5));*/
        //System.out.println(Arrays.toString(ConjugateGradient.gradient(function, x0, EPS, x0.length)));
        System.out.println();
    }
}
