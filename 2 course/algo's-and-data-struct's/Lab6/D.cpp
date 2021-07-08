//
// Created by Vlad on 21.05.2021.
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
long long n, m, k, e, C;

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

long long mul(long long a, long long b) {
    long long res = 0;
    while (b > 0) {
        if (b % 2 != 0) {
            res = (res + a) % n;
        }
        b /= 2;
        a = (a + a) % n;
    }
    return res;
}

long long pow(long long a, long long b) {
    long long res = 1;
    while (b > 0) {
        if (b % 2 == 1) {
            res = mul(res, a);
        }
        b /= 2;
        a = mul(a, a);
    }
    return res;
}

long long reversed(long long a, long long b) {
    long long x, y;
    long long g = gcd(a, b, x, y);
    return (x % b + b) % b;
}

long long findP() {
    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) {
            return i;
        }
    }
}

long long hack() {
    long long p = findP();
    long long q = n / p;
    long long phi = (p - 1) * (q - 1);
    long long d = reversed(e, phi);
    return pow(C, d);
}

int main() {
    cin >> n >> e >> C;
    cout << hack();
    return 0;
}