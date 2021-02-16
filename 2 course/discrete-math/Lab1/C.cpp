//
// Created by Vlad on 08.12.2020.
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

using namespace std;

struct edge {
    int from, to, w;
};

vector<int> ans;

const int INF = 1e9;
int n, m;

bool comparator(int l, int r) {
    string answer;
    printf("1 %i %i\n", l, r);
    cin >> answer;
    return answer[0] == 'Y';
}

vector<int> merge(vector<int> &left, vector<int> &right) {
    vector<int> result;
    while (!left.empty() || !right.empty()) {
        if (!left.empty() && !right.empty()) {
            if (comparator(left.front(), right.front())) {
                result.push_back(left.front());
                left.erase(left.begin());
            } else {
                result.push_back(right.front());
                right.erase(right.begin());
            }
        } else if (!left.empty()) {
            for (int &i : left)
                result.push_back(i);
            break;
        } else if (!right.empty()) {
            for (int &i : right)
                result.push_back(i);
            break;
        }
    }
    return result;
}

vector<int> mergeSort(vector<int> &res) {
    if (res.size() <= 1)
        return res;

    vector<int> left, right, result;
    int middle = ((int) res.size() + 1) / 2;

    for (int i = 0; i < middle; i++) {
        left.push_back(res[i]);
    }

    for (int i = middle; i < res.size(); i++) {
        right.push_back(res[i]);
    }

    left = mergeSort(left);
    right = mergeSort(right);
    result = merge(left, right);

    return result;
}


int main() {
    scanf("%i", &n);
    ans.resize(n);
    for (int i = 0; i < n; i++) {
        ans[i] = i + 1;
    }

    //sort(ans.begin(), ans.end(), comparator);
    ans = mergeSort(ans);

    printf("0 ");
    for (int i = 0; i < n; i++) {
        printf("%i ", ans[i]);
    }

    return 0;
}