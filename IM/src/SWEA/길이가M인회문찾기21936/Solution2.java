package SWEA.길이가M인회문찾기21936;

import java.util.Scanner;

public class Solution2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// tc개수 T
		int T = sc.nextInt();
		for(int tc = 1; tc<=T; tc++) {
			//문자열 길이 N
			int N = sc.nextInt();
			//회문 길이 M
			int M = sc.nextInt();
			//String 입력
			String str = sc.next();
			//회문 담을 sb
			StringBuilder sb = new StringBuilder();
			//str-M+1까지 순회하면서 
			for(int i = 0; i <= N-M; i++) {
				//회문인지 쳌
				boolean isTrue = true;
				for(int j = 0; j < M/2; j++) {
					//각 요소에 대해 회문검사하고, 아니면 break;
					if(str.charAt(j+i)!=str.charAt(M+i-1-j)) {
						isTrue = false;
						break;
					}
				}
				//회문이면 해당 인덱스 값부터 N개까지 회문 sb에 넣기
				if(isTrue) {
					for(int k = i; k < i+M; k++)
						sb.append(str.charAt(k));
				}
			}
			if(sb.length() == 0)
				sb.append("NONE");
			
			//출력
			System.out.println("#"+tc+" "+sb);
		}
	}

}
