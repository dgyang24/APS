package dg_exam;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			//방 개수 N
			int N = sc.nextInt();
			//방과 방 들어갔는지 여부체크 하는 배열 생성
			int[][] room = new int[N+1][1];
			for(int i = 2; i < N; i++) {
				room[i][0] = 1;
			}
//			System.out.println("room");
//			for(int i = 0; i < N+1; i++) {
//				System.out.print(room[i][0]+" ");
//			}
			
			//포탈위치 어디로 타야되는지 배열 생성 potal
			int[] potal = new int[N+1];
			for(int i = 1; i < N+1; i++) {
				potal[i] = sc.nextInt();
			}
//			System.out.println("potal");
//			for(int i = 0; i < N+1; i++) {
//				System.out.print(potal[i]);
//			}
			//포탈이동횟수
			int cnt = 0;
			//room 2부터 시작
			int ct = 1;
			ct = potal[ct];
			room[ct][0] = 0;
			//i가 N일때까지 반복
			while(true) {
				
				//room[i][0] == 0 <- 이미 들어간 방이으로 I++ cnt++ continue
				if(room[ct][0] == 0) {
					ct++;
					cnt++;
				}else if(room[ct][0] == 1) {
					room[ct][0] = 0;
					//그렇지 않은 경우는 i = potal[i]; 그리고 방문횟수 지워주기
					ct = potal[ct];
					
					//cnt++;
					cnt++;
				}
				if(ct == N) {
					break;
				}
				
			}
			cnt -=1;
			//출력
			System.out.println("#"+tc+" "+cnt);
			
			
			
			
			
		}//tc실행

	}

}


