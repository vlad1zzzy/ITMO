//
// Created by vlad on 04.06.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>

using namespace std;

bool comparator(const pair<int,int> &left, const pair<int,int> &right){
    return (left.second < right.second);
}

int main() {
    FILE *input = fopen("request.in", "r");
    FILE *output = fopen("request.out", "w");
    int n, s, f;
    int ans = 0;
    pair<int, int> cur;
    vector<pair<int, int>> times;
    fscanf(input, "%i", &n);
    for (int i = 0; i < n; i++) {
        fscanf(input, "%i %i", &s, &f);
        cur = make_pair(s, f);
        times.push_back(cur);
    }
    sort(times.begin(), times.end(), comparator);
    int lastEnd = 0;
    for (int i = 0; i < n; i++) {
        if (times[i].first < lastEnd)
            continue;
        ans++;
        lastEnd = times[i].second;
    }
    fprintf(output, "%i", ans);
}