package nonll1;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final Matcher matcher;
    private Token curToken;

    public Lexer(final String expression) {
        this.matcher = Pattern.compile("or|[^\s\t\r\n]").matcher(expression);
    }

    public void nextToken() {
        if (matcher.find()) {
            final String strToken = matcher.group();
            curToken = new Token(
                    Arrays
                            .stream(Token.Type.values())
                            .filter(t -> t.match(strToken))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Invalid token")),
                    strToken);
        } else {
            curToken = new Token(Token.Type.END, "$");
        }
    }

    public Token getToken() {
        return curToken;
    }
}
