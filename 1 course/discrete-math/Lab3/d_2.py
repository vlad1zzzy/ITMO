if __name__ == '__main__':
	file = open('gray.in')
	out = open('gray.out', 'w')
	n = int(file.readline())
	gray = ["0"]*(2**n)
	gray[1] = "1"
	x = 2
	for i in range(1, n):
		temp = x - 1
		x *= 2
		for j in range(x//2, x):
			gray[j] = gray[temp]
			gray[temp] += "0"
			gray[j] += "1"
			temp -= 1
	for i in gray:	
		out.write(i[::-1] + "\n")
	file.close()
	out.close()
