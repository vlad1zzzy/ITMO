package generators;

import grammar.MyGrammar;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static generators.Templates.*;
import static generators.Util.isTerm;

public class FirstFollow {
    private final String start;
    private final Map<MyGrammar.NonTerm, List<MyGrammar.Rule>> rules;
    private final Map<String, Set<String>> first = new HashMap<>();
    private final Map<String, Set<String>> follow = new HashMap<>();

    public FirstFollow(final MyGrammar grammar) {
        this.rules = grammar.getRules();
        this.start = grammar.getStart();
        makeFirst();
        makeFollow();
    }

    public boolean checkIsLL1() {
        final AtomicBoolean isLL1 = new AtomicBoolean(true);
        rules.forEach(((nonTerm, rules1) -> {
            for (int i = 1; i < rules1.size(); i++) {
                for (int j = 0; j < i; j++) {
                    var firstAlpha = getFirstForRules(rules1.get(i).getRuleNames());
                    var firstBetta = getFirstForRules(rules1.get(j).getRuleNames());
                    firstAlpha.forEach(s -> {
                        if (firstBetta.contains(s)) {
                            isLL1.set(false);
                        }
                    });

                    if (firstAlpha.contains(EPSILON)) {
                        var followA = getFollow().get(nonTerm.name());
                        followA.forEach(s -> {
                            if (firstBetta.contains(s)) {
                                isLL1.set(false);
                            }
                        });
                    }
                }
            }
        }));

        return isLL1.get();
    }

    public Map<String, Set<String>> getFollow() {
        return follow;
    }

    private void makeFirst() {
        final AtomicBoolean changed = new AtomicBoolean(true);
        while (changed.get()) {
            changed.set(false);
            rules.forEach(((nonTerminal, rules1) -> {
                final Set<String> firsts = first.computeIfAbsent(nonTerminal.name(), f -> new HashSet<>());
                final int curSize = firsts.size();
                rules1.forEach(rule -> firsts.addAll(getFirstForRules(rule.getRuleNames())));
                if (curSize < firsts.size()) {
                    changed.set(true);
                }
            }));
        }
    }

    private void makeFollow() {
        final AtomicBoolean change = new AtomicBoolean(true);
        follow.put(start, new HashSet<>(Set.of("END")));
        while (change.get()) {
            change.set(false);
            rules.forEach(((nonTerminal, rules1) -> {
                final String A = nonTerminal.name();
                rules1.forEach(rule -> {
                    final List<String> ruleNames = rule.getRuleNames();
                    for (int i = 0; i < ruleNames.size(); i++) {
                        final String B = ruleNames.get(i);
                        if (!(isTerm(B) || B.equals(EPSILON))) {
                            final Set<String> follows = follow.computeIfAbsent(B, f -> new HashSet<>());
                            final int curSize = follows.size();
                            final Set<String> gamma = getFirstForRules(ruleNames.subList(i + 1, ruleNames.size()));
                            if (gamma.contains(EPSILON)) {
                                follows.addAll(follow.computeIfAbsent(A, k -> new HashSet<>()));
                                gamma.remove(EPSILON);
                            }
                            follows.addAll(gamma);
                            if (curSize < follows.size()) {
                                change.set(true);
                            }
                        }
                    }
                });
            }));
        }
    }

    public Set<String> getFirstForRules(final List<String> ruleNames) {
        if (ruleNames.isEmpty()) {
            return new HashSet<>(Set.of(EPSILON));
        }

        final HashSet<String> res = new HashSet<>();
        for (final var a : ruleNames) {
            if (isTerm(a)) {
                res.add(a);
            } else if (first.containsKey(a)) {
                final Set<String> firstA = first.get(a);
                res.addAll(firstA);
                if (firstA.contains(EPSILON)) {
                    continue;
                }
            }
            break;
        }

        return res;
    }
}
