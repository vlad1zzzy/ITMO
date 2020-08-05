def toString(l,r):
	global ans, s
	if dp[l][r] == r - l + 1:
		return ans
	if not dp[l][r]:
		ans += s[l:r+1]
		return ans
	if midPos[l][r] == -1:
		ans += s[l]
		toString(l+1,r-1)
		ans += s[r]
		return ans
	toString(l, midPos[l][r])
	toString(midPos[l][r] + 1, r)
	return ans


if __name__ == '__main__':
	s = input()
	n = len(s)
	dp = [[1 if i == j else 0 for i in range(n+1)] for j in range(n+1)]
	midPos = [[-1 for j in range(n)] for i in range(n)]
	for j in range(n):
		for i in range(j-1,-1,-1):
			res = 101
			if (s[i]+s[j]) in {"()","[]","{}"}:
				res = dp[i+1][j-1]
			for k in range(i,j):
				if dp[i][k] + dp[k+1][j] < res:
					res = dp[i][k] + dp[k+1][j]
					midPos[i][j] = k
			dp[i][j] = res
	ans = ""
	print(toString(0,n-1))