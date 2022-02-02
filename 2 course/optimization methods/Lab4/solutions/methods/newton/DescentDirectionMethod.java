package methods.newton;

import util.GradFunction;

import static util.MatrixUtil.*;

public class DescentDirectionMethod {
    public static double[] solve(GradFunction gradFunction, double[] x, double eps, double a, double b) {
        double lambda;
        double[] s;
        int k = 0;
        do {
            k++;
            double[] d = gradFunction.findGradient(x, -1);
            lambda = gradFunction.findMinP(x, d, a, b, eps);
            s = multiply(d, lambda);
            x = add(x, s);
            double[][] hessian = gradFunction.getHessian(x);
            double[] gradient = gradFunction.findGradient(x, -1);
            double[] p = GaussMethod.solve(hessian, gradient);
            if (scalar(p, gradient) >= 0) {
                d = p;
            } else {
                d = gradient;
            }
            lambda = gradFunction.findMinP(x, d, a, b, eps);
            s = multiply(d, lambda);
            x = add(x, s);
        }
        while (module(s) > eps && k < 1000);
        printSolution(x, "Descent", k);
        return x;
    }
}
