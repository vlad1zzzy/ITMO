import { TokenizerException } from "./exceptions";
import { EndToken, NumberToken, Operation, Parenthesis, Token } from "./token";
import { blanks, operations } from "./util";

class Tokenizer {
    private readonly expression: string;
    private curPos: number = -1;
    private curChar!: string;
    private curToken!: Token;
    private lastNumber!: string;

    constructor(expression: string) {
        this.expression = expression + '$';
    }

    public getTokens(): Token[] {
        const tokens: Token[] = [];

        while (!(this.curToken instanceof EndToken)) {
            this.nextToken();
            tokens.push(this.curToken);
        }

        return tokens;
    }

    public nextToken(): void {
        this.nextChar();
        this.skipWS();
        switch (true) {
            case (this.isNumber() || this.isOperation()):
                this.curToken = this.getNumberOrOperation();
                return;
            case this.isParenthesis():
                this.curToken = this.getParenthesis();
                return;
            case this.isEnd():
                this.curToken = new EndToken();
                return;
            default:
                throw new TokenizerException(`Illegal char '${this.curChar}' at position ${this.curPos}`);
        }
    }

    public getPos(): number {
        return this.curPos;
    }

    private nextChar(): void {
        this.curChar = this.expression[++this.curPos];
    }

    private prevChar(): void {
        this.curChar = this.expression[--this.curPos];
    }

    private getNumberOrOperation(): Token {
        let token: string = '';
        let isNum = false;

        if (this.isUnary()) {
            token += this.curChar;
            this.nextChar();
        }

        while (this.isNumber()) {
            isNum = true;
            token += this.curChar;
            this.lastNumber = token;
            this.nextChar();
        }

        if (isNum) {
            this.prevChar();
            return new NumberToken(token);
        }

        if (this.isOperation()) {
            token += this.curChar;
            this.nextChar();
        }

        if (token.length === 1 && operations[token]) {
            this.prevChar();
            return new Operation(token);
        }

        throw new TokenizerException(`Illegal variable '${token}' at pos ${this.curPos}`);
    }

    private getParenthesis(): Token {
        return new Parenthesis(this.curChar);
    }

    private isMatch(regex: RegExp): boolean {
        return regex.test(this.curChar);
    }

    private isNumber(): boolean {
        return this.isMatch(/^\d+$/);
    }

    private isUnary(): boolean {
        return this.isMatch(/^\+|-$/);
    }

    private isOperation(): boolean {
        return this.isMatch(/^\*|\/|\+|-$/);
    }

    private isParenthesis(): boolean {
        return this.isMatch(/^\(|\)$/);
    }

    private isEnd(): boolean {
        return this.isMatch(/^\$$/);
    }

    private skipWS(): void {
        while (blanks.includes(this.curChar)) {
            this.nextChar();
        }
    }
}


export default Tokenizer;