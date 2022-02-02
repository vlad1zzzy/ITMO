package solutions;

public class Stat {
    private final int k;
    private  final double a;
    private  final double b;
    private  final double diff;
    private  final double x1;
    private  final double x2;
    private  final double f1;
    private  final double f2;

    public Stat(int k, double a, double b, double diff, double x1, double x2, double f1, double f2) {
        this.k = k;
        this.a = a;
        this.b = b;
        this.diff = diff;
        this.x1 = x1;
        this.x2 = x2;
        this.f1 = f1;
        this.f2 = f2;
    }

    public int getK() {
        return k;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getDiff() {
        return diff;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getF1() {
        return f1;
    }

    public double getF2() {
        return f2;
    }
}
