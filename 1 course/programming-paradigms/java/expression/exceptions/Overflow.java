package expression.exceptions;

public class Overflow extends ExpressionException {
    public Overflow(String expression) {
        super("Overflow error in expression: " + expression);
    }
}
