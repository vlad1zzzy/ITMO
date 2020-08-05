//
// Created by vlad on 24.04.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>

using namespace std;


int states[100001];
map<char, int[100001]> paths;

int main(){
    char str[100000];
    int n, m, k, pos, a, b;
    char c;
    FILE *input = fopen("problem1.in", "r");
    FILE *output = fopen("problem1.out", "w");
    fscanf(input, "%s %i %i %i", str, &n, &m, &k);
    for (int i = 0; i < k; i++){
        fscanf(input, "%i", &pos);
        states[pos] = 1;
    }
    for (int i = 0; i < m; i++) {
        fscanf(input, "%i %i %c", &a, &b, &c);
        paths[c][a] = b;
    }
    pos = 1;
    for (char i: str) {
        if (not i)
            break;
        pos = paths[i][pos];
    }
    if (states[pos])
        fprintf(output, "Accepts");
    else
        fprintf(output, "Rejects");
    fclose(input);
    fclose(output);
    return 0;
}