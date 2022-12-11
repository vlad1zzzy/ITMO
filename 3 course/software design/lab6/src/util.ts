import { NumberToken } from "./token";

export const enum TokenName {
    NUM = 'NUMBER',
    MUL = 'MUL',
    DIV = 'DIV',
    PLUS = 'PLUS',
    MINUS = 'MINUS',
    LPAREN = 'LPAREN',
    RPAREN = 'RPAREN',
    END = '$'
}

export type OperationTokenName = TokenName.MUL | TokenName.DIV | TokenName.PLUS | TokenName.MINUS;

export type ParenthesisTokenName = TokenName.LPAREN | TokenName.RPAREN;

export type OperationFunction = (a: NumberToken, b: NumberToken) => NumberToken;

export type OperationInfo = {
    name: OperationTokenName,
    priority: Priority,
    value: OperationFunction
}

export type Priority = 1 | 2;


export const operations: Record<string, OperationInfo> = {
    '*': { name: TokenName.MUL, priority: 1, value: (a, b) => new NumberToken(a.value * b.value) },
    '/': { name: TokenName.DIV, priority: 1, value: (a, b) => new NumberToken(a.value / b.value) },
    '+': { name: TokenName.PLUS, priority: 2, value: (a, b) => new NumberToken(a.value + b.value) },
    '-': { name: TokenName.MINUS, priority: 2, value: (a, b) => new NumberToken(a.value - b.value) },
};

export const parenthesis: Record<string, ParenthesisTokenName> = {
    '(': TokenName.LPAREN,
    ')': TokenName.RPAREN,
};

export const blanks: string[] = [' ', '\r', '\n', '\t'];
