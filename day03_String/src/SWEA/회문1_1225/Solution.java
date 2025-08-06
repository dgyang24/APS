package SWEA.회문1_1225;

import java.io.File;
import java.util.Scanner;

class Solution
{
	public static int sum = 0;
	public static void main(String args[]) throws Exception
	{
		File file = new File("./src/SWEA/회문1_1225/input.txt");
		Scanner sc = new Scanner(file);
		
		for(int test_case = 1; test_case <= 10; test_case++)
		{
			sum = 0;
			//회문 길이 입력
			int N = sc.nextInt();
			
			//8x8배열 생성
			char[][] arr = new char[8][8];
			for(int i = 0; i < 8; i++) {
				String str = sc.next();
				for(int j = 0; j < 8; j++) {
					arr[i][j] = str.charAt(j);
				}
			}
			
			
			//N개짜리 회문찾기 시작!
			//행기준(가로)
			for(int i = 0; i < arr.length; i++) {
				for(int j = 0; j < arr[i].length-N+1; j++) {
					String str = "";
					for(int k = 0; k < N; k++)
						str += arr[i][j+k];
					isPalindrome(str);
				}
				
			}
			//열기준(세로)
			//어차피 8X8으로 행과 열의 개수가 같으니까
			for(int j = 0; j < arr[0].length; j++) {
				for(int i = 0; i < arr.length-N+1; i++) {
					String str = "";
					for(int k = 0; k < N; k++)
						str += arr[i+k][j];
					isPalindrome(str);
				}
			}
			
			System.out.println("#" + test_case + " " + sum);
		}
	}
	
	
	
	//회문인지 파악하고 맞으면 sum++해주는 함수
	public static void isPalindrome(String str) {
		String tmp = "";
		for(int i = 0; i < str.length(); i++) {
			tmp += str.charAt(str.length()-1-i);
		}
		if(tmp.equals(str))
			sum++;
	}
}