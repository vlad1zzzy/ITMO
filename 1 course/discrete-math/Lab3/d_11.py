if __name__ == '__main__':
	file = open('subsets.in')
	out = open('subsets.out', 'w')
	n = int(file.readline())
	res = []
	for i in range(1, 2**n):
		res.extend([[]])
		for j in range(1,len(bin(i))):
			if bin(i)[-j] == '1':
				res[-1].append(j)
	res.sort()
	out.write('\n')
	for i in res:
		for j in i: 
			out.write(str(j) + " ")
		out.write('\n')
