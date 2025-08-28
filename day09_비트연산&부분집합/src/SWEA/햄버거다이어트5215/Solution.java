package SWEA.햄버거다이어트5215;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			//재료 개수
			int N = sc.nextInt();
			//칼로리 제한
			int L = sc.nextInt();
			//맛 최대 점수!
			int max = 0;
			//맛점수랑 칼로리 배열
			int[] score = new int[N];
			int[] cal = new int[N];
			for(int i = 0; i < N; i++) {
				score[i] = sc.nextInt();
				cal[i] = sc.nextInt();
			}
//			System.out.println(Arrays.toString(score));
//			System.out.println(Arrays.toString(cal));
			//조합 시작
			for(int i = 0; i < (1<<N); i++) {
				int tmpS = 0;
				int tmpC = 0;
				for(int j = 0; j < N; j++) {
					if((i & (1 << j)) != 0 && tmpC <= L) {
						tmpC += cal[j];
						tmpS += score[j];
//						System.out.println(tmpC);
//						System.out.println(tmpS);
					}
					if(tmpC > L) break; 
					max = Math.max(max, tmpS);
				}
			}
			//출력
			System.out.println("#"+tc+" "+max);
		}
		
		
	}
}
    