#include <string>
#include "Expression.cpp" // NOLINT

using namespace std;

class Parser {
public:
    Expression *parse(string str) {
        source = move(str);
        position = 0;
        return parse_impl();
    };

private:
    const static char OPEN = '(';
    const static char NOT = '!';
    const static char APOSTROPHE = '\'';
    const static char AND = '&';
    const static char OR = '|';
    const static char ADD = '+';
    const static char MUL = '*';
    const static char EQ = '=';
    const static char EXISTS = '@';
    const static char ALL = '?';
    const static char IMPL_ARROW_START = '-';
    const static char IMPL_ARROW_END = '>';
    string source;
    int position;

    Expression *parse_impl() { // NOLINT
        skip_ws();
        Expression *binary = parse_or();
        skip_ws();
        while (get_symbol() == IMPL_ARROW_START && source[position + 1] == IMPL_ARROW_END) {
            next_char_doubled();
            skip_ws();
            binary = new BinaryOperation("->", binary, parse_impl());
            skip_ws();
        }
        return binary;
    };

    Expression *parse_binary(const char ch, Expression *(Parser::*func)()) {
        skip_ws();
        Expression *binary = (this->*func)();
        skip_ws();
        while (get_symbol() == ch) {
            next_char();
            skip_ws();
            binary = new BinaryOperation(string(1, ch), binary, (this->*func)());
            skip_ws();
        }
        return binary;
    };

    Expression *parse_or() {
        return parse_binary(OR, &Parser::parse_and);
    };

    Expression *parse_and() {
        return parse_binary(AND, &Parser::parse_eq);
    };

    Expression *parse_eq() {
        return parse_binary(EQ, &Parser::parse_add);
    };

    Expression *parse_add() {
        return parse_binary(ADD, &Parser::parse_mul);
    };

    Expression *parse_mul() {
        return parse_binary(MUL, &Parser::parse_inc);
    };

    Expression *parse_inc() {
        skip_ws();
        Expression *unary = parse_unary();
        skip_ws();
        while (get_symbol() == APOSTROPHE) {
            next_char();
            skip_ws();
            unary = new UnaryOperation(string(1, APOSTROPHE), unary);
            skip_ws();
        }
        return unary;
    };

    Expression *parse_unary() {
        if (get_symbol() == OPEN) {
            next_char();
            skip_ws();
            Expression *impl = parse_impl();
            next_char();
            skip_ws();
            return impl;
        }
        if (is_variable() || is_predicate()) {
            Expression *variable = new Variable(string(1, get_symbol()));
            next_char();
            skip_ws();
            return variable;
        }
        if (get_symbol() == NOT) {
            next_char();
            skip_ws();
            return new UnaryOperation(string(1, NOT), parse_eq());
        }
        if (get_symbol() == EXISTS || get_symbol() == ALL) {
            string operation = string(1, get_symbol());
            next_char();
            skip_ws();
            string variable = string(1, get_symbol());
            next_char_doubled();
            skip_ws();
            return new Quantifier(operation, parse_impl(), variable);
        }
        return nullptr;
    };

    bool is_variable() {
        return position < source.size() && ('a' <= get_symbol() && get_symbol() <= 'z' || get_symbol() == '0');
    };

    bool is_predicate() {
        return position < source.size() && ('A' <= get_symbol() && get_symbol() <= 'Z');
    };

    char get_symbol() {
        return source[position];
    };

    void next_char() {
        position++;
    }

    void next_char_doubled() {
        position += 2;
    }

    void skip_ws() {
        while (position < source.size() and get_symbol() == ' ') next_char();
    }
};