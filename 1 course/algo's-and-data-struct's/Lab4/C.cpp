//
// Created by vlad on 05.03.2020.
//

#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int n;
long long INF = 1e18;
long long tree[400000], addTree[400000], setTree[400000];
bool addLast[400000], setLast[400000];

void add(int v, long long x) {
    if (setLast[v]) {
        setTree[v] += x;
        addLast[v] = false;
    } else {
        addTree[v] += x;
        addLast[v] = true;
    }
    tree[v] += x;
}

void set(int v, long long x) {
    addTree[v] = 0;
    setTree[v] = x;
    tree[v] = x;
    addLast[v] = false;
    setLast[v] = true;
}

void buildTree(int v, int l, int r) {
    if (l == r) {
        cin >> tree[v];
    } else {
        int m = (l + r) / 2;
        buildTree(v * 2, l, m);
        buildTree(v * 2 + 1, m + 1, r);
        tree[v] = min(tree[v * 2], tree[v * 2 + 1]);
    }
}

void push(int v) {
    if (addLast[v]) {
        add(v * 2, addTree[v]);
        add(v * 2 + 1, addTree[v]);
        addTree[v] = 0;
        addLast[v] = false;
    }
    if (setLast[v]) {
        set(v * 2, setTree[v]);
        set(v * 2 + 1, setTree[v]);
        setTree[v] = 0;
        setLast[v] = false;
    }
}


long long minTree(int v, int l, int r, int a, int b) {
    if (l > b or r < a)
        return INF;
    if (l != r)
        push(v);
    if (l >= a and r <= b)
        return tree[v];
    int m = (l + r) / 2;
    return min(minTree(v * 2, l, m, a, b),
               minTree(v * 2 + 1, m + 1, r, a, b));
}


void updateAdd(int v, int l, int r, int a, int b, long long x) {
    if (l > b or r < a)
        return;
    if (l != r)
        push(v);
    if (l >= a and r <= b) {
        add(v, x);
        return;
    }
    int m = (l + r) / 2;
    updateAdd(v * 2, l, m, a, b, x);
    updateAdd(v * 2 + 1, m + 1, r, a, b, x);
    tree[v] = min(tree[v * 2], tree[v * 2 + 1]);
}

void updateSet(int v, int l, int r, int a, int b, long long x) {
    if (l > b or r < a)
        return;
    if (l != r)
        push(v);
    if (l >= a and r <= b) {
        set(v, x);
        return;
    }
    int m = (l + r) / 2;
    updateSet(v * 2, l, m, a, b, x);
    updateSet(v * 2 + 1, m + 1, r, a, b, x);
    tree[v] = min(tree[v * 2], tree[v * 2 + 1]);
}


int main() {
    string command;
    cin >> n;
    buildTree(1, 0, n - 1);
    while (cin >> command) {
        int x, y;
        long long z;
        if (command == "min") {
            cin >> x >> y;
            cout << minTree(1, 0, n - 1, x - 1, y - 1) << endl;
        } else if (command == "set") {
            cin >> x >> y >> z;
            updateSet(1, 0, n - 1, x - 1, y - 1, z);
        } else {
            cin >> x >> y >> z;
            updateAdd(1, 0, n - 1, x - 1, y - 1, z);
        }
    }
    return 0;
}