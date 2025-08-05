package SWEA.ladder1_1210;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("./src/SWEA/ladder1_1210/input.txt");
		Scanner sc = new Scanner(file);
		int row = 0;
		int col = 0;
		for(int test_case = 1; test_case <= 1; test_case++)
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
			int result = col-1;
			System.out.println("#" + T + " " + result);
			
		}
		
		
	}

}
