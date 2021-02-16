//
// Created by Vlad on 27.12.2020.
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
vector<int> used, order, match, w, ans;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m;

bool comparator(int l, int r) {
    return w[l] > w[r];
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
    freopen("matching.in", "r", stdin);
    freopen("matching.out", "w", stdout);
    cin >> n;
    graph.resize(n + 1);
    w.resize(n + 1);
    order.resize(n + 1);
    match.resize(n + 1, -1);
    used.resize(n + 1);
    ans.resize(n + 1);
    for (int i = 1; i <= n; i++) {
        cin >> w[i];
        order[i] = i;
    }
    sort(order.begin() + 1, order.end(), comparator);
    /*for (int i : order)
        cout << i << " ";
    cout << endl;*/
    for (from = 1; from <= n; from++) {
        cin >> count;
        for (int j = 0; j < count; j++) {
            cin >> to;
            graph[from].push_back(to);
        }
    }

    for (int i = 1; i <= n; ++i) {
        used.assign(n + 1, 0);
        kuhn(order[i]);
    }

    for (int i = 1; i <= n; i++) {
        cout << ans[i] << " ";
    }

    return 0;
}


