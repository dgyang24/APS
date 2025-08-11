package 자기소계;

import java.util.Arrays;
import java.util.Scanner;

public class Algo2_서울_13_양동근 {
	//팔방 탐색을 위한 델타
	static int[] dr = {-1,1,0,0,-1,-1,1,1};
	static int[] dc = {0,0,-1,1,-1,1,-1,1};
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		
		//T만큼 반복
		for(int tc = 1; tc <= T; tc++) {
			//배열 크기 N 받기
			int N = sc.nextInt();
			//N+2크기의 배열 생성
			int[][] arr = new int[N+2][N+2];
			
			for(int i = 0; i < arr.length; i++) {
				for(int j = 0; j < arr.length; j++) {
					//가장 큰  벽 세우기
					if(i == 0 || j == 0 || i > N || j > N)
						arr[i][j] = Integer.MAX_VALUE;
					else
						//1~N-1까지 입력값으로 배열 입력
						arr[i][j] = sc.nextInt();
				}
			}
			
			//웅덩이 개수
			int count = 0;
			//1~N까지 순회 팔방탐색
			for(int i = 1; i < N+1; i++) {
				for(int j = 1; j < N+1; j++) {
					//웅덩이 맞나요?
					boolean isTrue = true;
					//기준 값
					int num = arr[i][j];
					//팔방탐색 ㄱㄱ 
					for(int k = 0; k < 8; k++) {
						int nr = i + dr[k];
						int nc = j + dc[k];
						
						//벽인지 & 웅덩이 조건에 맞는지
						if(num >= arr[nr][nc]) {
							isTrue = false;
							break;
						}
					}//팔방 끝
					//웅덩이 찾았으면 개수 세기
					if(isTrue) {
						count++;
					}
					
				}
			}//순회 끝
			System.out.println("#" + tc + " " + count);
			
			
			
		}

	}

}
