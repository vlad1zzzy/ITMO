def nextChoose(choose):
	global n,k
	choose.append(n+1)
	i = k - 1
	while i >= 0 and (choose[i+1] - choose[i]) < 2:
		i -= 1
	if i >= 0:
		choose[i] += 1
		for j in range(i+1,k):
			choose[j] = choose[j-1] + 1
		return choose[:-1]
	return [-1]


if __name__ == '__main__':
	file = open('nextchoose.in')
	out = open('nextchoose.out', 'w')
	n, k = map(int,file.readline().split())
	choose = list(map(int,file.readline().split()))
	nextchoose = nextChoose(choose)
	for i in nextchoose:
		out.write(str(i) + " ")
	file.close()
	out.close()