package logic;

public record Token(Type token, String stringify) {
    public enum Type {
        END("\\$"),
        OR("or"),
        XOR("xor"),
        AND("and"),
        NOT("not"),
        VAR("[a-z]"),
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