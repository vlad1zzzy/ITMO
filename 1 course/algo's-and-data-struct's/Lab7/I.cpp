//
// Created by vlad on 08.06.2020.
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

bool mulAndCheck(int matrix[4][4], int need[4][4]) {
    for (int i = 0; i < 4; i++){
        for (int j = 0; j < 4; j++) {
            int cur = 0;
            for (int k = 0; k < 4; k++){
                cur += matrix[i][k] * matrix[k][j];
            }
            cur %= 2;
            if (cur != need[i][j])
                return false;
        }
    }
    return true;
}

int main() {
    ifstream in("sqroot.in");
    ofstream out("sqroot.out");

    int matrix[4][4];
    int curMatrix[4][4];
    bool find;
    for (int i = 0; i < 4; i++)
        for (int j = 0; j < 4; j++)
            in >> matrix[i][j];

    for (int i = 0; i <= (1 << 16); i++) {
        int cur = i;
        for (int j = 3; j >= 0; j--){
            for (int k = 3; k >= 0; k--) {
                curMatrix[j][k] = cur & 1;
                cur >>= 1;
            }
        }
        find = mulAndCheck(curMatrix, matrix);
        if (find) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++)
                    out << curMatrix[j][k] << " ";
                out << endl;
            }
            return 0;
        }

    }
    out << "NO SOLUTION";
    in.close();
    out.close();
    return 0;
}

