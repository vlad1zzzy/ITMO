package solutions;

import static java.lang.Math.abs;
import static solutions.Lab1.func;

public class Parabola implements MinimizationMethod {
    public double findMin(double a, double b, final double eps) {
        double x1 = a, x2, x3 = b;
        double f1 = func(x1), f2, f3 = func(x3);
        x2 = f1 < f3 ? a + eps : b - eps;
        f2 = func(x2);
        double xi, fi, x;
        xi = minOfParabola(x1, x2, x3, f1, f2, f3);
        fi = func(xi);
        do {
            if (x1 < xi && xi < x2) {
                if (fi >= f2) {
                    x1 = xi;
                    f1 = fi;
                } else {
                    x3 = x2;
                    f3 = f2;
                    x2 = xi;
                    f2 = fi;
                }
            } else {
                if (f2 >= fi) {
                    x1 = x2;
                    f1 = f2;
                    x2 = xi;
                    f2 = fi;
                } else {
                    x3 = xi;
                    f3 = fi;
                }
            }
            x = minOfParabola(x1, x2, x3, f1, f2, f3);
            if (abs(x - xi) < eps) {
                break;
            }
            xi = x;
            fi = func(x);
        } while (true);
        return x;
    }
    static double minOfParabola(double x1, double x2, double x3, double f1, double f2, double f3) {
        double a0, a1, a2;
        a0 = f1;
        a1 = (f2 - a0) / (x2 - x1);
        a2 = ((f3 - f1) / (x3 - x1) - a1) / (x3 - x2);
        return 0.5 * (x1 + x2 - a1 / a2);
    }
}
