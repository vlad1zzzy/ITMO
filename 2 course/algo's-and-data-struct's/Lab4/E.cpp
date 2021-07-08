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
vector<vector<char>> full;
vector<int> used, px, py;
vector<pair<int, int>> ans;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m;

int getIndex(int i, int j) {
    return i * m + j;
}

bool inLeft(int i, int j) {
    return (i + j) % 2 == 0;
}


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
    px.resize(n * m, -1);
    py.resize(n * m, -1);
    bool isPath = true;
    while (isPath) {
        isPath = false;
        used.assign(n * m, false);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int cur = getIndex(i, j);
                if (inLeft(i, j) and px[cur] == -1) {
                    if (dfs(cur)) {
                        isPath = true;
                    }
                }
            }

        }
    }
}

int main() {
    int from, to;
    long long count = 0;
    int a, b;
    cin >> n >> m >> a >> b;
    full.resize(n, vector<char>(m));
    graph.resize(n * m);
    used.resize(n * m);
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            cin >> full[i][j];
        }
    }
    int empty = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (full[i][j] == '*') {
                empty++;
                int cur = getIndex(i, j);
                int upper = getIndex(i - 1, j);
                int left = getIndex(i, j - 1);
                if (i > 0 and full[i - 1][j] == '*') {
                    if (inLeft(i - 1, j)) {
                        graph[upper].push_back(cur);
                    } else {
                        graph[cur].push_back(upper);
                    }
                }
                if (j > 0 and full[i][j - 1] == '*') {
                    if (inLeft(i, j - 1)) {
                        graph[left].push_back(cur);
                    } else {
                        graph[cur].push_back(left);
                    }
                }
            }
        }
    }

    if (2 * b <= a) {
        cout << empty * b;
    } else {
        fordFulkerson();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                //cout << getIndex(i, j) << ") " << px[getIndex(i, j)] << endl;
                int cur = getIndex(i, j);
                if (inLeft(i, j) and px[cur] != -1) {
                    count++;
                }
            }
        }
        cout << count * a + (empty - 2 * count) * b;
    }


    return 0;
}


