//
// Created by vlad on 24.04.2020.
//
#include <iostream>
#include <vector>
#include <string>

using namespace std;

struct Node {
    int key, size;
    long long priority;
    Node *left, *right;
};

Node *creatNode(int key) {
    Node *newRoot = new Node;
    newRoot->priority = rand();
    newRoot->size = 1;
    newRoot->key = key;
    newRoot->left = newRoot->right = nullptr;
    return newRoot;
}

int sizeOfSubTree(Node *root) {
    return root == nullptr ? 0 : root->size;
}

void upd_size(Node *root) {
    if (root != nullptr)
        root->size = sizeOfSubTree(root->left) + sizeOfSubTree(root->right) + 1;
}

pair<Node *, Node *> split(Node *root, int k) {
    if (root == nullptr)
        return {nullptr, nullptr};
    int l = sizeOfSubTree(root->left);
    if (k > l) {
        pair<Node *, Node *> splited = split(root->right, k - l - 1);
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

Node *insert(Node *root, int pos, int k) {
    Node *newNode = creatNode(k);
    pair<Node *, Node *> splited = split(root, pos);
    return merge(merge(splited.first, newNode), splited.second);
}

Node *remove(Node *t, int pos) {
    pair<Node *, Node *> splited1 = split(t, pos);
    pair<Node *, Node *> splited2 = split(splited1.second, 1);
    return merge(splited1.first, splited2.second);
}

void f(Node *&tree) {
    if (tree != nullptr) {
        f(tree->left);
        printf("%i ", tree->key);
        f(tree->right);
    }
}

int main() {
    Node *tree = nullptr;
    int n, m, i, x;
    char cmd[4];
    cin >> n >> m;
    for (int j = 0; j < n; j++) {
        cin >> x;
        tree = merge(tree, creatNode(x));
    }
    for (int j = 0; j < m; j++) {
        cin >> cmd >> i;
        if (cmd[0] == 'a') {
            cin >> x;
            tree = insert(tree, i, x);
        } else {
            tree = remove(tree, i - 1);
        }
    }
    cout << sizeOfSubTree(tree) << endl;
    f(tree);
    return 0;
}
