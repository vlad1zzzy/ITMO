def horn():
	n,k = map(int,input().split())
	nabor = []
	for i in range(2 if n==1 else n**2):
		num = bin(i)[2:]
		l = len(num)
		nabor.append("0"*(15-l) + num)
	result = [0 for i in range(2 if n==1 else n**2)]
	for i in range(k):
		a = list(map(int,input().split()))
		flag = 0
		for j in range(len(nabor)):
			flag = 0
			for x in range(n):
				if (a[x] == 1 and nabor[j][-x-1] == '0') or (a[x] == 0 and nabor[j][-x-1] == '1'):
					break
				flag += 1
			if flag == n:
				result[j] += 1
	for i in result:
		if i == 0:
			return "NO"
	return "YES"

if __name__ == '__main__':
	print(horn())