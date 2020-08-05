package expression.exceptions;

public class BadSymbol extends ExpressionException {
    public BadSymbol(String message, int pos) {
        super(message + "\n" + " ".repeat(pos + 42) + "^");
    }
}
