//
// Created by Vlad on 16.12.2020.
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

vector<vector<bool>> graph;
vector<int> path, cycle;

const int INF = 1e9;
int n, m;

void print() {
    cout << "Path : ";
    for (int i : path) {
        cout << i << " ";
    }
    cout << endl;

    cout << "Cycle : ";
    for (int i : cycle) {
        cout << i << " ";
    }
    cout << endl;
}

void toPath(int v) {
    /*int left = 0, right = (int) path.size() - 1, mid;
    while (right - left > 1) {
        mid = (left + right) / 2;
        if (graph[path[mid]][v]) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    if (graph[path[mid]][v]) {
        mid++;
    }
    path.insert(path.begin() + mid, v);*/

    /*for (int i = 0; i < path.size(); i++) {
        if (not graph[path[i]][v]) {
            path.insert(path.begin() + i, v);
            break;
        }
    }*/

    auto p = path.begin();
    while (p != path.end() && graph[*p][v]) {
        ++p;
    }
    path.insert(p, v);
}

void toCycle() {
    int pos;
    for (pos = (int) path.size() - 1; pos >= 2; pos--) {
        if (graph[path[pos]][path[0]]) {
            break;
        }
    }
    cycle.insert(cycle.begin(), path.begin(), path.begin() + pos + 1);
    path.erase(path.begin(), path.begin() + pos + 1);

    //print();

    /*for (int i = 0; i < path.size(); i++) {
        for (int j = 0; j < cycle.size() - 1; j++) {
            if (not graph[cycle[i]][path[i]]) {
                cycle.insert(cycle.begin() + j, path.begin(), path.begin() + i + 1);
                path.erase(path.begin(), path.begin() + i + 1);
                i = 0; break;
            }
        }
    }*/

    for (auto p = path.begin(); p != path.end();) {
        auto c = cycle.begin();
        while (c != cycle.end() and graph[*c][*p]) {
            ++c;
        }
        if (c != cycle.end()) {
            cycle.insert(c, path.begin(), p + 1);
            path.erase(path.begin(), p + 1);
            p = path.begin();
        } else {
            p++;
        }
    }
}

/*
5

1
01
101
0110


5
0
10
011
1010
 */

int main() {
    scanf("%i", &n);
    graph.resize(n + 1, vector<bool>(n + 1));
    for (int i = 2; i <= n; i++) {
        for (int j = 1; j < i; j++) {
            char c;
            scanf(" %c", &c);
            if (c == '1') {
                graph[i][j] = true;
            } else {
                graph[j][i] = true;
            }
        }
    }
    path.push_back(1);
    for (int i = 2; i <= n; i++) {
        toPath(i);
    }

    //print();

    toCycle();

/*    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            cout << graph[i][j] << " ";
        }
        cout << endl;
    }*/

    for (int i : cycle) {
        cout << i << " ";
    }

    return 0;
}


