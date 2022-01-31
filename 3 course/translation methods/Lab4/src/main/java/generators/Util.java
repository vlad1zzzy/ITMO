package generators;

public class Util {
    static String getInner(final String outer) {
        return outer == null ? "" : outer.substring(1, outer.length() - 1);
    }

    static Boolean isTerm(final String s) {
        return s.matches("[A-Z]+");
    }

    static void append(final StringBuilder sourceCode, final String template, final Object... items) {
        sourceCode.append(String.format(template, items).replaceAll("this", "res"));
    }
}
