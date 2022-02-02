package solutions;

import static java.lang.Math.abs;

public interface MinimizationMethod {
    double findMin(double a, double b, final double eps);

    default boolean equals(double a, double b, double eps) {
        return abs(a - b) < eps;
    }

    default boolean threeNotEquals(double a, double b, double c, double eps) {
        return !equals(a, b, eps) && !equals(a, c, eps) && !equals(b, c, eps);
    }
}
