package expression.exceptions;

public class IllegalExpression extends ExpressionException {
    public IllegalExpression(String message, int pos) {
        super(message + "\n" + " ".repeat(pos + 50) + "^");
    }
}
