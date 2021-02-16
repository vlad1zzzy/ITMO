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

vector<int> visited, enter, ret, bridges;
vector<vector<pair<int, int>>> edges;
int time = 0;

void dfs(int v, int p) {
    visited[v] = 1;
    enter[v] = ret[v] = time++;
    for (pair<int, int> i : edges[v]) {
        if (i.first == p) continue;
        if (visited[i.first])
            ret[v] = min(ret[v], enter[i.first]);
        else {
            dfs(i.first, v);
            ret[v] = min(ret[v], ret[i.first]);
            if (ret[i.first] > enter[v]) {
                bridges.push_back(i.second);
            }
        }
    }
}


int main() {
    int n, m, start, end;
    scanf("%i %i", &n, &m);
    visited.resize(n + 1);
    enter.resize(n + 1);
    ret.resize(n + 1);
    edges.resize(n + 1);

    for (int i = 0; i < m; i++) {
        scanf("%i %i", &start, &end);
        edges[start].push_back(make_pair(end, i + 1));
        edges[end].push_back(make_pair(start, i + 1));
    }

    for (int i = 1; i < n + 1; i++) {
        if (not visited[i])
            dfs(i, -1);
    }
    int size = bridges.size();
    printf("%i\n", size);
    sort(bridges.begin(), bridges.end());
    for (int i : bridges)
        printf("%i ", i);
}