#include <iostream>
#include <vector>
#include <string>

using namespace std;

struct Node {
    long long key;
    Node *left, *right;
};

Node* insertInTree(Node *&root, long long x) {
    if (root == nullptr) {
        root = new Node;
        root->key = x;
        root->left = root->right = nullptr;
    } else if (x < root->key)
        root->left = insertInTree(root->left, x);
    else if (x > root->key)
        root->right = insertInTree(root->right, x);
    return root;
}

Node* minimum(Node *&root) {
    return (root->left == nullptr ? root : minimum(root->left));
}

Node* deleteFromTree(Node *&root, long long x) {
    if (root == nullptr)
        return root;
    if (x < root->key)
        root->left = deleteFromTree(root->left, x);
    else if (x > root->key)
        root->right = deleteFromTree(root->right, x);
    else if (root->left != nullptr && root->right != nullptr) {
        root->key = minimum(root->right)->key;
        root->right = deleteFromTree(root->right, root->key);
    } else {
        if (root->left != nullptr)
            root = root->left;
        else if (root->right != nullptr)
            root = root->right;
        else
            root = nullptr;
    }
    return root;
}

string exist(Node *root, long long x) {
    if (root == nullptr)
        return "false";
    if (x == root->key)
        return "true";
    if (x < root->key)
        return exist(root->left, x);
    else
        return exist(root->right, x);
}

string next(Node *root, long long x) {
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

string prev(Node *root, long long x) {
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

/*void f(Node *&tree) {
    if (tree != nullptr) {
        f(tree->left);
        printf("%i ", tree->key);
        f(tree->right);
    }
}*/


int main() {
    Node *Tree = nullptr;
    char command[7];
    long long x;
    while (cin >> command) {
        cin >> x;
        if (command[0] == 'i')
            insertInTree(Tree, x);
        else if (command[0] == 'd')
            deleteFromTree(Tree, x);
        else if (command[0] == 'e')
            cout << exist(Tree, x) << endl;
        else if (command[0] == 'n')
            cout << next(Tree, x) << endl;
        else if (command[0] == 'p')
            cout << prev(Tree, x) << endl;
/*        f(Tree);
        printf("\n");*/
    }
    return 0;
}

