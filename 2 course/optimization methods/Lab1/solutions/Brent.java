package solutions;

import static java.lang.Math.*;
import static solutions.Lab1.func;

public class Brent implements MinimizationMethod {
    @Override
    public double findMin(double a, double c, final double eps) {
        final double phi = (3 - sqrt(5)) / 2;
        double x2, x1, x3, xi = 0, f2, f1, f3, fi, d, e, g, tol;
        boolean accepted;
        x2 = x1 = x3 = a + phi * (c - a);
        f2 = f1 = f3 = func(x2);
        d = e = c - a;
        do {
            accepted = false;
            g = e;
            e = d;
            tol = eps * abs(x2) + eps / 10;
            if (abs(x2 - (a + c) / 2) + (c - a) / 2 <= 2 * tol) {
                break;
            }
            if (threeNotEquals(x2, x3, x1, eps)) {
                xi = Parabola.minOfParabola(x1, x2, x3, f1, f2, f3);
                if (a <= xi && xi <= c && abs(xi - x2) < g / 2) {
                    accepted = true;
                    if (xi - a < 2 * tol || c - xi < 2 * tol) {
                        xi = x2 - signum(x2 - (a + c) / 2) * tol;
                    }
                }
            }
            if (!accepted) {
                if (x2 < (a + c) / 2) {
                    xi = x2 + phi * (c - x2);
                    e = c - x2;
                } else {
                    xi = x2 - phi * (x2 - a);
                    e = x2 - a;
                }
            }
            if (abs(xi - x2) < tol) {
                xi = x2 + signum(xi - x2) * tol;
            }
            d = abs(xi - x2);
            fi = func(xi);
            if (fi <= f2) {
                if (xi >= x2) {
                    a = x2;
                } else {
                    c = x2;
                }
                x3 = x1;
                x1 = x2;
                x2 = xi;
                f3 = f1;
                f1 = f2;
                f2 = fi;
            } else {
                if (xi >= x2) {
                    c = xi;
                } else {
                    a = xi;
                }
                if (fi <= f1 || equals(x1, x2, eps)) {
                    x3 = x1;
                    x1 = xi;
                    f3 = f1;
                    f1 = fi;
                } else if (fi <= f3 || equals(x3, x2, eps) || equals(x3, x1, eps)) {
                    x3 = xi;
                    f3 = fi;
                }
            }
        } while (true);
        return x2;
    }
}
