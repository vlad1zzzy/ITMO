def generate(prefix):
	global n
	if len(prefix) == n:
		for i in prefix:
			out.write(i + " ")
		out.write("\n")
		return
	for i in range(1,n+1):
		if str(i) not in prefix:
			generate(prefix+str(i))

if __name__ == '__main__':
	file = open('permutations.in')
	out = open('permutations.out', 'w')
	n = int(file.readline())
	generate("")
	file.close()
	out.close()
