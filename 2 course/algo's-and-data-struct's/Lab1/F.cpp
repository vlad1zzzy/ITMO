//
// Created by Vlad on 17.10.2020.
//

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
#include <set>

using namespace std;

vector<int> visited, color, order;
vector<vector<int>> edges, edgesBack;
set<pair<int, int>> res;

int curColor = 0;

void dfs1(int v) {
    visited[v] = 1;
    for (int i : edges[v]) {
        if (not visited[i])
            dfs1(i);
    }
    order.push_back(v);
}

void dfs2(int v) {
    color[v] = curColor;
    for (int i : edgesBack[v]) {
        if (not color[i])
            dfs2(i);
    }
}


int main() {
    int n, m, start, end;
    scanf("%i %i", &n, &m);
    visited.resize(n + 1);
    edges.resize(n + 1);
    edgesBack.resize(n + 1);
    color.resize(n + 1);

    for (int i = 0; i < m; i++) {
        scanf("%i %i", &start, &end);
        edges[start].push_back(end);
        edgesBack[end].push_back(start);
    }

    for (int i = 1; i < n + 1; i++) {
        if (not visited[i])
            dfs1(i);
    }

    for (int i = n - 1; i >= 0; i--) {
        if (not color[order[i]]) {
            curColor++;
            dfs2(order[i]);
        }
    }
    //anti double-edge -> set
    for (int i = 1; i < n + 1; i++) {
        for (int v : edges[i])
            if (color[i] != color[v])
                res.insert(make_pair(color[i], color[v]));
    }
    printf("%i", res.size());
}