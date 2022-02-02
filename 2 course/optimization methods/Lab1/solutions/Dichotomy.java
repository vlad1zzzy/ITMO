package solutions;

import static solutions.Lab1.func;

public class Dichotomy implements MinimizationMethod {


    @Override
    public double findMin(double a, double b, final double eps) {
        double s = 0.00000001;
        double x1, x2, f1, f2;
        if (s > eps) {
            s = eps;
        }
        do {
            x1 = (a + b - s) / 2;
            x2 = (a + b + s) / 2;
            f1 = func(x1);
            f2 = func(x2);
            if (f1 <= f2) {
                b = x2;
            } else {
                a = x1;
            }
        } while ((b - a) / 2 >= eps);
        return (a + b) / 2;
    }
}
