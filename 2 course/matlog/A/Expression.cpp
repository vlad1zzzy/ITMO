#include <string>

using namespace std;

class Expression {
public:
    const string OPEN = "(";
    const string CLOSE = ")";
    const string COMMA = ",";

    virtual string to_string() = 0;

    virtual string to_prefix_string() = 0;
};

class Operation {
public:
    string operation;

    explicit Operation(string op) : operation(move(op)) {}
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