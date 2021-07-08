//
// Created by Vlad on 28.04.2021.
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

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m, k, s, t;
int max_flow, min_cost;

vector<vector<int>> costs, capacity, flow;
vector<int> push, mark, pred, dist;

void file_read() {
    int from, to, cap, cost;
    cin >> n >> m;
    s = 1;
    t = n;
    costs.resize(n + 1, vector<int>(n + 1));
    capacity.resize(n + 1, vector<int>(n + 1));
    flow.resize(n + 1, vector<int>(n + 1));
    for (int i = 0; i < m; i++) {
        cin >> from >> to >> cap >> cost;
        costs[from][to] = cost;
        capacity[from][to] = cap;
        capacity[to][from] = -cap;
    }
}

int edge_cost(int u, int v) {
    if (capacity[u][v] - flow[u][v] > 0) {
        return costs[u][v];
    } else {
        return INF;
    }
}

int check_cycles() {
    for (int u = 1; u <= n; u++)
        for (int v = 1; v <= n; v++)
            if (dist[v] > dist[u] + edge_cost(u, v))
                return u;

    return INF;
}

int bf() {
    mark.resize(n + 1, 0);
    push.resize(n + 1, 0);
    pred.resize(n + 1, 0);
    dist.resize(n + 1, INF);
    queue<int> Q;
    pred[s] = s;
    dist[s] = 0;

    Q.push(s);
    Q.push(INF);

    int u, series = 0;
    while (!Q.empty()) {
        while (Q.front() == INF) {
            Q.pop();
            if (++series >= n) {
                return check_cycles();
            } else {
                Q.push(INF);
            }
        }

        u = Q.front();
        Q.pop();
        for (int v = 1; v <= n; v++) {
            if (dist[v] > dist[u] + edge_cost(u, v)) {
                dist[v] = dist[u] + edge_cost(u, v);
                pred[v] = u;
                Q.push(v);
            }
        }
    }
}

int bfs() {
    mark.resize(n + 1, 0);
    push.resize(n + 1, 0);
    pred.resize(n + 1, 0);
    dist.resize(n + 1, INF);
    queue<int> Q;
    mark[s] = 1;
    pred[s] = s;
    push[s] = INF;

    Q.push(s);
    while (!mark[t] && !Q.empty()) {
        int u = Q.front();
        Q.pop();
        for (int v = 1; v <= n; v++)
            if (!mark[v] && (capacity[u][v] - flow[u][v] > 0)) {
                push[v] = min(push[u], capacity[u][v] - flow[u][v]);
                mark[v] = 1;
                pred[v] = u;
                Q.push(v);
            }
    }
    return mark[t];
}

void max_flow_ff() {
    int u, v, flow_current = 0;

    while (bfs()) {
        int add = push[t];
        v = t;
        u = pred[v];
        while (v != s) {
            flow[u][v] += add;
            flow[v][u] -= add;
            v = u;
            u = pred[v];
        }
        flow_current += add;
    }
    max_flow = flow_current;
}

void min_cost_flow() {
    max_flow_ff();

    int u, v, flow_current = 0;
    int add = INF;
    int neg_cycle;

    neg_cycle = bf();
    while (neg_cycle != INF) {
        v = neg_cycle;
        u = pred[v];
        do {
            add = min(add, capacity[u][v] - flow[u][v]);
            v = u;
            u = pred[v];
        } while (v != neg_cycle);

        v = neg_cycle;
        u = pred[v];
        do {
            flow[u][v] += add;
            flow[v][u] -= add;
            v = u;
            u = pred[v];
        } while (v != neg_cycle);
        neg_cycle = bf();
    }

    for (int u = 1; u <= n; u++) {
        for (int v = 1; v <= n; v++) {
            if (flow[u][v] > 0) {
                min_cost += flow[u][v] * costs[u][v];
            }
        }
    }
}

void file_write() {
    cout << max_flow << endl;
    cout << min_cost << endl;
}

int main() {
    file_read();
    min_cost_flow();
    file_write();
    return 0;
}