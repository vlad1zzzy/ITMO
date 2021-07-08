//
// Created by Vlad on 26.02.2021.
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

vector<vector<int>> graph;
vector<int> used, match, w1, w2, ans;
vector<edge> order;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m, e;

bool comparator(edge l, edge r) {
    return l.w > r.w;
}
bool kuhn(int v) {
    if (used[v]) {
        return false;
    }
    used[v] = 1;
    for (int to : graph[v]) {
        if (match[to] == -1 || kuhn(match[to])) {
            match[to] = v;
            ans[v] = to;
            return true;
        }
    }
    return false;
}

int main() {
    int from, to, count;
    cin >> n >> m >> e;
    graph.resize(n + 1);
    w1.resize(n + 1);
    w2.resize(m + 1);
    order.resize(e + 1);
    match.resize(n + 1, -1);
    used.resize(n + 1);
    ans.resize(n + 1);
    for (int i = 1; i <= n; i++) {
        cin >> w1[i];
    }
    for (int i = 1; i <= m; i++) {
        cin >> w2[i];
    }
    for (int i = 1; i <= e; i++) {
        cin >> from >> to;
        order[i] = {from, to, w1[from] + w2[to]};
        graph[from].push_back(to);
    }
    sort(order.begin() + 1, order.end(), comparator);
    /*for (int i : order)
        cout << i << " ";
    cout << endl;*/
    /*for (from = 1; from <= n; from++) {
        cin >> count;
        for (int j = 0; j < count; j++) {
            cin >> to;
            graph[from].push_back(to);
        }
    }*/

    for (int i = 1; i <= n; ++i) {
        used.assign(n + 1, 0);
        kuhn(order[i]);
    }

    for (int i = 1; i <= n; i++) {
        cout << ans[i] << " ";
    }

    return 0;
}


