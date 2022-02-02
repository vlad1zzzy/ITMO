package methods;

public class ConjugateAnswer {
    private final int k;
    private final double[] res;

    public ConjugateAnswer(int k, double[] res) {
        this.k = k;
        this.res = res;
    }

    public int getK() {
        return k;
    }

    public double[] getRes() {
        return res;
    }
}
