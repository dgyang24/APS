package SWEA.회문2_1216;

import java.io.File;
import java.util.Scanner;

class Solution {
	public static int max = 0;

	public static void main(String args[]) throws Exception {
		File file = new File("./src/SWEA/회문2_1216/input.txt");
		Scanner sc = new Scanner(file);

		for (int test_case = 1; test_case <= 10; test_case++) {
			max = 0;
			// 회문 길이 입력
			int T = sc.nextInt(); // 테스트 번호

			// 100x100배열 생성
			char[][] arr = new char[100][100];
			for (int i = 0; i < 100; i++) {
				String str = sc.next();
				for (int j = 0; j < 100; j++) {
					arr[i][j] = str.charAt(j);
				}
			}
			for (int N = 100; N > 0; N--) {
				// N개짜리 회문찾기 시작!
				// 행기준(가로)
				for (int i = 0; i < arr.length; i++) {
					for (int j = 0; j < arr[i].length - N + 1; j++) {
						boolean isFound = false;
						for (int k = 0; k < N/2; k++) {
							if(arr[i][j + k] != arr[i][j+N-1-k]) {
								break;
							}
							isFound = true;
						}
						if(isFound && N > max) {
							max = N;
							break;
						}
					}
				}
				// 열기준(세로)
				for (int j = 0; j < arr[0].length; j++) {
					for (int i = 0; i < arr.length - N + 1; i++) {
						boolean isFound = false;
						for (int k = 0; k < N; k++) {
							if(arr[i+k][j] != arr[i+N-1-k][j]) {
								break;
							}
							isFound = true;
						}
						if(isFound && N > max ) {
							max = N;
							break;
						}
					}
				}
			}

			System.out.println("#" + test_case + " " + max);
		}
	}

}
//답
//#1 18
//#2 17
//#3 17
//#4 20
//#5 18
//#6 21
//#7 18
//#8 18
//#9 17
//#10 18