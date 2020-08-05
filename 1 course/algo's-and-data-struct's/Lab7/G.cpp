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

const int INF = 2e9;
const double eps = 0e-10;

vector<int> primes;

bool beauty(int number) {
    vector<int> countDivisors;
    int countDivs = 1;
    for (int i: primes) {
        int num = number;
        if (i > num) break;
        int count = 0;
        while (not (num % i)) {
            count++;
            num /= i;
        }
        countDivs *= (count + 1);
    }
    return not (countDivs % 3);
}

bool nextPerm(int *perm, int n) {
    int j = n - 2;
    while (j != -1 && perm[j] >= perm[j + 1]) --j;
    if (j == -1)
        return false;
    int k = n - 1;
    while (perm[j] >= perm[k]) --k;
    swap(perm[j], perm[k]);
    int l = j + 1, r = n - 1;
    while (l<r) swap(perm[l++], perm[r--]);
    return true;
}

int main() {
    FILE *input = fopen("beautiful.in", "r");
    FILE *output = fopen("beautiful.out", "w");
    int n, r;
    vector<int> primeTable;
    int isGood[1000] = {0};
    fscanf(input, "%i %i", &n, &r);
    primeTable.resize(r + 1, 1);
    primeTable[0] = primeTable[1] = 0;
    for (int i = 2; i <= r; i++) {
        if (primeTable[i] and i * i <= r)
            for (int j = i * i; j <= r; j += i)
                primeTable[j] = 0;
    }
    for (int i = 0; i < r; ++i) {
        if(primeTable[i])
            primes.push_back(i);
    }
    int perm[n];
    long long factorial = 1;
    for (int i = 0; i < n; ++i) {
        perm[i] = i + 1;
        factorial *= (i + 1);
    }
    int ans = 0;
    int num = 0;
    for (int i = 0; i < n - 1; ++i)
        num += perm[i] * perm[i + 1];
    num %= r;
    if ((not num) or beauty(num)) {
        ans++;
        if (num)
            isGood[num] = 1;
    }
    while (nextPerm(perm, n)) {
        int cur = 0;
        for (int j = 0; j < n - 1; ++j)
            cur += perm[j] * perm[j + 1];
        cur %= r;
        if (isGood[cur] or (not cur) or beauty(cur)){
            ++ans;
            if (cur)
                isGood[cur] = 1;
        }
    }

    fprintf(output, "%i", ans);
    return 0;
}