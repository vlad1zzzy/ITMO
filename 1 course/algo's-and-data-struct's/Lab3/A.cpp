#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int main() {
	int n, k, maximum, jumps = 0;
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
	cin >> n >> k;
	vector <int> road(n), res(n), prev(n), way;
	for (int i = 1; i < n - 1; i++) {
		cin >> road[i];
	}
	for (int i = 1; i < n; i++) {
		maximum = res[i-1];
		prev[i] = i - 1;
		for (int j = i - 1; j >= max(0,i-k); j--) {
			if (res[j] > maximum) {
				maximum = res[j];
				prev[i] = j;
			}
		}
		res[i] = maximum + road[i];
	}
	int cur = n - 1;
	way.push_back(n);
	while (cur > 0) {
		way.push_back(prev[cur] + 1);
		cur = prev[cur];
		jumps++;
	}
	cout << res[prev[n-1]] << "\n";
	cout << jumps << "\n";
	for (int i = way.size() - 1; i > -1; i--) {
		cout << way[i] << " ";
	}
}