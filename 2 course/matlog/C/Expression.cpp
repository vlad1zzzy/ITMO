#include <string>

using namespace std;

class Expression {
public:
    const string OPEN = "(";
    const string CLOSE = ")";
    const string NOT = "!";
    const string COMMA = ",";

    virtual string to_string() = 0;

    virtual string to_prefix_string() = 0;
};

class Operation {
public:
    string operation;

    explicit Operation(string op) : operation(move(op)) {}
};

class UnaryOperation : public Operation, public Expression {
public:
    Expression *expression;

    UnaryOperation(const string &op, Expression *expr) :
            Operation(op), expression(expr) {}

    string to_string() override {
        return operation == NOT ?
               OPEN + operation + expression->to_string() + CLOSE :
               expression->to_string() + operation;
    };

    string to_prefix_string() override {
        return operation == NOT ?
               OPEN + operation + expression->to_prefix_string() + CLOSE :
               expression->to_prefix_string() + operation;
    };
};

class BinaryOperation : public Operation, public Expression {
public:
    Expression *left, *right;

    BinaryOperation(const string &op, Expression *l, Expression *r) :
            Operation(op), left(l), right(r) {};

    string to_string() override {
        return OPEN + left->to_string() + operation + right->to_string() + CLOSE;
    };

    string to_prefix_string() override {
        return OPEN + operation + COMMA + left->to_prefix_string() + COMMA + right->to_prefix_string() + CLOSE;
    };
};

class Variable : public Expression {
public:
    string variable;

    explicit Variable(string var) : variable(move(var)) {}

    string to_string() override { return variable; };

    string to_prefix_string() override { return to_string(); };
};