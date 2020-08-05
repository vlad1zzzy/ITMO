//
// Created by vlad on 06.03.2020.
//

#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int n, m, minimum, position;
const int N = 1e6;
int INF = 1e8;
int tree[4 * N], setTree[4 * N];


void set(int v, int x) {
    if (tree[v] < x) {
        setTree[v] = x;
        tree[v] = x;
    }
}

void push(int v) {
    set(v * 2, setTree[v]);
    set(v * 2 + 1, setTree[v]);
    setTree[v] = 0;
}

void getMinimumElement(int v, int l, int r) {
    if (l != r) {
        int m = (l + r) / 2;
        if (tree[v * 2] <= tree[v * 2 + 1])
            getMinimumElement(v * 2, l, m);
        else
            getMinimumElement(v * 2 + 1, m + 1, r);
    } else {
        position = l + 1;
    }
}

int getMin(int v, int l, int r, int a, int b) {
    if (l > b or r < a)
        return INF;
    if (l != r)
        push(v);
    if (l >= a and r <= b) {
        if (tree[v] < minimum) {
            minimum = tree[v];
            getMinimumElement(v, l, r);
        }
        return tree[v];
    }
    int m = (l + r) / 2;
    return min(getMin(v * 2, l, m, a, b),
               getMin(v * 2 + 1, m + 1, r, a, b));
}


void update(int v, int l, int r, int a, int b, int x) {
    if (l > b or r < a)
        return;
    if (l != r)
        push(v);
    if (l >= a and r <= b) {
        set(v, x);
        return;
    }
    int m = (l + r) / 2;
    update(v * 2, l, m, a, b, x);
    update(v * 2 + 1, m + 1, r, a, b, x);
    tree[v] = min(tree[v * 2], tree[v * 2 + 1]);
}


int main() {
    string command;
    cin >> n >> m;
    for (int i = 0; i < m; i++) {
        int a, b, c;
        cin >> command;
        minimum = INF;
        if (command == "defend") {
            cin >> a >> b >> c;
            update(1, 0, n - 1, a - 1, b - 1, c);
        } else {
            cin >> a >> b;
            getMin(1, 0, n - 1, a - 1, b - 1);
            cout << minimum << " " << position << "\n";
        }
    }
    return 0;
}