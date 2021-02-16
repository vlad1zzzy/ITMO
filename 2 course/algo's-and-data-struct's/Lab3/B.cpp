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

vector<long long> prefix;

const int INF = 1e9, p = 31;
int n, m;

void prefix_function(string s) {
    for (int i = 1; i < s.length(); i++) {
        int j = prefix[i - 1];
        while (j > 0 && s[i] != s[j]) {
            j = prefix[j - 1];
        }
        if (s[i] == s[j]) {
            j++;
        }
        prefix[i] = j;
    }
}

int main() {
    string s;
    cin >> s;
    prefix.resize(s.length());
    prefix_function(s);
    for (int i : prefix) {
        printf("%i ", i);
    }
}