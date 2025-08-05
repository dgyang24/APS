package SWEA.Sum1209;

import java.util.Scanner;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		
		for(int test_case = 1; test_case <= 10; test_case++)
		{
			int T = sc.nextInt();
			int[][] arr = new int[100][100];
			int max = 0;
			//1. 배열 생성 & 2. 각 행의 합
			for(int i = 0; i < arr.length; i++) {
				int sum = 0;
				for(int j = 0; j < arr[i].length; j++) {
					int num = sc.nextInt();
					arr[i][j] = num;
					sum += arr[i][j];
				}
				if(sum > max)
					max = sum;
			}
			//3. 각 열의 합
			for(int j = 0; j <arr[0].length; j++) {
				int sum = 0;
				for(int i = 0; i < arr.length; i++) {
					sum += arr[i][j];
					
				}
				if(sum > max)
					max = sum;
			}
			int crossSum = 0;
			//4. 우하향 대각선
			for(int i = 0; i < arr.length; i++) {
				crossSum+=arr[i][i];
			}
			if(crossSum > max)
				max = crossSum;
			//5. 우상향 대각선
			int crossUpSum = 0;
			for(int i = 0; i < arr.length; i++) {
				crossUpSum += arr[arr.length-1-i][i];
			}
			if(crossUpSum >max)
				max = crossUpSum;
			
			System.out.println("#" + T + " " + max);
			
		}
	}
}
