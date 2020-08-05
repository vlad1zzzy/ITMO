//
// Created by vlad on 06.03.2020.
//
#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int n, m;
const int N = 1e6;
int INF = INT_MAX;
int tree[4 * N];
struct Ask {
    int left;
    int right;
    int value;
};
Ask questions[N];

FILE *in = fopen("rmq.in", "r");
FILE *out = fopen("rmq.out", "w");

int getMin(int v, int l, int r, int a, int b) {
    if (l > b or r < a)
        return INF;
    if (l >= a and r <= b) {
        return tree[v];
    }
    int m = (l + r) / 2;
    return min(getMin(v * 2, l, m, a, b),
               getMin(v * 2 + 1, m + 1, r, a, b));
}

void pushUpdate(int v, int l, int r) {
    tree[v * 2] = tree[v * 2 + 1] = tree[v];
    if (l != r){
        int m = (l + r) / 2;
        pushUpdate(v * 2, l, m);
        pushUpdate(v * 2 + 1, m + 1, r);
    }
}
void updateMin(int v, int l, int r, int a, int b, int x) {
    if (l > b or r < a)
        return;
    if (l >= a and r <= b) {
        tree[v] = x;
        pushUpdate(v, l, r);
        return;
    }
    int m = (l + r) / 2;
    updateMin(v * 2, l, m, a, b, x);
    updateMin(v * 2 + 1, m + 1, r, a, b, x);
    tree[v] = min(tree[v * 2], tree[v * 2 + 1]);
}

int nextPow(int n) {
    int pw = 1;
    while (pw < n) {
        pw *= 2;
    }
    return pw;
}

int main() {
    fscanf(in, "%i %i", &n, &m);
    int k = nextPow(n);
    for (int i = 0; i < m; i++) {
        fscanf(in, "%i %i %i", &questions[i].left, &questions[i].right, &questions[i].value);
    }
    for (int i = 0; i < m; i++)
        for (int j = m - 1; j > i; j--)
            if (questions[j].value < questions[j - 1].value)
                swap(questions[j], questions[j - 1]);
/*    for (int i = 0; i < m; i++) {
        cout << questions[i].value << endl;
    }*/
    for (int i = 0; i < m; i++) {
        updateMin(1, 0, n - 1, questions[i].left - 1, questions[i].right - 1, questions[i].value);
    }
    int minimum;
    string ans;
    char result[13] = "consistent";
    for (int i = 0; i < m; i++) {
        minimum = getMin(1, 0, n - 1, questions[i].left - 1, questions[i].right - 1);
        if (minimum != questions[i].value) {
            ans = "inconsistent";
            break;
        }
    }
    if (ans[0] == 'i') {
        for (int i = 0; i < 12; i++)
            result[i] = ans[i];
    }
    fprintf(out, "%s\n", &result);
    if (result[0] == 'c') {
        for (int i = 0; i < n; i++)
            fprintf(out, "%i ", tree[i + k]);
        fprintf(out, "\n");
    }
/*    for (int i = 0; i < 4 * n; i++)
        cout << tree[i] << " ";*/
    return 0;
}