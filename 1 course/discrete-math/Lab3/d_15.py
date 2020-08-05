def choose(n,k):
	global c
	if k > n:
		return 0
	if k == 0:
		return 1
	return c[n-1][k] + c[n-1][k-1]

if __name__ == '__main__':
	file = open('num2choose.in')
	out = open('num2choose.out', 'w')
	n,k,m = map(int,file.readline().split())
	res = []
	c = [[0 for j in range(k+1)] for i in range(n+1)]
	for i in range(n+1):
		for j in range(k+1):
			c[i][j] = choose(i,j)
	cur = 1
	while k > 0:
		if m < c[n-1][k-1]:
			res.append(cur)
			k -= 1
		else:
			m -= c[n-1][k-1]
		n -= 1
		cur += 1
	for i in res:
		out.write(str(i) + " ")

	file.close()
	out.close()