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

struct Edge {
    int from, to, w;
};

vector<int> visited, color;
vector<Edge> edges;

const int INF = 1000000;

bool comparator(Edge e1, Edge e2) {
    return e1.w < e2.w;
}

int find(int v) {
    return (v == color[v] ? v : color[v] = find(color[v]));
}

void uni(int v, int u) {
    v = find(v);
    u = find(u);
    if (v != u)
        color[u] = v;
}

int main() {
    int n, m, start, end, w;
    long long weight = 0;
    scanf("%i %i", &n, &m);
    visited.resize(n + 1);
    color.resize(n + 1);

    for (int i = 0; i < m; i++) {
        scanf("%i %i %i", &start, &end, &w);
        edges.push_back({start, end, w});
    }

    sort(edges.begin(), edges.end(), comparator);

    for (int i = 1; i < n + 1; i++)
        color[i] = i;
    for (Edge e : edges) {
        if (find(e.from) != find(e.to)) {
            weight += e.w;
            uni(e.from, e.to);
        }
    }

    printf("%lld", weight);
}
