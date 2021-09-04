#include <iostream>
#include <vector>
#include <functional>
#include <map>
#include "Parser.cpp"

Parser parser;
vector<Expression *> past_lines, hypotheses;
unordered_map<string, int> proven;
unordered_map<string, vector<int>> implications;

void print_error(int line_id) {
    cout << "Proof is incorrect at line " << line_id + 1;
}

bool equals(Expression *left, Expression *right) {
    return left->to_string() == right->to_string();
}

bool valid_operation(Operation *expr, const string &ch) {
    return expr and expr->operation == ch;
}

bool print_all_hypos() {
    for (int i = 0; i < hypotheses.size() - 1; i++) {
        cout << hypotheses[i]->to_string() << ",";
    }
    if (!hypotheses.empty() and hypotheses[0]) {
        cout << hypotheses[hypotheses.size() - 1]->to_string();
        return true;
    }
    return false;
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

// (A->B)->(A->B->_|_)->(A->_|_)
bool is_axiom_9(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        auto *left = dynamic_cast<BinaryOperation *>(binary->left); // A->B
        auto *right = dynamic_cast<BinaryOperation *>(binary->right); // (A->B->_|_)->(A->_|_)
        if (valid_operation(left, "->") and valid_operation(right, "->")) {
            Expression *left_A = left->left; // A
            Expression *left_B = left->right; // B
            auto *right_left = dynamic_cast<BinaryOperation *>(right->left); // (A->B->_|_)
            auto *right_right = dynamic_cast<BinaryOperation *>(right->right); // (A->_|_)
            if (left_A and left_B and valid_operation(right_left, "->") and valid_operation(right_right, "->")) {
                Expression *right_left_A = right_left->left; // A
                auto *right_left_right = dynamic_cast<BinaryOperation *>(right_left->right); // B->_|_
                Expression *right_right_A = right_right->left; // A
                auto *right_right_not = dynamic_cast<Variable *>(right_right->right); // _|_
                if (right_left_A and right_right_A and right_right_not) {
                    if (valid_operation(right_left_right, "->") and right_right_not->variable == "_|_") {
                        Expression *right_left_right_B = right_left_right->left; // B
                        auto *right_left_right_not = dynamic_cast<Variable *>(right_left_right->right);
                        if (right_left_right_B and right_left_right_not and right_left_right_not->variable == "_|_") {
                            return (
                                    equals(left_A, right_left_A) and // A == A
                                    equals(left_A, right_right_A) and // A == A
                                    equals(left_B, right_left_right_B) // B == B
                            );
                        }
                    }
                }
            }
        }
    }
    return false;
}

// A->((A->_|_)->B)
bool is_axiom_10(Expression *expr) {
    auto *binary = dynamic_cast<BinaryOperation *>(expr);
    if (valid_operation(binary, "->")) {
        Expression *A = binary->left; // A
        auto *right = dynamic_cast<BinaryOperation *>(binary->right); // (A->_|_)->B
        if (A and valid_operation(right, "->")) {
            auto *right_left = dynamic_cast<BinaryOperation *>(right->left); // A->_|_
            Expression *B = right->right; // B
            if (valid_operation(right_left, "->") and B) {
                Expression *right_left_A = right_left->left; // A
                auto *right_left_not = dynamic_cast<Variable *>(right_left->right); // _|_
                if (right_left_A and right_left_not and right_left_not->variable == "_|_") {
                    return equals(A, right_left_A); // A == A
                }
            }
        }
    }
    return false;
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

auto impl_hypo(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << expr->to_string() << " [Ax]\n";
        };
    };
}

auto print_E(const string &line) {
    return [line](int ind) -> auto {
        return [line, ind]() -> void {
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << line << " [E->]\n";
        };
    };
}

auto impl_axiom_1(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            auto *binary = dynamic_cast<BinaryOperation *>(expr);
            auto *A = binary->left;
            auto *right = dynamic_cast<BinaryOperation *>(binary->right);
            auto *B = right->left;
            cout << "[" << ind + 2 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "," << B->to_string() << "|-" << A->to_string() << " [Ax]\n";
            cout << "[" << ind + 1 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "|-" << right->to_string() << " [I->]\n";
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << expr->to_string() << " [I->]\n";
        };
    };
}

void impl_axiom_2_hypos(Expression *left, Expression *right_left, Expression *A) {
    if (print_all_hypos()) cout << ",";
    cout << left->to_string() << "," << right_left->to_string() << "," << A->to_string();
}

auto impl_axiom_2(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            auto *binary = dynamic_cast<BinaryOperation *>(expr);
            auto *left = dynamic_cast<BinaryOperation *>(binary->left);
            auto *right = dynamic_cast<BinaryOperation *>(binary->right);
            auto *A = left->left;
            auto *B = left->right;
            auto *right_left = dynamic_cast<BinaryOperation *>(right->left);
            auto *right_right = dynamic_cast<BinaryOperation *>(right->right);
            auto *C = right_right->right;
            auto *right_left_right = dynamic_cast<BinaryOperation *>(right_left->right);
            cout << "[" << ind + 5 << "] ";
            impl_axiom_2_hypos(left, right_left, A);
            cout << "|-" << right_left->to_string() << " [Ax]\n";
            cout << "[" << ind + 5 << "] ";
            impl_axiom_2_hypos(left, right_left, A);
            cout << "|-" << A->to_string() << " [Ax]\n";
            cout << "[" << ind + 4 << "] ";
            impl_axiom_2_hypos(left, right_left, A);
            cout << "|-" << right_left_right->to_string() << " [E->]\n";
            cout << "[" << ind + 5 << "] ";
            impl_axiom_2_hypos(left, right_left, A);
            cout << "|-" << left->to_string() << " [Ax]\n";
            cout << "[" << ind + 5 << "] ";
            impl_axiom_2_hypos(left, right_left, A);
            cout << "|-" << A->to_string() << " [Ax]\n";
            cout << "[" << ind + 4 << "] ";
            impl_axiom_2_hypos(left, right_left, A);
            cout << "|-" << B->to_string() << " [E->]\n";
            cout << "[" << ind + 3 << "] ";
            impl_axiom_2_hypos(left, right_left, A);
            cout << "|-" << C->to_string() << " [E->]\n";
            cout << "[" << ind + 2 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << left->to_string() << "," << right_left->to_string() << "|-" << right_right->to_string() << " [I->]\n";
            cout << "[" << ind + 1 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << left->to_string() << "|-" << right->to_string() << " [I->]\n";
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << expr->to_string() << " [I->]\n";
        };
    };
}

auto impl_axiom_3(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            auto *binary = dynamic_cast<BinaryOperation *>(expr);
            auto *A = binary->left;
            auto *right = dynamic_cast<BinaryOperation *>(binary->right);
            auto *B = right->left;
            auto *right_right = dynamic_cast<BinaryOperation *>(right->right);
            cout << "[" << ind + 3 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "," << B->to_string() << "|-" << A->to_string() << " [Ax]\n";
            cout << "[" << ind + 3 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "," << B->to_string() << "|-" << B->to_string() << " [Ax]\n";
            cout << "[" << ind + 2 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "," << B->to_string() << "|-" << right_right->to_string() << " [I&]\n";
            cout << "[" << ind + 1 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "|-" << right->to_string() << " [I->]\n";
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << expr->to_string() << " [I->]\n";
        };
    };
}

auto impl_axiom_4(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            auto *binary = dynamic_cast<BinaryOperation *>(expr);
            auto *left = dynamic_cast<BinaryOperation *>(binary->left);
            auto *A = binary->right;
            cout << "[" << ind + 2 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << left->to_string() << "|-" << left->to_string() << " [Ax]\n";
            cout << "[" << ind + 1 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << left->to_string() << "|-" << A->to_string() << " [El&]\n";
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << expr->to_string() << " [I->]\n";
        };
    };
}

auto impl_axiom_5(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            auto *binary = dynamic_cast<BinaryOperation *>(expr);
            auto *left = dynamic_cast<BinaryOperation *>(binary->left);
            auto *B = left->right;
            cout << "[" << ind + 2 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << left->to_string() << "|-" << left->to_string() << " [Ax]\n";
            cout << "[" << ind + 1 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << left->to_string() << "|-" << B->to_string() << " [Er&]\n";
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << expr->to_string() << " [I->]\n";
        };
    };
}

auto impl_axiom_6(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            auto *binary = dynamic_cast<BinaryOperation *>(expr);
            auto *A = binary->left;
            auto *right = dynamic_cast<BinaryOperation *>(binary->right);
            cout << "[" << ind + 2 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "|-" << A->to_string() << " [Ax]\n";
            cout << "[" << ind + 1 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "|-" << right->to_string() << " [Il|]\n";
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << expr->to_string() << " [I->]\n";
        };
    };
}

auto impl_axiom_7(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            auto *binary = dynamic_cast<BinaryOperation *>(expr);
            auto *B = binary->left;
            auto *right = dynamic_cast<BinaryOperation *>(binary->right);
            cout << "[" << ind + 2 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << B->to_string() << "|-" << B->to_string() << " [Ax]\n";
            cout << "[" << ind + 1 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << B->to_string() << "|-" << right->to_string() << " [Ir|]\n";
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << expr->to_string() << " [I->]\n";
        };
    };
}

void impl_axiom_8_hypos(Expression *left, Expression *right_left, Expression *right_right_left) {
    if (print_all_hypos()) cout << ",";
    cout << left->to_string() << "," << right_left->to_string() << "," << right_right_left->to_string();
}

auto impl_axiom_8(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            auto *binary = dynamic_cast<BinaryOperation *>(expr);
            auto *left = dynamic_cast<BinaryOperation *>(binary->left);
            auto *A = left->left;
            auto *right = dynamic_cast<BinaryOperation *>(binary->right);
            auto *right_left = dynamic_cast<BinaryOperation *>(right->left);
            auto *right_right = dynamic_cast<BinaryOperation *>(right->right);
            auto *B = right_left->left;
            auto *C = right_left->right;
            auto *right_right_left = dynamic_cast<BinaryOperation *>(right_right->left);
            cout << "[" << ind + 5 << "] ";
            impl_axiom_8_hypos(left, right_left, right_right_left);
            cout << "," << A->to_string() << "|-" << left->to_string() << " [Ax]\n";
            cout << "[" << ind + 5 << "] ";
            impl_axiom_8_hypos(left, right_left, right_right_left);
            cout << "," << A->to_string() << "|-" << A->to_string() << " [Ax]\n";
            cout << "[" << ind + 4 << "] ";
            impl_axiom_8_hypos(left, right_left, right_right_left);
            cout << "," << A->to_string() << "|-" << C->to_string() << " [E->]\n";
            cout << "[" << ind + 5 << "] ";
            impl_axiom_8_hypos(left, right_left, right_right_left);
            cout << "," << B->to_string() << "|-" << right_left->to_string() << " [Ax]\n";
            cout << "[" << ind + 5 << "] ";
            impl_axiom_8_hypos(left, right_left, right_right_left);
            cout << "," << B->to_string() << "|-" << B->to_string() << " [Ax]\n";
            cout << "[" << ind + 4 << "] ";
            impl_axiom_8_hypos(left, right_left, right_right_left);
            cout << "," << B->to_string() << "|-" << C->to_string() << " [E->]\n";
            cout << "[" << ind + 4 << "] ";
            impl_axiom_8_hypos(left, right_left, right_right_left);
            cout << "|-" << right_right_left->to_string() << " [Ax]\n";
            cout << "[" << ind + 3 << "] ";
            impl_axiom_8_hypos(left, right_left, right_right_left);
            cout << "|-" << C->to_string() << " [E|]\n";
            cout << "[" << ind + 2 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << left->to_string() << "," << right_left->to_string() << "|-" << right_right->to_string() << " [I->]\n";
            cout << "[" << ind + 1 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << left->to_string() << "|-" << right->to_string() << " [I->]\n";
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << expr->to_string() << " [I->]\n";
        };
    };
}

void impl_axiom_9_hypos(Expression *left, Expression *right_left, Expression *A) {
    if (print_all_hypos()) cout << ",";
    cout << left->to_string() << "," << right_left->to_string() << "," << A->to_string();
}

auto impl_axiom_9(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            auto *binary = dynamic_cast<BinaryOperation *>(expr);
            auto *left = dynamic_cast<BinaryOperation *>(binary->left);
            auto *A = left->left;
            auto *B = left->right;
            auto *right = dynamic_cast<BinaryOperation *>(binary->right);
            auto *right_right = dynamic_cast<BinaryOperation *>(right->right);
            auto *right_left = dynamic_cast<BinaryOperation *>(right->left);
            auto *notB = dynamic_cast<BinaryOperation *>(right_left->right);
            cout << "[" << ind + 5 << "] ";
            impl_axiom_9_hypos(left, right_left, A);
            cout << "|-" << right_left->to_string() << " [Ax]\n";
            cout << "[" << ind + 5 << "] ";
            impl_axiom_9_hypos(left, right_left, A);
            cout << "|-" << A->to_string() << " [Ax]\n";
            cout << "[" << ind + 4 << "] ";
            impl_axiom_9_hypos(left, right_left, A);
            cout << "|-" << notB->to_string() << " [E->]\n";
            cout << "[" << ind + 5 << "] ";
            impl_axiom_9_hypos(left, right_left, A);
            cout << "|-" << left->to_string() << " [Ax]\n";
            cout << "[" << ind + 5 << "] ";
            impl_axiom_9_hypos(left, right_left, A);
            cout << "|-" << A->to_string() << " [Ax]\n";
            cout << "[" << ind + 4 << "] ";
            impl_axiom_9_hypos(left, right_left, A);
            cout << "|-" << B->to_string() << " [E->]\n";
            cout << "[" << ind + 3 << "] ";
            impl_axiom_9_hypos(left, right_left, A);
            cout << "|-_|_ [E->]\n";
            cout << "[" << ind + 2 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << left->to_string() << "," << right_left->to_string() << "|-" << right_right->to_string() << " [I->]\n";
            cout << "[" << ind + 1 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << left->to_string() << "|-" << right->to_string() << " [I->]\n";
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << binary->to_string() << " [I->]\n";
        };
    };
}

auto impl_axiom_10(Expression *expr) {
    return [expr](int ind) -> auto {
        return [expr, ind]() -> void {
            auto *binary = dynamic_cast<BinaryOperation *>(expr);
            auto *A = binary->left;
            auto *right = dynamic_cast<BinaryOperation *>(binary->right);
            auto *B = right->right;
            auto *right_left = dynamic_cast<BinaryOperation *>(right->left);
            cout << "[" << ind + 4 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "," << right_left->to_string() << "|-" << right_left->to_string() << " [Ax]\n";
            cout << "[" << ind + 4 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "," << right_left->to_string() << "|-" << A->to_string() << " [Ax]\n";
            cout << "[" << ind + 3 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "," << right_left->to_string() << "|-_|_ [E->]\n";
            cout << "[" << ind + 2 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "," << right_left->to_string() << "|-" << B->to_string() << " [E_|_]\n";
            cout << "[" << ind + 1 << "] ";
            if (print_all_hypos()) cout << ",";
            cout << A->to_string() << "|-" << right->to_string() << " [I->]\n";
            cout << "[" << ind << "] ";
            print_all_hypos();
            cout << "|-" << expr->to_string() << " [I->]\n";
        };
    };
}

string getExpressionToProve() {
    string line;
    getline(cin, line);
    string hypothesis, toProve;
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

class Printer {
public:
    bool isModusPonens;
    function<function<void()>(int)> print_yourself;
    Printer *left;
    Printer *right;

    Printer(bool isMP, function<function<void()>(int)> print) : isModusPonens(isMP), print_yourself(move(print)) {};

    Printer(bool isMP, function<function<void()>(int)> print, Printer *l, Printer *r) : isModusPonens(isMP),
                                                                                        print_yourself(move(print)),
                                                                                        left(l), right(r) {};

    void print(int ind) const {
        if (isModusPonens) {
            right->print(ind + 1);
            left->print(ind + 1);
        }
        print_yourself(ind)();
    }
};

int main() {
    iostream ::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    string line;
    Expression *toProveParsed = parser.parse(getExpressionToProve());
    vector<function<bool(Expression *)>> is_axioms_1_10 = {
            is_axiom_1, is_axiom_9, is_axiom_3, is_axiom_4, is_axiom_5,
            is_axiom_6, is_axiom_7, is_axiom_8, is_axiom_2, is_axiom_10
    };
    vector<function<function<function<void()>(int)>(Expression *)>> impls = {
            impl_axiom_1, impl_axiom_9, impl_axiom_3, impl_axiom_4, impl_axiom_5,
            impl_axiom_6, impl_axiom_7, impl_axiom_8, impl_axiom_2, impl_axiom_10,
    };
    int line_id = 0;
    vector<Printer *> printers;
    while (getline(cin, line)) {
        if (line.empty()) {
            break;
        }
        Expression *parsed = parser.parse(line);
        line = parsed->to_string();
        for (Expression *hypo: hypotheses) {
            if (hypo and equals(parsed, hypo)) {
                printers.emplace_back(new Printer(false, impl_hypo(parsed)));
                goto success;
            }
        }
        for (int axiom_id = 0; axiom_id < 10; axiom_id++) {
            function<bool(Expression *)> is_axiom = is_axioms_1_10[axiom_id];
            if (is_axiom(parsed)) {
                printers.emplace_back(new Printer(false, impls[axiom_id](parsed)));
                goto success;
            }
        }
        if (implications.count(line)) {
            int mp_first = -1, mp_second = -1;
            mp(line, mp_first, mp_second);
            if (mp_first != -1) {
                printers.emplace_back(
                        new Printer(true, print_E(line), printers[mp_first - 1], printers[mp_second - 1]));
                goto success;
            }
        }

        print_error(line_id + 1);
        return 0;

        success:
        proven[line] = line_id++;
        past_lines.push_back(parsed);
        auto *impl = dynamic_cast<BinaryOperation *>(parsed);
        if (valid_operation(impl, "->")) {
            implications[impl->right->to_string()].push_back(line_id - 1);
        }
    }
    if (past_lines.empty() || !equals(past_lines.back(), toProveParsed)) {
        cout << "The proof does not prove the required expression.";
        return 0;
    }
    printers[printers.size() - 1]->print(0);
    return 0;
}