#include <iostream>
#include <vector>

using namespace std;

int main(){
	int n, op, num, index;
	index = 1;
	std::vector<int> stack(1000000, 0);
	
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cin >> n;
	cin >> op >> num;

	stack[0] = num;
	for (int i = 1; i < n; i++){
		cin >> op;
		if (op == 1){
			cin >> num;
			if (index == 0){
				stack[0] = num;
			} else {
				stack[index] = min(num, stack[index-1]);
			}
			index++;
		} else if (op == 2){
			index--;
		} else {
			cout << stack[index-1] << '\n';
		}
	}
	
}