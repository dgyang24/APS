package SWEA.의석이5356;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		File file = new File("./src/SWEA/의석이5356/sample_input.txt");
		Scanner sc = new Scanner(file);
		int T;
		T=sc.nextInt();
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/

		for(int test_case = 1; test_case <= T; test_case++)
		{	
			int row = 5;
			int col = 15;
			char[][] arr = new char[5][15];
			int maxCol = 0;
			//1. row = 5, col = 15인 배열 생성
			for(int i = 0; i < arr.length; i++) {
				String colString = sc.next();
				//1-1. col은 각 길이에 맞게 삽입
				for(int j = 0; j < colString.length(); j++) {
					arr[i][j] = colString.charAt(j);
				}
				//1-2. col 길이의 최대값 구분해놓기
				if(colString.length() > maxCol)
					maxCol = colString.length();
			}
			
			String result = "";
			//2. 제일 긴 열만큼 순회 -> 스트링 합치기
			for(int j = 0; j < maxCol; j++) {
				for(int i = 0; i < arr.length; i++) {
					//2-1. 공백을 마주치면 continue
					//원시타입 char는 아스키코드를 이용하기 때문에 null체크는 == 0을 이용
					if(arr[i][j] == 0) {
						continue;
					}
					result += arr[i][j];
				}
			}
			System.out.println("#" + test_case  + " " + result);
			

		}
	}
}
