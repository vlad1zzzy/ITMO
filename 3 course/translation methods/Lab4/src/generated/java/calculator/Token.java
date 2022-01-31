package calculator;

public record Token(Type token, String stringify) {
    public enum Type {
        END("\\$"),
        POW("\\*\\*"),
        PLUS("\\+"),
        MINUS("-"),
        MUL("\\*"),
        DIV("/"),
        COMMA(","),
        NUM("[0-9]+"),
        OPEN("\\("),
        CLOSE("\\)");

        private final String regexp;

        Type(final String regexp) {
            this.regexp = regexp;
        }

        public boolean match(final String str) {
            return str.matches(regexp);
        }
    }
}