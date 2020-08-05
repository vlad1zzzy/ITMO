//
// Created by vlad on 23.04.2020.
//
#include <iostream>
#include <vector>
#include <string>

using namespace std;

struct Node {
    int key;
    long long priority, sum;
    Node *left, *right;
};

Node *creatNode(int key) {
    Node *newRoot = new Node;
    newRoot->priority = rand();
    newRoot->sum = key;
    newRoot->key = key;
    newRoot->left = newRoot->right = nullptr;
    return newRoot;
}

long long sizeOfSubTree(Node *root) {
    return root == nullptr ? 0 : root->sum;
}

void upd_size(Node *root) {
    if (root != nullptr)
        root->sum = root->key + sizeOfSubTree(root->left) + sizeOfSubTree(root->right);
}

pair<Node *, Node *> split(Node *root, int k) {
    if (root == nullptr) {
        return {nullptr, nullptr};
    } else if (k > root->key) {
        pair<Node *, Node *> splited = split(root->right, k);
        upd_size(splited.first);
        upd_size(splited.second);
        root->right = splited.first;
        upd_size(root);
        return {root, splited.second};
    } else {
        pair<Node *, Node *> splited = split(root->left, k);
        upd_size(splited.first);
        upd_size(splited.second);
        root->left = splited.second;
        upd_size(root);
        return {splited.first, root};
    }
}

Node *merge(Node *left, Node *right) {
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


bool exist(Node *root, int x) {
    if (root == nullptr)
        return false;
    if (x == root->key)
        return true;
    if (x < root->key)
        return exist(root->left, x);
    else
        return exist(root->right, x);
}

Node *insert(Node *root, int k) {
    if (exist(root, k))
        return root;
    Node *newNode = creatNode(k);
    pair<Node *, Node *> splited = split(root, k);
    return merge(merge(splited.first, newNode), splited.second);
}

long long sum(Node *&root, int l, int r) {
    pair<Node *, Node *> splited1 = split(root, l);
    pair<Node *, Node *> splited2 = split(splited1.second, r + 1);
    long long summa = splited2.first == nullptr ? 0 : splited2.first->sum;
    root = merge(merge(splited1.first, splited2.first), splited2.second);
    upd_size(root);
    return summa;
}

int main() {
    Node *tree = nullptr;
    int n, l, r, i;
    long long y;
    char cmd1 = '1', cmd2 = '+';
    cin >> n;
    for (int j = 0; j < n; j++) {
        cin >> cmd1;
        if (cmd1 == '+') {
            cin >> i;
            if (cmd2 == '+')
                tree = insert(tree, i);
            else
                tree = insert(tree, (i + y) % 1000000000);
            cmd2 = '+';
        } else {
            cin >> l >> r;
            y = sum(tree, l, r);
            cout << y << endl;
            cmd2 = '?';
        }
    }
    return 0;
}
