package methods.powell;

import util.*;

import static util.MatrixUtil.*;


public class DfpMethod {
    public static double[] solve(GradFunction gradFunction, double[] x, MinimisationMethod method, double epsilon, int a, int b) {
        int k = 1;
        double lambda = 0.1;
        double[][] G = getMatrixE(gradFunction.getDimension());
        double[] w = gradFunction.findGradient(x, -1);
        double[] p, deltaX, deltaW, xk, wk, vk;
        double[] x0 = copy(x);
        do {
            p = multiply(G, w);
            lambda = gradFunction.findMin(method, reducedAddVectors(x, p, lambda), a, b, epsilon);
            xk = reducedAddVectors(x, p, lambda);

            deltaX = subtract(xk, x);

            wk = gradFunction.findGradient(xk, -1);
            deltaW = subtract(wk, w);

            vk = multiply(G, deltaW);

            G = getG(G, vk, deltaX, deltaW);

            x = xk;
            w = wk;
            k++;
        } while (module(deltaX) > epsilon && k < 1000);

        printSolution(x, "DFP", k, x0);
        return x;
    }

    public static double[][] getG(double[][] G, double[] v, double[] deltaX, double[] deltaW) {
        return subtract(G,
                add(multiply(multiply(deltaX, deltaX), 1 / scalar(deltaW, deltaX)),
                        multiply(multiply(v, v), 1 / scalar(v, deltaW))));
    }
}
