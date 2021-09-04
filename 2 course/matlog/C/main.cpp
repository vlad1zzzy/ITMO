#include <iostream>
#include "Parser.cpp"

Parser parser;

int main() {
    string line;
    getline(cin, line);
    cout << parser.parse(line)->to_prefix_string();
    return 0;
}
