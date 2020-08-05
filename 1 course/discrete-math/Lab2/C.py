if __name__ == "__main__":
	file = open("mtf.in")
	outfile = open("mtf.out", "w")
	s = file.readline()[:-1]

	alf = [chr(i) for i in range(122, 96,-1)]

	for i in range(len(s)):
		index = alf.index(s[i])
		outfile.write(str(len(alf) - index) + " ")
		alf.remove(s[i])
		alf.append(s[i])

	file.close()
	outfile.close()