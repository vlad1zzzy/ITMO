//
// Created by Vlad on 16.04.2021.
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

vector<long long> p, q, sq, e, ln;

const int INF = 1e9;
const long long MOD = 998244353;
int n, m;


long long gcd(long long a, long long b, long long &x, long long &y) {
    if (a == 0) {
        x = 0;
        y = 1;
        return b;
    }
    long long x1, y1;
    long long d = gcd(b % a, a, x1, y1);
    x = y1 - (b / a) * x1;
    y = x1;
    return d;
}

long long reverse(long long a) {
    long long x, y;
    long long g = gcd(a, MOD, x, y);
    if (g != 1) {
        cout << "no solution";
    } else {
        x = (x % MOD + MOD) % MOD;
        return x;
    }
}

long long comb(long long k) {
    long long a = 1, b = 1;
    for (int i = 0; i < k; i++) {
        a = (a * (1 - 2 * i + MOD) % MOD) % MOD;
        b = (b * 2 * (i + 1)) % MOD;
    }
    return (a * reverse(b) + MOD) % MOD;
}

vector<long long> mul(const vector<long long> &left, const vector<long long> &right) {
    int size = min(m, (int) (left.size() + right.size() + 2));
    vector<long long> res(size);
    for (int i = 0; i < left.size(); i++) {
        for (int j = 0; j < right.size(); j++) {
            if (i + j < size) {
                res[i + j] += left[i] * right[j] % MOD;
                res[i + j] += MOD;
                res[i + j] %= MOD;
            }
        }
    }
    return res;
}

int main() {
    cin >> n >> m;
    p.resize(n + 1, 0);
    sq.resize(m + 1, 0);
    sq[0] = 1;
    e.resize(m + 1, 0);
    e[0] = 1;
    ln.resize(m + 1, 0);
    for (int i = 0; i <= n; i++) {
        cin >> p[i];
    }

    vector<long long> base(1);
    base[0] = 1;
    long long sqrtK, eK = 1, lnK = MOD - 1;
    for (int i = 1; i < m; i++) {
        base = mul(base, p);
        sqrtK = comb(i);
        eK = (eK * i) % MOD;
        lnK = -lnK + MOD;
        for (int j = 0 ; j < m && j < base.size(); j++) {
            sq[j] = (sq[j] + (sqrtK * base[j]) % MOD) % MOD;
            e[j] = (e[j] + (reverse(eK) * base[j]) % MOD) % MOD;
            ln[j] = (ln[j] + ((lnK * reverse(i)) % MOD * base[j]) % MOD) % MOD;
        }
    }
    for (int i = 0; i < m; i++) {
        cout << sq[i] << " ";
    }
    cout << endl;
    for (int i = 0; i < m; i++) {
        cout << e[i] << " ";
    }
    cout << endl;
    for (int i = 0; i < m; i++) {
        cout << ln[i] << " ";
    }
    return 0;
}