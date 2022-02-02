package methods.powell;

import util.MinimisationMethod;
import util.GradFunction;
import util.QuadraticFunction;

import static util.MatrixUtil.*;
import static util.MatrixUtil.printSolution;

public class PowellMethod {
    public static double[] solve(GradFunction gradFunction, double[] x, MinimisationMethod method, double epsilon, int a, int b) {
        int k = 1;
        double lambda = 0.1;
        double[][] G = getMatrixE(gradFunction.getDimension());
        double[] w = gradFunction.findGradient(x, -1);
        double[] p, deltaX, deltaW, xk, wk, deltaXXX;
        double[] x0 = copy(x);
        do {
            p = multiply(G, w);
            lambda = gradFunction.findMin(method, reducedAddVectors(x, p, lambda), a, b, epsilon);
            xk = reducedAddVectors(x, p, lambda);

            deltaX = subtract(xk, x);

            wk = gradFunction.findGradient(xk, -1);
            deltaW = subtract(wk, w);

            deltaXXX = add(deltaX, multiply(G, deltaW));
            G = getG(G, deltaXXX, deltaW);

            x = xk;
            w = wk;
            k++;
        } while (module(deltaX) > epsilon && k < 1000);
        printSolution(x, "Powell", k, x0);
        return x;
    }

    public static double[][] getG(double[][] G, double[] deltaXXX, double[] deltaW) {
        return subtract(G, multiply(multiply(deltaXXX, deltaXXX), 1 / scalar(deltaW, deltaXXX)));
    }

    public static double fmin(double[] p, double[][] xi, double ftol, GradFunction func, MinimisationMethod method)
            throws IllegalMonitorStateException,
            IllegalArgumentException {
        int k = 0;
        if (p.length != xi.length || xi.length != xi[0].length) {
            throw new IllegalArgumentException("dimentions must agree");
        }
        final int n = p.length;
        double[] pt = new double[n];
        double[] ptt = new double[n];
        double[] xit = new double[n];

        double fret = func.findFx(p);

        System.arraycopy(p, 0, pt, 0, n);

        for (int iter = 1; true; ++iter) {
            k++;
            double fp = fret;
            int ibig = 0;
            double del = 0.0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    xit[j] = xi[j][i];
                }
                double fptt = fret;
                //linmin.linmin(p, xit, func);
                //fret = linmin.getFret();
                fret = func.findMin(method, xit, -10, 10, 0.001);
                if (Math.abs(fptt - fret) > del) {
                    del = Math.abs(fptt - fret);
                    ibig = i;
                }
            }
            if (2.0 * Math.abs(fp - fret) <= ftol * (Math.abs(fp) + Math.abs(fret))) {
                break;
                //return fret;
            }
            if (iter == 1000) {
                break;
                //throw new IllegalMonitorStateException("powell exceeding maximum iterations.");
            }
            for (int j = 0; j < n; j++) {
                ptt[j] = 2.0 * p[j] - pt[j];
                xit[j] = p[j] - pt[j];
                pt[j] = p[j];
            }
            double fptt = func.findFx(ptt);
            if (fptt < fp) {
                double t = 2.0 * (fp - 2.0 * fret + fptt) * (fp - fret - del) * (fp - fret - del) -
                        del * (fp - fptt) * (fp - fptt);
                if (t < 0.0) {
                    //linmin = new Linmin(p, xit, func);
                    //fret = linmin.getFret();
                    fret = func.findMin(method, xit, -10, 10, 0.001);
                    for (int j = 0; j < n; j++) {
                        xi[j][ibig] = xi[j][n - 1];
                        xi[j][n - 1] = xit[j];
                    }
                }
            }
        }
        printSolution(xi[n - 1], "POWELL", k);
        return fret;
    }

    public static void main(String[] args) {
        GradFunction f = new QuadraticFunction(new double[][]{{1, 1.2}, {1.2, 1}}, new double[]{0, 0}, 0);
        double ans = fmin(new double[]{4, 1}, new double[][]{{1, 1.2}, {1.2, 1}}, 0.0000001, f, MinimisationMethod.BRENT);
        System.out.println(ans);
    }
}
