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

struct Info {
    int count, cost;
    double costByOne;
};

int comparator(Info &first, Info &second) {
    return first.costByOne < second.costByOne;
}

int minimal(int i, int n, vector<Info> table) {
    if (n) {
        int currentCost = (n / table[i].count) * table[i].cost;
        int possibleCost = min(table[i].cost, minimal(i + 1, n % table[i].count, table));
        return currentCost +  possibleCost;
    }
    return 0;
}

int main() {
    ifstream in("printing.in");
    ofstream out("printing.out");
    int n, cost;
    vector<Info> table;
    in >> n;
    for (short i = 0; i < 7; ++i) {
        in >> cost;
        int count = pow(10, i);
        table.push_back({count, cost, (double) cost / count});
    }
    sort(table.begin(), table.end(), comparator);

    out << minimal(0, n, table);

    in.close();
    out.close();
}