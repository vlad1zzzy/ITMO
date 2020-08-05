package expression.exceptions;

public class LessBrackets extends ExpressionException {
    public LessBrackets(String message, int pos) {
        super(message + "\n" + " ".repeat(pos + 45) + "^");
    }
}
