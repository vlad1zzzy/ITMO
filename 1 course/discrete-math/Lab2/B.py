if __name__ == "__main__":
	file = open("bwt.in")
	s = file.readline()[:-1]
	
	lines = []
	for i in range(len(s)):
		lines.append(s[i:] + s[:i])
	lines.sort()

	out = open("bwt.out", "w")
	for i in lines:
		out.write(i[-1])

	file.close()
	out.close()

s = open("bwt.in.txt").readline()[-1]
open("bwt.out.txt", "w").write("".join(i[-1] for i in sorted([s[x:]+s[:x] for x in range(len(s))])))