//
// Created by Vlad on 17.10.2020.
//

#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>

using namespace std;

vector<int> visited, ans, color;
vector<vector<int>> edges;

void dfs(int v) {
    visited[v] = 1;
    for (int i : edges[v]) {
        if (not visited[i])
            dfs(i);
    }
    ans.push_back(v);
}

void hasCycle(int v) {
    color[v] = 1;
    for (int i : edges[v]) {
        if (color[i] == 0)
            hasCycle(i);
        else if (color[i] == 1) {
            printf("-1");
            exit(0);
        }
    }
    color[v] = 2;
}

void topSort(int n) {
    for (int i = 1; i < n + 1; i++)
        if (color[i] != 2)
            hasCycle(i);
    for (int i = 1; i < n + 1; i++) {
        if (not visited[i])
            dfs(i);
    }
    reverse(ans.begin(), ans.end());
}

int main() {
    int n, m, start, end;
    scanf("%i %i", &n, &m);
    visited.resize(n + 1);
    edges.resize(n + 1);
    color.resize(n + 1);

    for (int i = 0; i < m; i++) {
        scanf("%i %i", &start, &end);
        edges[start].push_back(end);
    }

    topSort(n);
    for (int v : ans)
        printf("%i ", v);

}