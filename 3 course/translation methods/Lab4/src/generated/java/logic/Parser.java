package logic;

public record Parser(Lexer lexer) {
    public Parser(final Lexer lexer) {
        this.lexer = lexer;
        lexer.nextToken();
    }

    public Node term_(String acc) {
        final Node res = new Node("term_");
        switch (lexer.getToken().token()) {
            case AND -> {
                if (lexer.getToken().token() != Token.Type.AND) {
                    throw new RuntimeException("Invalid token");
                }
                String AND0 = lexer.getToken().stringify();
                res.addChild(AND0);
                lexer.nextToken();
                
                final Node factor1 = factor();
                res.addChild(factor1);
                res.val = acc + " && " + factor1.val;
                final Node term_2 = term_(res.val);
                res.addChild(term_2);
                res.val = term_2.val;
            }
            case OR, XOR, END, CLOSE -> {
                res.addChild("eps");
                res.val = acc;
            }
            default -> throw new RuntimeException("Invalid token");
        }

        return res;
    }

    public Node term() {
        final Node res = new Node("term");
        switch (lexer.getToken().token()) {
            case NOT, VAR, OPEN -> {
                final Node factor0 = factor();
                res.addChild(factor0);
                
                final Node term_1 = term_(factor0.val);
                res.addChild(term_1);
                res.val = term_1.val;
            }
            default -> throw new RuntimeException("Invalid token");
        }

        return res;
    }

    public Node factor() {
        final Node res = new Node("factor");
        switch (lexer.getToken().token()) {
            case VAR -> {
                if (lexer.getToken().token() != Token.Type.VAR) {
                    throw new RuntimeException("Invalid token");
                }
                String VAR0 = lexer.getToken().stringify();
                res.addChild(VAR0);
                lexer.nextToken();
                res.val = VAR0;
            }
            case OPEN -> {
                if (lexer.getToken().token() != Token.Type.OPEN) {
                    throw new RuntimeException("Invalid token");
                }
                String OPEN0 = lexer.getToken().stringify();
                res.addChild(OPEN0);
                lexer.nextToken();
                
                final Node expr1 = expr();
                res.addChild(expr1);
                
                if (lexer.getToken().token() != Token.Type.CLOSE) {
                    throw new RuntimeException("Invalid token");
                }
                String CLOSE2 = lexer.getToken().stringify();
                res.addChild(CLOSE2);
                lexer.nextToken();
                res.val = "(" + expr1.val + ")";
            }
            case NOT -> {
                if (lexer.getToken().token() != Token.Type.NOT) {
                    throw new RuntimeException("Invalid token");
                }
                String NOT0 = lexer.getToken().stringify();
                res.addChild(NOT0);
                lexer.nextToken();
                
                final Node factor1 = factor();
                res.addChild(factor1);
                res.val = "!" + factor1.val;
            }
            default -> throw new RuntimeException("Invalid token");
        }

        return res;
    }

    public Node expr_(String acc) {
        final Node res = new Node("expr_");
        switch (lexer.getToken().token()) {
            case OR -> {
                if (lexer.getToken().token() != Token.Type.OR) {
                    throw new RuntimeException("Invalid token");
                }
                String OR0 = lexer.getToken().stringify();
                res.addChild(OR0);
                lexer.nextToken();
                
                final Node term1 = term();
                res.addChild(term1);
                res.val = acc + " || " + term1.val;
                final Node expr_2 = expr_(res.val);
                res.addChild(expr_2);
                res.val = expr_2.val;
            }
            case XOR -> {
                if (lexer.getToken().token() != Token.Type.XOR) {
                    throw new RuntimeException("Invalid token");
                }
                String XOR0 = lexer.getToken().stringify();
                res.addChild(XOR0);
                lexer.nextToken();
                
                final Node term1 = term();
                res.addChild(term1);
                res.val = acc + " ^ " + term1.val;
                final Node expr_2 = expr_(res.val);
                res.addChild(expr_2);
                res.val = expr_2.val;
            }
            case END, CLOSE -> {
                res.addChild("eps");
                res.val = acc;
            }
            default -> throw new RuntimeException("Invalid token");
        }

        return res;
    }

    public Node expr() {
        final Node res = new Node("expr");
        switch (lexer.getToken().token()) {
            case NOT, VAR, OPEN -> {
                final Node term0 = term();
                res.addChild(term0);
                
                final Node expr_1 = expr_(term0.val);
                res.addChild(expr_1);
                res.val = expr_1.val;
            }
            default -> throw new RuntimeException("Invalid token");
        }

        return res;
    }


    public static class Node extends Tree {
        public String val;

        public Node(String node) {
            super(node);
        }
    }
}