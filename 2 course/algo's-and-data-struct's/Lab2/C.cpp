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

vector<edge> edges;
vector<int> dp, visited, parent;
set<pair<int, int>> q;

const int INF = 1e9;
int n, m;


int main() {
    int from, to, w;
    cin >> n;
    dp.resize(n + 1, INF);
    dp[1] = 0;
    parent.resize(n + 1, -1);

    int v;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cin >> v;
            if (v < 10000) {
                edge e{i + 1, j + 1, v};
                edges.push_back(e);
            }
        }
    }
    int x;
    for (int i = 0; i < n; i++) {
        x = -1;
        for (edge e : edges) {
            if (dp[e.to] > dp[e.from] + e.w) {
                dp[e.to] = max(-INF, dp[e.from] + e.w);
                parent[e.to] = e.from;
                x = e.to;
            }
        }
    }
    if (x == -1)
        printf("NO");
    else {
        for (int i = 0; i < n; i++)
            x = parent[x];
        vector<int> path;
        int cur = x;
        while(cur != x || path.size() <= 1) {
            path.push_back(cur);
            cur = parent[cur];
        }

        reverse(path.begin(), path.end());

        printf("YES\n%i\n", path.size());
        for (int i : path)
            printf("%i ", i);
    }
}
