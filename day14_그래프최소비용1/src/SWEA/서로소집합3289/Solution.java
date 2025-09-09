package SWEA.서로소집합3289;

import java.util.Scanner;

public class Solution {
	static int[] arr;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();// 집합 크기
			int M = sc.nextInt();// 명령 개수
			arr = new int[N+1];
			//초기 집합 초기화
			for(int i = 0; i < N; i++)
				arr[i] = i;
			StringBuilder sb = new StringBuilder();
			//명령을 M번 실행
			for(int i = 0; i < M; i++) {
				int cmd = sc.nextInt();
				int x = sc.nextInt();
				int y =sc.nextInt();
				//합집합
				if(cmd == 0) {
					arr[findSet(x)] = findSet(y);
				}
				//같은 집합에 있는 지 판단 후 sb 합치기
				else {
					if(findSet(x) == findSet(y)) sb.append('1');
					else sb.append('0');
				}
			}//명령
			System.out.println("#"+tc+" "+sb);
			
		}//tc

	}//main
	
	//findSet : 대표자 찾기
	static int findSet(int x) {
		if(x != arr[x])
			arr[x] = findSet(arr[x]);
		return arr[x];
	}

}
