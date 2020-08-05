if __name__ == "__main__":
	file = open("huffman.in")
	n = int(file.readline())
	p = list(map(int,file.readline().split()))
	result = 0

	for i in range(n-1):
		p.sort()
		CurrentSum = p[i] + p[i+1]
		p[i+1] = CurrentSum
		result += CurrentSum
	
	out = open("huffman.out", "w")
	out.write(str(result))