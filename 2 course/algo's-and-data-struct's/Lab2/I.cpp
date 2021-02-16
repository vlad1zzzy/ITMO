//
// Created by Vlad on 18.11.2020.
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

vector<vector<int>> edges, edgesR;
map<int, bool> pos;
vector<int> visited, ans, winnerPos, edgesCount;

const int INF = 1e9;
int n, m;


void dfs(int v) {
    visited[v] = 1;
    for (int i : edgesR[v]) {
        if (not visited[i]) {
            if (winnerPos[v] == 2) {
                winnerPos[i] = 1;
            } else if (--edgesCount[i] == 0) {
                winnerPos[i] = 2;
            } else {
                continue;
            }
            dfs(i);
        }
    }
}

int main() {
    int from, to;
    while(cin >> n >> m) {
        visited.resize(n + 1);
        edges.resize(n + 1);
        edgesR.resize(n + 1);
        edgesCount.resize(n + 1);
        winnerPos.resize(n + 1);

        for (int i = 0; i < m; i++) {
            //scanf("%i %i", &from, &to);
            cin >> from >> to;
            edges[from].push_back(to);
            edgesCount[from]++;
            edgesR[to].push_back(from);
        }
        for (int i = 1; i <= n; i++) {
            if (not edgesCount[i]) {
                winnerPos[i] = 2;
                dfs(i);
            }
        }

        for (int i = 1; i <= n; i++) {
            if (not winnerPos[i])
                printf("DRAW\n");
            else
                winnerPos[i] == 1 ? printf("FIRST\n") : printf("SECOND\n");
        }
        printf("\n");
        visited.clear();
        edges.clear();
        edgesR.clear();
        edgesCount.clear();
        winnerPos.clear();
    }
}

