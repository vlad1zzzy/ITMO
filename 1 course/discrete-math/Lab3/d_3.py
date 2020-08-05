def c(num):
	x = ''
	for i in range(len(num)):
		x += "1"
	num1 = ""
	num2 = ""
	for i in range(len(num)):
		num1 += str((int(num[i])+int(x[i]))%3)
	for i in range(len(num)):
		num2 += str((int(num1[i])+int(x[i]))%3)
	return num1, num2

if __name__ == '__main__':
	file = open('antigray.in')
	out = open('antigray.out', 'w')
	n = int(file.readline())
	for i in range(3**(n-1)):
		num = ""
		while i > 0:
			num = str(i % 3) + num
			i //= 3
		num = "0"*(n-len(num)) + num
		out.write(num + "\n")
		s = c(num)
		out.write(s[0] + "\n" + s[1] + "\n")
	file.close()
	out.close()
