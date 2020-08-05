def generate(opener, closer, prefix):
	global n
	if opener + closer == n * 2:
		out.write(prefix + "\n")
		return
	if opener < n:
		generate(opener+1, closer, prefix+"(")
	if closer < opener:
		generate(opener, closer+1, prefix+")")	
if __name__ == '__main__':
	file = open('brackets.in')
	out = open('brackets.out', 'w')
	n = int(file.readline())
	generate(0,0,"")
	file.close()
	out.close()
