import Tree from "./Tree";

export const enum Token {
    VAR, AND= 'and', OR = 'or', XOR = 'xor', NOT = 'not', LPAREN = '(', RPAREN = ')', END = '$'
}

export type Result = Tree | "";

export type ParseException = typeof Error;

export const blanks: string[] = [' ', '\r', '\n', '\t'];

export const operations: Record<string, Token> = {
    'and': Token.AND,
    'or': Token.OR,
    'xor': Token.XOR,
    'not': Token.NOT
}