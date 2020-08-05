if __name__ == '__main__':
	file = open('partition.in.txt')
	out = open('partition.out.txt', 'w')
	n = int(file.readline())
	res = [1]*n
	length = n
	while length > 0:
		out.write(str(res[0]))
		for i in range(1,length):
			out.write("+" + str(res[i]))
		if length == 1:
			break
		out.write("\n")
		res[length - 1] -= 1
		res[length - 2] += 1
		if res[length - 2] > res[length - 1]:
			res[length - 2] += res[length - 1]
			length -= 1
		else:
			while res[length - 2] * 2 <= res[length - 1]:
				res[length] = res[length - 1] - res[length - 2]
				length += 1
				res[length - 2] = res[length - 3]

	file.close()
	out.close()
