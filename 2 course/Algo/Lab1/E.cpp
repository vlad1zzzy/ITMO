//
// Created by Vlad on 17.10.2020.
//

#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>
#include <stack>

using namespace std;

vector<int> visited, enter, ret, ans;
vector<vector<pair<int, int>>> edges;

int time = 0;
int maxColor = 0;


void dfs(int v, int lastEdge) {
    visited[v] = 1;
    enter[v] = ret[v] = time++;
    for (pair<int, int> edge : edges[v]) {
        int i = edge.first;
        int indEdge = edge.second;
        if (indEdge == lastEdge) continue;
        if (visited[i])
            ret[v] = min(ret[v], enter[i]);
        else {
            dfs(i, indEdge);
            ret[v] = min(ret[v], ret[i]);
        }
    }
}

void paint(int v, int lastEdge, int color) {
    visited[v] = 1;
    for (pair<int, int> edge : edges[v]) {
        int i = edge.first;
        int indEdge = edge.second;
        if (indEdge == lastEdge) continue;
        if (not visited[i]) {
            if (ret[i] >= enter[v]) {
                ++maxColor;
                ans[edge.second] = maxColor;
                paint(i, indEdge, maxColor);
            } else {
                ans[edge.second] = color;
                paint(i, indEdge, color);
            }
        } else if (enter[i] < enter[v]) {
            ans[edge.second] = color;
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
    ans.resize(m + 1);

    for (int i = 0; i < m; i++) {
        scanf("%i %i", &start, &end);
        edges[start].push_back(make_pair(end, i + 1));
        edges[end].push_back(make_pair(start, i + 1));
    }

    for (int i = 1; i < n + 1; i++) {
        if (not visited[i])
            dfs(i, -1);
    }
    visited.clear();
    visited.resize(n + 1);
    for (int i = 1; i < n + 1; i++) {
        if (not visited[i]) {
            paint(i, -1, maxColor);
        }
    }

    printf("%i\n", maxColor);
    for (int i = 1; i <= m; i++)
        printf("%i ", ans[i]);
}