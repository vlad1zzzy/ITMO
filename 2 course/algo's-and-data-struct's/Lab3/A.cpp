//
// Created by Vlad on 22.12.2020.
//

#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>
#include <stack>
#include <queue>

using namespace std;

vector<long long> pows, hashs;

const int INF = 1e9, p = 31;
int n, m;


int main() {
    string s;
    int a, b, c, d;
    cin >> s >> n;
    pows.resize(s.length());
    hashs.resize(s.length());
    pows[0] = 1;
    hashs[0] = s[0] - 'a' + 1;
    for (int i = 1; i < s.length(); i++) {
        pows[i] = pows[i - 1] * p;
        hashs[i] = (s[i] - 'a' + 1) * pows[i] + hashs[i - 1];
    }
    for (int i = 0; i < n; i++) {
        scanf("%i %i %i %i", &a, &b, &c, &d);
        if (b - a != d - c) {
            printf("No\n");
        } else if (a == c) {
            printf("Yes\n");
        } else {
            long long hash1 = hashs[b - 1];
            long long hash2 = hashs[d - 1];
            if (a - 1) {
                hash1 -= hashs[a - 2];
            }
            if (c - 1) {
                hash2 -= hashs[c - 2];
            }
            if ((a < c and hash1 * pows[c - a] == hash2) ||( a > c and hash1 == hash2 * pows[a - c])) {
                printf("Yes\n");
            } else {
                printf("No\n");
            }
        }
    }
}