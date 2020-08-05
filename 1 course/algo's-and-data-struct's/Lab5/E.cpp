//
// Created by vlad on 23.04.2020.
//

//
// Created by vlad on 21.04.2020.
//

#include <iostream>
#include <vector>
#include <string>

using namespace std;

struct Node {
    int key, position;
    long long priority;
    Node *left, *right;
};

Node *creatNode(int key) {
    Node *newRoot = new Node;
    newRoot->priority = rand();
    newRoot->position = 1;
    newRoot->key = key;
    newRoot->left = newRoot->right = nullptr;
    return newRoot;
}

int sizeOfSubTree(Node *root) {
    return root == nullptr ? 0 : root->position;
}

void upd_size(Node *root) {
    if (root != nullptr)
        root->position = sizeOfSubTree(root->left) + sizeOfSubTree(root->right) + 1;
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
        return left;
    } else {
        right->left = merge(left, right->left);
        upd_size(right->left);
        return right;
    }
}

Node *insert(Node *root, int k) {
    Node *newNode = creatNode(k);
    pair<Node *, Node *> splited = split(root, k);
    return merge(merge(splited.first, newNode), splited.second);
}

Node *remove(Node *t, int x) {
    pair<Node *, Node *> splited1 = split(t, x);
    pair<Node *, Node *> splited2 = split(splited1.second, x + 1);
    return merge(splited1.first, splited2.second);
}

int kthMax(Node *root, int k) {
    int cnt = 0;
    if (root->right != nullptr)
        cnt = root->right->position;
    if (k == cnt + 1)
        return root->key;
    if (cnt < k)
        return kthMax(root->left, k - cnt - 1);
    else
        return kthMax(root->right, k);
}

int main() {
    Node *tree = nullptr;
    int n, c, k;
    cin >> n;
    for (int i = 0; i < n; i++) {
        cin >> c >> k;
        if (c > 0)
            tree = insert(tree, k);
        else if (c == 0)
            cout << kthMax(tree, k) << endl;
        else
            tree = remove(tree, k);
    }
    return 0;
}
