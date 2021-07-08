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

vector<vector<int>> graph, graphR;
vector<int> used, px, py;
vector<int> usedL, usedR, boys, girls;
vector<pair<int, int>> ans;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m, k;

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

void dfsCover(int x, int component) {
    if (component == -1) {
        usedL[x] = true;
        for (int y : graph[x]) {
            if (not usedR[y] and px[x] != y) {
                dfsCover(y, 1);
            }
        }
    } else {
        usedR[x] = true;
        for (int y : graphR[x]) {
            if (not usedL[y] and py[x] == y) {
                dfsCover(y, -1);
            }
        }
    }
}

void minVertexCover() {
    usedL.resize(n + 1);
    usedR.resize(m + 1);
    for (int i = 1; i <= n; i++) {
        if (px[i] == -1) {
            dfsCover(i, -1);
        }
    }
}

void solve() {
    int from, to;
    cin >> n >> m;
    graph.resize(n + 1);
    graphR.resize(m + 1);
    used.resize(n + 1);
    vector<bool> friends;
    for (int j = 0; j < n; j++) {
        friends.resize(151);
        from = j + 1;
        while (true) {
            cin >> to;
            if (to == 0) {
                break;
            }
            friends[to] = true;
        }
        for (int i = 1; i <= m; i++) {
            if (not friends[i]) {
                graph[from].push_back(i);
                graphR[i].push_back(from);
            }
        }
        friends.clear();
    }

    /*cout << "GRAPH" << endl;
    for (int i = 1; i <= n; i++) {
        for (int j : graph[i]) {
            cout << j << " ";
        }
        cout << endl;
    }*/

    fordFulkerson();
    minVertexCover();

    for (int i = 1; i <= n; i++) {
        if ( usedL[i]) {
            boys.push_back(i);
        }
    }
    for (int i = 1; i <= m; i++) {
        if (not usedR[i]) {
            girls.push_back(i);
        }
    }
    //cout << "count: " <<  boys.size() + girls.size() << endl;
    //cout << "boys: " <<  boys.size() <<  " girls: " << girls.size() << endl;
    cout << boys.size() + girls.size() << endl;
    cout << boys.size() << " " << girls.size() << endl;
    for (int i : boys) {
        cout << i << " ";
    }
    cout << endl;
    for (int i : girls) {
        cout << i << " ";
    }
    cout << endl;
    cout << endl;


    graph.clear();
    graphR.clear();
    used.clear();
    usedL.clear();
    usedR.clear();
    px.clear();
    py.clear();
    boys.clear();
    girls.clear();
}

int main() {
    int from, to, count = 0;
    cin >> k;
    for (int i = 0; i < k; i++) {
        solve();
    }

    return 0;
}