//
// Created by Vlad on 27.12.2020.
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

vector<pair<int, int>> tasks;
set<int> table;

const int INF = 1e9;
const long long INF_ll = 1e15;
int n, m;

bool comparator(pair<int, int> l, pair<int, int> r) {
    if (l.second == r.second)
        return l.first < r.first;
    return  l.second > r.second;
}

int main() {
    int d, w;
    long long res = 0;
    freopen("schedule.in", "r", stdin);
    freopen("schedule.out", "w", stdout);
    cin >> n;
    tasks.resize(n);
    for (int i = 0; i < n; i++) {
        cin >> d >> w;
        tasks[i] = {d, w};
        table.insert(i + 1);
    }
    sort(tasks.begin(), tasks.end(), comparator);

    for (pair<int, int> task : tasks) {
        auto time = table.upper_bound(task.first);
        if (time == table.begin()) {
            res += task.second;
        } else {
            time--;
            table.erase(time);
        }
        //cout << task.first << " " << task.second << endl;
    }
    cout << res;
    return 0;
}


