package generators;

public class Templates {
    static final String DIR = "./src/generated/java/";
    static final String EPSILON = "ε";
    static final String TAB = "    ";
    static final String SOURCE_PARSER = """
            package %s;
                         
            public record Parser(Lexer lexer) {
                public Parser(final Lexer lexer) {
                    this.lexer = lexer;
                    lexer.nextToken();
                }
                
            %s
                public static class Node extends Tree {
                    %s
                    
                    public Node(String node) {
                        super(node);
                    }
                }
            }""";
    static final String SOURCE_CASE = """
                        case %s -> {
            %s            }
            """;
    static final String SOURCE_CASE_EPS = """
                        case %s -> {
                            res.addChild("eps");
                            %s
                        }
            """;
    static final String SOURCE_TOKEN_CASE = """   
                            if (lexer.getToken().token() != Token.Type.%1$s) {
                                throw new RuntimeException("Invalid token");
                            }
                            String %1$s%2$d = lexer.getToken().stringify();
                            res.addChild(%1$s%2$d);
                            lexer.nextToken();
            """;
    static final String SOURCE_RULE_CASE = """
                            final Node %1$s%3$d = %1$s%2$s;
                            res.addChild(%1$s%3$d);
            """;
    static final String SOURCE_CODE = """
                            %s
            """;
    static final String SOURCE_SWITCH_CASE = """
                public Node %1$s%2$s {
                    final Node res = new Node("%1$s");
                    switch (lexer.getToken().token()) {
            %3$s            default -> throw new RuntimeException("Invalid token");
                    }
                    
                    return res;
                }
                
            """;
    static final String SOURCE_TOKEN_CLASS = """
            package %s;

            public record Token(Type token, String stringify) {
                public enum Type {
                    END("\\\\$"),
                    %s
                    private final String regexp;
                     
                    Type(final String regexp) {
                        this.regexp = regexp;
                    }
                     
                    public boolean match(final String str) {
                        return str.matches(regexp);
                    }
                }
            }""";
    static final String SOURCE_TREE_CLASS = """
            package %s;
                        
            import java.util.ArrayList;
            import java.util.List;
                        
            public class Tree {
                private final String node;
                private final List<Tree> children;
                        
                public Tree(final String node) {
                    this.node = node;
                    this.children = new ArrayList<>();
                }
                        
                public void addChild(final Tree node) {
                    children.add(node);
                }
                        
                public void addChild(final String node) {
                    children.add(new Tree(node));
                }
                        
                public String getNode() {
                    return node;
                }
                        
                public List<Tree> getChildren() {
                    return children;
                }
            }
            """;
    static final String SOURCE_LEXER_CLASS = """
            package %s;
               
            import java.util.Arrays;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
                            
            public class Lexer {
                private final Matcher matcher;
                private Token curToken;
                            
                public Lexer(final String expression) {
                    this.matcher = Pattern.compile(%s).matcher(expression);
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
            """;
}
