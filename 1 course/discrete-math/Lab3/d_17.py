if __name__ == '__main__':
	file = open('num2brackets.in')
	out = open('num2brackets.out', 'w')
	n,k = map(int,file.readline().split())
	answer = ""
	dp = [[0 for i in range(2*n+1)] for j in range(2*n+1)]
	dp[0][0] = 1
	k += 1
	for i in range(1,2*n+1):
		for j in range(2*n):
			dp[i][j] = (dp[i-1][j-1] if j > 0 else 0) + dp[i-1][j+1]
	deep = 1
	for i in range(2*n):
		cur = dp[2*n - (i+1)][deep]
		if cur >= k:
			answer += "("
			deep += 1
		else:
			k -= cur
			answer += ")"
			deep -= 1
	out.write(answer)