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

vector<long long> a, c, r;

const int INF = 1e9;
const int MOD = 104857601;
long long n, m, k;

vector<long long> mul(const vector<long long> &left, const vector<long long> &right) {
    vector<long long> res(left.size() + right.size() + 2);
    for (int i = 0; i < left.size(); i++) {
        for (int j = 0; j < right.size(); j++) {
            res[i + j] += left[i] * right[j] % MOD;
            res[i + j] += MOD;
            res[i + j] %= MOD;
        }
    }
    return res;
}

int main() {
    cin >> k >> n;
    n--;
    a.resize(k * 2, 0);
    c.resize(k + 1, 0);
    c[0] = 1;
    for (int i = 0; i < k; i++) {
        cin >> a[i];
    }
    int cur;
    for (int i = 1; i <= k; i++) {
        cin >> cur;
        c[i] = (MOD - cur) % MOD;
    }

    vector<long long> q;
    while (n >= k) {
        for (int i = k; i < k * 2; i++) {
            a[i] = 0;
            for (int j = 1; j <= k; j++) {
                a[i] -= (c[j] * a[i - j]) % MOD;
                a[i] %= MOD;
                if (a[i] < 0) {
                    a[i] += MOD;
                }
            }
        }
        /*for (int i = 0; i < k * 2; i++) {
            cout << "A{" << i << "} = " << a[i] << endl;
        }
        for (int i = 0; i <= k; i++) {
            cout << "C{" << i << "} = " << c[i] << endl;
        }*/
        // Q(-t)
        q.resize(k + 1, 0);
        for (int i = 0; i <= k; i++) {
            if (i % 2 == 0) {
                q[i] = c[i];
            } else {
                q[i] = (MOD - c[i]) % MOD;
            }
        }
        // R = Q(t) * Q(-t)
        r = mul(c, q);
        // filer a[i]
        for (int i = n % 2; i < k * 2; i += 2) {
            a[i / 2] = a[i];
        }
        // Q = R(sqrt(t))
        for (int i = 0; i <= k; i++) {
            if (i * 2 >= r.size()) {
                c[i] = 0;
            } else {
                c[i] = r[i * 2];
            }
        }
        n /= 2;
    }
    /*for (int t : a) {
        cout << t << ' ';
    }
    cout << endl;*/
    if (n >= k * 2) {
        cout << 0;
    } else {
        cout << a[n];
    }
    return 0;
}