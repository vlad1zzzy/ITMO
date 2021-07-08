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
const long long MOD = 1e12;
int n, m, k;


vector<long long> mul() {
    vector<long long> res(k + 1);
    for (int i = 0; i < k; i++) {
        for (int j = 0; j < k + 1; j++) {
            if (i + j <= k) {
                res[i + j] += p[i] * q[j];
            }
        }
    }
    return res;
}

int main() {
    cin >> k;
    p.resize(k);
    q.resize(k + 1);
    for (int i = 0; i < k; i++) {
        cin >> p[i];
    }
    q[0] = 1;
    int c;
    for (int i = 1; i <= k; i++) {
        cin >> c;
        q[i] = -c;
    }
    p = mul();

    int pMax = k - 1;
    while (pMax and not p[pMax]) pMax--;
    cout << pMax << endl;
    for (int i = 0; i <= pMax; i++) {
        cout << p[i] << " ";
    }
    cout << endl;

    int qMax = q.size() - 1;
    while (qMax and not q[qMax]) qMax--;
    cout << qMax << endl;
    for (int i = 0; i <= qMax; i++) {
        cout << q[i] << " ";
    }
    return 0;
}