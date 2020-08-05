from math import factorial as fac

def number(k):
	global n
	for i in range(1,n+1):
		curFac = fac(n - i)
		visited = k // curFac
		k %= curFac
		free = 0
		for j in range(1,n+1):
			if was[j] == False:
				free += 1
				if free == (visited + 1):
					res[i] = j
					was[j] = True
	return res

if __name__ == '__main__':
	file = open('num2perm.in')
	out = open('num2perm.out', 'w')
	n,k = map(int,file.readline().split())
	res = [0]*(n+1)
	was = [False]*(n+1)
	number(k)
	for i in range(1,n+1):
		out.write(str(res[i]) + " ")
	file.close()
	out.close()
