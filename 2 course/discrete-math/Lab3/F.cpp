//
// Created by Vlad on 22.03.2021.
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

vector<long long> c;

const int INF = 1e9 + 7;
int n, m, k;


int main() {
    cin >> k >> m;
    c.resize(m + 1);
    int ci;
    for (int i = 0; i < k; i++) {
        cin >> ci;
        c[ci] = 1;
    }

    vector<long long> res(m + 1), dp(m + 1);
    res[0] = dp[0] = 1;
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= i; j++) {
            if (c[j]) {
                res[i] = (res[i] + dp[i - j]) % INF;
            }
        }

        for (int j = 0; j <= i; j++) {
            dp[i] = (dp[i] + res[j] * res[i - j]) % INF;
        }
        cout << res[i] << " ";
    }

    return 0;
}