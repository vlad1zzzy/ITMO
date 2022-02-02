package methods.marquardt;

import methods.newton.GaussMethod;
import util.GradFunction;

import java.util.ArrayList;
import java.util.List;

import static util.MatrixUtil.*;

public class First {
    public static AnswerM solve(GradFunction gradFunction, double[] x, double eps, double t0, double b) {
        double[] s;
        double t = t0;
        double[][] I = getI(x.length);
        List<Double> tau = new ArrayList<>();
        do {
            tau.add(t);
            double[] grad = gradFunction.findGradient(x, -1);
            double[][] hessian = gradFunction.getHessian(x);
            double[] p = GaussMethod.solve(add(hessian, multiply(I, t)), grad);
            double[] x1 = add(x, p);
            double fx1 = gradFunction.findFx(x1);
            if (fx1 > gradFunction.findFx(x)) {
                t /= b;
            } else {
                t0 *= b;
                x = x1;
                t = t0;
            }
            s = p;
        }
        while (module(s) > eps);
        printSolution(x, "Marquardt first", tau.size());
        tau.forEach(qq->System.out.print(qq+" "));
        System.out.println();
        return new AnswerM(x, tau);
    }
}
