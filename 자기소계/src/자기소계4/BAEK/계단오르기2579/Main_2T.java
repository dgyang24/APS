package 자기소계4.BAEK.계단오르기2579;

import java.util.Scanner;

public class Main_2T {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] stairs = new int[N+1];
		int[] dp = new int[N+1];
		for(int i = 1; i <= N; i++)
			stairs[i] = sc.nextInt();
		//초기값설정
		if(N >= 1) dp[1] = stairs[1];
		if(N >= 2) dp[2] = dp[1] + stairs[2];
		for(int i = 3; i <= N; i++) {
			dp[i] = Math.max(dp[i-2]+stairs[i], dp[i-3]+stairs[i-1]+stairs[i]);
			
		}
		System.out.println(dp[N]);
	}

}
