//
// Created by Vlad on 15.11.2020.
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

vector<vector<int>> edges;
vector<int>  mex;

const int INF = 1e9;
int n, m;


void dfs(int v) {
    set<int> counter;
    for (int i : edges[v]) {
        if (mex[i] == -1)
            dfs(i);
        counter.insert(mex[i]);
    }
    for (int i = 0; i < n; i++)
        if (not counter.count(i)) {
            mex[v] = i;
            break;
        }

}


void topSort() {
    for (int i = 1; i < n + 1; i++) {
        if (mex[i] == -1)
            dfs(i);
    }
}

int main() {
    int from, to;

    scanf("%i %i", &n, &m);

    mex.resize(n + 1, -1);
    edges.resize(n + 1);

    for (int i = 0; i < m; i++) {
        scanf("%i %i", &from, &to);
        edges[from].push_back(to);
    }
    topSort();

    for (int i = 1; i <= n; i++)
        printf("%i\n", mex[i]);
}

