if __name__ == '__main__':
	file = open('partition.in')
	out = open('partition.out', 'w')
	n, r = map(int, file.readline().split())
	res = [[1]*n]
	length = n
	while length > 0:
		if length == 1:
			break
		res.append(res[-1].copy())
		res[-1][length - 1] -= 1
		res[-1][length - 2] += 1
		if res[-1][length - 2] > res[-1][length - 1]:
			res[-1][length - 2] += res[-1][length - 1]
			length -= 1
		else:
			while res[-1][length - 2] * 2 <= res[-1][length - 1]:
				res[-1][length] = res[-1][length - 1] - res[-1][length - 2]
				length += 1
				res[-1][length - 2] = res[-1][length - 3]
	out.write(str(res[r][0]))
	for i in res[r][1:]:
		out.write("+" + str(i))

	file.close()
	out.close()
