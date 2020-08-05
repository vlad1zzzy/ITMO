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

int main() {
    FILE *input = fopen("cobbler.in", "r");
    FILE *output = fopen("cobbler.out", "w");
    int n, k, cur;
    int ans = 0;
    vector<int> times;
    fscanf(input, "%i %i", &k, &n);
    for (int i = 0; i < n; i++) {
        fscanf(input, "%i", &cur);
        times.push_back(cur);
    }
    sort(times.begin(), times.end());
    int index = 0;
    while (index < n && ans < k) {
        if (ans + times[index] > k)
            break;
        ans += times[index++];
    }
    fprintf(output, "%i", index);
}