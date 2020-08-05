//
// Created by vlad on 03.03.2020.
//
#include <cstdio>

using namespace std;

int n;
int tree[130][130][130];


int getStars(int x, int y, int z) {
    int res = 0;
    for (int i = x; i >= 0; i = (i & (i + 1)) - 1)
        for (int j = y; j >= 0; j = (j & (j + 1)) - 1)
            for (int k = z; k >= 0; k = (k & (k + 1)) - 1)
                res += tree[i][j][k];
    return res;
}

void addStars(int x, int y, int z, int stars) {
    for (int i = x; i < n; i = (i | (i + 1)))
        for (int j = y; j < n; j = (j | (j + 1)))
            for (int k = z; k < n; k = (k | (k + 1)))
                tree[i][j][k] += stars;
}

int main() {
    int x1, x2, y1, y2, z1, z2, k, m, res;
    scanf("%i", &n);
    while (true) {
        scanf("%i", &m);
        if (m == 1) {
            scanf("%i %i %i %i", &x1, &y1, &z1, &k);
            addStars(x1, y1, z1, k);
        }
        if (m == 2) {
            scanf("%i %i %i %i %i %i", &x1, &y1, &z1, &x2, &y2, &z2);
            res = getStars(x2, y2, z2);
            res -= getStars(x1 - 1, y2, z2);
            res -= getStars(x2, y1 - 1, z2);
            res -= getStars(x2, y2, z1 - 1);
            res += getStars(x1 - 1, y1 - 1, z2);
            res += getStars(x1 - 1, y2, z1 - 1);
            res += getStars(x2, y1 - 1, z1 - 1);
            res -= getStars(x1 - 1, y1 - 1, z1 - 1);
            printf("%i\n", res);
        }
        if (m == 3)
            break;
    }
    return 0;
}