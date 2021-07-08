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

vector<long long> factorials;

long long comb(long long n, long long k) {
    long long f = 1;
    for (long long i = n - k + 1; i <= n; i++) {
        f *= i;
    }
    return f / factorials[k];
}


vector<long long> parse() {
    vector<long long> weights(7, 0);
    char ch, opener, closer;
    cin >> ch;
    if (ch == 'B') {
        weights[1] = 1;
    }
    if (ch == 'L' || ch == 'S') {
        vector<long long> inner;
        cin >> opener;
        inner = parse();
        cin >> closer;
        weights[0] = 1;
        if (ch == 'L') {
            for (int i = 1; i < 7; i++) {
                for (int j = 1; j <= i; j++) {
                    weights[i] += inner[j] * weights[i - j];
                }
            }
        } else {
            vector<vector<long long>> mnk (7, vector<long long> (7, 0));
            for (int i = 0; i < 7; i++) {
                mnk[0][i] = 1;
            }
            for (int n = 1; n < 7; n++) {
                for (int k = 1 ; k < 7; k++) {
                    for (int i = 0; i <= n / k; i++) {
                        mnk[n][k] += comb(max(inner[k] + i - 1, (long long) 0), i) * mnk[n - i * k][k - 1];
                    }
                }
                weights[n] = mnk[n][n];
            }
        }
    }
    if (ch == 'P') {
        vector<long long> inner1, inner2;
        char p;
        cin >> opener;
        inner1 = parse();
        cin >> p;
        inner2 = parse();
        cin >> closer;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <= i; j++) {
                weights[i] += inner1[j] * inner2[i - j];
            }
        }
    }
    /*cout << ch << ' ';
    for (int w : weights) {
        cout << w << " ";
    }
    cout << endl;*/
    return weights;
}

int main() {
    factorials.resize(20);
    factorials[0] = 1;
    for (int i = 1; i < 20; i++) {
        factorials[i] = factorials[i - 1] * i;
    }

    vector<long long> weights = parse();
    for (long long w : weights) {
        cout << w << " ";
    }
    return 0;
}