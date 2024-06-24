file_names = [f'test0{i}.out' for i in range(1,10)]

for file in file_names:
	with open(f'./output/{file}', 'r') as f1, open(f'./intended_output/{file}', 'r') as f2:
		cont1 = f1.read()
		cont2 = f2.read()
		
		if cont1 == cont2:
			print(f'{file}: Match')
		else:
			print(f'{file}: Does not Match')
