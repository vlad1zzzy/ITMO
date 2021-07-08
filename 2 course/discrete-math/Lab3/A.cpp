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

vector<long long> p, q;

const int INF = 1e9;
const int MOD = 998244353;
int n, m;

vector<long long> add() {
    vector<long long> res(max(n, m) + 1);
    for (int i = 0; i <= max(n, m); i++) {
        res[i] = (i > n ? 0 : p[i]) + (i > m ? 0 : q[i]) % MOD;
        res[i] %= MOD;
        //res[i] = p[i] + q[i];
    }
    return res;
}

vector<long long> mul() {
    vector<long long> res(n + m + 2);
    for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= m; j++) {
            res[i + j] += p[i] * q[j] % MOD;
            res[i + j] += MOD;
            res[i + j] %= MOD;
        }
    }
    return res;
}

vector<long long> div() {
    vector<long long> qInverse(1001, 0);
    qInverse[0] = 1;
    for (int i = 1; i < 1001; i++) {
        long long k = 0;
        for (int j = 1; j <= i; j++) {
            if (j <= m) {
                k = (k + q[j] * qInverse[i - j] + MOD) % MOD;
            }
        }
        qInverse[i] = (-k + MOD) % MOD;
    }

    vector<long long> res(1002 + n, 0);
    for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= 1000; j++) {
            res[i + j] += p[i] * qInverse[j];
            res[i + j] += MOD;
            res[i + j] %= MOD;
        }
    }
    return res;
}

int main() {
    cin >> n >> m;
    p.resize(n + 1, 0);
    q.resize(m + 1, 0);
    for (int i = 0; i <= n; i++) {
        cin >> p[i];
    }
    for (int i = 0; i <= m; i++) {
        cin >> q[i];
    }
    vector<long long> added = add();

    unsigned long long addedMax = added.size() - 1;
    while (addedMax and not added[addedMax]) addedMax--;
    cout << addedMax << endl;
    for (int i = 0; i <= addedMax; i++) {
        cout << added[i] << " ";
    }
    cout << endl;

    vector<long long> muled = mul();
    unsigned long long muledMax = muled.size() - 1;
    while (muledMax and not muled[muledMax]) muledMax--;
    cout << muledMax << endl;
    for (int i = 0; i <= muledMax; i++) {
        cout << muled[i] << " ";
    }
    cout << endl;

    vector<long long> dived = div();
    for (int i = 0; i < 1000; i++) {
        cout << dived[i] << " ";
    }

    return 0;
}