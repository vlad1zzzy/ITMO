//
// Created by Vlad on 10.03.2021.
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

vector<vector<int>> graph, haters;
vector<int> used, px, py, hateAlone;
vector<pair<int, int>> ans;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m, k;

bool dfs(int x) {
    if (used[x]) { return false; }
    used[x] = true;
    for (int y : graph[x]) {
        y -= n;
        if (py[y] == -1) {
            py[y] = x;
            px[x] = y;
            return true;
        } else {
            if (dfs(py[y])) {
                py[y] = x;
                px[x] = y;
                return true;
            }
        }
    }
    return false;
}

void fordFulkerson() {
    px.resize(n + 1, -1);
    py.resize(m + 1, -1);
    bool isPath = true;
    while (isPath) {
        isPath = false;
        used.assign(n + 1, false);
        for (int i = 1; i <= n; i++) {
            if (px[i] == -1) {
                if (dfs(i)) {
                    isPath = true;
                }
            }
        }
    }
}

int main() {
    int from, to, count = 0, t, q;
    cin >> n >> m >> k;
    graph.resize(n + 1);
    used.resize(n + 1);
    haters.resize(n + 1, vector<int>(m + 1));
    hateAlone.resize(n + m + 1);
    cin >> t;
    for (int i = 0; i < t; i++) {
        cin >> from >> to;
        haters[from][to - n] = 1;
    }
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            if (haters[i][j] == 0) {
                //cout << "i: " << i << " j: " << j << " n+j: " << (n+j) << endl;
                graph[i].push_back(n + j);
            }
        }
    }
    cin >> q;
    for (int i = 0; i < q; i++) {
        cin >> from;
        hateAlone[from] = 1;
    }

    /*cout << "GRAPH " << endl;
    for (int i = 1; i <= n; i++) {
        for (int j : graph[i]) {
            cout << j << " ";
        }
        cout << endl;
    }
    cout << "/GRAPH" << endl;*/

    fordFulkerson();


    vector<pair<int, int>> ans;
    //add matches with hateAlone dragons
    for (int i = 1; i <= n; i++) {
        if (px[i] == -1 and hateAlone[i]) {
            cout << "NO";
            return 0;
        } else if (hateAlone[i]) {
            ans.push_back({i, px[i] + n});
            //took pair and next skip if need
            py[px[i]] = -2;
            px[i] = -2;
            count++;
        }
    }
    //cout << count << "count" << endl;


    for (int i = 1; i <= m; i++) {
        if (py[i] == -2) {
            continue;
        } else if (py[i] == -1 and hateAlone[i]) {
            cout << "NO";
            return 0;
        } else if (hateAlone[i + n]) {
            ans.push_back({py[i], i + n});
            //took pair and next skip if need
            py[px[i]] = -2;
            px[i] = -2;
            count++;
        }
    }
    //cout << count << "count" << endl;
    if (count > k) {
        cout << "NO";
        return 0;
    } else if (count < k) {
        //add all others matches
        for (int i = 1; i <= n; i++) {
            if (px[i] != -2 and px[i] != -1) {
                ans.push_back({i, px[i] + n});
                py[px[i]] = -2;
                px[i] = -2;
                count++;
            }
            if (count == n) {
                break;
            }
        }
    }
    //if all matches added and need to add two single vertexes
    if (count != k) {
        vector<int> free1, free2;
        for (int i = 1; i <= n; i++) {
            if (px[i] != -2) {
                free1.push_back(i);
            }
        }
        for (int i = 1; i <= m; i++) {
            if (py[i] != -2) {
                free1.push_back(i + n);
            }
        }
        if (free1.size() < (k - count) || free2.size() < (k - count)) {
            cout << "NO";
            return 0;
        }
        int cur = 0;
        while (count < k and cur < free1.size() and cur < free2.size()) {
            ans.push_back({free1[cur], free2[cur]});
        }
    }
    if (count != k) {
        cout << "NO";
    } else {
        cout << "YES" << endl;
        for (pair<int, int> pair : ans) {
            cout << pair.first << " " << pair.second << endl;
        }
    }

    return 0;
}