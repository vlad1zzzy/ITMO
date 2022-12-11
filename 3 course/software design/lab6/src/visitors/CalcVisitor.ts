import { CalcException } from "../exceptions";
import { NumberToken, Operation, Parenthesis, Token, TokenVisitor } from "../token";

class CalcVisitor implements TokenVisitor {
    private stack: Token[] = [];

    visit(token: NumberToken): void;
    visit(token: Operation): void;
    visit(token: Parenthesis): void;
    visit(token: any): void {
        if (token instanceof NumberToken) {
            this.visitNumber(token);
        }

        if (token instanceof Parenthesis) {
            CalcVisitor.visitParenthesis(token);
        }

        if (token instanceof Operation) {
            this.visitOperation(token);
        }
    }

    private visitNumber(token: NumberToken) {
        this.stack.push(token);
    }

    private static visitParenthesis(_: Parenthesis) {
        throw CalcException('Invalid expression');
    }

    private visitOperation(token: Operation) {
        if (this.stack.length < 1) {
            throw CalcException("Invalid expression");
        }

        if (this.stack.length === 1) {
            this.stack.unshift(new NumberToken(0));
        }

        const [b, a] = [this.stack.pop() as NumberToken, this.stack.pop() as NumberToken];

        this.stack.push(token.value(a, b));
    }

    public getAns() {
        return (this.stack.pop() as NumberToken).value;
    }
}

export default CalcVisitor;