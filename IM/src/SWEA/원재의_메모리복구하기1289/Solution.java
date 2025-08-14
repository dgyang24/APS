package SWEA.원재의_메모리복구하기1289;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// tc 개수 T
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			// 목표 스트링
			String str = sc.next();
			// 목표 길이
			int N = str.length();
			// 초기 비트 int 배열
			int[] zero = new int[N];
			// 목표스트링 -> int 배열로 바꾸기
			int[] bit = new int[N];
			for (int i = 0; i < N; i++) {
				char c = str.charAt(i);
				if (c == '1') {
					bit[i] = 1;
				} else {
					bit[i] = 0;
				}
			}

			int cnt = 0;
			// 목표 배열을 순회하면서
			for (int i = 0; i < N; i++) {
				// 초기 배열과 같은 인덱스의 값이 다르면
				if (bit[i] != zero[i]) {
					// 해당 인덱스부터 끝까지 목표 배열인덱스 값으로 다 바꾸기
					for (int j = i; j < N; j++) {
						zero[j] = bit[i];
					}
					// 빠져나와서 cnt++
					cnt++;
				}
			}

			System.out.println("#" + tc + " " + cnt);

		}

	}

}

