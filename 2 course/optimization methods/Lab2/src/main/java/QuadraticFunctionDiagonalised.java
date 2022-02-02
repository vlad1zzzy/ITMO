public class QuadraticFunctionDiagonalised extends AbstractQuadraticFunction {
    final private double[] a;
    final private double[] b;
    final private double c;

    public QuadraticFunctionDiagonalised(double[] a, double[] b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }


    @Override
    public double findFdkX(int k, double[] x) {
        return  a[k] * x[k] + b[k];
    }

    @Override
    public double[] findAp(double[] p) {
        double[] ans = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            ans[i] = a[i] * p[i] ;
        }
        return ans;
    }

    @Override
    public double findFx(double[] x) {
        double ans = 0;
        double[] x2 = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            x2[i] = x[i] * x[i];
        }
        ans += reducedMultiplyVectors(a, x2) / 2;
        ans += reducedMultiplyVectors(b, x);
        ans += c;
        return ans;
    }

    @Override
    public int getDimension() {
        return a.length;
    }

}
