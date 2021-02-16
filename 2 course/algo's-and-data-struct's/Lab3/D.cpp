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
vector<int> subs;

const int INF = 1e9;
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
    string s, p, t;
    cin >> p >> t;
    s = p + "#" + t;
    n = s.length();
    m = p.length();
    prefix.resize(n);
    prefix_function(s);
    for (int i = m; i < n; i++) {
        if (prefix[i] == m) {
            subs.push_back(i + 1 - 2 * m);
        }
    }
    cout << subs.size() << endl;
    for (int sub : subs) {
        cout << sub << " ";
    }
}