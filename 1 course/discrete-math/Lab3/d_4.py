if __name__ == '__main__':
	file = open('chaincode.in')
	out = open('chaincode.out', 'w')
	n = int(file.readline())
	cur = "0" * n
	res = [cur]
	stat = [0]*(2**n)
	stat[0] = 1
	while True:
		prefix = cur[1:]
		print(int(prefix + "1", 2))
		if stat[int(prefix + "1", 2)] == 0:
			cur = prefix + "1"
			stat[int(prefix + "1", 2)] = 1
		elif stat[int(prefix + "0", 2)] == 0:
			cur = prefix + "0"
			stat[int(prefix + "0", 2)] = 1
		else:
			break
		res.append(cur)
	for i in res:
		out.write(i + "\n")
	file.close()
	out.close()