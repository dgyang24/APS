package SWEA.창용마을무리의개수74665;

import java.util.Scanner;

public class Solution {
	static int[] p;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T =sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			int M = sc.nextInt();
			p = new int[N+1];
			initP();
			//초기화
			
			//대표자 순회하면서 각 사람에 맞는 대표자 세팅
			for(int i = 0; i < M; i++) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				
				p[findSet(y)] = findSet(x);
				
			}
//			System.out.println(Arrays.toString(p));
			//무리 세기
			int cnt = 0;
			int n = p[1]; //비교대상
			for(int i = 1; i < p.length; i++) {
				//내 인덱스 배열과 같은 값만 세기
				if(i == p[i]) cnt++;
			}
			System.out.println("#"+tc+" "+cnt);
			
		}//tc

	}//main
	//대표자 세팅
	static void initP() {
		for(int i = 1; i < p.length; i++)
			p[i] = i;
	}
	//대표자 찾아줘잉
	static int findSet(int x) {
		if( x != p[x])
			p[x] = findSet(p[x]);
		return p[x];
		
	}
}
