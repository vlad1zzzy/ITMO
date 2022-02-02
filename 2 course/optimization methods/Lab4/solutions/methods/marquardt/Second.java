package methods.marquardt;

import methods.newton.GaussMethod;
import util.GradFunction;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static util.MatrixUtil.*;
import static util.MatrixUtil.printSolution;

public class Second {
    public static AnswerM solve(GradFunction gradFunction, double[] x, double eps) {
        double[] s;
        double t = 0;
        double[][] I = getI(x.length);
        List<Double> tau = new ArrayList<>();
        List<Integer> choletskyNum = new ArrayList<>();
        do {
            tau.add(t);
            double[] grad = gradFunction.findGradient(x, -1);
            double[][] hessian = gradFunction.getHessian(x);
            double[][] m = add(hessian, multiply(I, t));
            int q = 1;
            while (!hasCholeskyDecomposition(m)) {
                t = max(1, 2 * t);
                m = add(hessian, multiply(I, t));
                q++;
            }
            choletskyNum.add(q);
            s = GaussMethod.solve(m, grad);
            x = add(x, s);
        }
        while (module(s) > eps);
        printSolution(new double[]{}, "Marquardt second", tau.size());
        tau.forEach(qq -> System.out.print(qq + " "));
        System.out.println();
        return new AnswerM(x, tau, choletskyNum);
    }
}
