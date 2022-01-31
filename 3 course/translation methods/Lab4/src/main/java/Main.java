import generators.LexerGenerator;
import generators.ParserGenerator;
import grammar.MyGrammar;
import grammar.MyGrammarLexer;
import grammar.MyGrammarParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(final String[] args) throws IOException {
        generate("calculator");
        generate("logic");
        generate("nonll1");
    }

    private static void generate(final String name) throws IOException {
        final MyGrammar grammar = new MyGrammarParser(
                new CommonTokenStream(
                        new MyGrammarLexer(
                                CharStreams.fromString(
                                        Files.readString(
                                                Path.of("./src/main/grammars/" + name)
                                        )
                                )
                        )
                )
        )
                .run().grammar;

        new LexerGenerator(grammar).run();
        new ParserGenerator(grammar).run();
    }
}

