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

const int N = 1e9+7;
long long dp[26][101][101] = {0};

int main() {
    ifstream in("nfc.in");
    ofstream out("nfc.out");
    int n;
    char S, from;
    string arrow, word, to;
    in >> n >> S;
    vector<vector<string>> paths;
    paths.resize(26);
    for (int i = 0; i < n; i++) {
        in >> from >> arrow >> to;
        paths[from - 'A'].push_back(to);
    }
    in >> word;
    for (int letter = 0; letter < 26; letter++) {
        for (string current: paths[letter]) {
            if (current.length() == 1)
                for (int i = 0; i < word.length(); i++)
                    if (current[0] == word[i])
                        dp[letter][i][i]++;
        }
    }
    for (int len = 1; len < word.length(); len++) {
        for (int i = 0; i + len < word.length(); i++) {
            for (int letter = 0; letter < 26; letter++) {
                for (string current: paths[letter]) {
                    if (current.length() == 2)
                        for (int k = i; k < i + len; k++){
                            dp[letter][i][i + len] =
                                    (dp[letter][i][i + len] +
                                     dp[current[0] - 'A'][i][k] *
                                     dp[current[1] - 'A'][k+1][i+len]) % N;
                        }
                }
            }
        }
    }
    out << dp[S - 'A'][0][word.length() - 1];

    in.close();
    out.close();
    return 0;
}