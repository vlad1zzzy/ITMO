//
// Created by vlad on 10.05.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <queue>

using namespace std;

int states[2][1002];
map<char, int[1002]> paths1;
map<char, int[1002]> paths2;

bool used[1002][1002];

bool compare() {
    queue<pair<int, int>> Q;
    Q.push(make_pair(1, 1));
    while (!Q.empty()) {
        int u = Q.front().first;
        int v = Q.front().second;
        Q.pop();
        if (states[0][u] != states[1][v])
            return false;
        used[u][v] = true;
        for (int i = 'a'; i <= 'z'; i++) {
            if (not used[paths1[i][u]][paths2[i][v]])
                Q.push(make_pair(paths1[i][u], paths2[i][v]));
        }
    }
    return true;
}

int main(){
    int n, m, k, pos, a, b;
    char c;
    FILE *input = fopen("equivalence.in", "r");
    FILE *output = fopen("equivalence.out", "w");
    //--------------------------------------------------------------
    fscanf(input, "%i %i %i", &n, &m, &k);
    //cin >> str >> n >> m >> k;
    for (int i = 0; i < k; i++){
        fscanf(input, "%i", &pos);
        //cin >> pos;
        states[0][pos] = 1;
    }
    for (int i = 0; i < m; i++) {
        fscanf(input, "%i %i %c", &a, &b, &c);
        //cin >> a >> b >> c;
        paths1[c][a] = b;
    }
    //--------------------------------------------------------------
    fscanf(input, "%i %i %i", &n, &m, &k);
    //cin >> str >> n >> m >> k;
    for (int i = 0; i < k; i++){
        fscanf(input, "%i", &pos);
        //cin >> pos;
        states[1][pos] = 1;
    }
    for (int i = 0; i < m; i++) {
        fscanf(input, "%i %i %c", &a, &b, &c);
        //cin >> a >> b >> c;
        paths2[c][a] = b;
    }
    //--------------------------------------------------------------

    if (compare())
        fprintf(output, "YES");
    else
        fprintf(output, "NO");

    return 0;
}