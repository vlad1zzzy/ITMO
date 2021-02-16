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
vector<vector<int>> dp;

const int INF = 1e9;
int n, m, k, s;


int main() {
    int from, to, w;
    cin >> n >> m >> k >> s;

    dp.resize(k + 1, vector<int>(n + 1, INF));
    dp[0][s] = 0;

    for (int i = 0; i < m; i++) {
        scanf("%i %i %i", &from, &to, &w);
        edges.push_back({from, to, w});
    }
    for (int i = 0; i < k; i++) {
        for (edge e : edges)
            if (dp[i][e.from] < INF)
                dp[i + 1][e.to] = min(dp[i + 1][e.to], dp[i][e.from] + e.w);
    }
    for (int i = 1; i <= n; i++) {
        if (dp[k][i] != INF)
            printf("%i\n", dp[k][i]);
        else
            printf("-1\n");
    }
}