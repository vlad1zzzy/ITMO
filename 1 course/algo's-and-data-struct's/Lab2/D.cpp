#include <iostream>
#include <iostream>
#include <string>

using namespace std;

int tail1=0, head1=0, head2=500000, tail2=500000, count=0;
int queue[1000000];

int main(){
	int n, id;
	std::string op;
	
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cin >> n;

	for (int i = 0; i < n; i++){
		cin >> op;
		if (op == "+"){
			cin >> id;
			queue[tail2] = id;
			if (count % 2 == 0)
				queue[tail1++] = queue[head2++];
			tail2++;
			count++;

		} else if (op == "*"){
			cin >> id;
			if (count % 2 == 0){
				queue[tail1++] = id;
			} else {
				queue[--head2] = id;
			}
			count++;
		} else {
			cout << queue[head1] << endl;
			if (count % 2 == 0)
			    queue[tail1++] = queue[head2++];
			head1++;
			count--;
		}
	}	
}