def nextMultiPerm(perm):
	global n
	for i in range(n-2,-1,-1):
		if perm[i] < perm[i+1]:
			j = i + 1
			while j < (n - 1) and perm[i] < perm[j+1]:
			    j += 1
			perm[i], perm[j] = perm[j], perm[i]
			x = []
			for i in range(n-1, i, -1):
			    x.append(perm[i])
			return perm[:i] + x
	return [0]*n

if __name__ == '__main__':
	file = open('nextmultiperm.in')
	out = open('nextmultiperm.out', 'w')
	n = int(file.readline())
	perm = list(map(int,file.readline().split()))
	res = nextMultiPerm(perm)
	for i in res:
		out.write(str(i) + " ")
	file.close()
	out.close()