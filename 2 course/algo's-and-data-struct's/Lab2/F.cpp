//
// Created by Vlad on 16.11.2020.
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

const long long INF = 1e18;
int n, m;

void find(int from) {
    set<pair<int, int>> q;
    q.insert(make_pair(0, from));
    while (not q.empty()) {
        from = q.begin()->second;
        q.erase(q.begin());
        for (pair<int, int> edge : edges[from]) {
            int to = edge.first;
            int w = edge.second;
            if (dp[from] + w < dp[to]) {
                q.erase(make_pair(dp[to], to));
                dp[to] = dp[from] + w;
                q.insert(make_pair(dp[to], to));
            }
        }
    }
}


int main() {
    int from, to, w, a, b, c;
    cin >> n >> m;

    edges.resize(n + 1);

    for (int i = 0; i < m; i++) {
        scanf("%i %i %i", &from, &to, &w);
        edges[from].push_back(make_pair(to, w));
        edges[to].push_back(make_pair(from, w));
    }
    scanf("%i %i %i", &a, &b, &c);

    dp.resize(n + 1, INF);
    dp[a] = 0;
    find(a);
    long long ab = dp[b], ac = dp[c];

    dp.assign(n + 1, INF);
    dp[b] = 0;
    find(b);
    long long bc = dp[c];

    if ((ab == INF and (ac == INF or bc == INF)) or (ac == INF and bc == INF))
        cout << -1;
    else
        cout << min(ab + ac, min(ab + bc, ac + bc));
}

