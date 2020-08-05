//
// Created by vlad on 18.05.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>

using namespace std;

const int INF = 1e9;

int dp[200002][18];
int minTree[200002][18];
int depth[200002];
vector<vector<int>> tree;
vector<int> p;

void updateDp(int x) {
    for (int i = 1; i < 18; i++){
        dp[x][i] = dp[dp[x][i - 1]][i - 1];
        minTree[x][i] = min(minTree[x][i - 1], minTree[dp[x][i - 1]][i - 1]);
    }
    for (int i : tree[x]) {
        depth[i] = depth[x] + 1;
        updateDp(i);
    }
}

int lca(int v, int u) {
    int length = INF;
    if (depth[v] > depth[u])
        swap(u, v);
    for (int i = 17; i >= 0; i--)
        if (depth[dp[u][i]] - depth[v] >= 0){
            length = min(length, minTree[u][i]);
            u = dp[u][i];
        }
    //cout << u << " " << v << endl;
    if (u == v)
        return length;
    for (int i = 17; i >= 0; i--)
        if (dp[v][i] != dp[u][i]) {
            length = min(length, minTree[v][i]);
            length = min(length, minTree[u][i]);
            v = dp[v][i];
            u = dp[u][i];
        }
    //cout << v << " " << u << " " << length << "    ";
    length = min(length, min(minTree[v][0], minTree[u][0]));
    return length;
}

int main() {
    FILE *input = fopen("minonpath.in", "r");
    FILE *output = fopen("minonpath.out", "w");
    int n, m, x, y;
    fscanf(input, "%i", &n);
    tree.resize(n + 1);
    p.resize(n + 1);
    p[1] = 1;
    depth[1] = 1;
    for (int i = 2; i <= n; i++) {
        fscanf(input,"%i %i", &x, &y);
        p[i] = x;
        minTree[i][0] = y;
        tree[x].push_back(i);
        dp[i][0] = x;
    }
    updateDp(1);
    fscanf(input, "%i", &m);
    for (int i = 0; i < m; i++) {
        fscanf(input,"%i %i", &x, &y);
        fprintf(output, "%i\n", lca(x, y));
    }
/*    for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 6; j++) {
            cout << dp[i][j] << " ";
        }
        cout << endl;
    }
    for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 6; j++) {
            cout << minTree[i][j] << " ";
        }
        cout << endl;
    }*/
}