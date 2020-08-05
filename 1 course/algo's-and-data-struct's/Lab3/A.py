def dinamic(n,k):
	global road, res, prev
	for i in range(1,n):
		maximum = res[i-1]
		prev[i] = i - 1
		for j in range(i-1, max(-1, i-k),-1):
			if res[j] > maximum:
				maximum = res[j]
				prev[i] = j
		res[i] = maximum + road[i]
	return res
	
if __name__ == '__main__':
	file = open("input.txt")
	out = open("output.txt", "w")
	n,k = map(int,file.readline().split())
	road = [0] + list(map(int,file.readline().split())) + [0]
	res = [0]*n
	jumps = 0
	prev = [0]*n
	end = dinamic(n,k)
	way = [n]
	cur = n - 1
	while cur > 0:
		way.append(prev[cur] + 1)
		cur = prev[cur]
		jumps += 1
	way.reverse()
	out.write(str(res[prev[n-1]]) + "\n")
	out.write(str(jumps) + "\n")
	for i in way:
		out.write(str(i) + " ")
	file.close()
	out.close()
