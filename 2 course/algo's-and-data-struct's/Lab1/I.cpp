//
// Created by Vlad on 18.10.2020.
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
    int from, to;
    double w;
};

struct Point {
    int x, y;
};

vector<int> color;
vector<Edge> edges;
vector<Point> points;

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

double dist(Point p1, Point p2) {
    return sqrt(pow(p1.x - p2.x, 2) + pow(p1.y - p2.y, 2));
}

int main() {
    int n, x, y;
    long double weight = 0;
    scanf("%i", &n);
    color.resize(n);

    for (int i = 0; i < n; i++) {
        scanf("%i %i", &x, &y);
        points.push_back({x, y});
    }

    for (int i = 0; i < n - 1; i++)
        for (int j = i + 1; j < n; j++)
            edges.push_back({i, j, dist(points[i], points[j])});

    sort(edges.begin(), edges.end(), comparator);

    for (int i = 0; i < n; i++)
        color[i] = i;
    for (Edge e : edges) {
        if (find(e.from) != find(e.to)) {
            weight += e.w;
            uni(e.from, e.to);
        }
    }

    printf("%Lf", weight);
}