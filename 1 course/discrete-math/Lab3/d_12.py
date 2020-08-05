def toSet(pos,end):
	global n,k
	if pos > n:
		if not end:
			for i in range(k):
				c = []
				for j in range(n):
					if (sets[j+1] == i):
						c.append(j+1)
				parts.append(c)
		return
	for i in range(k - end + 1):
		sets[pos] = i
		toSet(pos+1,end if i < (k - end) else end - 1)
	return


if __name__ == '__main__':
	file = open('part2sets.in')
	out = open('part2sets.out', 'w')
	n,k = map(int,file.readline().split())
	sets = [0]*(n*2)
	parts = []
	toSet(1,k)
	current = 0
	while current < len(parts):
		for i in range(k):
			for j in parts[current]:
				out.write(str(j) + " ")
			current += 1
			out.write("\n")
		out.write("\n")
