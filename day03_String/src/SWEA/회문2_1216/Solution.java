package SWEA.회문2_1216;

import java.io.File;
import java.util.Scanner;

class Solution {
	public static int max = 0;

	public static void main(String args[]) throws Exception {
		File file = new File("./src/SWEA/회문2_1216/input.txt");
		Scanner sc = new Scanner(file);

		for (int test_case = 1; test_case <= 10; test_case++) {
			int T = sc.nextInt(); // 테스트 번호
			// 최댓값
			int max = Integer.MIN_VALUE;
			// 글자판 크기
			int N = 100;
			// 배열
			char[][] arr = new char[100][100];
			// 입력받은 값을 바탕으로 배열 값 할당
			for (int i = 0; i < N; i++) {
				String str = sc.next();
				for (int j = 0; j < N; j++) {
					arr[i][j] = str.charAt(j);
				}
			}

			// i를 N부터 감소하면서 회문 찾을거임
			for (int i = N; i > 0; i--) {
				// 1.행기준순회하면서
				for (int r = 0; r < arr.length; r++) {
					// 회문인지 표기
					// i길이에 맞는 회문 검사
					for (int c = 0; c < arr[r].length - i + 1; c++) {
						boolean isPd = true;
						for (int x = 0; x < i / 2; x++) {
							if (arr[r][c + x] != arr[r][c + i - 1 - x]) {
								// 회문이 아니면 false하고 break
								isPd = false;
								break;
							}
						}
						//맞으면 max값보다 큰지 비교하고 max에 할당
						if (isPd && (i > max)) {
							max = i;
						}
					} // 열순회

				} // 행순회

				// 2. 열기준 순회하면서
				for (int c = 0; c < arr[0].length; c++) {

					// i길이에 맞는 회문 검사
					for (int r = 0; r < arr.length - i + 1; r++) {
						// 회문인지 표기
						boolean isPd = true;
						for (int x = 0; x < i / 2; x++) {
							if (arr[r + x][c] != arr[r + i - 1 - x][c]) {
								isPd = false;
								break;
							}
						}
						// 맞으면 max값보다 큰지 비교하고 max에 할당
						if (isPd && (i > max)) {
							max = i;
						}
					} // 행

				} // 열
				if (max != Integer.MIN_VALUE)
					break;
			} // 회문검사
			// max값 출력
			System.out.println("#" + T + " " + max);
		} // 테스트 실행
	}
}