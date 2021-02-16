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

vector<int> visited, enter, ret, dots;
vector<vector<int>> edges;
int time = 0;

void dfs(int v, int p) {
    visited[v] = 1;
    enter[v] = ret[v] = time++;
    int count = 0;
    for (int i : edges[v]) {
        if (i == p) continue;
        if (visited[i])
            ret[v] = min(ret[v], enter[i]);
        else {
            dfs(i, v);
            count++;
            ret[v] = min(ret[v], ret[i]);
            if (p != -1 and ret[i] >= enter[v]) {
                dots.push_back(v);
            }
        }
    }
    if (p == -1 and count > 1)
        dots.push_back(v);
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
        edges[start].push_back(end);
        edges[end].push_back(start);
    }

    for (int i = 1; i < n + 1; i++) {
        if (not visited[i])
            dfs(i, -1);
    }

    sort(dots.begin(), dots.end());
    dots.erase(unique(dots.begin(),dots.end()),dots.end());
    int size = dots.size();
    printf("%i\n", size);
    for (int i : dots)
        printf("%i ", i);
}