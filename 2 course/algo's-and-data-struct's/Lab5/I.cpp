//
// Created by Vlad on 28.04.2021.
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

vector<vector<int>> graph;

int main() {
    int r1, r2, s1, s2, p1, p2;
    cin >> r1 >> s1 >> p1 >> r2 >> s2 >> p2;
    cout << max(r1 - r2 - p2, max(s1 - s2 - r2, max(p1 - p2 - s2, 0)));
    return 0;
}