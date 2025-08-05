package SWEA.Flatten1208;

import java.io.File;
import java.util.Scanner;

class Solution
{
	public static void main(String args[]) throws Exception
	{	
		File file = new File("./src/SWEA/Flatten1208/input.txt");
//		Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		for(int test_case = 1; test_case <= 10; test_case++)
		{	
			int N = sc.nextInt();
			//배열 길이 100인 배열 생성
			int[] arr = new int[100];
			for(int i = 0; i < 100; i++) {
				arr[i] = sc.nextInt();
			}
			int max = 0;
	 		int min = 101;
			//덤프개수를 입력받아 N번 반복
 			for(int i = 0; i < N; i++) {
 				
 				//배열순회 최대최소값 찾기
 				for(int j = 0; j < arr.length; j++) {
 					if(arr[j] > max) 
 						max = arr[j];
 					if(arr[j] < min)
 						min = arr[j];
 				}
 				int maxCnt = 0;
				int minCnt = 0;
 	 			//각 최대 최솟값에 +1,-1, 각 행동을 단 한번만!!!
 				for(int j = 0; j < arr.length; j++) {
 					if(arr[j] == max && maxCnt == 0) {
 						arr[j] -= 1;
 						max -= 1;
 						maxCnt++;
 					}
 					if(arr[j] == min && minCnt == 0) {
 						arr[j] += 1;
 						min += 1;
 						minCnt++;
 					}
 				}
 			}
 			int resultMax = 0;
	 		int resultMin = 101;
 			//덤프 작업이 끝나고 다시 max값과 min값을 뽑아내기!
 			//동일한 max/min값이 있을 수 있으므로!
 			for(int j = 0; j < arr.length; j++) {
				if(arr[j] > resultMax) 
					resultMax = arr[j];
				if(arr[j] < resultMin)
					resultMin = arr[j];
			}
 			int result = resultMax - resultMin;
 			System.out.println("#" + test_case + " " + result);
			
		}
	}
}
