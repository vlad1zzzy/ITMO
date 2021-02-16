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

using namespace std;

vector<int> visited, enter, ret, ans;
vector<vector<pair<int, int>>> edges;
stack<int> component;

int time = 0;
int color = 0;
const int INF = 1000000;

void paint(int v) {
    color++;
    int last = -1;
    while (last != v and not component.empty()) {
        ans[component.top()] = color;
        last = component.top();
        component.pop();
    }
}

void dfs(int v, int lastEdge) {
    visited[v] = 1;
    enter[v] = ret[v] = time++;
    component.push(v);
    for (pair<int, int> i : edges[v]) {
        if (i.second == lastEdge) continue;
        if (visited[i.first])
            ret[v] = min(ret[v], enter[i.first]);
        else {
            dfs(i.first, i.second);
            ret[v] = min(ret[v], ret[i.first]);
            if (ret[i.first] > enter[v]) {
                paint(i.first);
            }
        }
    }
    if (lastEdge == -1)
        paint(v);
}


int main() {
    int n, m, start, end;
    scanf("%i %i", &n, &m);
    visited.resize(n + 1);
    enter.resize(n + 1, INF);
    ret.resize(n + 1, INF);
    edges.resize(n + 1);
    ans.resize(n + 1);

    for (int i = 0; i < m; i++) {
        scanf("%i %i", &start, &end);
        edges[start].push_back(make_pair(end, i + 1));
        edges[end].push_back(make_pair(start, i + 1));
    }

    for (int i = 1; i < n + 1; i++) {
        if (not visited[i])
            dfs(i, -1);
    }

    printf("%i\n", color);
    for (int i = 1; i <= n; i++)
        printf("%i ", ans[i]);
}