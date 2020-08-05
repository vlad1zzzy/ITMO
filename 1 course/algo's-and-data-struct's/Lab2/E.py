if __name__ == "__main__":
	func = list(input().split())
	stack = []
	op = 0
	for i in func:
		if i == "+" or i == "-" or i == "*":
			op += 1
	for i in func:
		if i == "+" or i == "-" or i == "*":
			num1 = stack[-2]
			num2 = stack[-1]
			stack.pop()
			stack.pop()
			stack.append(str(eval(num1 + i + num2)))
		else:
			stack.append(i)
	print(stack[-1])