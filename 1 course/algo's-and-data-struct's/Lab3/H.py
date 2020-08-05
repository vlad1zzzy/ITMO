def nextTown(i):


if __name__ == '__main__':
	n = int(input())
	roads = []
	for i in range(n):
		roads.append(list(map(int,input().split())))
	dp = [[[0 for k in range(n)] for j in range(n)] for i in range(n)]
	for i in range(n):
		was = [0]*n
		was[i] = 1
		