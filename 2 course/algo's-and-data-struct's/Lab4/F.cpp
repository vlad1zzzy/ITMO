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
vector<int> used, px, py;
vector<pair<int, int>> ans;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m;
int hor, ver;

struct section {
    int x1, y1, x2, y2;
};

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
        used.assign(hor + 1, false);
        for (int i = 0; i < hor; i++) {
            if (px[i] == -1) {
                if (dfs(i)) {
                    isPath = true;
                }
            }
        }
    }
}

bool between(int i, int j, int k) {
    return i <= j and j <= k;
}

int main() {
    int from, to, count = 0;
    cin >> n;
    used.resize(n + 1);
    int x1, y1, x2, y2;
    vector<section> horizontal, vertical;
    for (from = 0; from < n; from++) {
        cin >> x1 >> y1 >> x2 >> y2;
        if (x1 == x2) {
            if (y1 > y2) {
                swap(y1, y2);
            }
            vertical.push_back({x1, y1, x2, y2});
        } else {
            if (x1 > x2) {
                swap(x1, x2);
            }
            horizontal.push_back({x1, y1, x2, y2});
        }
    }
    hor = horizontal.size();
    ver = vertical.size();
    for (int i = 0; i < hor; i++) {
        vector<int> cur;
        for (int j = 0; j < ver; j++) {
            if (between(horizontal[i].x1, vertical[j].x1, horizontal[i].x2) and between(vertical[j].y1, horizontal[i].y1, vertical[j].y2)) {
                cur.push_back(hor + j);
            }
        }
        graph.push_back(cur);
    }

    /*cout << "GRAPH " << hor << endl;
    for (int i = 0; i < hor; i++) {
        for (int j : graph[i]) {
            cout << j << " ";
        }
        cout << endl;
    }
    cout << "/GRAPH" << endl;*/
    fordFulkerson();

    for (int i = 0; i < hor; i++) {
        count += (px[i] == -1 ? 0 : 1);
    }
    cout << n - count << endl;
/*    for (int i = 0; i < n; i++) {
        if (px[i] != -1) {
            cout << i << " " << px[i] << endl;
        }
    }*/

    return 0;
}

