//
// Created by Vlad on 29.04.2021.
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
#include <iomanip>

using namespace std;

struct edge {
    int from, to;
    double cap, flow = 0;
};

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m, k;

vector<edge> edges;
vector<int> lvl, ptr;
vector<vector<int>> graph;
vector<vector<char>> table;


void add_edge(int from, int to, double cap) {
    edge e1 = {from, to, cap};
    edge e2 = {to, from, 0};
    graph[from].push_back(edges.size());
    edges.push_back(e1);
    graph[to].push_back(edges.size());
    edges.push_back(e2);
}

bool bfs(double flow) {
    lvl.clear();
    lvl.resize(n + 2, INF);
    lvl[n] = 0;
    queue<int> q;
    q.push(n);
    while (!q.empty()) {
        int v = q.front();
        q.pop();
        for (int ind : graph[v]) {
            edge edge = edges[ind];
            if (flow <= edge.cap - edge.flow && lvl[edge.to] == INF) {
                lvl[edge.to] = lvl[v] + 1;
                q.push(edge.to);
            }
        }
    }
    return lvl[n + 1] != INF;
}

double dfs(int v, double flow) {
    if (v == n + 1 || flow == 0) return flow;
    for (int i = ptr[v]; i < graph[v].size(); i++) {
        int id = graph[v][i], to = edges[id].to;
        if (lvl[to] == lvl[v] + 1) {
            double pushed = dfs(to, min(flow, edges[id].cap - edges[id].flow));
            if (pushed) {
                edges[id].flow += pushed;
                edges[id ^ 1].flow -= pushed;
                return pushed;
            }
            ptr[v]++;
        }
    }
    return 0;
}

double dinic() {
    double flow = 0;
    int max = INF;
    while (max) {
        double res = 0;
        while (bfs(max)) {
            ptr.clear();
            ptr.resize(n + 2, 0);
            double pushed = dfs(n, INF);
            while (pushed) {
                res += pushed;
                pushed = dfs(n, INF);
            }
        }
        flow += res;
        max /= 2;
    }
    return flow;
}

int main() {
    cin >> n;
    vector<int> already_have(n, 0), possible(n, 0);
    graph.resize(n + 2, vector<int>());
    table.resize(n + 2, vector<char>(n + 2));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cin >> table[i][j];
            if (table[i][j] == 'W') {
                already_have[i] += 3;
            } else if (table[i][j] == 'w') {
                already_have[i] += 2;
            } else if (table[i][j] == 'l') {
                already_have[i] += 1;
            } else if (table[i][j] == '.' && i < j) {
                add_edge(i, j, 3);
                possible[i] += 3;
            }
        }
    }
    int points;
    for (int i = 0; i < n; i++) {
        cin >> points;
        already_have[i] = points - already_have[i];
        add_edge(n, i, possible[i]);
        add_edge(i, n + 1, already_have[i]);
    }
    /*for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cout << table[i][j];
        }
        cout << endl;
    }*/

    /*for (int i = 0; i < n + 2; i++) {
        for (int ind : graph[i]) {
            cout << edges[ind].from << " " << edges[ind].to << " " <<  edges[ind].cap << endl;
        }
        cout << endl;
    }*/

    dinic();
    /*for (edge e : edges) {
        cout << e.flow << " ";
    }*/

    for (int i = 0; i < n; i++) {
        for (int ind : graph[i]) {
            edge e = edges[ind];
            if (table[i][e.to] == '.' && i < e.to) {
                table[i][e.to] = e.flow == 0 ? 'W' : e.flow == 1 ? 'w' : e.flow == 2 ? 'l' : 'L';
                table[e.to][i] = e.flow == 0 ? 'L' : e.flow == 1 ? 'l' : e.flow == 2 ? 'w' : 'W';
            }
        }
    }
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cout << table[i][j];
        }
        cout << endl;
    }
    return 0;
}