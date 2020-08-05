def scheme():
	result = []
	countLeaf = 0
	n = int(input())
	tree = []
	for i in range(n):
		x = input()
		#если 0 - это лист
		if x == "0":
			countLeaf += 1
			tree.append([0, 0]) #глубина + входной бит
		else:
			stat = list(map(int,x.split()))
			maxDeep = 0
			for e in stat[1:]:
				if tree[e-1][0] > maxDeep:
					maxDeep = tree[e-1][0] #глубина текущего (-1)
			#для каждой вершины храним глубину, таблицу истинности,
			#входы, результат функции
			tree.append([maxDeep+1, list(map(int,input().split())), stat[1:], 0])
	#для все чисел от 0 до k
	for i in range(2**countLeaf):
		#перевод в двоичное
		vhod = bin(i)[2:]
		num = "0"*(countLeaf-len(vhod)) + vhod
		indexNum = 0
		for j in range(n):
			#для всех переменных считаем значение
			#если это лист, просто добавляем из входного числа
			if len(tree[j]) < 3:
				tree[j][-1] = int(num[indexNum])
				indexNum += 1
			#иначе, берем значения из предыдущих
			else:
				pred = ""
				for k in tree[j][2]:
					pred += str(tree[k-1][-1])
				tree[j][-1] = tree[j][1][int(pred, 2)]
		result.append(tree[-1][-1])
	return tree[-1][0], result


if __name__ == '__main__':
	depth, res = scheme()
	print(depth)
	print(*res, sep='')