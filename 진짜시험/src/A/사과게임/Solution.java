package A.사과게임;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Solution {
	//1~4분면 
	static LinkedList<Integer> q;
	//사과좌표
	static int[][] apple;
	//변경횟수
	static int cnt;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			q = new LinkedList<Integer>();
			q.add(3);
			q.add(3);
			q.add(2);
			q.add(1);
			cnt = 0;
			apple = new int[11][2];
			int[][] arr = new int[N][N];
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					int n = sc.nextInt();
					arr[i][j] = n;
					if(n != 0) {
						apple[n][0] = i;
						apple[n][1] = j;
					}
				}
			}//입력 끝
			
			findApple(0,0);
			//출력
			System.out.println("#"+tc+" "+cnt);
			
			
		}
	}//main
	static void findApple(int r, int c) {
		//현재 위치
		int cr = r;
		int cc = c;
		//현재 방향
		int D = 0;
		//apple 배열을 순회하면서 0,0이 아니면 해당 좌표 찾을거야
		for(int i = 0; i < apple.length; i++) {
			int appR = apple[i][0];
			int appC = apple[i][1];
			if(appR != 0 && appC != 0) {
				int change = 0;
				int nr = cr - appR;
				int nc = cc - appC;
				//System.out.println(i+"번째 사과: " + "nc: " + nc + "nr "+nr);
				//1사분면
				if(nr > 0 && nc < 0) {
					change = q.get(0);
					//방향 카운트
					cnt += change;
					//System.out.println("1사분면이다잉~");
				}
				//2사분면
				else if(nr > 0 && nc > 0 ) {
					change = q.get(1);
					//방향 카운트
					cnt += change;
					//System.out.println("2사분면이다잉~");
				}
				//3사분면
				else if(nr < 0 && nc > 0) {
					change = q.get(2);
					//방향 카운트
					cnt += change;
					//System.out.println("3사분면이다잉~");
				}
				//4사분면
				else if(nr < 0 && nc < 0) {
					change = q.get(3);
					//방향 카운트
					cnt += change;
					//System.out.println("4사분면이다잉~");
				}
				//방향이동에 따라 턴하는 값도 변경
				for(int k = 0; k < change; k++) {
					q.offerLast(q.pollFirst());
					//System.out.println("방향: "+ D + " "+q);
				}
				cr = appR;
				cc = appC;
			}
		}//사과 순회
	}//사과 탐색
}