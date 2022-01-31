import calculator.Lexer;
import calculator.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ParserCalculatorTest {
    public static Stream<Map.Entry<String, Integer>> source() {
        return new HashMap<String, Integer>() {{
            put("0", 0);
            put(" - 1 ", -1);
            put("2 + 2", 4);
            put(" 2 * (2 + 2) ", 8);
            put("  (2 / (3 - 1)) / 4 ", 0);
            put("2 * 2 ** 3", 16);
            put("2 / (2 * 2 - 3)", 2);
            put("   2 + 2 - (  3  - 4  ) **   2 / 3  ", 4);
            put("2**3**2", 512);
            put("1,2", 0);
            put("4,2", 6);
            put("8,6", 28);
            put("8,4,2", 28);
        }}.entrySet().stream();
    }

    @ParameterizedTest(name = "Test :: {0}")
    @MethodSource("source")
    public void test(Map.Entry<String, Integer> test) {
        Assertions.assertEquals(new Parser(new Lexer(test.getKey())).expr().val, test.getValue());
    }
}
