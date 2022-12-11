import assert from "assert";
import CalcVisitor from "../visitors/CalcVisitor";
import ParserVisitor from "../visitors/ParserVisitor";
import PrintVisitor from "../visitors/PrintVisitor";
import Tokenizer from "../Tokenizer";
import { generateExpressions } from "./test-generator";

const EXPRESSIONS: string[] = generateExpressions();

describe('Tokenizer...', function () {
    EXPRESSIONS.forEach(expr => {
        test(`VALID: ${expr}`, function () {
            new Tokenizer(expr).getTokens();
        });
    });
});

describe('Printer...', function () {
    EXPRESSIONS.forEach(expr => {
        test(`Printer ${expr}`, function () {
            const tokenizer = new Tokenizer(expr);
            const printer = new PrintVisitor();

            const tokenizerTokens = tokenizer.getTokens();

            process.stdout.write(expr + ' --> ');
            process.stdout.write('{  ');
            tokenizerTokens.forEach(token => token.accept(printer));
            process.stdout.write('  }\n');
        });
    });
});


describe('Parser...', function () {
    EXPRESSIONS.forEach(expr => {
        test(`Parser ${expr}`, function () {
            const tokenizer = new Tokenizer(expr);
            const parser = new ParserVisitor();
            const printer = new PrintVisitor();

            const tokenizerTokens = tokenizer.getTokens();

            tokenizerTokens.forEach(token => token.accept(parser));

            const parserTokens = parser.getTokens();

            process.stdout.write(expr + ' --> ');
            process.stdout.write('{  ');
            parserTokens.forEach(token => token.accept(printer));
            process.stdout.write('  }\n');
        });
    });
});

describe('Calc...', function () {
    EXPRESSIONS.forEach(expr => {
        test(`Calc ${expr}`, function () {
            const tokenizer = new Tokenizer(expr);
            const parser = new ParserVisitor();
            const calc = new CalcVisitor();

            const tokenizerTokens = tokenizer.getTokens();

            tokenizerTokens.forEach(token => token.accept(parser));

            const parserTokens = parser.getTokens();

            parserTokens.forEach(token => token.accept(calc));

            assert.strictEqual(calc.getAns(), (Number(eval(expr))) || 0, expr);
        });
    });
});