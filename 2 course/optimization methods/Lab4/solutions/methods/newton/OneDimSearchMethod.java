package methods.newton;

import util.GradFunction;

import java.util.ArrayList;
import java.util.List;

import static util.MatrixUtil.*;

public class OneDimSearchMethod {
    public static double[] solve(GradFunction gradFunction, double[] x, double eps, double a, double b) {
        double[] x0 = copy(x);
        List<Double> oneDim = new ArrayList<>();
        double lambda;
        double[] s;
        int k = 0;
        do {k++;
            double[] grad = gradFunction.findGradient(x, -1);
            double[][] hessian = gradFunction.getHessian(x);
            double[] p = GaussMethod.solve(hessian, grad);
            lambda = gradFunction.findMinP(x, p, a, b, eps);
            oneDim.add(lambda);
            s = multiply(p, lambda);
            x = add(x, s);
        }
        while (module(s) > eps);
        printSolution(x, "OneDim", k, x0);
        return x;
    }
}
