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

long long mul(long long a, long long b, long long mod) {
    long long res = 0;
    while (b > 0) {
        if ((b & 1) > 0) {
            res = (res + a) % mod;
        }
        b >>= 1;
        a = (a + a) % mod;
    }
    return res;
}

long long pow(long long a, long long b, long long mod) {
    long long res = 1;
    while (b > 0) {
        if ((b & 1) > 0) {
            res = mul(res, a, mod);
        }
        b >>= 1;
        a = mul(a, a, mod);
    }
    return res;
}

bool test(long long value) {
    if (value < 4) {
        return value > 1;
    }
    if (value % 2 == 0) {
        return false;
    }
    long long power = 0, y = value - 1;
    while (y % 2 == 0) {
        ++power;
        y /= 2;
    }
    for (int i = 0; i < 5; i++) {
        long long a = (rand() % (value - 2)) + 2;
        long long test = pow(a, y, value);
        if (test != 1 and test != value - 1) {
            for (int j = 1; j < power; j++) {
                test = pow(test, 2, value);
                if (test == 1) {
                    return false;
                }
                if (test == value - 1) {
                    break;
                }
            }
            if (test != value - 1) {
                return false;
            }
        }
    }
    return true;
}

int main() {
    cin >> n;
    //srand(time(nullptr));
    long long value;
    for (int i = 0; i < n; i++) {
        cin >> value;
        if (test(value)) {
            printf("YES\n");
        } else {
            printf("NO\n");
        }
    }
    return 0;
}