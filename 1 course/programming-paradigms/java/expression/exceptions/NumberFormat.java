package expression.exceptions;

public class NumberFormat extends ExpressionException {
    public NumberFormat(String message, int pos) {
        super(message + "\n" + " ".repeat(pos + 50) + "^");
    }
}
