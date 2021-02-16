//
// Created by Vlad on 16.12.2020.
//
#include <iostream>
#include <random>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>
#include <stack>
#include <queue>
#include <set>
#include <random>
#include <ctime>

using namespace std;

struct edge {
    int from, to, w;
};

vector<vector<int>> G;
vector<int> colored1, colored2, d, pos;

const int INF = 1e9;
int n, m;

bool comparator(vector<int> &l, vector<int> &r) {
    return d[l[0]] > d[r[0]];
}

void solve(vector<int> &colored, vector<vector<int>> &graph) {
    for (int i = 1; i <= n; i++) {
        pos[graph[i][0]] = i;
    }

    int k = 1;
    for (int i = 1; i <= n; i++) {
        int v = graph[i][0];
        if (colored[v]) {
            continue;
        }
        colored[v] = k;
        while (count(graph[i].begin() + 1, graph[i].end(), 0)) {
            int j;
            for (j = 1; j <= n; j++) {
                if (v == j) continue;
                if (graph[i][j] == 0 and not colored[j]) break;
            }
            if (j == n + 1) {
                break;
            }
            colored[j] = k;
            for (int q = 1; q <= n; q++) {
                graph[i][q] = graph[i][q] or graph[pos[j]][q];
            }
        }
        k++;
    }
}

int main() {
    scanf("%i %i", &n, &m);
    G.resize(n + 1, vector<int>(n + 1));
    colored1.resize(n + 1);
    colored2.resize(n + 1);
    d.resize(n + 1);
    pos.resize(n + 1);

    for (int i = 0; i < m; i++) {
        int u, v;
        scanf("%i %i", &u, &v);
        G[u][v] = G[v][u] = 1;
        G[u][0] = u;
        G[v][0] = v;
        d[u]++;
        d[v]++;
    }
    int c = *max_element(begin(d), end(d));
    c % 2 ?: (c++);

    random_device rd;
    mt19937 g(rd());

    //sort(G.begin() + 1, G.end(), comparator);
    vector<vector<int>> graph1 = G;
    solve(colored1, graph1);
    int c1 = *max_element(begin(colored1), end(colored1));
    vector<int> colored = colored1;
    while (c1 > c) {
        shuffle(G.begin() + 1, G.end(), g);
        vector<vector<int>> graph2 = G;
        solve(colored2, graph2);
        int c2 = *max_element(begin(colored2), end(colored2));
        if (c2 < c1) {
            c1 = c2;
            colored = colored2;
        }
    }

    cout << c << endl;
    for (int i = 1; i <= n; i++) {
        cout << colored[i] << endl;
    }

    return 0;
}
/*
7 12
1 2
1 3
1 4
1 5
1 6
1 7
2 3
3 4
4 5
5 6
6 7
7 2


7 9
1 4
1 6
3 2
3 6
5 2
5 4
7 2
7 4
7 6
*/