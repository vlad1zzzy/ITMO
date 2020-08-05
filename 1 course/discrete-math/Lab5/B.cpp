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

using namespace std;

int main() {
    ifstream in("epsilon.in");
    ofstream out("epsilon.out");
    int n, m;
    char S, from;
    string arrow, word, to;
    vector<char> toEps;
    vector<int> isEps;
    isEps.resize(27, 0);
    in >> n >> S;
    vector<pair<char, string>> paths;
    int i = 0;
    next:
    while (i < n) {
        in >> from >> arrow;
        getline(in, to);
        cout << from << " " << arrow << " " << to << endl;
        for (char c: to) {
            if ('a' <= c and c <= 'z'){
                i++;
                goto next;
            }
        }
        if (not to.length()){
            isEps[from - 'A'] = 1;
        }
        else {
            pair<char, string> cur = make_pair(from, to.erase(0, 1));
            paths.push_back(cur);
        }
        i++;
    }
    for (pair<char, string> a: paths)
        cout << a.first << " " << a.second << endl;
    int oneChanged = 1;
    while (oneChanged) {
        oneChanged = 0;
        int q = 0;
        nextRule:
        while (q < paths.size()) {
            cout << paths[q].second << endl;
            for (char c: paths[q].second){
                cout << c << " " << isEps[c - 'A'] << endl;
                if (not isEps[c - 'A']){
                    q++;
                    goto nextRule;
                }
                cout << "qq" << endl;
            }
            isEps[paths[q].first - 'A']  = 1;
            paths.erase(paths.begin() + q);
            oneChanged = 1;
        }
    }
    for (int q = 0; q < isEps.size(); q++){
        if (isEps[q])
            toEps.push_back('A' + q);
    }
    cout << endl;
    for (char q: toEps){
        cout << q << " ";
    }
    sort(toEps.begin(), toEps.end());

    for (char q: toEps){
        out << q << " ";
    }

    in.close();
    out.close();
    return 0;
}