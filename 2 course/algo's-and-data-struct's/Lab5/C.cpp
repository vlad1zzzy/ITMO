//
// Created by Vlad on 20.04.2021.
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
    bool deleted = false;
};

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m, k, s, t;
bool found = false;

vector<edge> edges;
vector<int> lvl, ptr, visited, path1, path2, previous;
vector<vector<int>> graph;


void add_edge(int from, int to, double cap = 1) {
    edge e1 = {from, to, cap};
    edge e2 = {to, from, 0};
    graph[from].push_back(edges.size());
    edges.push_back(e1);
    graph[to].push_back(edges.size());
    edges.push_back(e2);
}

bool bfs() {
    lvl.clear();
    lvl.resize(n + 1, INF);
    lvl[s] = 0;
    queue<int> q;
    q.push(s);
    while (!q.empty()) {
        int v = q.front();
        q.pop();
        for (int ind : graph[v]) {
            edge edge = edges[ind];
            if (edge.cap - edge.flow > 0 && lvl[edge.to] == INF) {
                lvl[edge.to] = lvl[v] + 1;
                q.push(edge.to);
                previous[edge.to] = ind;
            }
        }
    }
    return lvl[t] != INF;
}

double dfs(int v, double flow) {
    if (v == n || flow == 0) return flow;
    //cout << flow << " " << ptr[v] << " " << graph[v].size() << " stat\n";
    for (int i = ptr[v]; i < graph[v].size(); i++) {
        int id = graph[v][i], to = edges[id].to;
        if (lvl[to] == lvl[v] + 1) {
            double pushed = dfs(to, min(flow, edges[id].cap - edges[id].flow));
            if (pushed) {
                // cout << edges[id].flow << " " << edges[id ^ 1].flow << " flows\n";
                edges[id].flow += pushed;
                edges[id ^ 1].flow -= pushed;
                //cout << edges[id].flow << " " << edges[id ^ 1].flow << " flows after\n";
                return pushed;
            }
            ptr[v]++;
        }
        //ptr[v]++;
    }
    return 0;
}

long double dinic() {
    long double flow = 0;
    while (flow != 2 && bfs()) {
        int way = 0;
        for (int v = t; v != s; v = edges[previous[v]].from) {
            if (previous[v] % 2 == 0) {
                way = 1;
            }
            edges[previous[v]].flow++;
            edges[previous[v] ^ 1].flow--;
        }
        flow = way ? flow + 1 : flow - 1;
    }
    return flow;
}


void find_path(int v, vector<int> &path) {
    if (found || visited[v]) return;
    visited[v] = 1;
    path.push_back(v);
    if (v == t) {
        found = true;
        return;
    }
    for (int ind : graph[v]) {
        edge &e = edges[ind];
        if (found || v == e.to) break;
        if (ind % 2) continue;
        //cout << ind << " " << e.from << " " << e.to << " " << e.deleted << " del\n";
        if (e.flow > 0 && !e.deleted) {
            e.deleted = true;
            //edges[ind] = {e.from, e.to, e.cap, e.flow, true};
            find_path(e.to, path);
        }
    }
}

int main() {
    int from, to, w;
    cin >> n >> m >> s >> t;
    graph.resize(n + 1, vector<int>());
    visited.resize(n + 1, 0);
    previous.resize(n + 1, -1);
    for (int i = 0; i < m; i++) {
        cin >> from >> to;
        if (from != to) {
            add_edge(from, to);
        }
    }
    long double ans = dinic();
    //cout << ans << endl;
    if (ans >= 2) {
        cout << "YES\n";
        find_path(s, path1);
        found = false;
        visited.clear();
        visited.resize(n + 1, 0);
        find_path(s, path2);
        for (int ind : path1) {
            cout << ind << " ";
        }
        /*cout << endl;
        for (edge e : edges) {
            cout << e.deleted << " del ";
        }*/
        cout << endl;
        for (int ind : path2) {
            cout << ind << " ";
        }
    } else {
        cout << "NO";
    }
    return 0;
}