import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gradient {
    public static double EPS = 0.001;
    public static int BORDER = 100;

    public static void main(String[] args) {
        //test1();
        test2();
        //test3();
    }

    public static void test1() {
        int n = 10;
        MinimisationMethod[] methods = new MinimisationMethod[]{
                MinimisationMethod.DICHOTOMY,
                MinimisationMethod.FIBONACCI,
                MinimisationMethod.GOLDEN_SECTION,
                MinimisationMethod.PARABOLA,
                MinimisationMethod.BRENT
        };
        for (int i = 0; i < 10; i++) {
            QuadraticFunctionDiagonalised function = QuadraticFunctionFactory.randomD(n, 20);
            double[] x0 = new double[function.getDimension()];
            Arrays.fill(x0, 100);
            printTest(function, x0);
        }
    }

    public static void test2() {
        List<QuadraticFunction> quadraticFunctionList = List.of(
                new QuadraticFunctionNotDiagonalised(new double[][]{{24, 6}, {6, 14}}, new double[]{-5, 10}, -11),
                new QuadraticFunctionNotDiagonalised(new double[][]{{64, 126}, {126, 64}}, new double[]{-10, 30}, 13),
                new QuadraticFunctionNotDiagonalised(new double[][]{{35, -69}, {-69, 35}}, new double[]{-34, 25}, -24));
        double[] x0 = new double[2];
        Arrays.fill(x0, 20);
        for (QuadraticFunction function : quadraticFunctionList) {
            printTest(function, x0);
        }
    }

    public static void test3() {
        int i = 10;
        int size = 99;
        int n = 10;
        int[] step = new int[size];
        int[] fastest = new int[size];
        int[] brent = new int[size];
        double[] x0 = new double[i];
        Arrays.fill(x0, 100);
        for (int f = 0; f < n; f++) {
            for (int j = 10; j < 1000; j += 10) {
                QuadraticFunction function = QuadraticFunctionFactory.randomD(i, j);
                addAns(function, x0, step, fastest, brent, j / 10 - 1);
            }
        }
        for (int j = 0; j < size; j++) {
            step[j] /= n;
            fastest[j] /= n;
            brent[j] /= n;
        }
        System.out.println(Arrays.toString(step) + "\n" + Arrays.toString(fastest) + "\n" + Arrays.toString(brent));

    }

    public static void printTest(QuadraticFunction function, double[] x0) {
        System.out.println("STEP:");
        Answer answer = StepGradient.gradient(x0, function, MinimisationMethod.DICHOTOMY, EPS, -BORDER, BORDER);
        answer.printAns();

        System.out.println("FASTEST:");
        answer = GreatDescentGradient.gradient(x0, function, MinimisationMethod.BRENT, EPS, -BORDER, BORDER);
        answer.printAns();


        System.out.println("CONJUGATE:");
        answer = ConjugateGradient.gradient(x0, function, MinimisationMethod.BRENT, EPS, -BORDER, BORDER, function.getDimension());
        answer.printAns();

        System.out.println("\n");
    }

    public static void addAns(QuadraticFunction function, double[] x0, int[] step, int[] fastest, int[] brent, int index) {
        Answer answer = StepGradient.gradient(x0, function, MinimisationMethod.BRENT, EPS, -BORDER, BORDER);
        step[index] += answer.numberOfIterations;
        answer = GreatDescentGradient.gradient(x0, function, MinimisationMethod.BRENT, EPS, -BORDER, BORDER);
        fastest[index] += answer.numberOfIterations;
        answer = ConjugateGradient.gradient(x0, function, MinimisationMethod.BRENT, EPS, -BORDER, BORDER, function.getDimension());
        brent[index] += answer.numberOfIterations;
    }
}
