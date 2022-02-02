package solutions;

import java.util.List;

public class Lab1 {

    public static void main(String[] args) {
        List<Double> e = List.of(0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001, 0.0000001, 0.00000001);
        List<MinimizationMethod> methods = List.of(new Dichotomy(), new GoldenSection(), new Fibonacci(), new Parabola(), new Brent());
        e.forEach(eps -> {
            System.out.println(eps);
            methods.forEach(it -> solve(it, 0.5, 4, eps));
            System.out.println();
        });
    }

    public static void solve(MinimizationMethod method, double a, double b, double eps) {
        double min = method.findMin(a, b, eps);
        double value = func(min);
        printData(method.getClass().getSimpleName(), min, value);
    }

    public static void printData(final String methodName, final double min, final double value) {
        System.out.printf("method: %-15s min: %11.10f value: %11.10f delta: %11.10f%n", methodName, min, value, Math.abs(1 - min));
    }

    public static double func(double x) {
        return x - Math.log(x);
    }
}