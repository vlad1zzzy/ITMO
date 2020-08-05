def generate(prefix):
	global n, res, count
	if len(prefix) == n:
		res.append(prefix)
		count += 1
		return
	generate(prefix + '0')
	if prefix == "" or prefix[-1] == '0':
		generate(prefix + '1')


if __name__ == '__main__':
	file = open('vectors.in')
	out = open('vectors.out', 'w')
	n = int(file.readline())
	count = 0
	res = []
	generate("")
	out.write(str(count) + "\n")
	for i in res:
		out.write(i + "\n")
	file.close()
	out.close()
