import { NumberToken, Operation, Parenthesis, TokenVisitor } from "../token";

class PrintVisitor implements TokenVisitor {
    visit(token: NumberToken): void;
    visit(token: Operation): void;
    visit(token: Parenthesis): void;
    visit(token: any): void {
        if (token instanceof NumberToken) {
            PrintVisitor.print(`${token.name}(${token.value})`);
        }

        if (token instanceof Parenthesis || token instanceof Operation) {
            PrintVisitor.print(` ${token.name} `);
        }
    }

    private static print(value: string) {
        process.stdout.write(value);
    }
}

export default PrintVisitor;