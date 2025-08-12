package 자기소계;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Algo1_서울_13_양동근 {
	static StringBuilder ans;
	
	public static void main(String[] args) throws FileNotFoundException{
//		File file = new File("./src/문제1번/input.txt");
//		Scanner sc = new Scanner(file);
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		
		// 65 - A + 32
		// 97 - a 
		int A = 65;
		int a = 97;
		char myAnswer = (char) (A + 32); // a
		
		for (int tc=1; tc<=T; tc++) {
			ans = new StringBuilder();
			
			// 문자 입력받기 (String) + 대문자화
			String s = sc.next().toUpperCase();
			
			// charAt
			// n <- .length() 길이를
			int n = s.length();
			
			// 투포인터 사용
			int i = 0;
			
			ans = new StringBuilder();
			// 문자의 길이만큼 반복
			// while ( i < n)
			// AAAABBCC
			
			while(i<n) {
				// 기준이될 알파벳
				char c = s.charAt(i);
				
				int j = i;
				// 일단 나의 기준은 i 그래서 j를 i부터 탐색 시작
				// j = i 
				// j ~ 다른 알파벳 나올때까지 반복
				// j ~ n의 길이까지 charAt(j)== charAt(i)
				// while(j<n && charAt(j)==charAt(i)) 
				// j를 하나씩 올려주기
				
				while(j<n && s.charAt(j)==c) {
					j++;
				}
				
				// ans 한 알파벳을 몇번 카운팅 했는지 적립
				// .append 활용해서 
				// 알파벳 써주고
				// 그 알파벳 몇번 나왔는지 적립 (j-i)
				ans.append(c)
					.append(j-i);

				// 01234567    
				// AAAAAAABCC
			   // i =0 
			   // i = j // 로 바꿔주면 됨
				i = j;
				
			}
			System.out.println("#" + tc + " " + ans);
		}
		sc.close();
	}
	

}
