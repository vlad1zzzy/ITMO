import static java.lang.Math.*;
import static java.lang.Math.abs;

public abstract class AbstractQuadraticFunction implements QuadraticFunction {
    @Override
    public double[] findGradient(double[] x, double k) {
        double[] ans = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            ans[i] = k * findFdkX(i, x);
        }
        return ans;
    }


    @Override
    public double dfNormalize(double[] x) {
        double[] gradient = findGradient(x, 1);
        return Math.sqrt(reducedMultiplyVectors(gradient, gradient));
    }

    @Override
    public double[] reducedAddVectors(double[] x1, double[] x2, double k2) {
        double[] ans = new double[x1.length];
        for (int i = 0; i < x1.length; i++) {
            ans[i] = x1[i] + k2 * x2[i];
        }
        return ans;
    }

    @Override
    public double reducedMultiplyVectors(double[] x1, double[] x2) {
        double ans = 0;
        for (int i = 0; i < x1.length; i++) {
            ans += x1[i] * x2[i];
        }
        return ans;
    }

    private double findG(double[] x, double lambda) {
        double[] newX = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            newX[i] = x[i] - lambda * findFdkX(i, x);
        }
        return findFx(newX);
    }

    @Override
    public double findMin(MinimisationMethod method, double[] x, double a, double b, double eps) {
        return switch (method) {
            case BRENT -> brent(x, a, b, eps);
            case PARABOLA -> parabola(x, a, b, eps);
            case DICHOTOMY -> dichotomy(x, a, b, eps);
            case FIBONACCI -> fibonacci(x, a, b, eps);
            case GOLDEN_SECTION -> goldenSection(x, a, b, eps);
        };
    }


    private double dichotomy(double[] x, double a, double b, double eps) {
        double s = eps / 1000;
        double x1, x2, f1, f2;
        do {
            x1 = (a + b - s) / 2;
            x2 = (a + b + s) / 2;
            f1 = findG(x, x1);
            f2 = findG(x, x2);
            if (f1 <= f2) {
                b = x2;
            } else {
                a = x1;
            }
        } while ((b - a) / 2 >= eps);
        return (a + b) / 2;
    }

    private static final int[] fibs = new int[]{
            1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597,
            2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418,
            317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465,
            14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296,
            433494437, 701408733, 1134903170, 1836311903
    };

    private double fibonacci(double[] x, double a, double b, double eps) {
        double anchor = (b - a) / eps;
        int n = -1;
        for (int i = 2; i < fibs.length; i++) {
            if (fibs[i] > anchor) {
                n = i;
                break;
            }
        }
        if (n == -1) {
            System.err.println("Cannot find F for this data. Choose different 'eps'.");
            return -1;
        }
        double x1 = a + ((fibs[n - 2] * 1.0) / fibs[n]) * (b - a);
        double x2 = a + ((fibs[n - 1] * 1.0) / fibs[n]) * (b - a);
        for (int k = 1; k < n - 1; k++) {
            double f1 = findG(x, x1);
            double f2 = findG(x, x2);
            if (f1 > f2) {
                a = x1;
                x1 = x2;
                x2 = a + ((fibs[n - k - 1] * 1.0) / fibs[n - k]) * (b - a);
            } else {
                b = x2;
                x2 = x1;
                x1 = a + ((fibs[n - k - 2] * 1.0) / fibs[n - k]) * (b - a);
            }
        }
        x2 = x1 + eps;
        if (abs(findG(x, x1) - findG(x, x2)) < eps) {
            a = x1;
        } else {
            b = x2;
        }
        return (a + b) / 2;
    }

    private double goldenSection(double[] x, double a, double b, double eps) {
        final double phi = (1 + sqrt(5)) / 2;
        final double resPhi = 2 - phi;
        double x1 = a + resPhi * (b - a);
        double x2 = b - resPhi * (b - a);
        double f1 = findG(x, x1);
        double f2 = findG(x, x2);
        do {
            if (f1 < f2) {
                b = x2;
                x2 = x1;
                f2 = f1;
                x1 = a + resPhi * (b - a);
                f1 = findG(x, x1);
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
                x2 = b - resPhi * (b - a);
                f2 = findG(x, x2);
            }
        } while (abs(b - a) > eps);
        return (x1 + x2) / 2;
    }

    private double parabola(double[] x, double a, double b, double eps) {
        double x1 = a, x2, x3 = b;
        double f1 = findG(x, x1), f2, f3 = findG(x, x3);
        x2 = f1 < f3 ? a + eps : b - eps;
        f2 = findG(x, x2);
        double xi, fi, X;
        xi = minOfParabola(x1, x2, x3, f1, f2, f3);
        fi = findG(x, xi);
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
            X = minOfParabola(x1, x2, x3, f1, f2, f3);
            if (abs(X - xi) < eps) {
                break;
            }
            xi = X;
            fi = findG(x, X);
        } while (true);
        return X;
    }

    private double brent(double[] x, double a, double c, double eps) {
        final double phi = (3 - sqrt(5)) / 2;
        double x2, x1, x3, xi = 0, f2, f1, f3, fi, d, e, g, tol;
        boolean accepted;
        x2 = x1 = x3 = a + phi * (c - a);
        f2 = f1 = f3 = findG(x, x2);
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
                xi = minOfParabola(x1, x2, x3, f1, f2, f3);
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
            fi = findG(x, xi);
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

    static double minOfParabola(double x1, double x2, double x3, double f1, double f2, double f3) {
        double a0, a1, a2;
        a0 = f1;
        a1 = (f2 - a0) / (x2 - x1);
        a2 = ((f3 - f1) / (x3 - x1) - a1) / (x3 - x2);
        return 0.5 * (x1 + x2 - a1 / a2);
    }

    boolean equals(double a, double b, double eps) {
        return abs(a - b) < eps;
    }

    boolean threeNotEquals(double a, double b, double c, double eps) {
        return !equals(a, b, eps) && !equals(a, c, eps) && !equals(b, c, eps);
    }
}
