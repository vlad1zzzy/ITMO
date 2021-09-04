#include <string>
#include <unordered_set>

using namespace std;

class Expression {
public:
    const string OPEN = "(";
    const string CLOSE = ")";
    const string NOT = "!";
    const string DOT = ".";
    const string ZERO = "0";
    const string COMMA = ",";

    virtual string to_string() = 0;

    virtual string to_prefix_string() = 0;

    virtual void get_vars(unordered_set<string> &) = 0;

    virtual bool is_free(unordered_set<string> &) = 0;

    virtual bool replace(Expression *, Expression *, Expression *&) = 0;
};

class Operation {
public:
    string operation;

    explicit Operation(string op) : operation(move(op)) {}
};

class UnaryOperation : public Operation, public Expression {
public:
    Expression *expression;

    UnaryOperation(const string &op, Expression *expr) : Operation(op), expression(expr) {}

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

    void get_vars(unordered_set<string> &vars) override {
        expression->get_vars(vars);
    };

    bool is_free(unordered_set<string> &vars) override {
        return expression->is_free(vars);
    };

    bool replace(Expression *expr, Expression *prev, Expression *&next) override {
        auto unary = dynamic_cast<UnaryOperation *>(expr);
        return unary and operation == unary->operation and expression->replace(unary->expression, prev, next);
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

    void get_vars(unordered_set<string> &vars) override {
        left->get_vars(vars);
        right->get_vars(vars);
    };

    bool is_free(unordered_set<string> &vars) override {
        return left->is_free(vars) or right->is_free(vars);
    };

    bool replace(Expression *expr, Expression *prev, Expression *&next) override {
        auto binary = dynamic_cast<BinaryOperation *>(expr);
        return binary and operation == binary->operation and
               left->replace(binary->left, prev, next) and
               right->replace(binary->right, prev, next);
    };
};

class Variable : public Expression {
public:
    string variable;

    explicit Variable(string var) : variable(move(var)) {}

    string to_string() override { return variable; };

    string to_prefix_string() override { return to_string(); };

    void get_vars(unordered_set<string> &vars) override { if (variable != ZERO) { vars.insert(variable); }};

    bool is_free(unordered_set<string> &vars) override {
        return vars.find(variable) != vars.end();
    };

    bool replace(Expression *expr, Expression *prev, Expression *&next) override {
        if (to_string() == prev->to_string()) {
            if (next) {
                return next->to_string() == expr->to_string();
            }
            return next = expr;
        }
        return to_string() == expr->to_string();
    };
};

class Quantifier : public Operation, public Variable {
public:
    Expression *expression;

    Quantifier(const string &op, Expression *expr, const string &var)
            : Operation(op), expression(expr), Variable(var) {}

    string to_string() override {
        return OPEN + operation + variable + DOT + expression->to_string() + CLOSE;
    };

    string to_prefix_string() override {
        return OPEN + operation + variable + DOT + expression->to_prefix_string() + CLOSE;
    };

    void get_vars(unordered_set<string> &vars) override {
        expression->get_vars(vars);
    };

    bool is_free(unordered_set<string> &vars) override {
        return vars.find(variable) == vars.end() and expression->is_free(vars);
    };

    bool replace(Expression *expr, Expression *prev, Expression *&next) override {
        auto quantifier = dynamic_cast<Quantifier * >(expr);
        return quantifier and operation == quantifier->operation and (
                to_string() == expr->to_string() or (
                        prev->to_string() != variable and
                        expression->replace(quantifier->expression, prev, next)
                )
        );
    };
};