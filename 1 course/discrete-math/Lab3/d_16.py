def C(n,k):
	global c
	if k > n:
		return 0
	if k == 0:
		return 1
	return c[n-1][k] + c[n-1][k-1]

if __name__ == '__main__':
	file = open('choose2num.in')
	out = open('choose2num.out', 'w')
	n,k = map(int,file.readline().split())
	choose = [0] + list(map(int,file.readline().split()))
	c = [[0 for j in range(k+1)] for i in range(n+1)]
	for i in range(n+1):
		for j in range(k+1):
			c[i][j] = C(i,j)
	num = 0
	for i in range(1,k+1):
		for j in range(choose[i-1] + 1, choose[i]):
			num += c[n-j][k-i]
	out.write(str(num))