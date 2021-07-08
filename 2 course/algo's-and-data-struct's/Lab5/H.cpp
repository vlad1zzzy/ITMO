//
// Created by Vlad on 28.04.2021.
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
#include <iomanip>

using namespace std;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m, k;

vector<vector<int>> graph;

int main() {
    cin >> n;
    graph.resize(n + 1, vector<int>(n + 1));
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            cin >> graph[i][j];
        }
    }
    vector<int> first(n + 1), second(n + 1), index(n + 1), way(n + 1);
    for (int i = 1; i <= n; i++) {
        int j0 = 0;
        index[0] = i;
        vector<char> used(n + 1, false);
        vector<int> minimum(n + 1, INF);
        do {
            used[j0] = true;
            int i0 = index[j0], delta = INF, j1;
            for (int j = 1; j <= n; ++j)
                if (!used[j]) {
                    int cur = graph[i0][j] - first[i0] - second[j];
                    if (cur < minimum[j])
                        minimum[j] = cur, way[j] = j0;
                    if (minimum[j] < delta)
                        delta = minimum[j], j1 = j;
                }
            for (int j = 0; j <= n; j++)
                if (used[j])
                    first[index[j]] += delta, second[j] -= delta;
                else
                    minimum[j] -= delta;
            j0 = j1;
        } while (index[j0] != 0);
        do {
            int j1 = way[j0];
            index[j0] = index[j1];
            j0 = j1;
        } while (j0);
    }
    long long sum = 0;
    for (int i = 1; i <= n; i++) {
        sum += graph[index[i]][i];
    }
    cout << sum << '\n';
    for (int i = 1; i <= n; i++) {
        cout << index[i] << ' ' << i << '\n';
    }
    return 0;
}