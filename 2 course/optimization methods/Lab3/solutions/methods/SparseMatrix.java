package methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SparseMatrix {
    private final double[] d;
    private final double[] al;
    private final double[] au;
    private final int[] ia;
    private final int[] ja;

    public SparseMatrix(double[] d, double[] al, double[] au, int[] ia, int[] ja) {
        this.d = d;
        this.al = al;
        this.au = au;
        this.ia = ia;
        this.ja = ja;
    }

    public SparseMatrix(double[][] A) {
        this.d = new double[A.length];
        for (int i = 0; i < A.length; i++) {
            d[i] = A[i][i];
        }
        List<Double> tmpLU = new ArrayList<>();
        List<Integer> tmpI = new ArrayList<>();
        List<Integer> tmpJ = new ArrayList<>();
        tmpI.add(0); tmpI.add(0);
        for (int i = 1; i < A.length; i++) {
            int inRow = 0;
            for (int j = 1; j < i; j++) {
                if (Math.abs(A[i][j]) > 1e-5) {
                    tmpLU.add(A[i][j]);
                    inRow++;
                    tmpJ.add(j);
                }
            }
            tmpI.add(tmpI.get(tmpI.size() - 1) + inRow);
        }
        this.al = new double[tmpLU.size()];
        this.au = new double[tmpLU.size()];
        this.ia = new int[tmpI.size()];
        this.ja = new int[tmpJ.size()];
        for (int i = 0; i < tmpLU.size(); i++) {
            this.al[i] = this.au[i] = tmpLU.get(i);
        }
        for (int i = 0; i < tmpI.size(); i++) {
            this.ia[i] = tmpI.get(i);
        }
        for (int i = 0; i < tmpJ.size(); i++) {
            this.ja[i] = tmpJ.get(i);
        }
    }

    public double[] getAx(double[] x) {
        int N = x.length;
        double[] ans = new double[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ans[i] += get(i, j) * x[j];
            }
        }
        return ans;
    }

    public double get(int i, int j) {
        if (i == j) {
            return d[i];
        }
        if (j < i) {
            return getFromA(i, j, true);
        }
        return getFromA(j, i, false);
    }

    public double getFromA(int i, int j, boolean l) {
        List<Integer> columns = new ArrayList<>();
        for (int k = ia[i]; k < ia[i + 1]; k++) {
            columns.add(ja[k]);
        }
        if (columns.contains(j)) {
            int ind = columns.indexOf(j);
            columns.clear();
            if (l) {
                return al[ia[i] + ind];
            }
            return au[ia[i] + ind];
        } else {
            return 0;
        }
    }

    public double[][] convertToNormalMatrix() {
        int n = d.length;
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = this.get(i, j);
            }
        }
        return res;
    }

    public void setDi(int i, double val) {
        d[i] = val;
    }

    public double[] getD() {
        return d;
    }
}
