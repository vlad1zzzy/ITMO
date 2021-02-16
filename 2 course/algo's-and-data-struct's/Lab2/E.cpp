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

vector<vector<pair<int, long long>>> edges;
vector<long long> dp;
vector<int> visited;
set<pair<int, int>> q;

const long long INF = 5e18;
int n, m, s;

void dfs(int v) {
    visited[v] = 1;
    for (pair<int, long long> edge : edges[v]) {
        if (!visited[edge.first])
            dfs(edge.first);
    }
}

int main() {
    int from, to;
    long long w;
    cin >> n >> m >> s;

    edges.resize(n + 1);
    dp.resize(n + 1, INF);
    dp[s] = 0;
    visited.resize(n + 1);

    for (int i = 0; i < m; i++) {
        scanf("%i %i %lld", &from, &to, &w);
        edges[from].push_back(make_pair(to, w));
    }
    for (int k = 0; k < n; k++) {
        for (int i = 1; i < n + 1; i++) {
            for (pair<int, long long> edge : edges[i]) {
                if (dp[i] != INF and dp[edge.first] > dp[i] + edge.second) {
                    dp[edge.first] = max(-INF, dp[i] + edge.second);
                }
            }
        }
    }
    for (int i = 1; i < n + 1; i++) {
        for (pair<int, long long> edge : edges[i]) {
            if (dp[i] != INF and not visited[edge.first] and dp[edge.first] > dp[i] + edge.second) {
                dfs(edge.first);
            }
        }
    }
    for (int i = 1; i < n + 1; i++) {
        if (dp[i] == INF)
            printf("*\n");
        else if (visited[i])
            printf("-\n");
        else
            printf("%lld\n", dp[i]);
    }
}

