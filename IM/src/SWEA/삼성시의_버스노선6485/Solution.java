package SWEA.삼성시의_버스노선6485;

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileInputStream;

class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			//노선개수
			int N = sc.nextInt();
			//노선개수만큼 배열 생성
			int[][] route = new int[N][2];
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < 2; j++) {
					route[i][j] = sc.nextInt();
				}
			}
			//노선 번호 개수
			int P = sc.nextInt();
			//노선 각 번호 배열 생성
			int[] arr = new int[P];
			for(int j = 0; j < P; j++) {
				arr[j] = sc.nextInt();
			}
			int[] cnt = new int[P];
			for(int i = 0; i < N; i++) {
				int A = route[i][0];
				int B = route[i][1];
				for(int j = 0; j < P; j++) {
					if(arr[j] >= A && arr[j] <= B) {
						cnt[j]++;
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < cnt.length; i++)
				sb.append(cnt[i]).append(" ");
			System.out.println("#"+test_case+" "+ sb);
			
			
		}
	}
}