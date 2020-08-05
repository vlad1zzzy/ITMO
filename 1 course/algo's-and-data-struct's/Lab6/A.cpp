//
// Created by vlad on 18.05.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>

using namespace std;

int dp[100002][18];
vector<vector<int>> tree;

void updateDp(int x) {
    for (int i = 1; i < 18; i++)
        dp[x][i] = dp[dp[x][i - 1]][i - 1];
    for (int i : tree[x]) {
        updateDp(i);
    }
}

int main() {
    int n, k;
    cin >> n;
    tree.resize(n + 1);
    for (int i = 1; i <= n; i++) {
        cin >> k;
        tree[k].push_back(i);
        dp[i][0] = k;
    }
    updateDp(0);
    for (int i = 1; i <= n; i++) {
        cout << i << ": ";
        int cur = 0;
        while (true) {
            if (dp[i][cur] <= 0)
                break;
            cout << dp[i][cur++] << " ";
        }
        cout << endl;
    }
}