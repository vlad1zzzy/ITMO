package util;

import static util.MatrixUtil.*;

public class GreatDescentGradient {

    public static double[] gradient(double[] xk, GradFunction function, MinimisationMethod method, double epsilon, int a, int b) {
        int k = 0;
        double lambda , g;
        double[] x0 = copy(xk);
        double[] x, p = function.findGradient(xk, -1);
        do {
            k++;
            x = xk;
            lambda = function.findMin(method, x, a, b, epsilon);
            xk = reducedAddVectors(x, p, lambda);
            g = module(xk);
            p = function.findGradient(xk, -1);

        } while (g > epsilon && k < 10000);
        printSolution(x,"Great descent", k, x0);
        return xk;
    }
}
