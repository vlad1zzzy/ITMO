package nonll1;

public record Token(Type token, String stringify) {
    public enum Type {
        END("\\$"),
        OR("or");

        private final String regexp;

        Type(final String regexp) {
            this.regexp = regexp;
        }

        public boolean match(final String str) {
            return str.matches(regexp);
        }
    }
}