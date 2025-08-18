package SWEA.두개의_숫자열1959;

import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			//첫번째 배열의 길이 M
			int M = sc.nextInt();
			
			//두번째 배열의 길이 N
			int N = sc.nextInt();
			//M과 N을 비교해서 큰 값을 arrM 작은 값을 arrN
			
			
//			int[] arrN = new int[N];
//			int[] arrM = new int[M];
//			for(int i = 0; i < M; i++) {
//				arrM[i] = sc.nextInt();
//			}
//			for(int i = 0; i < N; i++) {
//				arrN[i] = sc.nextInt();
//			}
//			//큰것-작은것
//			int min = Math.min(M, N);
//			int max = 0;
//			//배열을 Math.abs(M-N+1)만큼 순회하면서 서로를 곱하고 sum에 합을 구함
//			for(int i = 0; i < Math.abs(M-N+1); i++) {
//				int sum = 0;
//				for(int j = i; j < min+i; j++) {
//					sum+= arrM[j] * arrN[i];
//				}
//				if(sum > max)
//					max = sum;
//			}
//			System.out.println("#"+tc+" "+max);
			
			
		}

	}

}
