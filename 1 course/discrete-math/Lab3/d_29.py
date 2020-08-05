def nextPart(part):
	part[-1] -= 1
	part[-2] += 1
	if part[-2] > part[-1]:
			part[-2] += part[-1]
			part.pop()
	else:
		while part[-2] * 2 <= part[-1]:
			part.append(part[-1] - part[-2])
			part[-2] = part[-3]
	return part


if __name__ == '__main__':
	file = open('nextpartition.in')
	out = open('nextpartition.out', 'w')
	inp = file.readline()
	index = 0
	part = []
	while index < len(inp):
		num = ''
		while index < len(inp) and inp[index] != "=" and inp[index] != "+":
		    num += inp[index]
		    index += 1
		part.append(int(num))
		index += 1
	n = part[0]
	if n == part[1]:
		out.write("No solution")
	else:	
		res = nextPart(part[1:])
		out.write(str(n) + "=" + str(res[0]))
		for i in range(1,len(res)):
			out.write("+" + str(res[i]))
	file.close()
	out.close()