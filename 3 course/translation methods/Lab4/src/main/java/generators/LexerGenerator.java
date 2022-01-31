package generators;

import grammar.MyGrammar;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static generators.Templates.*;

public class LexerGenerator {
    private static final String TAB = "    ";
    private final MyGrammar grammar;
    private final String path;

    public LexerGenerator(final MyGrammar grammar) {
        this.grammar = grammar;
        this.path = DIR + grammar.getName();
    }

    public void run() {
        try {
            Files.createDirectories(Paths.get(path));
            generateClass(
                    "/Token.java",
                    SOURCE_TOKEN_CLASS,
                    entry -> entry.first() + "(" + entry.second() + ")",
                    Collectors.joining(",\n" + TAB + TAB, "", ";\n"));
            generateClass(
                    "/Lexer.java",
                    SOURCE_LEXER_CLASS,
                    entry -> entry.second().substring(1, entry.second().length() - 1),
                    Collectors.joining("|", "\"", "|[^\\s\\t\\r\\n]\""));
            generateClass(
                    "/Tree.java",
                    SOURCE_TREE_CLASS,
                    String::valueOf,
                    Collectors.joining());
        } catch (final IOException ignored) {
            System.err.println("Fail to create dir");
        }
    }

    private void generateClass(
            final String file,
            final String template,
            final Function<MyGrammar.Term, String> mapper,
            final Collector<CharSequence, ?, String> collector) {
        try (final BufferedWriter writer = Files.newBufferedWriter(Path.of(path + file))) {
            writer.write(String.format(template, grammar.getName(), grammar
                    .getTerminals()
                    .stream()
                    .map(mapper)
                    .collect(collector)));
        } catch (final IOException ignored) {
            System.err.println("Failed to create file");
        }
    }
}
