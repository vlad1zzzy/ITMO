if __name__ == "__main__":
	file = open("lzw.in")
	outfile = open("lzw.out", 'w')
	s = file.readline()[:-1]

	buff = [chr(i) for i in range(97, 123)]
	t = ""
	
	for c in s:
		tc = t + c
		if tc in buff:
			t = tc
		else:
			outfile.write(str(buff.index(t)) + ' ')
			buff.append(tc)
			t = c
	outfile.write(str(buff.index(t)) + ' ')

	file.close()
	outfile.close()

		