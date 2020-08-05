package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.generic.Parser;
import expression.generic.TripleExpression;
import expression.generic.operations.Operation;

public class ExpressionParser<T> implements Parser<T> {

    private final char END = '\0';
    private String expression;
    private int pos;
    private String currentOp = "None";
    private Operation<T> type;

    public ExpressionParser(Operation<T> type) {
        this.type = type;
    }

    public TripleExpression<T> parse(String source) throws ExpressionException {
        pos = 0;
        this.expression = source + END;
        TripleExpression<T> result = parseAddOrSub();
        if (!checkChar(END)) {
            throw new BadSymbol(expression + " (Expected END, found \"" + getChar(pos) + "\")", pos);
        }
        return result;
    }

    private TripleExpression<T> parseAddOrSub() throws ExpressionException {
        TripleExpression<T> leftOperand = parseMulOrDiv();
        for (;;) {
            skipWhiteSpace();
            switch (currentOp) {
                case "+":
                    leftOperand =  new Add<>(leftOperand, parseMulOrDiv(), type);
                    break;
                case "-":
                    leftOperand = new Subtract<>(leftOperand, parseMulOrDiv(), type);
                    break;
                default:
                    return leftOperand;
            }
        }
    }

    private TripleExpression<T> parseMulOrDiv() throws ExpressionException {
        TripleExpression<T> leftOperand = parseValue();
        for (;;) {
            skipWhiteSpace();
            currentOp = operation();
            switch (currentOp) {
                case "*":
                    leftOperand =  new Multiply<>(leftOperand, parseValue(), type);
                    break;
                case "/":
                    leftOperand =  new Divide<>(leftOperand, parseValue(), type);
                    break;
                default:
                    return leftOperand;
            }
        }
    }

    private TripleExpression<T> parseValue() throws ExpressionException {
        currentOp = "None";
        skipWhiteSpace();
        if (Character.isDigit(getChar(pos)) || getChar(pos) == '-' && Character.isDigit(getChar(pos + 1))) {
            StringBuilder parseValue = new StringBuilder();
            do {
                parseValue.append(getChar(pos));
            } while (Character.isDigit(nextChar()));
            try {
                return new Const<>(type.parseNum(parseValue.toString()));
            } catch (NumberFormatException e) {
                pos -= parseValue.length();
                throw new NumberFormat(expression + " (Wrong number format)", pos);
            }
        }
        if (getChar(pos) == '-') {
            pos++;
            return new Minus<>(parseValue(), type);
        }
        if (checkChar('(')) {
            pos++;
            TripleExpression<T> nextOne = parseAddOrSub();
            skipWhiteSpace();
            if (checkChar(')')) {
                pos++;
                return nextOne;
            }
            pos++;
            throw new LessBrackets(expression + " (Less parenthesis)", pos);
        }
        if (!checkChar(END)) {
            String val = opOrVal();
            if (val.equals("x") || val.equals("y") || val.equals("z")) {
                return new Variable<>(val);
            }
            //PREVIOUS OPERATIONS ONLY FOR INTEGERS:
            /*if ((val.equals("log") || val.equals("pow")) && getChar(pos++) == '2' &&
                    (checkChar(' ') || checkChar('-') || checkChar('('))) {
                skipWhiteSpace();
                int begin = pos;
                TripleExpression<T> underLogOrPow = parseValue();
                try {
                    if (val.equals("log"))
                        return new Log2<>(underLogOrPow, type);
                    return new Pow2<>(underLogOrPow, type);
                } catch (NumberFormatException e) {
                    pos = begin;
                    throw new NumberFormat(expression + " (Wrong number format)", pos);
                }
            }
            if ((val.equals("count") && (checkChar(' ') || checkChar('-') || checkChar('(')))) {
                return new Count<>(parseValue(), type);
            }*/
            pos -= val.length();
            throw new IllegalExpression(expression + " (Expected value, found \"" + val + "\")", pos);
        }
        throw new BadSymbol(expression + " (Expected value, found \"" + getChar(pos) + "\")", pos);
    }


    private String operation() throws ExpressionException {
        skipWhiteSpace();
        String op = opOrVal();
        if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("min") || op.equals("max")) {
            return op;
        }
        pos -= op.length();
        if (checkChar(')')) {
            return "None";
        }
        if (!checkChar(END)) {
            throw new BadSymbol(expression + " (Expected value, found \"" + getChar(pos) + "\")", pos);
        }
        return "None";
    }

    private String opOrVal() {
        StringBuilder strBuild = new StringBuilder();
        if (!checkChar(END)) {
            strBuild.append(getChar(pos));
            if (Character.isLetter(getChar(pos)))
                while (Character.isLetter(nextChar())) {
                    strBuild.append(getChar(pos));
                }
            else
                pos++;
        }
        return strBuild.toString();
    }
    

    private char getChar(int pos) {
        if (pos >= expression.length()) {
            return END;
        }
        return expression.charAt(pos);
    }

    private char nextChar() {
        if (pos >= expression.length() - 1) {
            return END;
        }
        return expression.charAt(++pos);
    }

    private boolean checkChar(char ch) {
        return (getChar(pos) == ch);
    }

    private void skipWhiteSpace() {
        while (Character.isWhitespace(expression.charAt(pos))) {
            pos++;
        }
    }
}