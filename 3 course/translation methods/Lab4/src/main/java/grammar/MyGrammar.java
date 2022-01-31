package grammar;

import java.util.*;
import java.util.stream.Collectors;

public class MyGrammar {
    private String name;
    private String fields;
    private String start = null;
    private final Map<NonTerm, List<Rule>> rules = new HashMap<>();
    private final List<Term> terminals = new ArrayList<>();

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFields(final String fields) {
        this.fields = fields;
    }

    public String getFields() {
        return fields;
    }

    public void setStart(final String start) {
        if (this.start == null) {
            this.start = start;
        }
    }

    public String getStart() {
        return start;
    }

    public Map<NonTerm, List<Rule>> getRules() {
        return rules;
    }

    public List<Term> getTerminals() {
        return terminals;
    }

    public void addRule(final NonTerm nonTerminal, final Rule rule) {
        rules.computeIfAbsent(nonTerminal, s -> new ArrayList<>()).add(rule);
    }

    public void addTerminal(final String name, final String primitive) {
        terminals.add(new Term(name, primitive));
    }

    public record Rule(List<Triple> triples) {
        public List<String> getRuleNames() {
            return triples.stream()
                    .map(Triple::first)
                    .filter(f -> !f.isEmpty())
                    .collect(Collectors.toList());
        }
    }

    public record Term(String first, String second) {
    }

    public record NonTerm(String name, String inherited) {
    }

    public record Triple(String first, String second, String third) {
    }
}
