def binSearch(dp, x):
	global n
	l = -1
	r = n
	while r - l > 1:
		m = (l + r) // 2
		if dp[m] < x:
			l = m
		else:
			r = m
	return r


if __name__ == '__main__':
	n = int(input())
	a = list(map(int,input().split()))
	dp = [10**9]*(n + 1)
	dp[0] = -10**9
	dp[1] = a[0]
	pos = [-1]*(n+1)
	prev = [0]*n
	length = 0
	for i in range(n):
		j = binSearch(dp, a[i])
		if dp[j-1] < a[i] <= dp[j]:
			dp[j] = a[i]
			pos[j] = i
			prev[i] = pos[j-1]
			length = max(length, j)
	cur = pos[length]
	res = []
	while cur != -1:
		res.append(a[cur])
		cur = prev[cur]
	res.reverse()
	print(length)
	print(*res)