//
// Created by vlad on 01.03.2020.
//
#include <iostream>
#include <cstdio>

using namespace std;


int n, m, u, v;
int table[100000][30];


void buildTable() {
    for (int j = 1; (1 << j) <= n; j++) {
        for (int i = 0; i + (1 << j) <= n; i++) {
            table[i][j] = min(table[i][j - 1], table[i + (1 << (j - 1))][j - 1]);
        }
    }
}

int log(int n) {
    int pw = 0;
    while ((1 << ++pw) <= n) {
    }
    return pw - 1;
}


int main() {
    scanf("%i %i %i", &n, &m, &table[0][0]);
    for (int i = 1; i < n; i++) {
        table[i][0] = (23 * table[i - 1][0] + 21563) % 16714589;
    }
    buildTable();
    scanf("%i %i", &u, &v);
    int res, cur;
    int left = min(u, v);
    int right = max(u, v);
    cur = log(right - left + 1);
    res = min(table[left - 1][cur], table[right - (1 << cur)][cur]);
    for (int i = 1; i < m; i++) {
        u = ((17 * u + 751 + res + 2 * i) % n) + 1;
        v = ((13 * v + 593 + res + 5 * i) % n) + 1;
        int left = min(u, v);
        int right = max(u, v);
        cur = log(right - left + 1);
        res = min(table[left - 1][cur], table[right - (1 << cur)][cur]);
    }
    printf("%i %i %i", u, v, res);
    return 0;
}