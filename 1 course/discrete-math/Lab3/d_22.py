if __name__ == '__main__':
	file = open('part2num.in')
	out = open('part2num.out', 'w')
	part = list(map(int,file.readline().split("+")))
	n = sum(part)
	dp = [[1 if i == j else 0 for j in range(n+1)] for i in range(n+1)]
	for i in range(n):
		for j in range(i-1,-1,-1):
			dp[i][j] = dp[i][j+1] + dp[i-j][j]
	num = l = summa = 0
	for i in part:
		for j in range(l, i):
			num += dp[n-summa-j][j]
		summa += i
		l = i
	out.write(str(num))