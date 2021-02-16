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

vector<int> visited, order, color;
vector<string> names;
vector<vector<int>> edges, reversedEdges;
map<string, int> indexes;

const int INF = 1e9;
int n, m;


void dfs1(int v) {
    visited[v] = 1;
    for (int i : edges[v]) {
        if (not visited[i])
            dfs1(i);
    }
    order.push_back(v);
}

void dfs2(int v, int c) {
    color[v] = c;
    for (int i : reversedEdges[v]) {
        if (color[i] == -1)
            dfs2(i, c);
    }
}

int rv(int v) {
    return (v % 2) ? v - 1 : v + 1;
}

int main() {
    string name1, arrow, name2;
    cin >> n >> m;
    visited.resize(n * 2);
    color.resize(n * 2, -1);
    edges.resize(n * 2);
    reversedEdges.resize(n * 2);

    for (int i = 0; i < n; i++) {
        cin >> name1;
        names.push_back(name1);
        indexes["+" + name1] = i * 2;
        indexes["-" + name1] = i * 2 + 1;
    }

    n *= 2;
    for (int i = 0; i < m; i++) {
        cin >> name1 >> arrow >> name2;
        int v = indexes[name1], u = indexes[name2];
        int v1 = rv(v);
        int u1 = rv(u);
        edges[v].push_back(u);
        edges[u1].push_back(v1);
        reversedEdges[u].push_back(v);
        reversedEdges[v1].push_back(u1);
    }

    for (int i = 0; i < n; i++)
        if (not visited[i])
            dfs1(i);

    int clr = 0;
    for (int i = 1; i <= n; i++) {
        int v = order[n - i];
        if (color[v] == -1)
            dfs2(v, clr++);
    }

    for (int i = 0; i < n; i += 2)
        if (color[i] == color[i + 1]) {
            cout << -1;
            return 0;
        }
    vector<int> ans;
    for (int i = 0; i < n; i += 2) {
        if (color[i] > color[i + 1])
            ans.push_back(i / 2);
    }
    cout << ans.size() << endl;
    for (int i : ans) {
        cout << names[i] << endl;
    }
}
