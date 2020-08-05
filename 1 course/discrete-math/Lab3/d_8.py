def generate(prefix):
	global n,k
	if len(prefix) == k:
		for i in prefix:
			out.write(str(i) + " ")
		out.write("\n")
		return
	for i in range(1,n+1):
		if prefix == [] or i > int(prefix[-1]):
			generate(prefix+[i])

if __name__ == '__main__':
	file = open('choose.in')
	out = open('choose.out', 'w')
	n,k = map(int,file.readline().split())
	generate([])
	file.close()
	out.close()