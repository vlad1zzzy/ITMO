grammar MyGrammar;

run returns[MyGrammar grammar] @init {$grammar = new MyGrammar();} : grammarName[$grammar] fields[$grammar]? grammarRule[$grammar]* EOF;

grammarName[MyGrammar grammar]: GRAMMAR name SEMI { $grammar.setName($name.text); };

fields[MyGrammar grammar]: FIELDS attr SEMI { $grammar.setFields($attr.text); };

grammarRule[MyGrammar grammar]: terminal[$grammar] | nonTerminal[$grammar];

terminal[MyGrammar grammar]: name COLON primitive SEMI { $grammar.addTerminal($name.text, $primitive.text); };

nonTerminal[MyGrammar grammar]: name inheritedAttr RETURNS
    rulE[$grammar, new MyGrammar.NonTerm($name.text, $inheritedAttr.text)]
        (OR rulE[$grammar, new MyGrammar.NonTerm($name.text, $inheritedAttr.text)])* SEMI { $grammar.setStart($name.text); };

rulE[MyGrammar grammar, MyGrammar.NonTerm nonTerm] @init { List<MyGrammar.Triple> triples = new ArrayList<>(); }:
    triple[triples] { $grammar.addRule($nonTerm, new MyGrammar.Rule(triples)); }
    | EPS code? { $grammar.addRule($nonTerm, new MyGrammar.Rule(List.of(new MyGrammar.Triple("", "", $code.text)))); }
    ;

triple[List<MyGrammar.Triple> triples]:
    name inheritedAttr? code? { $triples.add(new MyGrammar.Triple($name.text, $inheritedAttr.text, $code.text)); }
        (THEN name inheritedAttr? code? { $triples.add(new MyGrammar.Triple($name.text, $inheritedAttr.text, $code.text)); })*;

name: NAME;
primitive: REGEXP;
code: CODE;
attr: ATTR;
inheritedAttr: HERIT_ATTR;

GRAMMAR: 'grammar';
FIELDS: 'fields' ;

NAME: [a-zA-Z]+[a-zA-Z0-9_]*;
REGEXP: '"'.*?'"';

OR: '|';
COLON: ':';
SEMI: ';' ;
RETURNS: '=>';
THEN: '->';

OPEN_BRACE: '{';
CLOSE_BRACE: '}';
OPEN_BRACKET: '[';
CLOSE_BRACKET: ']';
OPEN_PAREN: '(';
CLOSE_PAREN: ')';

CODE: OPEN_BRACE .*? CLOSE_BRACE;
ATTR: OPEN_BRACKET .*? CLOSE_BRACKET;
HERIT_ATTR: OPEN_PAREN .*? CLOSE_PAREN;

EPS: 'ε';
WS: [ \t\r\n]+ -> skip;
