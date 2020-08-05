//
// Created by vlad on 24.04.2020.
//

#include <iostream>
#include <vector>
#include <string>

using namespace std;

struct Node {
    int key, position;
    bool reversed;
    long long priority;
    Node *left, *right;
};

Node *creatNode(int key) {
    Node *newRoot = new Node;
    newRoot->priority = rand();
    newRoot->position = 1;
    newRoot->reversed = false;
    newRoot->key = key;
    newRoot->left = newRoot->right = nullptr;
    return newRoot;
}

int sizeOfSubTree(Node *root) {
    return root == nullptr ? 0 : root->position;
}

void upd_size(Node *root) {
    if (root != nullptr)
        root->position = 1 + sizeOfSubTree(root->left) + sizeOfSubTree(root->right);
}

void upd_reverse(Node *root) {
    if (root != nullptr && root->reversed) {
        root->reversed = false;
        swap(root->left, root->right);
        if (root->left != nullptr) root->left->reversed ^= true;
        if (root->right != nullptr) root->right->reversed ^= true;
    }
}

pair<Node *, Node *> split(Node *root, int k, int countOfPrevious) {
    if (root == nullptr)
        return {nullptr, nullptr};
    upd_reverse(root);
    if (k > countOfPrevious + sizeOfSubTree(root->left)) {
        pair<Node *, Node *> splited = split(root->right, k,  countOfPrevious + sizeOfSubTree(root->left) + 1);
        upd_size(splited.first);
        upd_size(splited.second);
        root->right = splited.first;
        upd_size(root);
        return {root, splited.second};
    } else {
        pair<Node *, Node *> splited = split(root->left, k, countOfPrevious);
        upd_size(splited.first);
        upd_size(splited.second);
        root->left = splited.second;
        upd_size(root);
        return {splited.first, root};
    }
}

Node *merge(Node *left, Node *right) {
    upd_reverse(left);
    upd_reverse(right);
    if (left == nullptr)
        return right;
    if (right == nullptr)
        return left;
    if (left->priority > right->priority) {
        left->right = merge(left->right, right);
        upd_size(left->right);
        upd_size(left);
        return left;
    } else {
        right->left = merge(left, right->left);
        upd_size(right->left);
        upd_size(right);
        return right;
    }
}


void f(Node *tree) {
    if (tree != nullptr) {
        upd_reverse(tree);
        f(tree->left);
        printf("%i ", tree->key);
        f(tree->right);
    }
}

int main() {
    Node *tree = nullptr;
    int n, m, l, r;
    cin >> n >> m;
    for (int i = 1; i <= n; i++) {
        tree = merge(tree, creatNode(i));
    }
    for (int i = 0; i < m; i++) {
        cin >> l >> r;
        pair <Node* , Node* > leftRight = split(tree, l - 1, 0);
        pair <Node* , Node* > midRight = split(leftRight.second, r - l + 1, 0);
        midRight.first->reversed ^= true;
        tree = merge(merge(leftRight.first, midRight.first), midRight.second);
    }
    f(tree);
    return 0;
}
