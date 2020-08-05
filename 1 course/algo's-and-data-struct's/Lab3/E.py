if __name__ == '__main__':
	s1 = input()
	s2 = input()
	dp = [[j if i == 0 else 0 for j in range(len(s1)+1)] for i in range(len(s2)+1)]
	for i in range(len(s2)+1):
		dp[i][0] = i
	for i in range(1,len(s2)+1):
		for j in range(1,len(s1)+1):
			dp[i][j] = min(dp[i-1][j-1] if s1[j-1] == s2[i-1]
				else dp[i-1][j-1] + 1, dp[i-1][j] + 1, dp[i][j-1] + 1)
	print(dp[len(s2)][len(s1)])