if __name__ == '__main__':
	n, m = map(int,input().split())
	dp = [[0 for j in range(n)] for i in range(n)]
	for i in range(2**n):
		for j in range(2**n):
			dp[i][j] = 1 if can else 0

	for i in range(2**n):
		a[0][i] = 1
	for k in range(1,m):
		for i in range(2**n):
			for j in range(2**n):
				a[k][i] += a[k-1][j]*d[j][i]
	ans = 0
	for i in range(2**n):
		ans += a[m-1][i]

	return ans
