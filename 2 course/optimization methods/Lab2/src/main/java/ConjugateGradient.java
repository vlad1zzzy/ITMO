public class ConjugateGradient {

    public static Answer gradient(double[] xk, QuadraticFunction function, MinimisationMethod method, double epsilon, int a, int b, int n) {
        int k = 0;
        double lambda, beta, ng1 = 10;
        double[] x, p = function.findGradient(xk, -1), g1, g;
        g1 = function.findGradient(xk, 1);
        do {
            k++;
            x = xk;
            g = g1;
            double[] apk = function.findAp(p);
            double f = function.reducedMultiplyVectors(apk, p);
            double ng = Math.sqrt(function.reducedMultiplyVectors(g, g));
            lambda = ng * ng / f;
            xk = function.reducedAddVectors(x, p, lambda);
            g1 = function.reducedAddVectors(g, apk, lambda);
            if (k % n == 0) {
                g1 = function.findGradient(xk, -1);
            } else {
                ng1 = Math.sqrt(function.reducedMultiplyVectors(g1, g1));
                beta = ng1 * ng1 / (ng * ng);
                p = function.reducedAddVectors(function.findGradient(xk, -1), p, beta);
            }
        } while (ng1 > epsilon && k < 10000);
        return new Answer(xk, function.findFx(xk), k);
    }
}
