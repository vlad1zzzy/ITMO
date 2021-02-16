//
// Created by Vlad on 15.11.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>
#include <stack>
#include <queue>
#include <set>

using namespace std;

struct edge {
    int from, to, w;
};

vector<vector<int>> edges;
map<int, vector<int>> edgesR;
map<int, bool> pos;
vector<int> visited, ans;

const int INF = 1e9;
int n, m, k, s;


void dfs(int v) {
    visited[v] = 1;
    for (int i : edges[v]) {
        if (not visited[i])
            dfs(i);
    }
    ans.push_back(v);
}


void topSort() {
    for (int i = 1; i < n + 1; i++) {
        if (not visited[i])
            dfs(i);
    }
    reverse(ans.begin(), ans.end());
}

int main() {
    int from, to;

    freopen("game.in", "r", stdin);
    freopen("game.out", "w", stdout);

    cin >> n >> m >> s;

    visited.resize(n + 1);
    edges.resize(n + 1);
    ans.resize(n);

    for (int i = 0; i < m; i++) {
        cin >> from >> to;
        edges[from].push_back(to);
        edgesR[to].push_back(from);
    }
    topSort();

    pos[ans[n - 1]] = false;
    for (int i = n - 1; i >= 0; i--) {
        for (int u : edgesR[ans[i]]) {
            pos[u] = (pos[u] || not pos[ans[i]]);
        }
    }
    if (pos[s])
        cout << "First player wins";
    else
        cout << "Second player wins";
}

