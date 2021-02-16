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
    int from, to, index;
    long long w;
};

vector<edge> graph;
vector<int> parents, taken, ans;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m;
long long s;

bool comparator(edge l, edge r) {
    return l.w > r.w;
}

int find(int v) {
    if (v == parents[v])
        return v;
    return parents[v] = find(parents[v]);
}

void uni(edge e) {
    int u = find(e.from);
    int v = find(e.to);
    if (u != v) {
        parents[v] = u;
        taken[e.index] = true;
    }
}

int main() {
    int from, to;
    long long w;
    freopen("destroy.in", "r", stdin);
    freopen("destroy.out", "w", stdout);
    cin >> n >> m >> s;
    graph.resize(m);
    parents.resize(n + 1);
    taken.resize(m + 1);
    for (int i = 0; i < m; i++) {
        cin >> from >> to >> w;
        graph[i] = {from, to, i + 1, w};
    }
    for (int i = 1; i <= n; i++) {
        parents[i] = i;
    }
    sort(graph.begin(), graph.end(), comparator);
    for (edge e : graph) {
        uni(e);
        //cout << e.from << " " << e.to << " " << e.w << " " << e.index << endl;
    }
    sort(graph.rbegin(), graph.rend(), comparator);
    for (edge e : graph) {
        if (not taken[e.index] and e.w <= s) {
            ans.push_back(e.index);
            s -= e.w;
        }
    }
    cout << ans.size() << endl;
    sort(ans.begin(), ans.end());
    for (int i : ans) {
        cout << i << " ";
    }
    /*cout << endl;
    for (int I : parents)
        cout << I << " ";*/
    return 0;
}