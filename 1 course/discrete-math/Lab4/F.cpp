//
// Created by vlad on 10.05.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>

using namespace std;

int states[2][100002];
int visited[100000];
map<char, int[100002]> paths1;
map<char, int[100002]> paths2;

int associations[100000];

bool dfs(int u, int v) {
    visited[u] = true;
    if (states[0][u] != states[1][v])
        return false;
    associations[u] = v;
    bool result = true;
    for (char i = 'a'; i <= 'z'; i++) {
        if (paths1[i][u] and paths2[i][v]) {
            int t1 = paths1[i][u];
            int t2 = paths2[i][v];
            if (visited[t1])
                result = result and (t2 == associations[t1]);
            else
                result = result and dfs(t1, t2);
        } else if (paths1[i][u] or paths2[i][v])
            return false;
    }
    return result;
}

int main(){
    int n, m, k, pos, a, b;
    char c;
    FILE *input = fopen("isomorphism.in", "r");
    FILE *output = fopen("isomorphism.out", "w");
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

    if (dfs(1, 1))
        fprintf(output, "YES");
    else
        fprintf(output, "NO");

    return 0;
}