file1 = ['1', '2', '3', '4', '5', '6', '7']
file2 = ['10words', '1000words', '1000words2', 'sawyer', 'sawyer-mohicans', 'mohicans', 'mohicans-sawyer']

n = int(input())
for i in range(n, n+1):
	with open(f'./output/{file1[i]}.out', 'r') as f1, open(f'./public/{file2[i]}.out', 'r') as f2:
		lines1 = f1.readlines()
		lines2 = f2.readlines()

		min_len = min(len(lines1), len(lines2))

		for j in range(min_len):
			if lines1[j] != lines2[j]:
				print(f'Difference in {file2[i]} line {j + 1}:')
				print(f'Output: {lines1[j]}')
				print(f'Public: {lines2[j]}')
