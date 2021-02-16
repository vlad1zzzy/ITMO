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

vector<int> z;

const int INF = 1e9, p = 31;
int n, m;

void z_function(string s) {
    int l = 0, r = 0;
    for (int i = 1; i < s.length(); i++) {
        if (i <= r) {
            z[i] = min(r - i + 1, z[i - l]);
        }
        while (i + z[i] < s.length() && s[z[i]] == s[i + z[i]]) {
            z[i]++;
        }
        if (i + z[i] - 1 > r) {
            l = i, r = i + z[i] - 1;
        }
    }
}

int main() {
    string s;
    cin >> s;
    z.resize(s.length());
    z_function(s);
    for (int i = 1; i < z.size(); i++) {
        printf("%i ", z[i]);
    }
}

