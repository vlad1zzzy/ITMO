def predvector(vec):
	global n
	if vec == "1":
		return "0\n"
	for i in range(n,0,-1):
		if vec[i] == "1":
			return vec[:i] + "0" + "1"*(n-i) + "\n"
	return "-\n"

def nextvector(vec):
	global n
	if vec == "0":
		return "1"
	for i in range(n,0,-1):
		if vec[i] == "0":
			return vec[:i] + "1" + "0"*(n-i)
	return "-"

if __name__ == '__main__':
	file = open('nextvector.in')
	out = open('nextvector.out', 'w')
	vec = file.readline()[:-1]
	n = len(vec) - 1
	pred = predvector(vec)
	nextn = nextvector(vec)
	out.write(pred)
	out.write(nextn)
	file.close()
	out.close()