//
// Created by vlad on 09.06.2020.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>
#include <fstream>
#include <set>
#include <queue>

using namespace std;

set<char> nonTerms;
map<char, vector<string>> paths;
int possible[26] = {0}, isTerm[26] = {0}, result[26] = {0};

int main() {
    ifstream in("useless.in");
    ofstream out("useless.out");

    int n;
    char S, from;
    string arrow, to;
    queue<char> que;
    in >> n >> S;
    possible[S - 'A'] = 1;
    for (int i = 0; i < n; i++) {
        in >> from >> arrow;
        getline(in, to);
        nonTerms.insert(from);
        if (to.empty()) {
            isTerm[from - 'A'] = 1;
            continue;
        }
        to = to.erase(0, 1);
        for (char c: to) {
            if (not('a' <= c and c <= 'z'))
                nonTerms.insert(c);
        }
        paths[from].push_back(to);
    }
    //possible to reach:
    que.push(S);
    while (!que.empty()) {
        char cur = que.front();
        que.pop();
        for (string N_i : paths[cur]) {
            for (char next : N_i) {
                if (('a' <= next and next <= 'z') or possible[next - 'A'])
                    continue;
                possible[next - 'A'] = 1;
                que.push(next);
            }
        }
    }
    //check nonTerm to term:
    for (int i = 0; i < 50; i++) {
        for (char c = 'A'; c <= 'Z'; c++) {
            if (not isTerm[c - 'A']) {
                for (string &next : paths[c]) {
                    int allTerms = 0;
                    for (char cur: next) {
                        if ('a' <= cur and cur <= 'z')
                            allTerms++;
                        else
                            allTerms += isTerm[cur - 'A'];
                    }
                    if (allTerms)
                        isTerm[c - 'A'] = 1;
                }
            }
        }
    }
    if (isTerm[S - 'A']) {
        result[S - 'A'] = 1;
        que.push(S);
    } else {
        for (char i: nonTerms)
            out << i << " ";
    }
    while (!que.empty()) {
        char cur = que.front();
        que.pop();
        for (string N_i : paths[cur]) {
            int allTerms = 0;
            for (char next : N_i) {
                if ('a' <= next and next <= 'z')
                    allTerms++;
                else
                    allTerms += isTerm[next - 'A'];
            }
            if (allTerms) {
                for (char j : N_i) {
                    if (not(('a' <= j and j <= 'z') and result[j - 'A'])) {
                        result[j - 'A'] = 1;
                        que.push(j);
                    }
                }
            }
        }
    }
    for (char i: nonTerms)
        if (not(possible[i - 'A'] && isTerm[i - 'A'] && result[i - 'A']))
            out << i << " ";


    in.close();
    out.close();
    return 0;
}