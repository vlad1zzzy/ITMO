package calculator;

public record Parser(Lexer lexer) {
    public Parser(final Lexer lexer) {
        this.lexer = lexer;
        lexer.nextToken();
    }

    public Node expr_(int acc) {
        final Node res = new Node("expr_");
        switch (lexer.getToken().token()) {
            case PLUS -> {
                if (lexer.getToken().token() != Token.Type.PLUS) {
                    throw new RuntimeException("Invalid token");
                }
                String PLUS0 = lexer.getToken().stringify();
                res.addChild(PLUS0);
                lexer.nextToken();
                
                final Node term1 = term();
                res.addChild(term1);
                res.val = acc + term1.val;
                final Node expr_2 = expr_(res.val);
                res.addChild(expr_2);
                res.val = expr_2.val;
            }
            case MINUS -> {
                if (lexer.getToken().token() != Token.Type.MINUS) {
                    throw new RuntimeException("Invalid token");
                }
                String MINUS0 = lexer.getToken().stringify();
                res.addChild(MINUS0);
                lexer.nextToken();
                
                final Node term1 = term();
                res.addChild(term1);
                res.val = acc - term1.val;
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

    public Node term() {
        final Node res = new Node("term");
        switch (lexer.getToken().token()) {
            case NUM, OPEN, MINUS -> {
                final Node power0 = power();
                res.addChild(power0);
                
                final Node term_1 = term_(power0.val);
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
            case NUM -> {
                if (lexer.getToken().token() != Token.Type.NUM) {
                    throw new RuntimeException("Invalid token");
                }
                String NUM0 = lexer.getToken().stringify();
                res.addChild(NUM0);
                lexer.nextToken();
                res.val = Integer.parseInt(NUM0);
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
                res.val = expr1.val;
            }
            case MINUS -> {
                if (lexer.getToken().token() != Token.Type.MINUS) {
                    throw new RuntimeException("Invalid token");
                }
                String MINUS0 = lexer.getToken().stringify();
                res.addChild(MINUS0);
                lexer.nextToken();
                
                final Node factor1 = factor();
                res.addChild(factor1);
                res.val = (-1) * factor1.val;
            }
            default -> throw new RuntimeException("Invalid token");
        }

        return res;
    }

    public Node power_(int acc) {
        final Node res = new Node("power_");
        switch (lexer.getToken().token()) {
            case POW -> {
                if (lexer.getToken().token() != Token.Type.POW) {
                    throw new RuntimeException("Invalid token");
                }
                String POW0 = lexer.getToken().stringify();
                res.addChild(POW0);
                lexer.nextToken();
                
                final Node power1 = power();
                res.addChild(power1);
                res.val = (int) Math.pow(acc, power1.val);
            }
            case COMMA -> {
                if (lexer.getToken().token() != Token.Type.COMMA) {
                    throw new RuntimeException("Invalid token");
                }
                String COMMA0 = lexer.getToken().stringify();
                res.addChild(COMMA0);
                lexer.nextToken();
                
                final Node power1 = power();
                res.addChild(power1);
                
                 int k = acc - power1.val;
                 int akk1 = k = acc + 1 - power1.val, akk2 = 1;
                 k++;
                 for (int i = 2; i <= power1.val; i++, k++) akk1 = akk1 * k;
                 for (int i = 2; i <= power1.val; i++, k++) akk2 = akk2 * i;
                 res.val =  akk1 / akk2;
             
            }
            case DIV, MUL, END, CLOSE, PLUS, MINUS -> {
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
            case NUM, OPEN, MINUS -> {
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

    public Node term_(int acc) {
        final Node res = new Node("term_");
        switch (lexer.getToken().token()) {
            case MUL -> {
                if (lexer.getToken().token() != Token.Type.MUL) {
                    throw new RuntimeException("Invalid token");
                }
                String MUL0 = lexer.getToken().stringify();
                res.addChild(MUL0);
                lexer.nextToken();
                
                final Node power1 = power();
                res.addChild(power1);
                res.val = acc * power1.val;
                final Node term_2 = term_(res.val);
                res.addChild(term_2);
                res.val = term_2.val;
            }
            case DIV -> {
                if (lexer.getToken().token() != Token.Type.DIV) {
                    throw new RuntimeException("Invalid token");
                }
                String DIV0 = lexer.getToken().stringify();
                res.addChild(DIV0);
                lexer.nextToken();
                
                final Node power1 = power();
                res.addChild(power1);
                res.val = acc / power1.val;
                final Node term_2 = term_(res.val);
                res.addChild(term_2);
                res.val = term_2.val;
            }
            case END, CLOSE, PLUS, MINUS -> {
                res.addChild("eps");
                res.val = acc;
            }
            default -> throw new RuntimeException("Invalid token");
        }

        return res;
    }

    public Node power() {
        final Node res = new Node("power");
        switch (lexer.getToken().token()) {
            case NUM, OPEN, MINUS -> {
                final Node factor0 = factor();
                res.addChild(factor0);
                
                final Node power_1 = power_(factor0.val);
                res.addChild(power_1);
                res.val = power_1.val;
            }
            default -> throw new RuntimeException("Invalid token");
        }

        return res;
    }


    public static class Node extends Tree {
        public int val;

        public Node(String node) {
            super(node);
        }
    }
}