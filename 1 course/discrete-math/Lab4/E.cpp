//
// Created by vlad on 10.05.2020.
//

#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <queue>
#include <set>

using namespace std;
const long long N =  1e9 + 7;

int states1[102];
int states2[102];
long long attainable[102];
map<char, int[102][102]> paths1;
map<char, int[102][102]> paths2;

void tompson() {
    queue<set<int>> P;
    map<set<int>, int> was;
    set <int> pd = {1};
    int size = 1;
    P.push(pd);
    was.insert({pd, size});
    while (!P.empty()) {
        pd = P.front();
        P.pop();
        bool checked = false;
        for (char i = 'a'; i <= 'z'; i++) {
            set <int> next;
            for (int p: pd) {
                if (not checked and states1[p]) {
                    checked = true;
                    states2[was.find(pd)->second] = 1;
                    //cout << p << " " <<  was.find(pd)->second << endl;
                }
                for (int j = 1; j < 102; j++) {
                    if (paths1[i][p][j]) {
                        next.insert(j);
                    }
                }
            }
            if (next.empty()) continue;
            if (was.find(next) == was.end()) {
                paths2[i][was.find(pd)->second][++size] = 1;
                P.push(next);
                was.insert({next, size});
            } else {
                paths2[i][was.find(pd)->second][was.find(next)->second] = 1;
            }
        }
    }
    for (pair<set<int>,int> w : was) {
        for (int n : w.first)
            cout << n;
        cout << " " << w.second << endl;
    }
}


int main(){
    int n, m, k, l, pos, a, b;
    char c;
    FILE *input = fopen("problem5.in", "r");
    FILE *output = fopen("problem5.out", "w");
    fscanf(input, "%i %i %i %i", &n, &m, &k, &l);
    //cin >> str >> n >> m >> k;
    for (int i = 0; i < k; i++){
        fscanf(input, "%i", &pos);
        //cin >> pos;
        states1[pos] = 1;
    }
    for (int i = 0; i < m; i++) {
        fscanf(input, "%i %i %c", &a, &b, &c);
        //cin >> a >> b >> c;
        paths1[c][a][b] = 1;
    }

    tompson();

    attainable[1] = 1;
    for(int w = 0; w < l; w++) {
        long long current[102] = {0};
        for (char i = 'a'; i <= 'z'; i++) {
            for (int j = 1; j <= 100; j++) {
                if (attainable[j] > 0) {
                    for (int r = 1; r <= 100; r++) {
                        if (paths2[i][j][r]) {
                            current[r] += attainable[j];
                            current[r] %= N;
                        }
                    }
                }
            }
        }
        for (int q = 1; q <= 100; q++)
            attainable[q] = current[q];
    }
/*    for (int i = 1; i < 10; i++)
        cout << attainable[i] << "  ";
    cout << endl;*/
    long long count = 0;
    for (int i = 1; i <= 100; i++) {
        if (states2[i]){
            count += attainable[i];
            count %= N;
        }
    }
/*    for (char i = 'a'; i <= 'b'; i++) {
        cout << i << " letter :" << endl;
        for (int j = 1; j <= 9; j++) {
            for (int r = 1; r <= 9; r++) {
                cout << paths1[i][j][r] << " " << paths2[i][j][r] << "   ";
            }
            cout << endl;
        }
        cout << endl;
    }
    for (int r = 1; r <= 10; r++) {
        cout << states1[r] << " " << states2[r] << endl;
    }*/
    fprintf(output, "%llu", count);
    return 0;
}