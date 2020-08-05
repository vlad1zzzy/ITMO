#include <iostream>

using namespace std;

int tail, head;
int queue[100000];
int index[100000];

int front_count(int *queue, int id){
	if (tail <= index[id]){
		return index[id] - tail;
	}
	return 100000 - tail + index[id];
}
int main(){
	int n, op, id;
	tail = 50000;
	head = 50000;

	
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cin >> n;
	for (int i = 0; i < n; i++){
		cin >> op;
		if (op == 1){
			cin >> id;
			queue[head] = id;
			index[id] = head;
			head = (head + 1) % 100000;
		} else if (op == 2){
			tail = (tail + 1) % 100000;
		} else if (op == 3){
			head = (head - 1) % 100000;
		} else if (op == 4){
			cin >> id;
			cout << front_count(queue, id) << '\n';
		} else {
			cout << queue[tail] << '\n';
		}
	}	
}