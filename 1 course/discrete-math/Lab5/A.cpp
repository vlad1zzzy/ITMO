//
// Created by vlad on 07.06.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>
#include <fstream>

using namespace std;

int main() {
    ifstream in("automaton.in");
    ofstream out("automaton.out");
    int n, m;
    char S, from;
    string arrow, word, to;
    in >> n >> S;
    int paths[30][30][30];
    for (int i = 0; i < n; i++) {
        in >> from >> arrow >> to;
        if (to.length() == 1)
            paths[from - 'A'][to[0] - 'a'][29] = 1;
        else
            paths[from - 'A'][to[0] - 'a'][to[1] - 'A'] = 1;
    }
    in >> m;
    for (int i = 0; i < m; i++) {
        in >> word;
        int possible[10002][30] = {0};
        possible[0][S - 'A'] = 1;
        int time = 0;
        for (char j: word) {
            if (not j)
                break;
            for (char k = 0; k < 30; k++) {
                if (possible[time][k])
                    for (int q = 0; q < 30; q++)
                        if (paths[k][j - 'a'][q])
                            possible[time + 1][q] = 1;
            }
            time++;
        }
/*        for (int y = 0; y < 30; y++) {
            for (int z = 0; z < 30; z++) {
                cout << possible[y][z] << " ";
            }
            cout << endl;
        }
        cout << endl;*/
        if (possible[time][29]) out << "yes" << endl;
        else out << "no" << endl;
    }
    in.close();
    out.close();
    return 0;
}