//
// Created by Vlad on 17.10.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>
#include <stack>
#include <queue>

using namespace std;

vector<int> visited;
vector<vector<int>> edges, reversedEdges;

const int INF = 1000000;
int n, visit = 0;


int dfs(int v, int maxW, vector<vector<int>> &e) {
    visit++;
    visited[v] = 1;
    for (int i = 0; i < n; i++) {
        if (not visited[i] and e[v][i] <= maxW)
            dfs(i, maxW, e);
    }
    return visit;
}

int main() {
    int w, weight = 0;

    freopen("avia.in", "r", stdin);
    freopen("avia.out", "w", stdout);

    cin >> n;
    visited.resize(n);
    edges.resize(n, vector<int>(n));
    reversedEdges.resize(n, vector<int>(n));

    for (int i = 0 ; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cin >> w;
            edges[i][j] = reversedEdges[j][i]= w;
            if (w > weight)
                weight = w;
        }
    }


    int m, l = -1, r = weight + 1;
    int visit1, visit2;
    while (l + 1 < r) {
        m = (l + r) / 2;
        visit1 = dfs(0, m, edges);
        visit = 0;
        visited.assign(n, 0);
        visit2 = dfs(0, m, reversedEdges);
        visit = 0;
        visited.assign(n, 0);
        if (visit1 + visit2 < n * 2)
            l = m;
        else
            r = m;
    }

    cout << r;
}
