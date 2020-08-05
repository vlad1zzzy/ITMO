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

const int INF = 2e9;
const double eps = 0e-10;
const int N = 40000;

bool comparator(const pair<int,double> &left, const pair<int,double> &right){
    return left.second > right.second;
}

int main() {
    FILE *input = fopen("sequence.in", "r");
    FILE *output = fopen("sequence.out", "w");
    int n, num;
    int sum = 0;
    vector<pair<int, int>> nums;
    fscanf(input, "%i", &n);
    for (int i = 0; i < n; i++) {
        fscanf(input, "%i", &num);
        pair<int, int> cur = make_pair(i, num);
        nums.push_back(cur);
        sum += num;
    }
    if (sum % 2) {
        fprintf(output, "-1");
        return 0;
    }
    sum /= 2;
    sort(nums.begin(), nums.end(), comparator);

    int count = 0;
    int currentSum = 0;
    vector<int> ans;
    for (int i = 0; i < n; i++) {
        if (currentSum + nums[i].second <= sum) {
            currentSum += nums[i].second;
            count++;
            ans.push_back(nums[i].first + 1);
        }
    }
    if (currentSum == sum) {
        fprintf(output, "%i\n", count);
        for (int number: ans)
            fprintf(output, "%i ", number);
    } else {
        fprintf(output, "-1");
    }
    return 0;
}