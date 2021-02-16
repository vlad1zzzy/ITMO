//
// Created by Vlad on 29.12.2020.
//
#include <iostream>
#include <random>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>
#include <stack>
#include <queue>
#include <set>
#include <random>
#include <ctime>

using namespace std;

struct edge {
    int from, to, w;
};

vector<int> sets, isIndependent, sizes;

const int INF = 1e9;
int n, m;

bool inSet(int set, int num) {
    return set & (1 << num);
}

int setAddNum(int set, int num) {
    return set |= (1 << num);
}

int setDiffNum(int set, int num) {
    return set ^ (1 << num);
}

int setsDiff(int set1, int set2) {
    int res = 0;
    for (int i = 0; i < n; i++) {
        if (inSet(set1, i) and not inSet(set2, i)) {
            res = setAddNum(res, i);
        }
    }
    return res;
}

bool axiom1() {
    return isIndependent[0];
}

bool axiom2() {
    for (int set : sets) {
        for (int j = 0; j < n; j++) {
            if (inSet(set, j) and (!isIndependent[setDiffNum(set, j)])) {
                return false;
            }
        }
    }
    return true;
}

bool axiom3() {
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < m; j++) {
            if (sizes[i] > sizes[j]) {
                int A = sets[i], B = sets[j];
                int diff = setsDiff(A, B);

                bool x = false;
                for (int k = 0; k < n; k++) {
                    if (inSet(diff, k) and isIndependent[B | (1 << k)]) {
                        x = true;
                        break;
                    }
                }
                if (!x) {
                    return false;
                }
            }
        }
    }
    return true;
}

bool axioms() {
    return axiom1() and axiom2() and axiom3();
}

int main() {

    freopen("check.in", "r", stdin);
    freopen("check.out", "w", stdout);

    cin >> n >> m;
    sets.resize(m);
    sizes.resize(m);
    isIndependent.resize(1 << n);
    for (int i = 0; i < m; i++) {
        int count;
        cin >> count;
        for (int j = 0; j < count; j++) {
            int num;
            cin >> num;
            sets[i] = setAddNum(sets[i], --num);
            sizes[i]++;
        }
        isIndependent[sets[i]] = 1;
    }

    cout << (axioms() ? "YES" : "NO");

/*    for (int i : sets) {
        cout << i << " ";
    }*/
    return 0;
}