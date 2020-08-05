package expression.exceptions;

public class DivideByZero extends RuntimeException {
    public DivideByZero(String expression) {
        super("Divide by zero in expression: " + expression);
    }
}
