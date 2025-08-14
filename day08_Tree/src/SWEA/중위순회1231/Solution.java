package SWEA.중위순회1231;

import java.util.Scanner;

public class Solution {
	static String[] arr;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int tc = 0; tc < 10; tc++) {
			//정점 개수
			int N = sc.nextInt();
			arr = new String[N+1];
			sc.nextLine();//공백제거
			//트리 생성
			int[][] tree = new int[N+1][4]; //정점 값, 왼자, 오자, 부모
			//입력값을 트리에 넣기
			for(int i=1; i < N+1; i++) {
				//입력값 관리 배열
				String[] str = sc.nextLine().split(" ");
				//정점 위치
				int n = Integer.parseInt(str[0]);
				//위치에 해당하는 값 할당
				arr[n] = str[1];
				
				//자식이 있는지 확인
				if(str.length > 2) {
					//자식1
					int c1 = Integer.parseInt(str[2]);
					
					
				}
			}
			
			
		}
		

	}

}
