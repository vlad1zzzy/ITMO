//
// Created by vlad on 18.05.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>

using namespace std;

int dp[200002][18];
int depth[200002];
vector<vector<int>> tree;
vector<int> p;

void updateDp(int x) {
    for (int i = 1; i < 18; i++)
        dp[x][i] = dp[dp[x][i - 1]][i - 1];
    for (int i : tree[x]) {
        depth[i] = depth[x] + 1;
        updateDp(i);
    }
}

int lca(int v, int u) {
    if (depth[v] > depth[u])
        swap(u, v);
    for (int i = 17; i >= 0; i--)
        if (depth[dp[u][i]] - depth[v] >= 0)
            u = dp[u][i];
    //cout << u << " " << v << " ";
    if (u == v)
        return u;
    for (int i = 17; i >= 0; i--)
        if (dp[v][i] != dp[u][i]) {
            v = dp[v][i];
            u = dp[u][i];
        }
    //cout << v << " ";
    return p[v];
}

int main() {
    int n, m, k, u, v;
    scanf("%i", &n);
    tree.resize(n + 1);
    p.resize(n + 1);
    p[1] = 1;
    for (int i = 2; i <= n; i++) {
        scanf("%i", &k);
        p[i] = k;
        tree[k].push_back(i);
        dp[i][0] = k;
    }
    updateDp(1);
    scanf("%i", &m);
    for (int i = 0; i < m; i++) {
        scanf("%i %i", &v, &u);
        printf("%i\n", lca(v, u));
    }
}