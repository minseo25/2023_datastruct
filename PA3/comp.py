def compare_files(file1, file2):
    """두 파일의 내용을 한 줄씩 비교하고 차이점을 출력합니다."""
    with open(file1, 'r') as f1, open(file2, 'r') as f2:
        line_number = 1
        while True:
            line1 = f1.readline()
            line2 = f2.readline()

            # 두 파일 중 하나라도 끝에 도달했다면 반복을 중단합니다.
            if not line1 or not line2:
                break
            
            # 최단경로가 다르지만 출발지/출발시각/도착지/도착시각 같으면 pass
            if line1.startswith('['):
                if line1[-6:-2]==line2[-6:-2] and line1[-16:-13]==line2[-16:-13]:
                #if line1[1:4]==line2[1:4] and line1[10:14]==line2[10:14] and line1[-6:-2]==line2[-6:-2] and line1[-16:-13]==line2[-16:-13]:
                    line_number += 1
                    continue

            if line1 != line2:
                print(f"Difference in line {line_number}:")
                print(f"{file1}: {line1.strip()}")
                print(f"{file2}: {line2.strip()}\n")

            line_number += 1

def main():
    files = ["tickets01.out", "tickets02.out", "tickets03.out", "tickets04.out", "tickets05.out", "tickets06.out", "tickets07.out"]
    
    for filename in files:
        print(f"Comparing {filename} ...")
        compare_files(f"my/{filename}", f"public/{filename}")

if __name__ == "__main__":
    main()

