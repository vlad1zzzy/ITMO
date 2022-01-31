import logic.Lexer;
import logic.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ParserLogicTest {
    public static Stream<Map.Entry<String, String>> source() {
        return new HashMap<String, String>() {{
            put("a", "a");
            put("a   and b  ", "a && b");
            put("a or b    and c", "a || b && c");
            put("a xor (a and b  )", "a ^ (a && b)");
            put("     not a or    c", "!a || c");
            put(" not  not a and b or c", "!!a && b || c");
            put("a or  not   a", "a || !a");
            put("b or  not c and  c and a", "b || !c && c && a");
        }}.entrySet().stream();
    }

    @ParameterizedTest(name = "Test :: {0}")
    @MethodSource("source")
    public void test(Map.Entry<String, String> test) {
        Assertions.assertEquals(new Parser(new Lexer(test.getKey())).expr().val, test.getValue());
    }
}
