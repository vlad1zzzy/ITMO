public class GreatDescentGradient {

    public static Answer gradient(double[] xk, QuadraticFunction function, MinimisationMethod method, double epsilon, int a, int b) {
        int k = 0;
        double lambda, g;
        double[] x, p = function.findGradient(xk, -1);
        do {

            k++;
            x = xk;
            lambda = function.findMin(method, x, a, b, epsilon);
            xk = function.reducedAddVectors(x, p, lambda);
            g = function.dfNormalize(xk);
            p = function.findGradient(xk, -1);

        } while (g > epsilon && k < 10000);
        return new Answer(xk, function.findFx(xk), k);
    }
}
