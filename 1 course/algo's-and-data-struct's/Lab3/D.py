if __name__ == '__main__':
	n = int(input())
	const = 10**9
	mobile1 = [1]*10
	mobile1[0] = mobile1[8] = 0
	mobile2 = mobile1.copy()
	for i in range(n-1):
		mobile2[0] = (mobile1[4] + mobile1[6]) % const
		mobile2[1] = (mobile1[6] + mobile1[8]) % const
		mobile2[2] = (mobile1[7] + mobile1[9]) % const
		mobile2[3] = (mobile1[4] + mobile1[8]) % const
		mobile2[4] = (mobile1[0] + mobile1[3] + mobile1[9]) % const
		mobile2[6] = (mobile1[0] + mobile1[1] + mobile1[7]) % const
		mobile2[7] = (mobile1[2] + mobile1[6]) % const
		mobile2[8] = (mobile1[1] + mobile1[3]) % const
		mobile2[9] = (mobile1[2] + mobile1[4]) % const
		mobile1 = mobile2.copy()
	res = 8 if n == 1 else (sum(mobile1) - 1) % const
	print(res)
	