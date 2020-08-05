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

struct abIndexUsed {
    int a, b, index, used;
};

int comparator(abIndexUsed &first, abIndexUsed &second) {
    if (first.a > first.b and second.a > second.b)
        return first.b > second.b;
    else if (first.a > first.b and second.a <= second.b)
        return 0;
    else if (second.a > second.b)
        return 1;
    return first.a < second.a;
}

int main() {
    FILE *input = fopen("apples.in", "r");
    FILE *output = fopen("apples.out", "w");

    int n, s;
    vector<abIndexUsed> info;
    fscanf(input, "%i%i", &n, &s);
    for (int i = 0; i < n; i++) {
        int a, b;
        fscanf(input, "%i%i", &a, &b);
        struct abIndexUsed cur = {a, b, i + 1, 0};
        info.push_back(cur);
    }
    sort(info.begin(), info.end(), comparator);

    for (abIndexUsed q: info)
        cout << q.a << " " << q.b << endl;


    vector<int> ans;
    int used = 0;
    while (ans.size() < n){
        bool oneUsed = false;
        for (int i = 0; i < n; i++) {
            if (not info[i].used) {
                if (s - info[i].a > 0) {
                    used++;
                    s -= info[i].a - info[i].b;
                    ans.push_back(info[i].index);
                    info[i].used = 1;
                    oneUsed = true;
                }
            }
        }
        if (used == n)
            break;
        if (not oneUsed) {
            fprintf(output, "-1");
            return 0;
        }
    }
    for (int i: ans)
        fprintf(output, "%i ", i);

    return 0;
}