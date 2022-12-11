import { OperationFunction, operations, parenthesis, Priority, TokenName } from "./util";

export interface TokenVisitor {
    visit(token: NumberToken): void;

    visit(token: Parenthesis): void;

    visit(token: Operation): void;

    visit(token: EndToken): void;
}

export interface Token {
    accept(visitor: TokenVisitor): void;
}

export class NumberToken implements Token {
    name = TokenName.NUM;
    value: number;

    constructor(value: string | number) {
        this.value = Number(value);
    }

    accept(visitor: TokenVisitor) {
        visitor.visit(this);
    }
}

export class Parenthesis implements Token {
    name: string;

    constructor(paren: string) {
        this.name = parenthesis[paren];
    }

    accept(visitor: TokenVisitor) {
        visitor.visit(this);
    }
}

export class Operation implements Token {
    name: string;
    priority: Priority;
    value: OperationFunction;

    constructor(operation: string) {
        const { name, priority, value } = operations[operation];
        this.name = name;
        this.priority = priority;
        this.value = value;
    }

    accept(visitor: TokenVisitor) {
        visitor.visit(this);
    }
}

export class EndToken implements Token {
    name = TokenName.END;

    accept(visitor: TokenVisitor): void {
        visitor.visit(this);
    }
}