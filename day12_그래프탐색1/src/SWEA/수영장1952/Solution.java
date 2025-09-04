package SWEA.수영장1952;

import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			// 각 개월까지의 최소 요금(동적 계획법 적용)
			int[] dp = new int[13];
			// 유형별 요금
			int [] fee = new int[4];
			// 각 개월당 계획 1~12로 계획하고 싶음
			int [] flan = new int[13];
			// 유형별 요금 초기화
			for (int i = 0; i < 4; i++)
				fee[i] = sc.nextInt();
			// 플랜 세우기 1~12월
			for (int i = 1; i <= 12; i++)
				flan[i] = sc.nextInt();
			// 초기화 끝!

			// 1~12개월까지 돌면서 각 개월까지의 최저요금을 갱신할거야.
			for (int i = 1; i <= 12; i++) {
				//1. 일 vs 월별 중 싼 것 넣기
				int cost = Math.min(flan[i]*fee[0], fee[1]);
				dp[i] = dp[i-1] + cost;
				
				//2. 3개월 요금 환산
				if(i >= 3) {
					dp[i] = Math.min(dp[i], dp[i-3] + fee[2]);
				}
			} // 요금 계산 끝
			//3. 12월에 비교
			dp[12] = Math.min(dp[12], fee[3]);
			// 출력
			System.out.println("#" + tc + " " + dp[12]);

		} // tc
	}
	
}
