//
// Created by Vlad on 08.12.2020.
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
    int from, to, w;
};

vector<vector<int>> graph;
vector<int> parent, degree, code;

const int INF = 1e9;
int n, m;

void dfs(int v) {
    for (int i = 0; i < graph[v].size(); i++) {
        int to = graph[v][i];
        if (to != parent[v]) {
            parent[to] = v;
            dfs(to);
        }
    }
}

int main() {
    int from, to;
    scanf("%i", &n);
    graph.resize(n + 1, vector<int>());
    degree.resize(n + 1, 1);
    code.resize(n - 2);
    for (int i = 0; i < n - 2; i++) {
        scanf("%i", &code[i]);
    }
    for (int i = 0; i < n - 2; i++) {
        degree[code[i]]++;
    }
    int flag = 1;
    while (flag < n and degree[flag] != 1) {
        flag++;
    }
    int leaf = flag;
    vector<pair<int, int>> ans;
    for (int i = 0; i < n - 2; ++i) {
        int v = code[i];
        //ans[i] = {leaf, v};
        ans.push_back(make_pair(leaf, v));
        degree[leaf]--;
        if (--degree[v] == 1 and v < flag) {
            leaf = v;
        } else {
            flag++;
            while (flag < n and degree[flag] != 1) {
                flag++;
            }
            leaf = flag;
        }
    }
    for (int v = 0; v < n - 1; v++) {
        if (degree[v + 1] == 1) {
            ans.push_back(make_pair(v + 1, n));
            /*ans[v] = {v, n - 1};*/
        }
        cout << ans[v].first << " " << ans[v].second << endl;
    }
    return 0;
}

