def full():
	Null = One = Bin = Mono = Line = False
	n = int(input())
	for i in range(n):
		s, double = input().split()
		if not Null:
			Null = isNotNull(double)
		if not One:
			One = isNotOne(double)
		if not Bin:
			Bin = isNotBin(double)
		if not Mono:
			Mono = isNotMono(double, s)
		if not Line:
			Line = isNotLinear(double)
	if (Null and One and Bin and Mono and Line):
		return "YES"
	return "NO"


def isNotNull(f):
	if f[0] == '1':
		return True
	return False

def isNotOne(f):
	if f[-1] == '0':
		return True
	return False

def isNotBin(f):
	if len(f) == 1:
		return True
	for i in range(len(f)//2):
		if f[i] == f[len(f) - i - 1]:
			return True
	return False

def isNotMono(f, s):
	m = len(f)
	delim = m//2
	ind = 1
	for i in range(int(s)):
		k = 0
		for j in range(ind):
			for l in range(delim):
				if f[l+k] > f[l+delim+k]:
					return True
			k += delim*2
		ind *= 2
		delim //= 2
	return False


def isNotLinear(f):
	m = k = len(f)
	func = []
	if len(f) == 1:
		return False
	for i in f:
		func.append(int(i))
	for i in range(len(f) - 1):
		if i not in {0,1,2,4,8,16} and func[0] == 1:
			return True
		for j in range(m - 1):
			func[j] = func[j]^func[j+1]
		m -= 1
	if k > 2 and func[0] == 1:
		return True
	return False

if __name__ == '__main__':
	print(full())
