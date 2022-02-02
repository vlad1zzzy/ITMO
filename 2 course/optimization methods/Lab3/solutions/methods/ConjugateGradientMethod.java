package methods;

import java.util.Arrays;
import static methods.MatrixUtil.*;

public class ConjugateGradientMethod {
    private static final double EPS = 1e-7;

    public ConjugateAnswer solve(SparseMatrix A, double[] B) {
        //инициализируем начальные значения
        int N = A.getD().length, k = 1;
        double[] x = Arrays.stream(new double[N]).map(i -> 1).toArray();
        double[] r = subtract(B, A.getAx(x));
        double[] z = r;
        double alpha, betta;
        double[] xk, rk, zk;
        while (true) {
            //считаем по формулам
            alpha = scalar(r, r) / scalar(A.getAx(z), z);
            xk = add(x, multiply(z, alpha));
            rk = subtract(r, multiply(A.getAx(z), alpha));
            betta = scalar(rk, rk) / scalar(r, r);
            zk = add(rk, multiply(z, betta));

            //проверяем условия выхода
            if (module(rk) / module(B) < EPS || k > 300) {
                break;
            }
            //переходим на новую итерацию
            x = xk; r = rk; z = zk; k++;
        }
        return new ConjugateAnswer(k, xk);
    }
}
