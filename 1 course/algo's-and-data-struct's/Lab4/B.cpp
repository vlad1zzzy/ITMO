//
// Created by vlad on 01.03.2020.
//
#include <iostream>

using namespace std;


int n;
long long tree[2000000];

void buildTree(int v, int l, int r) {
    if (l == r)
        cin >> tree[v];
    else {
        int m = (l + r) / 2;
        buildTree(v * 2, l, m);
        buildTree(v * 2 + 1, m + 1, r);
        tree[v] = tree[v * 2] + tree[v * 2 + 1];
    }
}

long long sum(int v, int l, int r, int a, int b) {
    if (l > b or r < a)
        return 0;
    if (l >= a and r <= b)
        return tree[v];
    int m = (l + r) / 2;
    return sum(v * 2, l, m, a, b) + sum(v * 2 + 1, m + 1, r, a, b);
}



void set(int v, int l, int r, int i, long long x) {
    if (l == r)
        tree[v] = x;
    else {
        int m = (l + r) / 2;
        if (i <= m)
            set (v * 2, l, m, i, x);
        else
            set(v * 2 + 1, m + 1, r, i, x);
        tree[v] = tree[v * 2] + tree[v * 2 + 1];
    }
}


int main() {
    string command;
    cin >> n;
    buildTree(1, 0, n - 1);
    while (cin >> command)
    {
        int x, y;
        cin >> x >> y;
        if (command == "sum")
            cout << sum(1, 0, n - 1, x - 1, y - 1) << endl;
        else
            set(1, 0,n - 1, x - 1, y);
    }
    return 0;
}