//
// Created by Vlad on 19.05.2021.
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
long long n, m, k;
set<long long> primes;

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

long long reversed(long long a, long long b) {
    long long x, y;
    long long g = gcd(a, b, x, y);
    return (x % b + b) % b;
}


int main() {
    int a, b;
    cin >> a >> b >> n >> m;
    long long mod = n * m;
    // ((a * (m^-1 mod n) * m) % mod + (b * (n^-1 mod m) * n) * mod) % mod
    cout << ((a * reversed(m, n) * m) % mod + (b * reversed(n, m) * n) % mod) % mod;
    return 0;
}