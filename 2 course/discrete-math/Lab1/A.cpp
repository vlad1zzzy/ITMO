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

vector<vector<bool>> graph;

const int INF = 1e9;
int n, m;

void swapSubQueue(deque<int> &q, int l, int r) {
    for (int i = 0; l + i < r - i; i++) {
        //swap(q[l + i], q[r - i]);
        int tmp = q[l + i];
        q[l + i] = q[r - i];
        q[r - i] = tmp;
    }
}

deque<int> find() {
    deque<int> q;
    for (int i = 0; i < n; i++) {
        q.push_back(i);
    }
    for (int k = 0; k < n * (n - 1); k++) {
        if (!graph[q[0]][q[1]]) {
            int i = 2;
            while (!graph[q[0]][q[i]] || !graph[q[1]][q[i + 1]]) {
                i++;
            }
            swapSubQueue(q, 1, i);
        }
        q.push_back(q.front());
        q.pop_front();
    }
    return q;
}


int main() {
    scanf("%i", &n);
    graph.resize(n, vector<bool>(n));
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            char c;
            scanf(" %c", &c);
            if (c == '1') {
                graph[i][j] = graph[j][i] = true;
            }
        }
    }

    /*for (int k = 0; k < n; k++) {
        for (int j = 0; j < n; j++) {
            cout << graph[k][j] << " ";
        }
        cout << endl;
    }*/

    deque<int> q = find();

    while(!q.empty()) {
        printf("%i ", q.front() + 1);
        q.pop_front();
    }
    return 0;
}

