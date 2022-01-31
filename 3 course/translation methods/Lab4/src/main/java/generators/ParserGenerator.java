package generators;

import grammar.MyGrammar;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static generators.Templates.*;
import static generators.Util.*;

public class ParserGenerator {
    private final MyGrammar grammar;
    private final FirstFollow firstFollow;
    private final String path;

    public ParserGenerator(final MyGrammar grammar) {
        this.grammar = grammar;
        this.path = DIR + grammar.getName();
        this.firstFollow = new FirstFollow(grammar);
    }

    public void run() {
        if (firstFollow.checkIsLL1()) {
            try {
                Files.createDirectories(Paths.get(path));
                generateClass(String.format(SOURCE_PARSER, grammar.getName(), generateRuleFunctions(), getNodeFields()));
            } catch (final IOException ignored) {
                System.err.println("Fail to create dir");
            }
        } else {
            System.out.println("Grammar: " + grammar.getName() + " is not LL(1)");
        }
    }

    private String generateRuleFunctions() {
        final StringBuilder sourceCode = new StringBuilder();
        grammar
                .getRules()
                .forEach((key, value) -> sourceCode.append(String.format(SOURCE_SWITCH_CASE, key.name(), key.inherited(), generateRuleFunction(key, value))));
        return sourceCode.toString();
    }

    private String generateRuleFunction(final MyGrammar.NonTerm key, final List<MyGrammar.Rule> rules) {
        final StringBuilder sourceCode = new StringBuilder();
        for (final var rule : rules) {
            final Set<String> firsts = firstFollow.getFirstForRules(rule.getRuleNames());
            if (firsts.contains(EPSILON)) {
                append(sourceCode, SOURCE_CASE_EPS, String.join(", ", firstFollow.getFollow().get(key.name())), getInner(rule.triples().get(0).third()));
                firsts.remove(EPSILON);
            }
            if (firsts.size() > 0) {
                append(sourceCode, SOURCE_CASE, String.join(", ", firsts), generateCase(rule.triples()));
            }
        }
        return sourceCode.toString();
    }

    private String generateCase(final List<MyGrammar.Triple> triples) {
        final StringBuilder sourceCode = new StringBuilder();
        int id = 0;
        for (final var triple : triples) {
            if (isTerm(triple.first())) {
                append(sourceCode, SOURCE_TOKEN_CASE, triple.first(), id++);
            } else {
                append(sourceCode, SOURCE_RULE_CASE, triple.first(), triple.second(), id++);
            }
            append(sourceCode, SOURCE_CODE, getInner(triple.third()));
        }
        return sourceCode.toString();
    }

    private String getNodeFields() {
        return Arrays
                .stream(getInner(grammar.getFields()).split(","))
                .map(attr -> "public " + attr.trim() + ";")
                .collect(Collectors.joining("\n" + TAB + TAB));
    }

    private void generateClass(final String source) {
        try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(path + "/Parser.java"))) {
            bufferedWriter.write(String.format(source, grammar.getName()));
        } catch (final IOException ignored) {
            System.err.println("Failed to create file");
        }
    }
}
