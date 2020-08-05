if __name__ == "__main__":
	n = int(input())
	a = list(map(int,input().split()))
	stack = [a[0]]
	commands = ["push"]
	deleteMax = 0
	for i in range(1, n):
		if a[i] < deleteMax:
			commands = ["impossible"]
			break
		if a[i] < stack[-1]:
			stack.append(a[i])
			commands.append("push")
			curMin = a[i]
		else:
			while stack and a[i] > stack[-1]:
				commands.append("pop")
				deleteMax = stack[-1]
				stack.pop()
			stack.append(a[i])
			commands.append("push")
		if i == n-1 and stack != []:
			for j in stack:
				commands.append("pop")

	print(*commands, sep="\n")