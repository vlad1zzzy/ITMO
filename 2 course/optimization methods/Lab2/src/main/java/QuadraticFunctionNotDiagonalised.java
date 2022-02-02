public class QuadraticFunctionNotDiagonalised extends AbstractQuadraticFunction {
    final private double[][] a;
    final private double[] b;
    final private double c;

    public QuadraticFunctionNotDiagonalised(double[][] a, double[] b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double findFdkX(int k, double[] x) {
        double ans = reducedMultiplyVectors(a[k], x);
        ans += a[k][k] * x[k];
        ans += b[k];
        return ans;
    }

    @Override
    public double[] findAp(double[] p) {
        double[] ans = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            ans[i] += a[i][i] * p[i];
            for (int j = 0; j < getDimension(); j++) {
                ans[i] += a[i][j] * p[j];
            }
        }
        return ans;
    }

    @Override
    public double findFx(double[] x) {
        double ans = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                ans += a[i][j] * x[i] * x[j];
            }
        }
        ans += reducedMultiplyVectors(b, x);
        ans += c;
        return ans;
    }

    @Override
    public int getDimension() {
        return a.length;
    }


}
