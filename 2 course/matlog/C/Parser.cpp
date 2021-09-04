#include <string>
#include "Expression.cpp"

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
    const static char IMPL_ARROW_START = '-';
    const static char IMPL_ARROW_END = '>';
    string source;
    int position;

    Expression *parse_impl() {
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
        return parse_binary(AND, &Parser::parse_unary);
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
        if (is_variable()) {
            string var;
            while (is_variable() || is_digit() || get_symbol() == APOSTROPHE) {
                var += string(1, get_symbol());
                next_char();
            }
            skip_ws();
            return new Variable(var);
        }
        if (get_symbol() == NOT) {
            next_char();
            skip_ws();
            return new UnaryOperation(string(1, NOT), parse_unary());
        }
        return nullptr;
    };

    bool is_variable() {
        return position < source.size() && ('A' <= get_symbol() && get_symbol() <= 'Z');
    };

    bool is_digit() {
        return position < source.size() && ('0' <= get_symbol() && get_symbol() <= '9');
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