if __name__ == '__main__':
	file = open('brackets2num.in')
	out = open('brackets2num.out', 'w')
	answer = file.readline()
	n = len(answer) // 2
	dp = [[0 for i in range(2*n+1)] for j in range(2*n+1)]
	dp[0][0] = 1
	for i in range(1,2*n+1):
		for j in range(2*n):
			dp[i][j] = (dp[i-1][j-1] if j > 0 else 0) + dp[i-1][j+1]
	deep = 1
	num = 0
	for i in range(2*n):
		if answer[i] == "(":
			deep += 1
		else:
			num += dp[2*n - (i+1)][deep]
			deep -= 1
	out.write(str(num))