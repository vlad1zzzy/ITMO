#include <iostream>
#include <vector>

using namespace std;

void nextPart(vector<int> &part) {
  int n = part.size();
  part[n-1]--;
  part[n-2]++;
  if (part[n-2] > part[n-1]) {
  	part [n-2] += part[n-1];
  	part.pop_back();
  } else {
  	while (part[n-2]*2 <= part[n-1]) {
  		part.push_back(part[n-1] - part[n-2]);
  		part[n-2] = part[n-3];
  	}
  }
}

int main() {
	int n, r;
	freopen("num2part.in", "r", stdin);
	freopen("num2part.out", "w", stdout);
	cin >> n >> r;
	vector <int> part(n), newPart(n);
	for (int i = 0; i < n; i++) {
		part[i] = 1;
	}
	for (int i = 0; i < r; i++) {
		nextPart(part);
	}
	cout << part[0];
	for (int i = 1; i < part.size(); i++) {
		cout << "+" << part[i];
	}
	return 0;
}