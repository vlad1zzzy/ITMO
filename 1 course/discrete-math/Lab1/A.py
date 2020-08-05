def krom():
	n,m = map(int, input().split())
	nabor = []
	for i in range(2**n):
		num = bin(i)[2:]
		l = len(num)
		nabor.append("0"*(15-l) + num)
	result = [0 for i in range(2**n)]
	for i in range(m):
		a, b = map(int,input().split())
		areverse = breverse = 0
		if a < 0:
			areverse = 1
			a = -a
		if b < 0:
			breverse = 1
			b = -b
		for j in range(2**n):
			if int(nabor[j][-a])^areverse == 0 and int(nabor[j][-b])^breverse == 0:
				result[j] += 1
	for i in result:
		if i == 0:
			return "NO"
	return "YES"

if __name__ == '__main__':
	print(krom())