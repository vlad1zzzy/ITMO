package methods.newton;

import util.GradFunction;

import static util.MatrixUtil.*;

public class ClassicMethod {
    public static double[] solve(GradFunction gradFunction, double[] x, double eps) {
        double[] x0 = copy(x);
        double[] s;
        int k = 0;
        do {
            k++;
            double[] grad = gradFunction.findGradient(x, -1);
            double[][] hessian = gradFunction.getHessian(x);
            double[] p = GaussMethod.solve(hessian, grad);
            s = p;
            x = add(x, p);
        }
        while (module(s) > eps);
        printSolution(x, "Classic", k, x0);
        return x;
    }
}
