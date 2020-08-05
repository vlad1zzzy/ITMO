def polinom():
	n = int(input())
	tab = []
	func = []
	for i in range(2**n):
		x,f = input().split()
		tab.append(x)
		func.append(int(f))
	m = 2**n
	for i in range(2**n - 1):
		print(tab[i], func[0])
		for j in range(m - 1):
			func[j] = func[j]^func[j+1]
		m -= 1
	print(tab[-1], func[0])

if __name__ == '__main__':
	polinom()