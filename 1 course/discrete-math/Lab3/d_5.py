if __name__ == '__main__':
	file = open('telemetry.in')
	out = open('telemetry.out', 'w')
	n, k = map(int, file.readline().split())
	res = [""]*(k**n)
	count = 1
	for q in range(n):	
		pos = cur = 0
		reverse = False
		while pos < k**n:
			for i in range(count):
				res[pos] += str(cur)
				pos += 1
			if reverse:
				cur -= 1
			else:
				cur += 1
			if cur == k:
				reverse = True
				cur -= 1
			elif cur == -1:
				reverse = False
				cur += 1
		count *= k
	for i in res:
		out.write(i + "\n")
	file.close()
	out.close()
