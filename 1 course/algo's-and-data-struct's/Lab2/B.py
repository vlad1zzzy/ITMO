if __name__ == "__main__":
	line = list(map(int,input().split()))
	stack = [[line[1], 1]]
	count = 0
	for i in range(2,line[0]+1):
		if line[i] == stack[-1][0]:
			stack[-1][1] += 1
		elif stack[-1][1] > 2:
			count += stack[-1][1]
			stack.pop()
			if line[i] == stack[-1][0]:
				stack[-1][1] += 1
			else:
				stack.append([line[i], 1])
		else:
			stack.append([line[i], 1])

		if i == line[0] and stack[-1][1] > 2:
			count += stack[-1][1]
	print(count)