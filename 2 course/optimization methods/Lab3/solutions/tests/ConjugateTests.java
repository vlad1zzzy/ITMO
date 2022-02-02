package tests;

import generator.Generator;
import methods.ConjugateAnswer;
import methods.ConjugateGradientMethod;
import methods.SparseMatrix;

import java.util.Random;
import java.util.stream.IntStream;

import static methods.MatrixUtil.*;

public class ConjugateTests {
    public static void main(String[] args) {
        //test1();
        //test2();
        test3();
        //test4();
    }


    public static void test1() {
        ConjugateGradientMethod conjugate = new ConjugateGradientMethod();
        Random random = new Random();
        Generator generator = new Generator();
        for (int n = 3; n < 10; n++) {
            final int j = n;
            SparseMatrix sparseMatrix = generator.generateSparseMatrix(n, -1);
            double[][] A = sparseMatrix.convertToNormalMatrix();
            double[] x = IntStream.range(1, n + 1).mapToDouble(d -> j * random.nextDouble()).toArray();
            double[] b = getAx(A, x);
            ConjugateAnswer answer = conjugate.solve(sparseMatrix, b);
            double delta = module(subtract(x, answer.getRes()));
            System.out.println(delta);
        }
    }

    public static void test2() {
        buildTable(-1);
    }

    public static void test3() {
        buildTable(1);
    }

    public static void test4() {
        ConjugateGradientMethod conjugate = new ConjugateGradientMethod();
        Generator generator = new Generator();
        for (int n = 3; n < 10; n++) {
            double[][] A = generator.generateGilbertMatrix(n);
            SparseMatrix sparseMatrix = new SparseMatrix(A);
            printTable(n, sparseMatrix, conjugate, A);
        }
    }

    public static void buildTable(int sign) {
        ConjugateGradientMethod conjugate = new ConjugateGradientMethod();
        Generator generator = new Generator();
        for (int n = 10; n < 3e3; n += 133) {
            SparseMatrix sparseMatrix = generator.generateSparseMatrix(n, sign);
            double[][] A = sparseMatrix.convertToNormalMatrix();
            printTable(n, sparseMatrix, conjugate, A);
        }
    }

    public static void printTable(int n, SparseMatrix sparseMatrix, ConjugateGradientMethod conjugate, double[][] A) {
        double[] x = IntStream.range(1, n + 1).mapToDouble(i -> i).toArray();
        double[] b = sparseMatrix.getAx(x);
        ConjugateAnswer answer = conjugate.solve(sparseMatrix, b);
        double delta = module(subtract(x, answer.getRes()));
        double div = delta / module(x);
        double cond = moduleA(A) * moduleReversedA(A);
        double condMin = (delta * module(b)) / (module(subtract(b, getAx(A, answer.getRes()))) * module(x));
        System.out.format("%4d ", n);
        System.out.format("%4d ", answer.getK());
        System.out.print(delta + " ".repeat(25 - String.valueOf(delta).length()));
        System.out.println(div + " ".repeat(25 - String.valueOf(div).length()) + cond);
    }
}
