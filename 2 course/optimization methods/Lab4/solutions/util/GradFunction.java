package util;

public interface GradFunction {

    double[] findAp(double[] p);

    double findFx(double[] x);

    double findFdkX(int k, double[] x);

    double[] findGradient(double[] x, double k);

    double findMin(MinimisationMethod method, double[] x, double a, double b, double eps);

    double findMinP(double[] x, double[] p, double a, double c, double eps);

    int getDimension();

    double[][] getHessian(double[] x);
}
