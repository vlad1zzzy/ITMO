//
// Created by vlad on 05.03.2020.
//

#include <iostream>

using namespace std;


int n;
const int N = 1e6;
long long tree[4*N], treeLeftColor[4*N], treeRightColor[4*N], length[4*N];
bool needToUpdNext[4*N];

void update(int v, int l, int r, int color) {
    tree[v] = treeLeftColor[v] = treeRightColor[v] = color;
    length[v] = (r - l + 1) * color;
    needToUpdNext[v] = (l != r);
}
void counter(int v, int l, int r, int a, int b, int color) {
    if (l > b or r < a)
        return;
    if (a <= l && r <= b) {
        update(v, l, r, color);
        return;
    }
    int m = (l + r) / 2;
    if (needToUpdNext[v]) {
        update(v * 2, l, m, tree[v] ? 1 : 0);
        update(v * 2 + 1, m + 1, r, tree[v] ? 1 : 0);
        needToUpdNext[v] = false;
    }
    counter(v * 2, l, m, a, b, color);
    counter(v * 2 + 1, m + 1, r, a, b, color);

    tree[v] = tree[v * 2] + tree[v * 2 + 1];
    if (treeRightColor[v * 2] && treeLeftColor[v * 2 + 1])
        tree[v] -= 1;
    treeLeftColor[v] = treeLeftColor[v * 2];
    treeRightColor[v] = treeRightColor[v * 2 + 1];
    length[v] = length[v * 2] + length[v * 2 + 1];
}


int main() {
    char color;
    cin >> n;
    for (int i = 0; i < n; i++) {
        int x, y;
        cin >> color >> x >> y;
        x += 500000;
        if (color == 'W')
            counter(1, 0, 1e6, x, x + y - 1, 0);
        else
            counter(1, 0, 1e6, x, x + y - 1, 1);
        cout << tree[1] << " " << length[1] << endl;
    }
    return 0;
}