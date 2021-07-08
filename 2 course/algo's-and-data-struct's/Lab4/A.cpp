//
// Created by Vlad on 26.02.2021.
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
vector<int> used, px, py;
vector<pair<int, int>> ans;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m;

bool dfs(int x) {
    if (used[x]) { return false; }
    used[x] = true;
    for (int y : graph[x]) {
        if (py[y] == -1) {
            py[y] = x;
            px[x] = y;
            return true;
        } else {
            if (dfs(py[y])) {
                py[y] = x;
                px[x] = y;
                return true;
            }
        }
    }
    return false;
}

void fordFulkerson() {
    px.resize(n + 1, -1);
    py.resize(m + 1, -1);
    bool isPath = true;
    while (isPath) {
        isPath = false;
        used.assign(n + 1, false);
        for (int i = 1; i <= n; i++) {
            if (px[i] == -1) {
                if (dfs(i)) {
                    isPath = true;
                }
            }
        }
    }
}

int main() {
    int from, to, count = 0;
    cin >> n >> m;
    graph.resize(n + 1);
    used.resize(n + 1);
    for (from = 1; from <= n; from++) {
        while (true) {
            cin >> to;
            if (to == 0) {
                break;
            }
            graph[from].push_back(to);
        }
    }

    fordFulkerson();

    for (int i = 1; i <= n; i++) {
        count += (px[i] == -1 ? 0 : 1);
    }
    cout << count << endl;
    for (int i = 1; i <= n; i++) {
        if (px[i] != -1) {
            cout << i << " " << px[i] << endl;
        }
    }

    return 0;
}


