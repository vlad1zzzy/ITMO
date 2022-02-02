public class StepGradient {
    public static Answer gradient(double[] xk, QuadraticFunction function, MinimisationMethod method, double epsilon, int a, int b) {
        int k = 0;
        double lambda = function.findMin(method, xk, a, b, epsilon);
        double[] x;
        do {
            x = xk;
            xk = function.reducedAddVectors(x, function.findGradient(x, -1), lambda);
            System.out.println(xk[0] + " " +  xk[1]);
            while (function.findFx(xk) > function.findFx(x)) {
                if (k > 10000) {
                    return new Answer(x, function.findFx(x), k);
                }
                lambda /= 2;
                xk = function.reducedAddVectors(x, function.findGradient(x, -1), lambda);
                k++;
            }
            k++;
        } while (function.dfNormalize(xk) > epsilon && k < 10000);

        return new Answer(xk, function.findFx(xk), k);
    }
}
