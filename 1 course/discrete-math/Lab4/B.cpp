//
// Created by vlad on 24.04.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>

using namespace std;


int states[102];
int attainable[102];
map<char, int[102][102]> paths;

int main(){
    char str[10001];
    int n, m, k, pos, a, b;
    char c;
    FILE *input = fopen("problem2.in", "r");
    FILE *output = fopen("problem2.out", "w");
    fscanf(input, "%s %i %i %i", str, &n, &m, &k);
    //cin >> str >> n >> m >> k;
    for (int i = 0; i < k; i++){
        fscanf(input, "%i", &pos);
        //cin >> pos;
        states[pos] = 1;
    }
    for (int i = 0; i < m; i++) {
        fscanf(input, "%i %i %c", &a, &b, &c);
        //cin >> a >> b >> c;
        paths[c][a][b] = 1;
    }
    attainable[1] = 1;
    for (char i: str) {
        if (not i)
            break;
/*        for (int q = 0; q <= n; q++)
            cout << attainable[q] << " ";
        cout << endl;*/
        int current[102] = {0};
        for (int j = 1; j <= n; j++) {
            if (attainable[j]) {
                for (int r = 1; r <= n; r++) {
                    if (paths[i][j][r]) {
                        current[r] = 1;
                    }
                }
            }
        }
        for (int q = 0; q <= n; q++)
            attainable[q] = current[q];
    }
    for (int i = 1; i <= n; i++) {
        if (states[i] and attainable[i]){
            fprintf(output, "Accepts");
            return 0;
        }
    }
    fprintf(output, "Rejects");
    return 0;
}