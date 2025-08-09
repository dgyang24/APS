package SWEA.ladder1_1210;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("./src/SWEA/ladder1_1210/input.txt");
		Scanner sc = new Scanner(file);
		int row = 0;
		int col = 0;
		for(int test_case = 1; test_case <= 10; test_case++)
		{
			int T = sc.nextInt();
			int[][] arr = new int[100][102];
			//배열 생성
			for(int i = 0; i < 100; i++) {
				for(int j = 0; j<102; j++) {
					if(j == 0 || j ==101)
						arr[i][j] = 0;
					else {
						int num = sc.nextInt();
						arr[i][j] = num;
						//도착지 기록
						if(num == 2) {
							row = i;
							col = j;
						}
					}
				}
			}
			///////////////////////////////
			//사다리타기 시작~
			//전제 조건. row가 0이 아닐동안 무한 반복
			while(row > 0) {
				//규칙 1. 좌우 살피고 1인 방향이 있으면 해당 방향으로 계속 직진
				//(가로선이 다른 막대를 가로지르는 경우는 x)
				for(int i = 2; i < 4; i++) {
					boolean move = false;
					int c = col + dc[i];
					while(arr[row][c] == 1) {
						move = true;
						col = c;
						c = col + dc[i];
					}
					if(move == true)break;//이미 움직인 경우 반대방향 확인하지 않고 상향 움직임 준비
				}//좌우 이동
				
				//규칙 2. 좌우 방향이 없고, 위로  위로 한칸 직진
				int r = row + dr[0];
				if(arr[r][col] == 1) {
					row = r;
				}//상향이동
			}//사다리 타기
			int result = col-1;//끝에 열 하나를 추가 했기 때문에 -1
			System.out.println("#" + T + " " + result);
			
		}
	}

}
