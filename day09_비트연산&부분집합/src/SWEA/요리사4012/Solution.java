package SWEA.요리사4012;

import java.util.Scanner;

public class Solution {
	static int[][] arr;
	static boolean[] selected;
	static int min;
	static int N;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <=T; tc++) {
			min = Integer.MAX_VALUE;
			N = sc.nextInt();
			arr = new int[N][N];
			selected = new boolean[N];
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					arr[i][j] = sc.nextInt();
				}
			}
			cooking(0,0,selected);
			
			
			System.out.println("#"+tc+" "+min);
			
		}
	}
	public static void cooking(int idx, int cnt, boolean[] selected) {
		//종료파트
		if(cnt == N/2) {
			int sumA = 0;
			int sumB = 0;
			for(int i = 0; i < N; i++) {
				for(int j = i+1; j < N; j++) {
					if(selected[i] && selected[j]) sumA+=arr[i][j] + arr[j][i];
					else if(!selected[i] && !selected[j]) sumB+=arr[i][j] + arr[j][i];
				}
			}
			min = Math.min(min, Math.abs(sumA-sumB));
			return;
		}
		if(idx >= N) return;
		
		//재귀파트
		//현재료를 A에 넣기
		selected[idx] = true;
		cooking(idx+1, cnt+1, selected);
		//B에 넣기
		selected[idx] = false;
		cooking(idx+1, cnt, selected);
		
	}	
	
	
	

}
