package A.나무;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			int[] tree = new int[N];
			int[] diff = new int[N];
			int ans = 0; // 걸리는 날짜
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < N; i++) {
				int n = sc.nextInt();
				tree[i] = n;
				max = Math.max(max, n);
			}
			// 각 최대값과 요소의 차 구하기
			for (int i = 0; i < N; i++) {
				diff[i] = max - tree[i];
			}
			// 현재날짜 기록용
			int day = 0;
			boolean isDone = false;
			// 물주기 시작
			// 각 차가 0이 아닐 동안
			while (!isDone) {
				// 모든 차 검사해서 0이면 break;
				for(int i = 0; i < N; i++) {
					if(diff[i] != 0) {
						isDone = false;
						break;
					}
					isDone = true;
				}
				
				
				day = (day+1)%2 == 0 ? 2 : 1;
				//물 줬니?
				boolean isWater = false;
				// 전체 차 중에 하나라도 day와 같은 차가 있으면 그걸 물주고 break;
				for (int k = 0; k < N; k++) {
					if (diff[k] != 0 && diff[k]%2 == day%2) {
						int water = diff[k] % 2 == 0 ? 2 : 1;
						diff[k] -= water;
						isWater = true;
						break;
					}
				} // for
				//물 안준 경우
				if(!isWater) {
					//diff[k]자체가 2 보다 크면 물줘버려
					for (int k = 0; k < N; k++) {
						if (diff[k] > 2) {
							diff[k] -= day;
							break;
						}
					} // for
				}
				
				//System.out.println("day: " + day +" "+ "diff: " + Arrays.toString(diff));
				ans ++;
				//System.out.println("ans: " + ans);
				
				
			}
		// 출력
		System.out.println("#" + tc + " " + ans);

		} // tc

	}// main

}
