def nextBrackets(s):
	op = cl = 0
	for i in reversed(s):
		if i == "(":
			op += 1
			if cl > op:
				break
		elif i == ")":
			cl += 1  
	new = s[:len(s) - op - cl]
	if new == "":
		return "-"
	new += ")" + "("*op + ")"*(cl-1)
	return new


if __name__ == '__main__':
	file = open('nextbrackets.in')
	out = open('nextbrackets.out', 'w')
	brackets = file.readline()[:-1]
	out.write(nextBrackets(brackets))
	file.close()
	out.close()