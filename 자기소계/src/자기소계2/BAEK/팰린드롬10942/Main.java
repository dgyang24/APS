package 자기소계2.BAEK.팰린드롬10942;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N =sc.nextInt();
		int[] arr = new int[N+1];
		for(int i = 1; i<= N; i++)
			arr[i] = sc.nextInt();
		//회문 판단 배열
		boolean[][] dp = new boolean[N+1][N+1];
		//길이가 1일땐 무조건회문
		for(int i = 1; i <= N; i++) dp[i][i] = true;
		//길이2
		for(int i = 1; i < N; i++) {
			if(arr[i] == arr[i+1]) {
				dp[i][i+1] =true;
			}
		}
		//길이가 3이상일때
		for(int len = 3; len <= N; len++) {
			for(int s = 1; s + len -1 <=N; s++) {
				int e = s + len -1;
				if(arr[s] == arr[e] && dp[s+1][e-1]) {
					dp[s][e] = true;
				}
			}
		}
		//질문 수
		int T = sc.nextInt();
		StringBuilder sb = new StringBuilder();
		for(int tc = 0; tc < T; tc++) {
			int s = sc.nextInt();
			int e = sc.nextInt();
			
			//회문검사
			if(dp[s][e]) sb.append(1).append("\n");
			else sb.append(0).append("\n");
		}
		System.out.println(sb);
		
	}

}
