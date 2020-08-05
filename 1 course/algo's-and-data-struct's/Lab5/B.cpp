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

string exist(Node *root, int x) {
    if (root == nullptr)
        return "false";
    if (x == root->key)
        return "true";
    if (x < root->key)
        return exist(root->left, x);
    else
        return exist(root->right, x);
}

string next(Node *root, int x) {
    Node *&current = root;
    string ans = "none";
    while (current != nullptr) {
        if (current->key > x) {
            ans = to_string(current->key);
            current = current->left;
        } else
            current = current->right;
    }
    return ans;
}

string prev(Node *root, int x) {
    Node *&current = root;
    string ans = "none";
    while (current != nullptr) {
        if (current->key < x) {
            ans = to_string(current->key);
            current = current->right;
        } else
            current = current->left;
    }
    return ans;
}

int main() {
    Node *tree = nullptr;
    char command[7];
    int x;
    while (cin >> command) {
        cin >> x;
        if (command[0] == 'i')
            tree = insert(tree, x);
        else if (command[0] == 'd')
            tree = remove(tree, x);
        else if (command[0] == 'e')
            cout << exist(tree, x) << endl;
        else if (command[0] == 'n')
            cout << next(tree, x) << endl;
        else if (command[0] == 'p')
            cout << prev(tree, x) << endl;
    }
    return 0;
}