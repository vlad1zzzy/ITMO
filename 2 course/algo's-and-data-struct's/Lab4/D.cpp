//
// Created by Vlad on 08.03.2021.
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
vector<int> used, px, py, time, x, y;
vector<pair<int, int>> ans;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m, v;

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
    py.resize(n + 1, -1);
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
    cin >> n >> v;
    graph.resize(n + 1);
    used.resize(n + 1);
    time.resize(n + 1);
    x.resize(n + 1);
    y.resize(n + 1);
    int hours, minutes;
    char dots;
    for (int i = 1; i <= n; i++) {
        cin >> hours >> dots >> minutes >> x[i] >> y[i];
        time[i] = hours * 60 + minutes;
    }

    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if (i != j) {
                if (sqrt(pow(x[i] - x[j], 2) + pow(y[i] - y[j], 2)) / v * 60 <= time[j] - time[i]) {
                    graph[i].push_back(j);
                }
            }
        }
    }


    fordFulkerson();

    for (int i = 1; i <= n; i++) {
        count += (px[i] == -1 ? 0 : 1);
    }
    cout << n - count;

    return 0;
}