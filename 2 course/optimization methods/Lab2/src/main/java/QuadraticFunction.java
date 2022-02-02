public interface QuadraticFunction {

    double[] findAp(double []p);

    double findFx(double[] x);

    double findFdkX(int k, double[] x);

    double[] findGradient(double[] x, double k);

    double dfNormalize(double[] x);

    double[] reducedAddVectors(double[] x1, double[] x2, double k2);

    double reducedMultiplyVectors(double[] x1, double[] x2);

    double findMin(MinimisationMethod method, double[] x, double a, double c, double eps);

    int getDimension();
}
