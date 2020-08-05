//
// Created by vlad on 01.03.2020.
//

#include <iostream>
#include <cstdio>
#include <vector>

using namespace std;


int r, n, m;
vector<vector<int>> tree(800000);
vector<int> e{1, 0, 0, 1};

FILE *in = fopen("crypto.in", "r");
FILE *out = fopen("crypto.out", "w");

vector<int> matrixProduce(vector<int> a,  vector<int> b) {
    vector<int> newMatrix(4);
    newMatrix[0] = (a[0] * b[0] + a[1] * b[2]) % r;
    newMatrix[1] = (a[0] * b[1] + a[1] * b[3]) % r;
    newMatrix[2] = (a[2] * b[0] + a[3] * b[2]) % r;
    newMatrix[3] = (a[2] * b[1] + a[3] * b[3]) % r;
    return newMatrix;
}

void buildTree(int v, int l, int r) {
    if (l == r)
        for(int i = 0; i < 4; i++) {
            int element;
            fscanf(in, "%i", &element);
            tree[v].push_back(element);
        }
    else {
        int m = (l + r) / 2;
        buildTree(v * 2, l, m);
        buildTree(v * 2 + 1, m + 1, r);
        tree[v] = matrixProduce(tree[v * 2], tree[v * 2 + 1]);
    }
}

vector<int> produce(int v, int l, int r, int a, int b) {
    if (l > b or r < a)
        return e;
    if (l >= a and r <= b)
        return tree[v];
    int m = (l + r) / 2;
    return matrixProduce(produce(v * 2, l, m, a, b), produce(v * 2 + 1, m + 1, r, a, b));
}


int main() {
    fscanf(in, "%i %i %i", &r, &n, &m);
    buildTree(1, 0, n - 1);
    for (int i = 0; i < m; i++) {
        int x, y;
        vector<int> res(4);
        fscanf(in, "%i %i", &x, &y);
        res = produce(1, 0, n - 1, x - 1, y - 1);
        fprintf(out, "%i %i\n", res[0], res[1]);
        fprintf(out, "%i %i\n\n", res[2], res[3]);
    }
    fclose(in);
    fclose(out);
    return 0;
}