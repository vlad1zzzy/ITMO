#include <iostream>
#include <string>
#include <vector>

using namespace std;

int set[200000];
int ranks[200000];
int AlreadyInSet[200000];
int exper[200000];
std::vector<vector<int>> vec(200000);

int addToSet(int x){
	set[x] = x;
	ranks[x] = 0;
	AlreadyInSet[x] = x;
	vec[x].push_back(x);
	exper[x] = 0;
	return 0;
}

int FindInSet(int x){
	if (x == set[x])
		return x;
	set[x] = FindInSet(set[x]);
	return set[x];
}

int addExp(int x, int v){
	x = FindInSet(x);
	for (int hero: vec[x]){
		exper[hero] += v;
	}
}


int UnionInSet(int x, int y){
	x = FindInSet(x);
	y = FindInSet(y);
	if (x == y){
		return 0;
	}
	if (ranks[x] < ranks[y]){
		for (int hero: vec[x]){
			vec[y].push_back(hero);
		}
		set[x] = y;
	}else {
		for (int hero: vec[y]){
			vec[x].push_back(hero);
		}
		set[y] = x;
		if (ranks[x] == ranks[y])
			ranks[x]++;
	return 0;
	}
}


int main(){
	int n, m, x, y, v, res;
	std::string op;
	
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cin >> n >> m;

	for (int i = 0; i < m; i++){
		cin >> op;
		if (op == "join"){
			cin >> x >> y;
			if (AlreadyInSet[x] != x)
				addToSet(x);
			if (AlreadyInSet[y] != y)
				addToSet(y);
			UnionInSet(x,y);
		}else if (op == "add") {
			cin >> x >> v;
			if (AlreadyInSet[x] != x)
				addToSet(x);
			addExp(x,v);
		} else {
			cin >> x;
			if (AlreadyInSet[x] != x)
				addToSet(x);
			res = exper[x];
			cout << res << "\n";
		}
	}
	return 0;
}