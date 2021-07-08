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
int n, m, k;


int main() {
    cin >> n;
    int d = 2;
    while (d * d <= n) {
        if (n % d == 0) {
            cout << d << " ";
            n /= d;
            continue;
        }
        d++;
    }
    if (n > 1) {
        cout << n;
    }
    return 0;
}