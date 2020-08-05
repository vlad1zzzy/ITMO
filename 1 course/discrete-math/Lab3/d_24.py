def nextPerm(perm):
	global n
	permCopy = perm.copy()
	for i in range(n-2,-1,-1):
		if permCopy[i] < permCopy[i+1]:
			minimum = i + 1
			for j in range(i+1,n):
				if permCopy[i] < permCopy[j] < permCopy[minimum]:
					minimum = j
			permCopy[i], permCopy[minimum] = permCopy[minimum], permCopy[i]
			x = []
			for i in range(n-1, i, -1):
			    x.append(permCopy[i])
			return permCopy[:i] + x
	return [0]*n

def prevPerm(perm):
	global n
	for i in range(n-2,-1,-1):
		if perm[i] > perm[i+1]:
			maximum = i + 1
			for j in range(i+1,n):
				if perm[maximum] < perm[j] < perm[i]:
					maximum = j
			perm[i], perm[maximum] = perm[maximum], perm[i]
			x = []
			for i in range(n-1, i, -1):
			    x.append(perm[i])
			return perm[:i] + x
	return [0]*n


if __name__ == '__main__':
	file = open('nextperm.in')
	out = open('nextperm.out', 'w')
	n = int(file.readline())
	perm = list(map(int,file.readline().split()))
	nextperm = nextPerm(perm)
	prevperm = prevPerm(perm)
	for i in prevperm:
		out.write(str(i) + " ")
	out.write("\n")
	for i in nextperm:
		out.write(str(i) + " ")
	file.close()
	out.close()