import { ParseException } from "../exceptions";
import { EndToken, NumberToken, Operation, Parenthesis, Token,  TokenVisitor } from "../token";
import { TokenName } from "../util";

class ParserVisitor implements TokenVisitor {
    private tokens: Token[] = [];
    private stack: Token[] = [];

    visit(token: NumberToken): void;
    visit(token: Operation): void;
    visit(token: Parenthesis): void;
    visit(token: EndToken): void;
    visit(token: any): void {
        if (token instanceof NumberToken) {
            this.visitNumber(token);
        }

        if (token instanceof Parenthesis) {
            this.visitParenthesis(token);
        }

        if (token instanceof Operation) {
            this.visitOperation(token);
        }

        if (token instanceof EndToken) {
            this.visitEndToken();
        }
    }

    private visitNumber(token: NumberToken) {
        this.tokens.push(token);
    }

    private visitParenthesis(token: Parenthesis) {
        if (token.name == TokenName.LPAREN) {
            this.stack.push(token);
            return;
        }

        while (true) {
            const popStack = this.stack[this.stack.length - 1];
            if (popStack instanceof Parenthesis) {
                break;
            }

            this.stack.pop();
            this.tokens.push(popStack);
        }

        if (!(this.stack.pop())) {
            throw ParseException('Invalid expression');
        }
    }

    private visitOperation(token: Operation) {
        while (this.stack.length > 0) {
            const popStack = this.stack[this.stack.length - 1];
            if (!(popStack instanceof Operation) || popStack.priority > token.priority) {
                break;
            }
            this.tokens.push(popStack);
            this.stack.pop();
        }

        this.stack.push(token);
    }

    private visitEndToken() {
        this.tokens.push(...this.stack.reverse());
    }

    public getTokens() {
        return this.tokens;
    }
}

export default ParserVisitor;