if __name__ == '__main__':
	file = open("input.txt")
	out = open("output.txt", "w")
	n,m = map(int,file.readline().split())
	stat = []
	road = [[0 for j in range(m)] for i in range(n)]
	for i in range(n):
		stat.append(list(map(int,file.readline().split())))
		for j in range(m):
			if (i + j) == 0:
				continue
			if i == 0:
				stat[i][j] += stat[i][j-1]
				road[i][j] = "R"
				continue
			if j == 0:
				stat[i][j] += stat[i-1][j]
				road[i][j] = "D"
				continue
			if stat[i-1][j] > stat[i][j-1]:
				stat[i][j] += stat[i-1][j]
				road[i][j] = "D"
			else:
				stat[i][j] += stat[i][j-1]
				road[i][j] = "R"
	way = []
	i = n - 1
	j = m - 1
	print(road)
	while i > 0 or j > 0:
		way.append(road[i][j])
		if road[i][j] == "D":
			i -= 1
		else:
			j -= 1
	way.reverse()
	out.write(str(stat[n-1][m-1]) + "\n")
	for i in way:
		out.write(i)
	file.close()
	out.close()