#include <iostream>
#include <fstream>
#include <vector>
#include <functional>
#include "Parser.cpp" // NOLINT

Parser parser;
string error;
vector<Expression *> past_lines;
unordered_map<string, int> proven;
unordered_map<string, vector<int>> implications;

void print_ax_sch(int line_id, int axiom_id, const string &line) {
    cout << "[" << line_id << ". " << "Ax. sch. " << axiom_id << "] " << line << "\n";
}

void print_ax_sch_9(int line_id, const string &line) {
    cout << "[" << line_id << ". " << "Ax. sch. A9] " << line << "\n";
}

void print_ax_a(int line_id, int axiom_id, const string &line) {
    cout << "[" << line_id << ". Ax. A" << axiom_id << "] " << line << "\n";
}

void print_mp(int line_id, int first, int second, const string &line) {
    cout << "[" << line_id << ". " << "M.P. " << first << ", " << second << "] " << line << "\n";
}

void print_intro(int line_id, int intro, const string &line, const string &c) {
    cout << "[" << line_id << ". " << c << "-intro " << intro << "] " << line << "\n";
}

void print_error(int line_id) {
    cout << "Expression " << line_id << (error.empty() ? " is not proved." : ": " + error);
}

bool equals(Expression *left, Expression *right) {
    return left->to_string() == right->to_string();
}

bool valid_operation(Operation *expr, const string &ch) {
    return expr and expr->operation == ch;
}

// A->(B->A)
bool is_axiom_1(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        Expression *A = binary->left; // A
        auto *right = dynamic_cast<BinaryOperation *>(binary->right); // B->A
        if (A and valid_operation(right, "->")) {
            return equals(A, right->right); // A == A
        }
    }
    return false;
}

// (A->B)->(A->B->C)->(A->C)
bool is_axiom_2(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *left = dynamic_cast<BinaryOperation *>(binary->left); // A->B
        auto *right = dynamic_cast<BinaryOperation *>(binary->right); // (A->B->C)->(A->C)
        if (valid_operation(left, "->") and valid_operation(right, "->")) {
            Expression *left_A = left->left; // A
            Expression *left_B = left->right; //B
            auto *right_left = dynamic_cast<BinaryOperation *>(right->left); // (A->B->C)
            auto *right_right = dynamic_cast<BinaryOperation *>(right->right); // (A->C)
            if (left_A and left_B and valid_operation(right_left, "->") and valid_operation(right_right, "->")) {
                Expression *right_left_A = right_left->left; // A
                auto *right_left_right = dynamic_cast<BinaryOperation *>(right_left->right); // B->C
                Expression *right_right_A = right_right->left; // A
                Expression *right_right_C = right_right->right; // C
                if (right_left_A and valid_operation(right_left_right, "->") and
                    right_right_A and right_right_C) {
                    return (
                            equals(left_A, right_left_A) and // A == A
                            equals(left_A, right_right_A) and // A == A
                            equals(left_B, right_left_right->left) and // B == B
                            equals(right_left_right->right, right_right_C) // C == C
                    );
                }
            }
        }
    }
    return false;
}

// A->B->A&B
bool is_axiom_3(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        Expression *A = binary->left; // A
        auto *right = dynamic_cast<BinaryOperation *>(binary->right); // B->A&B
        if (A and valid_operation(right, "->")) {
            Expression *right_B = right->left; // B
            auto *right_right = dynamic_cast<BinaryOperation *>(right->right); // A&B
            if (right_B and valid_operation(right_right, "&")) {
                Expression *right_right_A = right_right->left; // A
                Expression *right_right_B = right_right->right; // B
                if (right_right_A and right_right_B) {
                    return (
                            equals(A, right_right_A) and  // A == A
                            equals(right_B, right_right_B) // B == B
                    );
                }
            }
        }
    }
    return false;
}

// A&B->A
bool is_axiom_4(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *left = dynamic_cast<BinaryOperation *>(binary->left); // A&B
        Expression *A = binary->right; // A
        if (valid_operation(left, "&") and A) {
            return equals(left->left, A); // A == A
        }
    }
    return false;
}

// A&B->B
bool is_axiom_5(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *left = dynamic_cast<BinaryOperation *>(binary->left); // A&B
        Expression *B = binary->right; // B
        if (valid_operation(left, "&") and B) {
            return equals(left->right, B); // B == B
        }
    }
    return false;
}

// A->A|B
bool is_axiom_6(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        Expression *A = binary->left; // A
        auto *right = dynamic_cast<BinaryOperation *>(binary->right); // A|B
        if (A and valid_operation(right, "|")) {
            return equals(A, right->left); // A == A
        }
    }
    return false;
}

// B->A|B
bool is_axiom_7(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        Expression *B = binary->left; // B
        auto *right = dynamic_cast<BinaryOperation *>(binary->right); // A|B
        if (B and valid_operation(right, "|")) {
            return equals(B, right->right); // B == B
        }
    }
    return false;
}

// (A->C)->(B->C)->(A|B->C)
bool is_axiom_8(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *left = dynamic_cast<BinaryOperation *>(binary->left); // A->C
        auto *right = dynamic_cast<BinaryOperation *>(binary->right); // (B->C)->(A|B->C)
        if (valid_operation(left, "->") and valid_operation(right, "->")) {
            Expression *left_A = left->left; // A
            Expression *left_C = left->right; // C
            auto *right_left = dynamic_cast<BinaryOperation *>(right->left); // (B->C)
            auto *right_right = dynamic_cast<BinaryOperation *>(right->right); // (A|B->C)
            if (left_A and left_C and valid_operation(right_left, "->") and valid_operation(right_right, "->")) {
                Expression *right_left_B = right_left->left; // B
                Expression *right_left_C = right_left->right; // C
                auto *right_right_left = dynamic_cast<BinaryOperation *>(right_right->left); // A|B
                Expression *right_right_C = right_right->right; // C
                if (right_left_B and right_left_C and
                    valid_operation(right_right_left, "|") and right_right_C) {
                    return (
                            equals(left_A, right_right_left->left) and // A == A
                            equals(left_C, right_left_C) and // C == C
                            equals(left_C, right_right_C) and // C == C
                            equals(right_left_B, right_right_left->right) // B == B
                    );
                }
            }
        }
    }
    return false;
}

// (A->B)->(A->!B)->!A
bool is_axiom_9(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *left = dynamic_cast<BinaryOperation *>(binary->left); // A->B
        auto *right = dynamic_cast<BinaryOperation *>(binary->right); // (A->!B)->!A
        if (valid_operation(left, "->") and valid_operation(right, "->")) {
            Expression *left_A = left->left; // A
            Expression *left_B = left->right; // B
            auto *right_left = dynamic_cast<BinaryOperation *>(right->left); // A->!B
            auto *right_right = dynamic_cast<UnaryOperation *>(right->right); // !A
            if (left_A and left_B and valid_operation(right_left, "->") and valid_operation(right_right, "!")) {
                Expression *right_left_A = right_left->left; // A
                auto *right_left_right = dynamic_cast<UnaryOperation *>(right_left->right); // !B
                Expression *right_right_A = right_right->expression; // A
                if (right_left_A and valid_operation(right_left_right, "!") and right_right_A) {
                    Expression *right_left_right_B = right_left_right->expression; // B
                    return right_left_right_B and (
                            equals(left_A, right_left_A) and // A == A
                            equals(left_A, right_right_A) and // A == A
                            equals(left_B, right_left_right_B) // B == B
                    );
                }
            }
        }
    }
    return false;
}

// !!A->A
bool is_axiom_10(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *left = dynamic_cast<UnaryOperation *>(binary->left); // !!A
        Expression *A = binary->right; // A
        if (valid_operation(left, "!") and A) {
            auto *left_expr = dynamic_cast<UnaryOperation *>(left->expression); // !A
            if (valid_operation(left_expr, "!")) {
                Expression *left_expr_A = left_expr->expression; // A
                return left_expr_A and equals(left_expr_A, A); // A == A
            }
        }
    }
    return false;
}

// @x.(P)->(P[x:=a])
int is_axiom_11(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *left = dynamic_cast<Quantifier *>(binary->left); // @x.(P)
        Expression *right = binary->right, *substitution = nullptr; // P, a
        if (valid_operation(left, "@") and right) {
            if (left->expression->replace(right, new Variable(left->variable), substitution)) {
                if (substitution) {
                    unordered_set<string> vars;
                    substitution->get_vars(vars);
                    if (vars.empty() or right->is_free(vars)) {
                        return 1;
                    }
                    error = "variable " + left->variable + " is not free for term " +
                            substitution->to_string() + " in @-axiom.";
                    return 0;
                }
                return 1;
            }
        }
    }
    return -1;
}

// (P[x:=a])->?x.(P)
int is_axiom_12(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *right = dynamic_cast<Quantifier *>(binary->right); // ?x.(P)
        if (valid_operation(right, "?")) {
            Expression *left = binary->left, *substitution = nullptr; // P, a
            if (right->expression->replace(left, new Variable(right->variable), substitution)) {
                if (substitution) {
                    unordered_set<string> vars;
                    substitution->get_vars(vars);
                    if (vars.empty() or left->is_free(vars)) {
                        return 1;
                    }
                    error = "variable " + right->variable + " is not free for term " +
                            substitution->to_string() + " in ?-axiom.";
                    return 0;
                }
                return 1;
            }
        }
    }
    return -1;
}

// ((P)[x:=0])&(@x.((P)->(P)[x:=x'])))->(P)
bool is_sch_axiom_a9(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto left = dynamic_cast<BinaryOperation *>(binary->left); // ((P)[x:=0])&(@x.((P)->(P)[x:=x']))
        if (valid_operation(left, "&")) {
            Expression *left_left = left->left; // (P)[x:=0]
            auto *left_right = dynamic_cast<Quantifier *>(left->right); // @x.((P)->(P)[x:=x'])
            if (left_left and valid_operation(left_right, "@")) {
                Expression *right = binary->right, *zero = new Variable("0"); // P, 0
                auto *var = new Variable(left_right->variable); // x
                if (right and var and right->replace(left_left, var, zero)) {
                    auto *left_right_expr = dynamic_cast<BinaryOperation *>(left_right->expression); // (P)->(P)[x:=x']
                    if (valid_operation(left_right_expr, "->")) {
                        Expression *left_right_expr_left = left_right_expr->left; // P
                        if (equals(right, left_right_expr_left)) {
                            Expression *left_right_expr_right = left_right_expr->right; // (P)[x:=x']
                            Expression *x_inc = new UnaryOperation("'", var); // x'
                            return right->replace(
                                    left_right_expr_right, var, x_inc
                            );
                        }
                    }
                }
            }
        }
    }
    return false;
}

// if P->G then P->@x.G
int is_intro_all(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *right = dynamic_cast<Quantifier *>(binary->right); // @x.G
        if (valid_operation(right, "@")) {
            Expression *left = binary->left; // P
            string without_quantifier = (new BinaryOperation("->", left, right->expression))->to_string(); // P->G
            if (proven.count(without_quantifier)) {
                string x = right->variable;
                unordered_set<string> vars;
                vars.insert(x);
                if (left->is_free(vars)) {
                    error = "variable " + x + " occurs free in @-rule.";
                    return 0;
                }
                return proven[without_quantifier] + 1;
            }
        }
    }
    return -1;
}

// if P->G then ?x.P->G
int is_intro_exist(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *left = dynamic_cast<Quantifier *>(binary->left); // ?x.P
        if (valid_operation(left, "?")) {
            Expression *right = binary->right; // G
            string without_quantifier = (new BinaryOperation("->", left->expression, right))->to_string(); // P->G
            if (proven.count(without_quantifier)) {
                string x = left->variable;
                unordered_set<string> vars;
                vars.insert(x);
                if (right->is_free(vars)) {
                    error = "variable " + x + " occurs free in ?-rule.";
                    return 0;
                }
                return proven[without_quantifier] + 1;
            }
        }
    }
    return -1;
}

//if P and P->G then G
void mp(const string &line, int &mp_first, int &mp_second) {
    for (int binary_id : implications[line]) {
        auto binary = dynamic_cast<BinaryOperation *>(past_lines[binary_id]);
        if (proven.count(binary->left->to_string()) and proven.count(binary->to_string())) {
            mp_first = proven[binary->left->to_string()] + 1;
            mp_second = proven[binary->to_string()] + 1;
            return;
        }
    }
}

/*bool readline(string &line) {
    char c;
    string str;
    while(cin.peek() != '\n') {
        cin >> c;
        str += c;
    }
    cin.ignore();
    if (str.empty()) {
        return false;
    }
    line = str;
    return true;
}*/

string getExpressionToProve() {
    string line;
    getline(cin, line);
    //readline(line);
    string hypothesis, toProve;
    vector<Expression *> hypotheses;
    for (int i = 0; i < line.size(); i++) {
        if (line[i] == '|' and line[i + 1] == '-') {
            hypothesis = line.substr(0, i);
            toProve = line.substr(i + 2);
            break;
        }
    }
    hypothesis.append(",");
    for (int i = 0, last_pos = 0; i < hypothesis.size(); i++) {
        if (hypothesis[i] == ',') {
            hypotheses.push_back(parser.parse(hypothesis.substr(last_pos, i - last_pos)));
            last_pos = i + 1;
        }
    }
    return toProve;
}

/*
bool is_variable(char ch) {
    return 'a' <= ch && ch <= 'z';
};

bool is_formal_axiom(const string &axiom, const string &line) {
    if (axiom.size() != line.size()) return false;
    int pos = 0;
    char a = 0, b = 0, c = 0;
    while (pos < axiom.size() and pos < line.size()) {
        if (axiom[pos] == 'a') {
            if (is_variable(line[pos])) {
                if (!a) {
                    a = line[pos++];
                    continue;
                }
                if (a != line[pos]) return false;
            } else {
                return false;
            }
        }
        if (axiom[pos] == 'b') {
            if (is_variable(line[pos])) {
                if (!b) {
                    b = line[pos++];
                    continue;
                }
                if (b != line[pos]) return false;
            } else {
                return false;
            }
        }
        if (axiom[pos] == 'c') {
            if (is_variable(line[pos])) {
                if (!c) {
                    c = line[pos++];
                    continue;
                }
                if (c != line[pos]) return false;
            } else {
                return false;
            }
        }
        if (axiom[pos] != line[pos]) {
            return false;
        }
        pos++;
    }
    return true;
}
*/

int main() {
    iostream ::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    string line;
    Expression *toProveParsed = parser.parse(getExpressionToProve());
    vector<Expression *> formal_axioms;
    vector<function<bool(Expression *)>> is_axioms_1_10 = {
            is_axiom_1, is_axiom_2, is_axiom_3, is_axiom_4, is_axiom_5,
            is_axiom_6, is_axiom_7, is_axiom_8, is_axiom_9, is_axiom_10
    };
    vector<function<int(Expression *)>> is_axioms_11_12 = {is_axiom_11, is_axiom_12};
    vector<function<int(Expression *)>> is_intros = {is_intro_exist, is_intro_all};
    for (const string &str: {
            "a=b->a'=b'", "a=b->a=c->b=c", "a'=b'->a=b", "!a'=0", "a+b'=(a+b)'", "a+0=a", "a*0=0", "a*b'=a*b+a"
    }) {
        formal_axioms.push_back(parser.parse(str));
    }
    int line_id = 0, isAxiom, isIntro;
    cout << "|-" << toProveParsed->to_string() << "\n";
    //while (readline(line)) {
    while (getline(cin, line)) {
        if (line.empty()) {
            break;
        }
        Expression *parsed = parser.parse(line);
        line = parsed->to_string();
        for (int axiom_id = 0; axiom_id < 10; axiom_id++) {
            function<bool(Expression *)> is_axiom = is_axioms_1_10[axiom_id];
            if (is_axiom(parsed)) {
                print_ax_sch((proven[line] = line_id++) + 1, axiom_id + 1, line);
                goto success;
            }
        }
        for (int i = 0; i < 8; i++) {
            if (equals(formal_axioms[i], parsed)) {
                print_ax_a((proven[line] = line_id++) + 1, i + 1, line);
                goto success;
            }
        }
        if (implications.count(line)) {
            int mp_first = -1, mp_second = -1;
            mp(line, mp_first, mp_second);
            if (mp_first != -1) {
                print_mp((proven[line] = line_id++) + 1, mp_first, mp_second, line);
                goto success;
            }
        }
        if (is_sch_axiom_a9(parsed)) {
            print_ax_sch_9((proven[line] = line_id++) + 1, line);
            goto success;
        }
        for (int axiom_id = 0; axiom_id < 2; axiom_id++) {
            function<int(Expression *)> is_axiom = is_axioms_11_12[axiom_id];
            if ((isAxiom = is_axiom(parsed)) >= 0) {
                if (isAxiom == 1) {
                    print_ax_sch((proven[line] = line_id++) + 1, axiom_id + 11, line);
                    goto success;
                }
                goto error;
            }
        }
        for (int intro_id = 0; intro_id < 2; intro_id++) {
            function<int(Expression *)> is_intro = is_intros[intro_id];
            if ((isIntro = is_intro(parsed)) >= 0) {
                if (isIntro > 0) {
                    print_intro((proven[line] = line_id++) + 1, isIntro, line, intro_id == 0 ? "?" : "@");
                    goto success;
                }
                goto error;
            }
        }

        error:
        print_error(line_id + 1);
        return 0;

        success:
        past_lines.push_back(parsed);
        auto *expr = dynamic_cast<BinaryOperation *>(parsed);
        if (valid_operation(expr, "->")) {
            implications[expr->right->to_string()].push_back(line_id - 1);
        }
    }
    if (!equals(past_lines.back(), toProveParsed)) {
        cout << "The proof proves different expression.";
    }
    return 0;
}
