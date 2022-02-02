public class Test {


    public static void main(String[] args) {
        QuadraticFunctionDiagonalised functionDiagonalised = new QuadraticFunctionDiagonalised(
                new double[]{2, 2}, new double[]{2, 1}, 1
        );
        System.out.println(functionDiagonalised.findFdkX(1,new double[]{3, 2}));
    }
}
