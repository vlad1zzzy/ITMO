def nextDay(i,j):
	if days[i-1] > 100:
		if j > 0 and dp[i-1][j-1] + days[i-1] <= dp[i-1][j+1]:
			dp[i][j] = dp[i-1][j-1] + days[i-1]
			road[i][j] = "left"
			return
		dp[i][j] = dp[i-1][j+1]
		road[i][j] = "rigth"
		return
	if dp[i-1][j] + days[i-1] <= dp[i-1][j+1]:
		dp[i][j] = dp[i-1][j] + days[i-1]
		road[i][j] = "up"
		return
	dp[i][j] = dp[i-1][j+1]
	road[i][j] = "rigth"
	return

def nMoreThenO():
	for i in range(1,n+2):
		dp[0][i] = inf
	for i in range(1,n+1):
		for j in range(n+1):
			nextDay(i,j)
		dp[i][n+1] = inf

	res = index = inf
	for i in range(3):
		if dp[n][i] <= res:
			res = dp[n][i]
			index = i
	print(res)
	print(index, end=" ")
	way = []
	for i in range(n,0,-1):
		if road[i][index] == "rigth":
			way.append(i)
			index += 1
		elif road[i][index] == "left":
			index -= 1
	print(len(way))
	print(*reversed(way), sep="\n")

if __name__ == '__main__':
	n = int(input())
	days = [int(input()) for i in range(n)]
	inf = 30001
	dp = [[0 for j in range(n+2)] for i in range(n+1)]
	road = [[0 for j in range(n+2)] for i in range(n+1)]
	if n == 0:
		print(0)
		print(0, 0)
	else:
		nMoreThenO()
	