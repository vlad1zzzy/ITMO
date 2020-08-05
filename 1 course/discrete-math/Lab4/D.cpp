//
// Created by vlad on 24.04.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>

using namespace std;
const long long N =  1e9 + 7;

int states[102];
long long attainable[102];
map<char, int[102][102]> paths;

int main(){
    int n, m, k, l, pos, a, b;
    char c;
    FILE *input = fopen("problem4.in", "r");
    FILE *output = fopen("problem4.out", "w");
    fscanf(input, "%i %i %i %i", &n, &m, &k, &l);
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
    for(int w = 0; w < l; w++) {
        long long current[102] = {0};
        for (char i = 'a'; i <= 'z'; i++) {
            for (int j = 1; j <= n; j++) {
                if (attainable[j] > 0) {
                    for (int r = 1; r <= n; r++) {
                        if (paths[i][j][r]) {
                            current[r] += attainable[j];
                            current[r] %= N;
                        }
                    }
                }
            }
        }
        for (int q = 1; q <= n; q++)
            attainable[q] = current[q];
    }
    long long count = 0;
    for (int i = 1; i <= n; i++) {
        if (states[i]){
            count += attainable[i];
            count %= N;
        }
    }
    fprintf(output, "%lld", count);
    return 0;
}