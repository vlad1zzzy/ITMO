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
	file = open('num2part.in')
	out = open('num2part.out', 'w')
	n, r = map(int, file.readline().split())
	part = [1]*n
	for i in range(r):
		part = nextPart(part)
	out.write(str(part[0]))
	for i in part[1:]:
		out.write("+" + str(i))

	file.close()
	out.close()
