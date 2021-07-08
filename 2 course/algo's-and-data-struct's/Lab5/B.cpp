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
};

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m, k;

vector<edge> edges;
vector<int> lvl, ptr, visited;
vector<vector<int>> graph;


void add_edge(int from, int to, double cap) {
    edge e1 = {from, to, cap};
    edge e2 = {to, from, 0};
    edge e3 = {from, to, 0};
    edge e4 = {to, from, cap};
    graph[from].push_back(edges.size());
    edges.push_back(e1);
    graph[to].push_back(edges.size());
    edges.push_back(e2);
    graph[from].push_back(edges.size());
    edges.push_back(e3);
    graph[to].push_back(edges.size());
    edges.push_back(e4);
}

bool bfs(double flow) {
    //cout << flow << " max_flow_bfs" << endl;
    lvl.clear();
    lvl.resize(n + 1, INF);
    lvl[1] = 0;
    queue<int> q;
    q.push(1);
    while (!q.empty()) {
        int v = q.front();
        q.pop();
        for (int ind : graph[v]) {
            edge edge = edges[ind];
            if (flow <= edge.cap - edge.flow && lvl[edge.to] == INF) {
                lvl[edge.to] = lvl[v] + 1;
                q.push(edge.to);
                //cout << "LVL++\n";
            }
        }
    }
    //cout << lvl[n] << " lvl\n";
    return lvl[n] != INF;
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

double dinic() {
    double flow = 0;
    int max = INF;
    while (max) {
        double res = 0;
        while (bfs(max)) {
            //cout << max << " max" << endl;
            ptr.clear();
            ptr.resize(n + 1, 0);
            double pushed = dfs(1, INF);
            //cout << pushed << " pushed" << endl;
            while (pushed) {
                //cout << pushed << " flow" << endl;
                res += pushed;
                pushed = dfs(1, INF);
            }
        }
        flow += res;
        max /= 2;
    }
    return flow;
}

void cut_dfs(int v) {
    if (visited[v]) return;
    visited[v] = 1;
    for (int ind : graph[v]) {
        edge e = edges[ind];
        if (e.cap != e.flow) {
            cut_dfs(e.to);
        }
    }
}

int main() {
    int from, to, w;
    cin >> n >> m;
    graph.resize(n + 1, vector<int>());
    visited.resize(n + 1, 0);
    for (int i = 0; i < m; i++) {
        cin >> from >> to >> w;
        add_edge(from, to, w);
    }

    int ans = dinic();
    vector<int> list;
    cut_dfs(1);

    //cout << setprecision(3) << fixed << ans << endl;
    /*for (edge e : edges) {
        cout << e.flow << " ";
    }*/
    for (int i = 0; i < m * 4; i += 4) {
        edge e = edges[i];
        if (visited[e.from] && !visited[e.to] || !visited[e.from] && visited[e.to]) {
            list.push_back(i / 4 + 1);
        }
    }
    cout << list.size() << " " << ans << endl;
    for (int ind : list) cout << ind << " ";
    return 0;
}