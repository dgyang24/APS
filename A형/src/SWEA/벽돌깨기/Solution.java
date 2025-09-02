package SWEA.벽돌깨기;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	// 크기
	static int N;
	// 경사로 길이
	static int X;
	// 절벽지대
	static int[][] arr;
	// 활주로 개수
	static int cnt;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			// N, X, 활주로 개수 초기화
			N = sc.nextInt();
			X = sc.nextInt();
			cnt = 0;
			// 절벽지대 초기화
			arr = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					arr[i][j] = sc.nextInt();
				}
			}
			// 탐색 시작!
			// 가로줄
			for (int i = 0; i < N; i++) {
				findFlyLoad(arr[i]);
			}
			// 세로줄
			for (int j = 0; j < N; j++) {
				int[] tmp = new int[N];
				for (int i = 0; i < N; i++) {
					tmp[i] = arr[i][j];
				}
				findFlyLoad(tmp);
			}
			// 출력
			System.out.println("#" + tc + " " + cnt);

		} // tc
	}// main

	// 활주로 탐색 한줄씩 할거양
	public static void findFlyLoad(int[] arr) {
		// 각행의 최대값 도출
		int max = Integer.MIN_VALUE;
		boolean isSame = true;
		for (int i = 0; i < arr.length - 1; i++) {
			max = Math.max(max, arr[i]);
			if (arr[i] != arr[i + 1]) {
				isSame = false;
			}
		}
		max = Math.max(max, arr[arr.length - 1]);
		if (isSame) {
			cnt++;
			System.out.println(Arrays.toString(arr));
			System.out.println("이거걸림");
		} else {
			int tmpI = 0;
			// 2포인터
			for (int i = 0; i < arr.length; i++) {
				System.out.println("시작i: " + i);
				// max값이면 continue
				if (arr[i] == max)
					continue;
				// 포이터
				int n = i;
				// n+1이 범위를 벗어나지 않고, n == n+1이 같을동안 D++
				while (n + 1 < arr.length && (arr[n] == arr[n + 1])) {
					n++;
				}
				// D != X면 break;
				if (Math.abs(i - n) + 1 < X) {
					if (tmpI > 0) {
						tmpI = 0;
						System.out.println(Arrays.toString(arr));
						System.out.println("이거 안걸림");
					}
					break;
				}
				// D==X면 i-1과 n+1값 비교
				else if (Math.abs(i - n) + 1 >= X) {
					int tmp = 0;
					if ((i - 1 >= 0) && (arr[i - 1] == arr[i] + 1))
						tmp++;
					if ((n + 1 < arr.length) && (arr[n + 1] == arr[i] + 1))
						tmp++;
					if (tmp == 1) {
						System.out.println("i: " + i);
						System.out.println("n: " + n);
						System.out.println("거리: " + (Math.abs(i - n) + 1));
						tmpI++;
						i = n--;
					}
				}

			}
			
			//역방향 탐색
			if(fly2(arr, max)) {
				return;
			}else {
				if (tmpI == 1) {
					System.out.println(Arrays.toString(arr));
					System.out.println("이거걸림");
					cnt++;
				}
			}
			
			
			

		}

	}
	//역방향 탐색
	static boolean fly2(int[] arr, int max) {
		int tmpI = 0;
		// 2포인터
		for (int i = arr.length-1; i >= 0; i--) {
			// max값이면 continue
			if (arr[i] == max)
				continue;
			// 포이터
			int n = i;
			// n+1이 범위를 벗어나지 않고, n == n+1이 같을동안 D++
			while (n + 1 < arr.length && (arr[n] == arr[n - 1])) {
				n--;
			}
			// D != X면 break;
			if (Math.abs(i - n) + 1 < X) {
				if (tmpI > 0) {
					tmpI = 0;
				}
				break;
			}
			// D==X면 i-1과 n+1값 비교
			else if (Math.abs(i - n) + 1 >= X) {
				int tmp = 0;
				if ((i - 1 >= 0) && (arr[i - 1] == arr[i] + 1))
					return true;
				if ((n + 1 < arr.length) && (arr[n + 1] == arr[i] + 1))
					return true;
				if (tmp == 1) {
					return true;
				}
			}

		}
		if (tmpI == 1) {
			return true;
		}else {
			return false;
		}
		
	}

}
