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
vector<int> parent, degree;

const int INF = 1e9;
int n, m;

void dfs(int v) {
    for (int i = 0; i < graph[v].size(); i++) {
        int to = graph[v][i];
        if (to != parent[v]) {
            parent[to] = v;
            dfs(to);
        }
    }
}

int main() {
    int from, to;
    scanf("%i", &n);
    graph.resize(n + 1, vector<int>());
    parent.resize(n + 1);
    degree.resize(n + 1);
    parent[n] = -1;
    for (int i = 1; i < n; i++) {
        scanf("%i %i", &from, &to);
        graph[from].push_back(to);
        graph[to].push_back(from);
    }
    dfs(n);
    /*for (int i = 1; i <= n; i++) {
        cout << parent[i] << " ";
    }
    cout << endl;*/
    int flag = -1;
    for (int i = 1; i <= n; ++i) {
        degree[i] = graph[i].size();
        if (degree[i] == 1 && flag == -1) {
            flag = i;
        }
    }

    vector<int> ans;
    int v = flag;
    for (int i = 0; i < n - 2; i++) {
        int next = parent[v];
        ans.push_back(next);
        degree[next]--;
        if (degree[next] == 1 && next < flag) {
            v = next;
        } else {
            flag++;
            while (flag < n && degree[flag] != 1)
                flag++;
            v = flag;
        }
    }
    for (int i = 0; i < n - 2; i++) {
        cout << ans[i] << " ";
    }
    return 0;
}