if __name__ == '__main__':
	file = open('allvectors.in')
	out = open('allvectors.out', 'w')
	n = int(file.readline())
	for i in range(2**n):
		out.write("0"*(n-len(bin(i)[2:])) + bin(i)[2:] + "\n")
	file.close()
	out.close()
