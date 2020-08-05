//
// Created by vlad on 03.03.2020.
//

#include <iostream>

using namespace std;


int n, m;
long long tree[400000];

FILE *in = fopen("parking.in", "r");
FILE *out = fopen("parking.out", "w");

void freePlace(int v, int l, int r, int pos) {
    if (l == r) {
        tree[v] = 0;
        return;
    }
    int m = (l + r) / 2;
    if (pos <= m)
        freePlace(v * 2, l, m, pos);
    else
        freePlace(v * 2 + 1, m + 1, r, pos);
    tree[v] = tree[v * 2] + tree[v * 2 + 1];
}



int placeUpdate(int v, int l, int r, int a, int b) {
    if (tree[v] == (r - l + 1))
        return -2;
    if (l == r) {
        if (tree[v] == 1)
            return -2;
        tree[v] = 1;
        return l;
    }
    int currentPlace = -2;
    int m = (l + r) / 2;
    if (a <= m)
        currentPlace = placeUpdate(v * 2, l, m, a, b);
    if (currentPlace == -2 && b > m)
        currentPlace = placeUpdate(v * 2 + 1, m + 1, r, a, b);
    tree[v] = tree[v * 2] + tree[v * 2 + 1];
    return currentPlace;
}


int main() {
    int x, place;
    char command[5];
    fscanf(in, "%i %i", &n, &m);
    for (int i = 0; i < m; i++) {
        fscanf(in, "%s %i", command, &x);
        x--;
        if (command[1] == 'n') {
            place = placeUpdate(1, 0, n - 1, x, n - 1) + 1;
            if (place == -1)
                place = placeUpdate(1, 0, n - 1, 0, x - 1) + 1;
            fprintf(out, "%i\n", place);
        } else
            freePlace(1, 0, n - 1, x);
    }
    fclose(in);
    fclose(out);
    return 0;
}