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

vector<vector<pair<int, int>>> edges;
vector<int> dp, visited;
set<pair<int, int>> q;

const int INF = 1e9;
int n, m;


int main() {
    int from, to, w;
    cin >> n >> m;

    edges.resize(n + 1);
    dp.resize(n + 1, INF);
    dp[1] = 0;
    visited.resize(n + 1);

    for (int i = 0; i < m; i++) {
        scanf("%i %i %i", &from, &to, &w);
        edges[from].push_back(make_pair(to, w));
        edges[to].push_back(make_pair(from, w));
    }

    q.insert(make_pair(0, 1));
    while (not q.empty()) {
        from = q.begin()->second;
        q.erase(q.begin());
        for (pair<int, int> edge : edges[from]) {
            to = edge.first;
            w = edge.second;
            if (dp[from] + w < dp[to]) {
                q.erase(make_pair(dp[to], to));
                dp[to] = dp[from] + w;
                q.insert(make_pair(dp[to], to));
            }
        }
    }
    /*for (int i = 0; i < n; i++) {
        from = -1;
        for (int j = 1; j <= n; ++j)
            if (not visited[j] and (from == -1 || dp[j] < dp[from]))
                from = j;
        if (dp[from] == INF)
            break;
        visited[from] = true;

        for (pair<int, int> edge : edges[from]) {
            to = edge.first;
            w = edge.second;
            if (dp[from] + w < dp[to]) {
                dp[to] = dp[from] + w;
            }
        }
    }*/
    for (int i = 1; i <= n; i++)
        printf("%i ", dp[i]);
}

