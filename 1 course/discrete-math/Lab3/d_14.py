def number(n):
	global num, was, fac
	for i in range(1,n+1):
		for j in range(1,res[i]):
			if not was[j]:
				num += fac[n-i]
		was[res[i]] = True
	return num


if __name__ == '__main__':
	file = open('perm2num.in.txt')
	out = open('perm2num.out.txt', 'w')
	n = int(file.readline())
	res = [0] + list(map(int,file.readline().split()))
	fac = [1]
	for i in range(1,n+1):
		fac.append(fac[-1]*i)
	was = [False]*(n+1)
	num = 0
	out.write(str(number(n)))
	file.close()
	out.close()
